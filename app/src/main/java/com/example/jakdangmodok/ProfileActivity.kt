package com.example.jakdangmodok

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.jakdangmodok.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        buttonClick()
    }

    private fun buttonClick() {
        binding.buttonProfileEdit.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSettingNotification.setOnClickListener {
            val intent = Intent(this, ProfileNotificationActivity::class.java)
            startActivity(intent)
        }

        binding.buttonApplyGroup.setOnClickListener {
            val intent = Intent(this, ApplyGroupActivity::class.java)
            startActivity(intent)
        }

        binding.buttonMyGroup.setOnClickListener {
            val intent = Intent(this, MyGroupActivity::class.java)
            startActivity(intent)
        }

        binding.buttonCompleteList.setOnClickListener {
            val intent = Intent(this, CompleteGroupActivity::class.java)
            startActivity(intent)
        }

    }

    // 뒤로가기 버튼
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)

        }

    }

}