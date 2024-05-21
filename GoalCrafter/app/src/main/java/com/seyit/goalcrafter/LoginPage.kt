package com.seyit.goalcrafter




import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.seyit.goalcrafter.databinding.ActivityLoginPageBinding

class LoginPage : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleGiris.setOnClickListener {
            signInWithGoogle()
        }


        binding.loginButton.setOnClickListener {
            var email = binding.emailLogin.text.toString()
            var password = binding.passwordLogin.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){task ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "Başarıyla giriş yapıldı", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginPage, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, "Giriş Başarısız", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Lütfen boş alan bırakmayın", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signupButton.setOnClickListener {//Kayıt ol ekranına yönlendir
            startActivity(Intent(this@LoginPage, RegisterPage::class.java))
            finish()
        }
    }

    private fun signInWithGoogle() {// Google ile giriiş yap fonksiyonu
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }









    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Google Sign-In sonucunu işle
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google giriş işlemi başarılı ise Firebase'e kaydet
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!.idToken!!)
            } catch (e: ApiException) {// Google giriş işlemi başarılı değilse
                Toast.makeText(this, "Google girişi başarısız", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Google ile giriş yapıldı", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginPage, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Google ile giriş başarısız", Toast.LENGTH_SHORT).show()
                }
            }
    }
}