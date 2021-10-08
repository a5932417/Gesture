package s1071928.pu.edu.tw.gesture

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
//import kotlinx.android.synthetic.main.activity_gesture.*
import kotlinx.android.synthetic.main.activity_gesture_new.*
import kotlinx.android.synthetic.main.activity_gesture_new.viewFinder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.txv
import org.tensorflow.lite.support.image.TensorImage
import s1071928.pu.edu.tw.gesture.ml.Gestrue
import s1071928.pu.edu.tw.gesture.ml.Gesture01
import s1071928.pu.edu.tw.gesture.ml.ModelFp16
import s1071928.pu.edu.tw.gesture.ml.ModelFp16v2

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
// Listener for the result of the ImageAnalyzer
typealias ImageProxyListener = (bmp: Bitmap) -> Unit

class GestureNew : AppCompatActivity(), PermissionListener {

    private lateinit var cameraExecutor: ExecutorService
    val backG: IntArray = intArrayOf(
        R.drawable.bg01, R.drawable.bg02,
        R.drawable.bg03, R.drawable.bg04, R.drawable.bg05, R.drawable.bg06,
        R.drawable.bg07, R.drawable.bg08, R.drawable.bg09, R.drawable.bg10,
        R.drawable.bg11, R.drawable.bg12, R.drawable.bg13, R.drawable.bg14,
        R.drawable.bg15, R.drawable.bg16
    )
    val gestureString = arrayOf("fight", "gh", "good","hi","idk","no","qk","uncomfortable","wait","sad")

    private class ImageAnalyzer(ctx: Context, private val listener: ImageProxyListener) :
        ImageAnalysis.Analyzer {
        /**
         * Convert Image Proxy to Bitmap
         */
        private val yuvToRgbConverter = YuvToRgbConverter(ctx)
        private lateinit var bitmapBuffer: Bitmap
        private lateinit var rotationMatrix: Matrix

        override fun analyze(imageProxy: ImageProxy) {
            // Convert Image to Bitmap
            val bmp:Bitmap? = toBitmap(imageProxy)

            if (bmp != null) {
                listener(bmp)
            }
            // Close the image,this tells CameraX to feed the next image to the analyzer
            imageProxy.close()
        }

        @SuppressLint("UnsafeExperimentalUsageError")
        private fun toBitmap(imageProxy: ImageProxy): Bitmap? {
            val image = imageProxy.image ?: return null
            // Initialise Buffer
            if (!::bitmapBuffer.isInitialized) {
                // The image rotation and RGB image buffer are initialized only once
                rotationMatrix = Matrix()
                rotationMatrix.postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
                bitmapBuffer = Bitmap.createBitmap(
                    imageProxy.width, imageProxy.height, Bitmap.Config.ARGB_8888
                )
            }

            // Pass image to an image analyser
            yuvToRgbConverter.yuvToRgb(image, bitmapBuffer)

            // Create the Bitmap in the correct orientation
            return Bitmap.createBitmap(bitmapBuffer, 0, 0,
                bitmapBuffer.width, bitmapBuffer.height, rotationMatrix, false
            )
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture_new)

        var intent = intent
        var random =intent.getIntExtra("video",0)

        gestureNext.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent.setClass(this@GestureNew, Zoo::class.java)
                startActivity(intent)
            }
        })
        // Request camera permissions
        Dexter.withContext(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(this)
            .check()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        Toast.makeText(this, "您已允許拍照權限", Toast.LENGTH_SHORT).show()
        startCamera()
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        if (p0!!.isPermanentlyDenied) {
            Toast.makeText(this, "您永久拒絕拍照權限", Toast.LENGTH_SHORT).show()
            var it: Intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            var uri: Uri = Uri.fromParts("package", getPackageName(), null)
            it.setData(uri)
            startActivity(it)
        }
        else{
            Toast.makeText(this, "您拒絕拍照權限，無法使用本App",
                Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        p1?.continuePermissionRequest()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        var intent = intent
        var random =intent.getIntExtra("video",0)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.createSurfaceProvider())
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            //val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA  //自拍

            // Set up the image analysis use case which will process frames in real time
            val imageAnalyzer = ImageAnalysis.Builder()
                .setTargetResolution(Size(224, 224))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            /*
            imageAnalyzer.setAnalyzer(cameraExecutor,  { image ->
                txv.text = image.imageInfo.rotationDegrees.toString()
            })
             */

            //分析... 藉由ImageAnalyzer，將ImageProxy轉為Bitmap
            imageAnalyzer.setAnalyzer(cameraExecutor, ImageAnalyzer(this) { bitmap ->
                //val model = Gesture01.newInstance(this)
                val model = ModelFp16.newInstance(this)

// Creates inputs for reference.
                val image = TensorImage.fromBitmap(bitmap)

                // Runs model inference and gets result.
                //val outputs = model.process(image)
                //val probability = outputs.probabilityAsCategoryList
                //txv.text = probability.toString()

                val outputs = model.process(image)
                    .probabilityAsCategoryList.apply {
                        sortByDescending { it.score } // 排序，高匹配率優先
                    }.take(2)
                var Result:String = ""
                for (output in outputs) {
                    when (output.label) {
                        "fight"-> Result += "加油"
                        "gh"-> Result += "回家"
                        "good"-> Result += "好棒"
                        "hi"-> Result += "你好"
                        "idk"-> Result += "不知道"
                        "no"-> Result += "不要"
                        "qk"-> Result += "休息"
                        "uncomfortable"-> Result += "不舒服"
                        "wait"-> Result += "排隊"
                        "sad"-> Result += "難過"

                    }
                    Log.e("printRE",Result)
                    Log.e("printOP",output.label)
                    Log.e("printGE",gestureString[random])
                    if(output.label == gestureString[random]&&output.score * 100.0f > 60){

                        intent.setClass(this@GestureNew, Zoo::class.java)
                        Looper.prepare()
                        Toast.makeText(this, "辨識為$Result" + String.format("%.1f%%", output.score * 100.0f), Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        Looper.loop()

                    }
                    Result += ": " + String.format("%.1f%%", output.score * 100.0f) + ";  "
                }
                txv.text = Result


                // Releases model resources if no longer used.
                model.close()
            })

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer)

            } catch(exc: Exception) {
                Toast.makeText(this, "Use case binding failed: ${exc.message}",
                    Toast.LENGTH_SHORT).show()
            }

        }, ContextCompat.getMainExecutor(this))
    }

}