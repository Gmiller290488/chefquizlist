package com.ctingcter.chefquizlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    List<Question> questionList;
    int score;
    int qId = 1;
    Question currentQ;
    TextView Question_TV, Answer1_TV, Answer2_TV, Answer3_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question(1, "Which of these is the name of a knife?", "pane", "flat", "deba", "deba"));
        questions.add(new Question(2, "Which of these is a fish?", "Turnip", "Turbot", "fillet", "Turbot"));

        questionList = questions;
        currentQ = questionList.get(qId);
        Question_TV = (TextView) findViewById(R.id.Question_TV);
        Answer1_TV = (TextView) findViewById(R.id.Answer1_TV);
        Answer2_TV = (TextView) findViewById(R.id.Answer2_TV);
        Answer3_TV = (TextView) findViewById(R.id.Answer3_TV);
        setQuestionView();
    }

    private void setQuestionView() {
        Question_TV.setText(currentQ.getQuestion());
        Answer1_TV.setText(currentQ.getAnswer1());
        Answer2_TV.setText(currentQ.getAnswer2());
        Answer3_TV.setText(currentQ.getAnswer3());
        qId++;
    }


}
