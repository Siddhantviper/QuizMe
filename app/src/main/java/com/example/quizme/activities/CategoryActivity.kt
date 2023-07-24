package com.example.quizme.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.quizme.R
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executor


class CategoryActivity : AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var choose_dsa:CardView
    private lateinit var choose_dbms:CardView
    private lateinit var choose_java:CardView
    private lateinit var choose_os:CardView
    private lateinit var startBtn:ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)





        choose_dsa = findViewById(R.id.imageView5)
        choose_dbms = findViewById(R.id.cardView)
        choose_java = findViewById(R.id.cardView2)
        choose_os = findViewById(R.id.cardView3)
        startBtn=findViewById(R.id.startBtn)


        choose_dsa.setOnClickListener {
           clickHandle(1)
        }
        choose_os.setOnClickListener {
           clickHandle(2)
        }
        choose_java.setOnClickListener {
           clickHandle(3)
        }
        choose_dbms.setOnClickListener {
           clickHandle(4)
        }

        Biometric()

    }

    private fun Biometric() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                    Biometric()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT)
                        .show()

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed",
                     Toast.LENGTH_SHORT)
                      .show()

                    Biometric()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()


        biometricPrompt.authenticate(promptInfo)
    }

    private fun clickHandle(i: Int) {
        when(i){
            1->{
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("CATEGORY","dsa")
                choose_dsa.setCardBackgroundColor(Color.parseColor("#18206F"))
                choose_dbms.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                choose_os.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                choose_java.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                startBtn.setOnClickListener {
                    startActivity(intent)
                }


            }
            2->{
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("CATEGORY","os")
                choose_dsa.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                choose_dbms.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                choose_os.setCardBackgroundColor(Color.parseColor("#18206F"))
                choose_java.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                startBtn.setOnClickListener {
                    startActivity(intent)
                }


            }
            3->{
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("CATEGORY","java")
                choose_dsa.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                choose_dbms.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                choose_os.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                choose_java.setCardBackgroundColor(Color.parseColor("#18206F"))
                startBtn.setOnClickListener {
                    startActivity(intent)
                }



            }
            4->{
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("CATEGORY","dbms")
                choose_dsa.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                choose_dbms.setCardBackgroundColor(Color.parseColor("#18206F"))
                choose_os.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                choose_java.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                startBtn.setOnClickListener {
                    startActivity(intent)
                }


            }
        }


    }
}







