package com.example.quizme.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizme.R
import com.example.quizme.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var confirmPassword:String
    lateinit var signUp:Button
    lateinit var editName:EditText
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
//        setName()

        editName=findViewById(R.id.name)
        signUp=findViewById(R.id.button3)



        firebaseAuth= FirebaseAuth.getInstance()

        signUp.setOnClickListener{



            var name = editName.text.toString()


            if (name.isBlank()){
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else{

                signUpUser(name)
            }

        }

        findViewById<TextView>(R.id.textView8).setOnClickListener{
            val intent=Intent(this, LoginActivity::class.java)

            startActivity(intent)
            finish()
        }

    }
//    private fun setName() {
////        firestore= FirebaseFirestore.getInstance()
////        firestore.collection("user").get()
////            .addOnCompleteListener {
////                val result:StringBuffer=StringBuffer()
////                if (it.isSuccessful){
////                    for (document in it.result!!){
////                        val documentId = document.id
////                        result.append(document.data.getValue(documentId))
////                    }
////                }
////            }
//    }


    private fun signUpUser(name:String){
       email = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
       password =  findViewById<EditText>(R.id.editTextTextPassword).text.toString()
       confirmPassword =  findViewById<EditText>(R.id.editTextTextPassword2).text.toString()


        if (email.isBlank()||password.isBlank()||confirmPassword.isBlank()){
            Toast.makeText(this,"Email and Password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }
        if(password!=confirmPassword){
            Toast.makeText(this,"Password should be match with Confirm Password",Toast.LENGTH_SHORT).show()
            return
        }

       firebaseAuth.createUserWithEmailAndPassword(email,password)
           .addOnCompleteListener(this){
               if (it.isSuccessful){
                   //code
                   Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                   val intent = Intent(this, LoginActivity::class.java)

                   val data = UserModel(name)
                   val uid = FirebaseAuth.getInstance().currentUser?.uid
                   firestore=FirebaseFirestore.getInstance()
                   if (uid != null) {
                       firestore.collection("username").document(uid).set(data)
                           .addOnSuccessListener {
                               Toast.makeText(this,"Name added",Toast.LENGTH_SHORT).show()
                           }
                           .addOnFailureListener {
                               Toast.makeText(this,"Problem in adding name",Toast.LENGTH_SHORT).show()
                           }
                   }


                   startActivity(intent)
                   finish()
               }
               else{
                   //code
                   Toast.makeText(this,"Invalid Email and Password",Toast.LENGTH_SHORT).show()
               }
           }



    }
}