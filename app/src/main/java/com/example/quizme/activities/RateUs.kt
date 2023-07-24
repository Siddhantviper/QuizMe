package com.example.quizme.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.example.quizme.R
import com.example.quizme.models.UserRating
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RateUs : AppCompatActivity() {
    lateinit var ratingBar: RatingBar
    lateinit var submit:Button
    lateinit var noThanks:TextView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_us)
        ratingBar=findViewById(R.id.ratingBar)
        submit=findViewById(R.id.button5)
        noThanks=findViewById(R.id.textView12)

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)

        ratingBar.onRatingBarChangeListener = object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(ratingBar: RatingBar, rating: Float, fromUser: Boolean) {
                // Perform actions based on the rating change
                if (fromUser) {
                    // Rating changed by user interaction
                    // Do something with the new rating value
                    // For example, display a toast message
                    submit.setOnClickListener {
                        Toast.makeText(applicationContext, "Rating: $rating", Toast.LENGTH_SHORT).show()
                        val userRating= rating.toString()
                        val feedback=findViewById<EditText?>(R.id.editTextText3).text.toString()
                        val data = UserRating(userRating,feedback)
                        firestore=FirebaseFirestore.getInstance()
                        val uid = FirebaseAuth.getInstance().currentUser?.uid
                        if (uid != null) {
                            firestore.collection("userfeedback").document(uid).set(data)
                                .addOnSuccessListener {
                                    Toast.makeText(applicationContext, "Your feedback was successfully submitted", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(applicationContext,"Error in storing your feedback", Toast.LENGTH_SHORT).show()
                                }
                        }


                    }
                }
            }
        }
        noThanks.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }







    }
}