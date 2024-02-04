package com.example.jakdangmodok

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.jakdangmodok.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 일정 시간 지연 이후 실행하기 위한 코드
        Handler(Looper.getMainLooper()).postDelayed({

            // 일정 시간이 지나면 MainActivity로 이동
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)

            //이전 키를 눌렀을 때 스플래시 스크린 화면으로 이동을 방지하기 위해
            //이동한 다음 사용안함으로 finish 처리
            finish()

        }, 1000) // 시간 1초 이후 실행
    }

}