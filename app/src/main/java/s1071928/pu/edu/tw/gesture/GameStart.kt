package s1071928.pu.edu.tw.gesture

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_game_start.*
import kotlinx.android.synthetic.main.activity_main.*

class GameStart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_start)
        img_btn01.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                Log.e("print",intent.toString())
                Log.e("print",random.toString())
                intent.putExtra("animal",0)
                startActivity(intent)
            }
        })
        img_btn02.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)
                val random = (0..9).random()
                intent.putExtra("video",random)

                intent.putExtra("animal",1)
                startActivity(intent)
            }
        })
        img_btn03.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",2)
                startActivity(intent)
            }
        })
        img_btn04.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",3)
                startActivity(intent)
            }
        })
        img_btn11.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",4)
                startActivity(intent)
            }
        })
        img_btn12.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",5)
                startActivity(intent)
            }
        })
        img_btn13.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",6)
                startActivity(intent)
            }
        })
        img_btn14.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",7)
                startActivity(intent)
            }
        })
        img_btn21.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",8)
                startActivity(intent)
            }
        })
        img_btn22.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",9)
                startActivity(intent)
            }
        })
        img_btn23.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",10)
                startActivity(intent)
            }
        })
        img_btn24.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",11)
                startActivity(intent)
            }
        })
        img_btn31.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",12)
                startActivity(intent)
            }
        })
        img_btn32.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",13)
                startActivity(intent)
            }
        })
        img_btn33.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",14)
                startActivity(intent)
            }
        })
        img_btn34.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                intent = Intent(this@GameStart, Situation::class.java)

                val random = (0..9).random()
                intent.putExtra("video",random)
                intent.putExtra("animal",15)
                startActivity(intent)
            }
        })
        var txv2: TextView = findViewById(R.id.txv2)
        txv2.text = "動物樂園"
        txv2.setTypeface(
            Typeface.createFromAsset(assets,
            "font/HanyiSenty Candy-color-mono.ttf"))
    }
}