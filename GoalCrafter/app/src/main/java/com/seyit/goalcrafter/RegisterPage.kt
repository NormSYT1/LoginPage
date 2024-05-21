package com.seyit.goalcrafter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.seyit.goalcrafter.databinding.ActivityRegisterPageBinding

class RegisterPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPageBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var  googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            val email = binding.emailSignup.text.toString()//Emaili gir
            val password = binding.passwordSignup.text.toString()//Şifreyi gir









            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                    if(task.isSuccessful){//Girilen bilgiler doğru ise
                        Toast.makeText(this, "Başarıyla kayıt olundu", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterPage, LoginPage::class.java)
                        startActivity(intent)
                        finish()
                    }else{//Girilen bilgiler yanlış ise
                        Toast.makeText(this, "Kayıt olma işlemi başarısız", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Lütfen boş alan bırakmayınız", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this@RegisterPage, LoginPage::class.java))
            finish()
        }
    }
}