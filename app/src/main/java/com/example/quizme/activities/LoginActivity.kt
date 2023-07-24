package com.example.quizme.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizme.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email:String
    private lateinit var password:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()

        val name = intent.getStringExtra("NAME")

        findViewById<Button>(R.id.button2).setOnClickListener{
            login()
        }

        findViewById<TextView>(R.id.textView5).setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun login(){
        email = findViewById<EditText>(R.id.editTextText).text.toString()
        password = findViewById<EditText>(R.id.editTextText2).text.toString()

        if (email.isBlank()||password.isBlank()){
            Toast.makeText(this,"Email and Password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if (it.isSuccessful){
                Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}