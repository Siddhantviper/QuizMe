package com.example.quizme.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.quizme.R
import com.example.quizme.models.Quiz
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {

    lateinit var quiz:Quiz
    lateinit var button4:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setUpViews()
    }

    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<Quiz>(quizData, Quiz::class.java)
        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value
            Log.d("HELLO",question.toString())
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            findViewById<TextView>(R.id.txtAnswer).text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            findViewById<TextView>(R.id.txtAnswer).text = Html.fromHtml(builder.toString());
        }
    }

    private fun calculateScore() {
        button4=findViewById(R.id.button4)
        button4.visibility = View.GONE
        var score = 0
        for (entry in quiz.questions.entries) {
            val question = entry.value
            if (question.answer == question.userAnswer) {
                score += 10
            }
        }
        findViewById<TextView>(R.id.txtScore).text = "Your Score : $score/50"

        if(score==quiz.questions.size*10){
            button4.visibility=View.VISIBLE
        }
        button4.setOnClickListener {
            val intent = Intent(this,RedeemActivity::class.java)
            startActivity(intent)
        }
    }
}