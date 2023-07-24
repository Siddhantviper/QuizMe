package com.example.quizme.activities




import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizme.R
import com.example.quizme.adapters.QuizAdapter
import com.example.quizme.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executor


class MainActivity : AppCompatActivity() {
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var btnDatePicker:FloatingActionButton
    private lateinit var quizRecyclerView: RecyclerView

    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    lateinit var firestore:FirebaseFirestore
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView


//    lateinit var menuItem: MenuItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView=findViewById(R.id.navigationView)




        setViews()








    }







//    val userId= FirebaseAuth.getInstance().currentUser?.uid


    private fun setViews() {
        val category=intent.getStringExtra("CATEGORY")

        setUpFireStore(category)
        setUpDrawerLayout()
        setUpRecyclerview(category)
        setUpDatePicker(category)
    }




    private fun setUpFireStore(category: String?) {


        var collectionPath="quizzes"
        when(category){
            "dsa"->{
                collectionPath="quizzes"
            }
            "os"->{
                collectionPath = "quizzes1"
            }
            "java"->{
                collectionPath="quizzes2"
            }
            "dbms"->{
                collectionPath="quizzes3"
            }

        }






        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection(collectionPath)
        collectionReference.addSnapshotListener { value, error ->
            if (value==null || error!=null){
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }



            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()



        }
    }


    private fun setUpDatePicker(category: String?) {
        btnDatePicker = findViewById(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener{
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)

                val intent=Intent(this,QuestionActivity::class.java)
                intent.putExtra("DATE",datePicker.headerText)

                intent.putExtra("CHOOSE",category)


                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER","Date picker cancelled")
            }
        }
    }

    private fun setUpRecyclerview(category: String?) {
        adapter = category?.let { QuizAdapter(this, quizList, it) }!!
        quizRecyclerView = findViewById(R.id.quizRecyclerView)
        quizRecyclerView.layoutManager = GridLayoutManager(this, 2)
        quizRecyclerView.adapter = adapter
    }

    private fun setUpDrawerLayout() {
        drawerLayout= findViewById<DrawerLayout>(R.id.mainDrawer)
        setSupportActionBar(findViewById(R.id.topAppBar))
        actionBarDrawerToggle=
            ActionBarDrawerToggle(this,drawerLayout,
                R.string.app_name,
                R.string.app_name
            )
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.profile->{
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)


                    true
                }
                R.id.rateUs->{
                    val intent = Intent(this, RateUs::class.java)
                    startActivity(intent)


                    true
                }

                R.id.share->{
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "text/plain"
                    val shareBody = "Check out this awesome app!"
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "My App")
                    sharingIntent.putExtra(Intent.EXTRA_TEXT,  "Check out this awesome app! Download it from: https://www.example.com/myapp")
                    startActivity(Intent.createChooser(sharingIntent, "Share via"))
                    true
                }


                R.id.redeem->{
                    val intent = Intent(this, ShopActivity::class.java)
                    startActivity(intent)

                    true
                }

                else -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                    true
                }
            }
        }







    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
            
        }

        return super.onOptionsItemSelected(item)
    }


}