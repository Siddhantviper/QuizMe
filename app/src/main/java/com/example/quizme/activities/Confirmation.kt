package com.example.quizme.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.example.quizme.R
import com.example.quizme.utils.PointsManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Confirmation : AppCompatActivity() {
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)


        firestore=FirebaseFirestore.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid != null) {
            firestore.collection("userscore").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val totalPoints = document.getLong("points")
                        Log.d("Points", "Retrieved Points: $totalPoints") // Debugging statement
                        PointsManager.Points = totalPoints?.toInt() ?: 0
                        Log.d("Points", "Updated Points: ${PointsManager.Points}") // Debugging statement

                    }
                    findViewById<TextView>(R.id.finalPoints).text="${PointsManager.Points}"

                }
                .addOnFailureListener {
                    Toast.makeText(this,"Fail to fetch Data", Toast.LENGTH_SHORT).show()
                }
        }

        findViewById<Button>(R.id.backToShop).setOnClickListener {
            val intent = Intent(this,ShopActivity::class.java)
            startActivity(intent)
            finish()
            findViewById<DrawerLayout>(R.id.mainDrawer).closeDrawers()
        }
    }
}