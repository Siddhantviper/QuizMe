package com.example.quizme.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.example.quizme.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        firestore=FirebaseFirestore.getInstance()
        val uid= FirebaseAuth.getInstance().currentUser?.uid

        firestore= FirebaseFirestore.getInstance()
        if (uid != null) {
            firestore.collection("username").document(uid).get()
                .addOnSuccessListener {document->
                    val userName = document.get("name").toString()
                    findViewById<TextView>(R.id.textView29).text=userName
                }
                .addOnFailureListener {

                    Toast.makeText(this, "Error in showing Name", Toast.LENGTH_SHORT).show()
                }
        }


        firebaseAuth = FirebaseAuth.getInstance()
        findViewById<TextView>(R.id.txtEmail).text = firebaseAuth.currentUser?.email

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginIntro::class.java)
            startActivity(intent)

            finishAffinity()

        }
    }

}