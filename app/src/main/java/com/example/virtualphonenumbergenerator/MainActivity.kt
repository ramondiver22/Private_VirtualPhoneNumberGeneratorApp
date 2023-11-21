package com.example.virtualphonenumbergenerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RadioButton을 위한 RadioGroup 선언 및 초기화
        val radioGroup: RadioGroup = findViewById(R.id.radio_group)
        // 생성 버튼 선언 및 초기화
        val generateBtn: Button = findViewById(R.id.generate_btn)

        var country: Int = -1
        // 국가 목록 선택 이벤트
        radioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.korea_radioBtn -> country = 0
                R.id.usa_radioBtn -> country = 1
                R.id.canada_radioBtn -> country = 2
                R.id.france_radioBtn -> country = 3
            }
        }

        // 생성 버튼 클릭 이벤트
        generateBtn.setOnClickListener {
            // RadioButton을 누르지 않았을 시
            if (country == -1) {
                Toast.makeText(this, "국가를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 결과 액티비티로 이동
            val intent: Intent = Intent(this@MainActivity, ResultActivity::class.java)
            intent.putExtra("country", country)
            startActivity(intent)
        }
    }
}