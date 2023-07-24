package com.example.quizme.activities
//
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizme.R
import com.example.quizme.adapters.OptionAdapter
import com.example.quizme.models.Question
import com.example.quizme.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson


class QuestionActivity : AppCompatActivity() {
    private lateinit var optionList:RecyclerView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var btnPrevious:Button
    private lateinit var btnNext:Button
    private lateinit var btnSubmit:Button

    private var quizzes : MutableList<Quiz>? = null
    private var questions: MutableMap<String, Question>? = null
//    private var questions:
    private var index = 1





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        //API code Starts here

//        val retrofitBuilder=Retrofit.Builder()
//            .baseUrl("https://opentdb.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiInterface::class.java)
//
//        val retrofitData = retrofitBuilder.getQuizData(
//            amount = 10,
//            category = 18,
//            difficulty = null,
//            type = "multiple"
//
//
//        )
//
//        retrofitData.enqueue(object : Callback<MyData?> {
//            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
//                //if api call is a success, then use the data of API and show in your app
//                var responseBody = response.body()
//                questions = responseBody?.results ?: emptyList()
//                showQuestion(currentQuestionIndex)
//
//
////                val resultList= responseBody?.results?:
////                Toast.makeText(this@QuestionActivity,resultList?.question,Toast.LENGTH_LONG).show()
//
////                val collectDataInSB = StringBuilder()
////                for (i in resultList){
////                     collectDataInSB.append(i.question+" ")
////                }
////                Toast.makeText(this@QuestionActivity,collectDataInSB,Toast.LENGTH_LONG).show()
//////                val tv = findViewById<TextView>(R.id.)
//////                tv.text = collectDataInSB
////                val questions = collectDataInSB.toString().split("?")
////                if (questions.isNotEmpty()) {
////                    val firstQuestion = questions[0]
////                    // Use the firstQuestion variable as needed
////                    Toast.makeText(this@QuestionActivity,firstQuestion,Toast.LENGTH_LONG).show()
////                }
//            }
//
//            override fun onFailure(call: Call<MyData?>, t: Throwable) {
//                //if api call fails
//                Log.d("Question Activity", "onFailure: "+t.message)
//            }
//        })












        //API code ends here
        setUpFirestore()
        setUpEventListener()
    }



    private fun setUpEventListener() {
        btnPrevious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnPrevious.setOnClickListener {
            index--
            bindViews()
        }

        btnNext.setOnClickListener {
            index++
            bindViews()
        }

        btnSubmit.setOnClickListener {
//            Log.d("FINALQUIZ", questions.toString())

            val intent = Intent(this, ResultActivity::class.java)
            val json  = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
            finish()
        }


    }
//
    private fun setUpFirestore() {
    val category=intent.getStringExtra("CHOOSE")
        val firestore = FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if (date!=null){

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

            firestore.collection(collectionPath).whereEqualTo("title",date)
                .get()
                .addOnSuccessListener {
                    if (it!=null && !it.isEmpty){
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindViews()
                    }

                }
        }
    }

    private fun bindViews() {

        btnPrevious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnPrevious.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        btnNext.visibility = View.GONE

        when(index){
            1 -> btnNext.visibility = View.VISIBLE
            questions!!.size -> {
                btnSubmit.visibility = View.VISIBLE
                btnPrevious.visibility = View.VISIBLE
            }
            else -> {
                btnPrevious.visibility = View.VISIBLE
                btnNext.visibility = View.VISIBLE
            }
        }

//        if(index == 1){ //first question
//            btnNext.visibility = View.VISIBLE
//        }
//        else if(index == questions!!.size) { // last question
//            btnSubmit.visibility = View.VISIBLE
//            btnPrevious.visibility = View.VISIBLE
//        }
//        else{ // Middle
//            btnPrevious.visibility = View.VISIBLE
//            btnNext.visibility = View.VISIBLE
//        }

        var question = questions!!["question$index"]
        question?.let {
            optionList = findViewById(R.id.optionList)
            findViewById<TextView>(R.id.description).text = it.description
            val optionAdapter = OptionAdapter(this, it)
            optionList.layoutManager =LinearLayoutManager(this)
            optionList.adapter = optionAdapter
            optionList.setHasFixedSize(true)
        }




    }
}
