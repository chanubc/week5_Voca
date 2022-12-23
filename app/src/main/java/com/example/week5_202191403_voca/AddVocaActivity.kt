package com.example.week5_202191403_voca

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week5_202191403_voca.databinding.ActivityAddVocaBinding
import java.io.PrintStream

class AddVocaActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddVocaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVocaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.apply {
            button3.setOnClickListener {
                val word = word.text.toString()
                val meaning = meaning.text.toString()
                writeFile(word, meaning)

            }

            button4.setOnClickListener {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }

    private fun writeFile(word: String, meaning: String) {
        val output = PrintStream(openFileOutput("out.txt", Context.MODE_APPEND))
        output.println(word)
        output.println(meaning)
        output.close()
        val intent = Intent()
        intent.putExtra("voc",MyData(word,meaning))
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}