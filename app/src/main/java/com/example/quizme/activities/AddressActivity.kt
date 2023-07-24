package com.example.quizme.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizme.R
import com.example.quizme.models.UserAddress
import com.example.quizme.models.UserScore
import com.example.quizme.utils.PointsManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddressActivity : AppCompatActivity() {



    private lateinit var confirm:Button
    private lateinit var itemName:TextView
    private lateinit var itemPrice:TextView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)


        confirm=findViewById(R.id.button9)
        itemName=findViewById(R.id.itemName)
        itemPrice=findViewById(R.id.itemPrice)

        val item = intent.getStringExtra("ITEM")
        when(item){
            "book"->{
                itemName.text="C Programming Book"
                itemPrice.text="1000 points"
            }
            "pen"->{
                itemName.text="Ball Point Pen"
                itemPrice.text="500 points"
            }
            "dairy"->{
                itemName.text="Notes Book(5)"
                itemPrice.text="800 points"
            }
            "box"->{
                itemName.text="Geometry Box"
                itemPrice.text="1100 points"
            }
            "bag"->{
                itemName.text="Bag pack"
                itemPrice.text="3000 points"
            }
            "book_2"->{
                itemName.text="ML Book(Python)"
                itemPrice.text="2000 points"
            }


        }



        confirm.setOnClickListener {

                val name=findViewById<EditText>(R.id.edit_name).text.toString()
                val address=findViewById<EditText>(R.id.edit_address).text.toString()
                val state=findViewById<EditText>(R.id.edit_state).text.toString()
                val pin=findViewById<EditText>(R.id.edit_pin).text.toString()
                val check=findViewById<CheckBox>(R.id.checkBox)

            if (name.isBlank()||address.isBlank()||state.isBlank()||pin.isBlank()){
                Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()
            }
            else if (!check.isChecked){
                Toast.makeText(this,"Please check the checkbox ", Toast.LENGTH_SHORT).show()
            }

            else {

                val uid = FirebaseAuth.getInstance().currentUser?.uid
                firestore = FirebaseFirestore.getInstance()
                val data = UserAddress(name,address,state,pin)
                if (uid != null) {
                    firestore.collection("useraddress").document(uid).set(data)
                        .addOnSuccessListener {
                            Toast.makeText(this, "User address details added", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error in storing address details", Toast.LENGTH_SHORT).show()
                        }
                }
                val intent = Intent(this, Confirmation::class.java)
                startActivity(intent)
                finish()

                when(item){
                    "book"->{
                        PointsManager.Points-=1000
                        storePoints(PointsManager.Points)
                    }
                    "pen"->{
                        PointsManager.Points-=500
                        storePoints(PointsManager.Points)
                    }
                    "dairy"->{
                        PointsManager.Points-=800
                        storePoints(PointsManager.Points)
                    }
                    "box"->{
                        PointsManager.Points-=1100
                        storePoints(PointsManager.Points)
                    }
                    "bag"->{
                        PointsManager.Points-=3000
                        storePoints(PointsManager.Points)
                    }
                    "book_2"->{
                        PointsManager.Points-=2000
                        storePoints(PointsManager.Points)
                    }
                }




                Toast.makeText(this, "Order confirmed", Toast.LENGTH_SHORT).show()

            }
        }







    }

    private fun storePoints(points: Int) {
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
    }


}