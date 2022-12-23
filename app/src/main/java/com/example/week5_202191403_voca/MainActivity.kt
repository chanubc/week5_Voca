package com.example.week5_202191403_voca

import android.opengl.Visibility
import androidx.recyclerview.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.week5_202191403_voca.MyAdapter
import com.example.week5_202191403_voca.MyData
import com.example.week5_202191403_voca.R
import com.example.week5_202191403_voca.databinding.ActivityMainBinding
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    //    lateinit var recyclerView: RecyclerView
    var data: ArrayList<MyData> = ArrayList()
    lateinit var adapter: MyAdapter //lateinit = 초기값을 처음에 주지 않고 나중에 값을 주겠다. 미리예약
    lateinit var tts: TextToSpeech
    var ttsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initRecyclerView()
        initTTS()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (ttsReady) {
            tts.stop()
            tts.shutdown()
        }
    }

    fun readFileScan(scan: Scanner) {
        while (scan.hasNextLine()) {
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            data.add(MyData(word, meaning)) // mydata 생성
        }
    }

    private fun initTTS() {
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            ttsReady = true
            tts.language = Locale.US
        })
    }

    private fun initData() {
        try {
            val scan2 = Scanner(openFileInput("out.txt"))
            readFileScan(scan2)
        }catch (e:Exception){
            Toast.makeText(this,"추가된 단어 없음",Toast.LENGTH_SHORT).show()
        }



        val scan = Scanner(resources.openRawResource(R.raw.words))
        readFileScan(scan)

    }


    private fun initRecyclerView() {
//        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        binding.recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        adapter = MyAdapter(data)
        // adapter에 itemClickListener 달아줄수 있음
        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
            override fun OnItemClick(data: MyData) {
                if (ttsReady) {
                    tts.speak(data.word, TextToSpeech.QUEUE_ADD, null, null)
                    /*  //원하는 메시지를 화면에 원하는 시간 만큼 띄울 수 있음
                      Toast.makeText(applicationContext, data.meaning, Toast.LENGTH_SHORT).show()*/
                }
            }
        }
        binding.recyclerView.adapter = adapter
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                adapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.adapterPosition)
            }

        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    } //initRecyclerView

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return true
//        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuitem1 ->{
                recyclerView.layoutManager = LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL,false)
            }
            R.id.menuitem2 ->{
                recyclerView.layoutManager = GridLayoutManager(this,3)

            }
            R.id.menuitem3 ->{
                recyclerView.layoutManager = StaggeredGridLayoutManager(3,
                    StaggeredGridLayoutManager.VERTICAL)
            }
        }
        return super.onOptionsItemSelected(item)
    }*/
}
