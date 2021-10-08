package s1071928.pu.edu.tw.gesture

import android.content.Intent
import android.graphics.Typeface
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_situation.*
import kotlinx.android.synthetic.main.activity_watch.*

class Situation : AppCompatActivity() {
    private val situationVideos: IntArray = intArrayOf(R.raw.s_fight,R.raw.s_gh,R.raw.s_good,R.raw.s_hi,R.raw.s_idk,R.raw.s_no,R.raw.s_qk,R.raw.s_uncomfortable,R.raw.s_wait,R.raw.s_sad)
    val situationString = arrayOf("加油", "回家", "好棒","你好","不知道","不要","休息","不舒服","排隊","難過")
    val images: IntArray = intArrayOf(R.drawable.no, R.drawable.hi)
    val backG: IntArray = intArrayOf(R.drawable.bg01, R.drawable.bg02,
        R.drawable.bg03, R.drawable.bg04, R.drawable.bg05, R.drawable.bg06,
        R.drawable.bg07, R.drawable.bg08, R.drawable.bg09, R.drawable.bg10,
        R.drawable.bg11, R.drawable.bg12, R.drawable.bg13, R.drawable.bg14,
        R.drawable.bg15, R.drawable.bg16)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_situation)

        var intent = intent
        var random =intent.getIntExtra("video",0)
        var animal =intent.getIntExtra("animal",0)
        bg02.setBackgroundResource(backG[animal])
        var situationTextView: TextView = findViewById(R.id.situation_textView)
        situationTextView.text = situationString[random]
        //situationTextView.setBackgroundColor(android.graphics.Color.WHITE);
        /*situationTextView.typeface = Typeface.createFromAsset(assets,
            "font/HanyiSenty Candy-color-mono.ttf")*/
        fun setupVideoView() {
            var videoView = findViewById<VideoView>(R.id.situation_View)
            Log.e("print",random.toString())
            videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + situationVideos[random]))
            videoView.start()
            // hide medie controller
            videoView.setMediaController(null)
        }
        situation_View.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
            mp.isLooping = true
        })
        setupVideoView()
        btn_s_ok.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent.setClass(this@Situation, Watch::class.java)
                startActivity(intent)
            }
        })
        var txv: TextView = findViewById(R.id.situation_textView)
        txv.text = situationString[random]
        txv.setTypeface(Typeface.createFromAsset(assets,
            "font/HanyiSenty Candy-color-mono.ttf"))
    }
}