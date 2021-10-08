package s1071928.pu.edu.tw.gesture

import android.content.Intent
import android.graphics.Typeface
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game_start.*
//import kotlinx.android.synthetic.main.activity_gesture.*
import kotlinx.android.synthetic.main.activity_watch.*


class Watch : AppCompatActivity() {
    val videos: IntArray = intArrayOf(R.raw.fight,R.raw.gh,R.raw.good,R.raw.hi,R.raw.idk,R.raw.no,R.raw.qk,R.raw.uncomfortable,R.raw.wait,R.raw.sad)
    val watchString = arrayOf("加油", "回家", "好棒","你好","不知道","不要","休息","不舒服","排隊","難過")
    val backG: IntArray = intArrayOf(R.drawable.bg01, R.drawable.bg02,
        R.drawable.bg03, R.drawable.bg04, R.drawable.bg05, R.drawable.bg06,
        R.drawable.bg07, R.drawable.bg08, R.drawable.bg09, R.drawable.bg10,
        R.drawable.bg11, R.drawable.bg12, R.drawable.bg13, R.drawable.bg14,
        R.drawable.bg15, R.drawable.bg16)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch)

        var intent = intent
        var random =intent.getIntExtra("video",0)
        Log.e("print",intent.toString())
        var animal =intent.getIntExtra("animal",0)
        bg01.setBackgroundResource(backG[animal])
        var watchTextView: TextView = findViewById(R.id.watch_textView)
        watchTextView.text = watchString[random]
        fun setupVideoView() {
            var videoView = findViewById<VideoView>(R.id.video_View)
            Log.e("print",random.toString())
            videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + videos[random]))
            videoView.start()
            // hide medie controller
            videoView.setMediaController(null)
        }
        video_View.setOnPreparedListener(OnPreparedListener { mp -> mp.isLooping = true })
        setupVideoView()
        btnLearn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent.setClass(this@Watch, GestureNew::class.java)
                startActivity(intent)
            }
        })
        var txv: TextView = findViewById(R.id.watch_textView)
        txv.text = watchString[random]

        txv.setTypeface(
            Typeface.createFromAsset(assets,
            "font/HanyiSenty Candy-color-mono.ttf"))
    }
}