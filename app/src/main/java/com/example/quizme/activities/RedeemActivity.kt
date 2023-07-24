package com.example.quizme.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.quizme.R
import com.example.quizme.models.UserScore
import com.example.quizme.utils.PointsManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class RedeemActivity : AppCompatActivity() {
    private lateinit var points:TextView
    private lateinit var totalPoints:TextView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var redeem:Button
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redeem)

        points=findViewById(R.id.textView14)
        totalPoints = findViewById(R.id.textView17)
        redeem=findViewById(R.id.button7)




        firestore=FirebaseFirestore.getInstance()
        val uid= FirebaseAuth.getInstance().currentUser?.uid

        firestore= FirebaseFirestore.getInstance()
        if (uid != null) {
            firestore.collection("username").document(uid).get()
                .addOnSuccessListener {document->
                    val userName = document.get("name").toString()
                    findViewById<TextView>(R.id.textView15).text= "Hello, $userName"
                }
                .addOnFailureListener {

                    Toast.makeText(this, "Error in showing Name", Toast.LENGTH_SHORT).show()
                }
        }




//        val randomNumber = Random.nextInt(10, 51)
//        Points+=randomNumber


        // Storing the points
        val randomNumber = Random.nextInt(10, 51)
        PointsManager.Points+=randomNumber

        var sharedPref = getSharedPreferences("MyPoints", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("Points", PointsManager.Points)
        editor.apply()

        // Retrieving the points
        sharedPref = getSharedPreferences("MyPoints", Context.MODE_PRIVATE)
        PointsManager.Points = sharedPref.getInt("Points", 0) // 0 is the default value if no points are found in the shared preferences




        val userId=FirebaseAuth.getInstance().currentUser?.uid
        val email = FirebaseAuth.getInstance().currentUser?.email

        val data = email?.let { UserScore(it, PointsManager.Points) }



        firestore=FirebaseFirestore.getInstance()
        if (userId != null) {
            if (data != null) {
                firestore.collection("userscore").document(userId).set(data)
                    .addOnSuccessListener {

                        Toast.makeText(this,"Points added", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this,"problem in adding", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        if (userId != null) {
            firestore.collection("userscore").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val totalPoints = document.getLong("points")
                        Log.d("Points", "Retrieved Points: $totalPoints") // Debugging statement
                        PointsManager.Points = totalPoints?.toInt() ?: 0
                        Log.d("Points", "Updated Points: ${PointsManager.Points}") // Debugging statement

                    }
                    totalPoints.text="Your total points : ${PointsManager.Points}"

                }
                .addOnFailureListener {
                    Toast.makeText(this,"Fail to fetch Data",Toast.LENGTH_SHORT).show()
                }
        }

        redeem.setOnClickListener {
            val intent = Intent(this,ShopActivity::class.java)
            startActivity(intent)
            finish()
        }




        points.text = "Congratulations! You got $randomNumber points"

    }
}