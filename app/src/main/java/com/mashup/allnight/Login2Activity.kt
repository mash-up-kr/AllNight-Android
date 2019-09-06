package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login2.*

class Login2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        val pref = this.getSharedPreferences("prefs",0)

        yes_button.setOnClickListener{
            val intent = Intent(this,DrinkKindActivity::class.java)
            intent.putExtra("bucket", false)
            startActivity(intent)
        }

        no_button.setOnClickListener {
            pref.edit().putBoolean("leadOff", false).commit()
            finish()
        }

        // 로그인액티비티도 그냥 시작하기 뉴르면 메인 띄우고 바로 피니시로 날려버려 메인에서 백키누르면 종료
    }
}
    