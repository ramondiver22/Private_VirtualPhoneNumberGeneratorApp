package com.example.virtualphonenumbergenerator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random
import kotlin.random.nextInt

@SuppressLint("SetTextI18n")
class ResultActivity : AppCompatActivity() {

    // 다른 함수들에서도 쓰기위해 이곳에서 선언
    // 사용할 때 초기화가 되게 하기 위해서 by lazy로 선언
    private val resultTitleText: TextView by lazy { findViewById(R.id.resultTitle_text) }
    private val virtualPhoneNumberText: TextView by lazy { findViewById(R.id.virtualPhoneNumber_text) }
    private val regenerateBtn: Button by lazy { findViewById(R.id.regenerate_btn) }
    private val backBtn: Button by lazy { findViewById(R.id.back_btn) }

    // 캐나다 지역번호 리스트
    // 미국 지역번호와 겹치지 않게 하기위해 만듬
    private val canadaRegionNumberList: List<Int> = listOf(
        403, 578, 780, 819, 902,
        519, 226, 905, 289, 514,
        438, 613, 343, 581, 418,
        306, 705, 249, 600, 506,
        709, 450, 579, 807, 647,
        416, 236, 778, 604, 250,
        204, 867,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // intent로 넘어온 국가 값 받기
        val country = intent.getIntExtra("country", -1)
        // 국가 값을 국가 이름으로 변환
        val countryName = when (country) {
            0 -> "대한민국"
            1 -> "미국"
            2 -> "캐나다"
            3 -> "프랑스"
            else -> ""
        }

        // 결과 타이틀 변경
        resultTitleText.text = "가상전화번호(${countryName})"
        // 가상전화번호 생성 함수
        generateVirtualPhoneNumber(country)

        // 재생성 버튼 클릭 이벤트
        regenerateBtn.setOnClickListener {
            generateVirtualPhoneNumber(country)
        }

        // 돌아가기 버튼 클릭 이벤트
        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun generateVirtualPhoneNumber(country: Int) {
        // 각 나라마다 전화번호 형식이 다르기 때문에 함수로 나눠서 진행
        when (country) {
            0 -> generateKoreaPhoneNumber()
            1 -> generateUSAPhoneNumber()
            2 -> generateCanadaPhoneNumber()
            3 -> generateFrancePhoneNumber()
        }
    }


    private fun generateKoreaPhoneNumber() {
        // 랜덤값을 받는 방법 1
        // List를 문자열로 변환
        val numberList = List<Int>(8) { Random.nextInt(1, 10)}.joinToString("")
        // 형식 : +82 010-XXXX-XXXX
        virtualPhoneNumberText.text = "+82 010-${numberList.substring(0..3)}-${numberList.substring(4..7)}"
    }

    private fun generateUSAPhoneNumber() {
        // 랜덤값을 받는 방법 2
        // List에서 값을 빼옴
        // 미국 지역번호는 100~199가 들어갈 수 없음
        val regionNumber = (200..999).filter { it !in canadaRegionNumberList }.shuffled().take(1)[0]
        // 첫번째 숫자열에는 100~199가 들어갈 수 없음
        val firstNumber = Random.nextInt(200, 1000)
        val secondNumber = Random.nextInt(1000, 10000)
        // 형식 : +1 (XXX) XXX-XXXX
        virtualPhoneNumberText.text = "+1 (${regionNumber}) ${firstNumber}-${secondNumber}"
    }

    private fun generateCanadaPhoneNumber() {
        val regionNumber = canadaRegionNumberList.shuffled().take(1)[0]
        val numberList = List<Int>(7) { Random.nextInt(1, 10)}.joinToString("")
        // 형식 : +1 XXX XXXXXXXX
        virtualPhoneNumberText.text = "+1 $regionNumber $numberList"
    }

    private fun generateFrancePhoneNumber() {
        val regionNumber = Random.nextInt(1, 10)
        val numberList = List<Int>(8) { Random.nextInt(1, 10)}.joinToString("")
        // 형식 : +33 0X XX XX XX XX
        virtualPhoneNumberText.text =
            "+33 0${regionNumber} ${numberList.substring(0..1)} ${numberList.substring(2..3)} ${numberList.substring(4..5)} ${numberList.substring(6..7)}"
    }
}