package com.example.quizme.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.quizme.R
import com.example.quizme.models.UserScore
import com.example.quizme.utils.PointsManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ShopActivity : AppCompatActivity() {

    lateinit var firestore: FirebaseFirestore
    private lateinit var textView: TextView
    private lateinit var book:CardView
    private lateinit var pen:CardView
    private lateinit var dairy:CardView
    private lateinit var box:CardView
    private lateinit var bag:CardView
    private lateinit var book_2:CardView
    private lateinit var buyNow:Button
    private var isZoomed= false


    //    private lateinit var box:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        textView=findViewById(R.id.textView23)
        book = findViewById(R.id.card1)
        pen = findViewById(R.id.card2)
        dairy = findViewById(R.id.card3)
        box = findViewById(R.id.card4)
        bag = findViewById(R.id.card5)
        book_2 = findViewById(R.id.card6)
        buyNow=findViewById(R.id.button6)

        book.setOnClickListener {
            clickHandle(1)
        }
        pen.setOnClickListener {
            clickHandle(2)
        }
        dairy.setOnClickListener {
            clickHandle(3)
        }
        box.setOnClickListener {
            clickHandle(4)
        }
        bag.setOnClickListener {
            clickHandle(5)
        }
        book_2.setOnClickListener {
            clickHandle(6)
        }










        val userId= FirebaseAuth.getInstance().currentUser?.uid
        firestore=FirebaseFirestore.getInstance()
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
                    textView.text="${PointsManager.Points}"

                }
                .addOnFailureListener {
                    Toast.makeText(this,"Fail to fetch Data", Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun clickHandle(i: Int) {



        when(i){
            1->{
                book.setBackgroundResource(R.drawable.shop_item_select_bg)
                pen.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                dairy.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                box.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                bag.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                book_2.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                buyNow.setOnClickListener {
//                    PointsManager.Points-=10
//                    storePoints(PointsManager.Points)
//                    textView.text="${PointsManager.Points}"

//                    if(PointsManager.Points < 1000){
//                        Toast.makeText(this,"You have not enough Points", Toast.LENGTH_SHORT).show()
//                    }
//                    else{
                        val intent= Intent(this, AddressActivity::class.java)
                        intent.putExtra("ITEM","book")
                        startActivity(intent)
                        finish()
//                    }
                }




            }
            2->{
                book.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                pen.setBackgroundResource(R.drawable.shop_item_select_bg)
                dairy.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                box.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                bag.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                book_2.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                buyNow.setOnClickListener {

//                    if(PointsManager.Points < 500){
//                        Toast.makeText(this,"You have not enough Points", Toast.LENGTH_SHORT).show()
//                    }
//                    else{
                        val intent= Intent(this, AddressActivity::class.java)
                        intent.putExtra("ITEM","pen")
                        startActivity(intent)
                        finish()
//                    }
                }
            }
            3->{
                book.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                pen.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                dairy.setBackgroundResource(R.drawable.shop_item_select_bg)
                box.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                bag.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                book_2.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                buyNow.setOnClickListener {
//                    if(PointsManager.Points < 800){
//                        Toast.makeText(this,"You have not enough Points", Toast.LENGTH_SHORT).show()
//                    }
//
//                    else{
                        val intent= Intent(this, AddressActivity::class.java)
                        intent.putExtra("ITEM","dairy")
                        startActivity(intent)
                        finish()
//                    }
                }
            }
            4->{
                book.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                pen.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                dairy.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                box.setBackgroundResource(R.drawable.shop_item_select_bg)
                bag.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                book_2.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                buyNow.setOnClickListener {

//                    if(PointsManager.Points < 1100){
//                        Toast.makeText(this,"You have not enough Points", Toast.LENGTH_SHORT).show()
//                    }
//                    else{
                        val intent= Intent(this, AddressActivity::class.java)
                        intent.putExtra("ITEM","box")
                        startActivity(intent)
                        finish()
//                    }
                }
            }
            5->{
                book.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                pen.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                dairy.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                box.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                bag.setBackgroundResource(R.drawable.shop_item_select_bg)
                book_2.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                buyNow.setOnClickListener {
//                    if(PointsManager.Points < 3000){
//                        Toast.makeText(this,"You have not enough Points", Toast.LENGTH_SHORT).show()
//                    }
//                    else{
                        val intent= Intent(this, AddressActivity::class.java)
                        intent.putExtra("ITEM","bag")
                        startActivity(intent)
                        finish()
//                    }
                }
            }
            6->{
                book.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                pen.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                dairy.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                box.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                bag.setBackgroundResource(R.drawable.shop_item_not_select_bg)
                book_2.setBackgroundResource(R.drawable.shop_item_select_bg)
                buyNow.setOnClickListener {
//                    if(PointsManager.Points < 2000){
//                        Toast.makeText(this,"You have not enough Points", Toast.LENGTH_SHORT).show()
//                    }
//                    else{
                        val intent= Intent(this, AddressActivity::class.java)
                        intent.putExtra("ITEM","book_2")
                        startActivity(intent)
                        finish()
//                    }
                }

            }


        }

    }



}