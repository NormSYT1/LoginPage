package com.seyit.goalcrafter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seyit.goalcrafter.LoginPage
import com.seyit.goalcrafter.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.quitButton.setOnClickListener {//Kayıt ol ekranına yönlendir
            intent = Intent(applicationContext, RegisterPage::class.java)
            startActivity(intent)
        }
    }
}