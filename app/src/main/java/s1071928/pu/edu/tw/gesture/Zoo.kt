package s1071928.pu.edu.tw.gesture

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_watch.*
import kotlinx.android.synthetic.main.activity_zoo.*
import java.util.*

class Zoo : AppCompatActivity() {
    val zooan: IntArray = intArrayOf(R.drawable.an_01, R.drawable.an_02,
        R.drawable.an_03, R.drawable.an_04, R.drawable.an_05, R.drawable.an_06,
        R.drawable.an_07, R.drawable.an_08, R.drawable.an_09, R.drawable.an_10,
        R.drawable.an_11, R.drawable.an_12, R.drawable.an_13, R.drawable.an_14,
        R.drawable.an_15, R.drawable.an_16)
    companion object {
        private const val DATA = "DATA"
        private const val mark = "0000000000000000"
        var chang ="0000000000000000"
    }
    var zooimg: Array<ImageView?> = arrayOfNulls(16)

    private lateinit var settings: SharedPreferences
    var s = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoo)

        zooimg = arrayOf(findViewById(R.id.imageView1),findViewById(R.id.imageView2),
            findViewById(R.id.imageView3),findViewById(R.id.imageView4),findViewById(R.id.imageView5),
            findViewById(R.id.imageView6),findViewById(R.id.imageView7),findViewById(R.id.imageView8),
            findViewById(R.id.imageView9),findViewById(R.id.imageView10),findViewById(R.id.imageView11),
            findViewById(R.id.imageView12),findViewById(R.id.imageView13),findViewById(R.id.imageView14),
            findViewById(R.id.imageView15),findViewById(R.id.imageView16))

        var intent = intent
        val name = intent.getIntExtra("animal",-1)
        saveData(name)
        zsetimg()


        zoo_btnBack.setOnClickListener {
            intent = Intent(this@Zoo, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        resetzoo.setOnClickListener{
            AlertDialog.Builder(this@Zoo)
                    .setTitle("確定重置嗎?")
                    .setPositiveButton("確定") { dialog, which ->
                        clearData()
                        intent = Intent(this@Zoo, Zoo::class.java)
                        startActivity(intent)
                    }
                .setNegativeButton("取消", null).create()
                    .show()

        }
        val txv3: TextView = findViewById(R.id.txv3)
        txv3.text = "收藏館"
        txv3.typeface = Typeface.createFromAsset(assets,
            "font/HanyiSenty Candy-color-mono.ttf")
    }
    fun zsetimg() {
        s = readData()
        if (s.substring(0,1) == "1") {
            zooimg[0]?.setImageResource(zooan[0])
        }
        if (s.substring(15) == "1") {
            zooimg[15]?.setImageResource(zooan[15])
        }

        for (i in 1..14) {
            if (s.substring(i,i+1) == "1") {
                zooimg[i]?.setImageResource(zooan[i])
            }
        }
        Log.e("markZ", s)
    }

    fun readData(): String {
        settings = getSharedPreferences(DATA, 0)
        settings.getString(mark, "0000000000000000")
        Log.e("markR", settings.getString(mark, "").toString())
        return settings.getString(mark, "0000000000000000").toString()
    }

    fun saveData(tet:Int) {
        if(tet == -1){
        }else {
            chang = if (tet == 0) {
                "1" + chang.substring(IntRange(1, 15))
            } else if (tet == 15) {
                chang.substring(IntRange(0, 14)) + "1"
            } else {
                chang.substring(IntRange(0, tet - 1)) + "1" + chang.substring(
                    IntRange(
                        tet + 1,
                        15
                    )
                )
            }
            Log.e("chang", chang)
            settings = getSharedPreferences(DATA, 0)
            settings.edit()
                .putString(mark,chang)
                .apply()

            Log.e("markS", mark)
        }
    }
    fun clearData(){
        settings = getSharedPreferences(DATA, 0)
        settings.edit().clear().apply()
    }
}