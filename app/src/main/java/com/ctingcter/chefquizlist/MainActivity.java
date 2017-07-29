package com.ctingcter.chefquizlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Question> questionList;
    float score;
    int qId;
    int questionsCount;
    Question currentQ;
    TextView Question_TV, Answer1_TV, Answer2_TV, Answer3_TV;
    LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question(1, "Which of these is the name of a knife?", "pane", "flat", "deba", "deba"));
        questions.add(new Question(2, "Which of these is a fish?", "Turnip", "Turbot", "fillet", "Turbot"));
        questions.add(new Question(3, "Which chef owns The Fat Duck?", "Heston Blumenthal", "Gordon Ramsay", "Donald Duck", "Heston Blumenthal"));

        questionList = questions;
        questionsCount = questions.size();
        currentQ = questionList.get(qId);
        Question_TV = (TextView) findViewById(R.id.Question_TV);
        Answer1_TV = (TextView) findViewById(R.id.Answer1_TV);
        Answer2_TV = (TextView) findViewById(R.id.Answer2_TV);
        Answer3_TV = (TextView) findViewById(R.id.Answer3_TV);
        container = (LinearLayout) findViewById(R.id.container);
        Answer1_TV.setOnClickListener(answerListener);
        Answer2_TV.setOnClickListener(answerListener);
        Answer3_TV.setOnClickListener(answerListener);
        setQuestionView();
    }

    private void setQuestionView() {
        container.setBackgroundColor(Color.WHITE);
        Question_TV.setText(currentQ.getQuestion());
        Answer1_TV.setText(currentQ.getAnswer1());
        Answer2_TV.setText(currentQ.getAnswer2());
        Answer3_TV.setText(currentQ.getAnswer3());
        qId++;
    }

    private View.OnClickListener answerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Answer1_TV:
                    checkAnswer(currentQ.getAnswer1());
                    break;
                case R.id.Answer2_TV:
                    checkAnswer(currentQ.getAnswer2());
                    break;
                case R.id.Answer3_TV:
                    checkAnswer(currentQ.getAnswer3());
                    break;
            }
        }
    };

    public void checkAnswer(String answer) {

        if (currentQ.getCorrectanswer().equals(answer)) {
            score++;
            MediaPlayer mp = MediaPlayer.create(this, R.raw.right);
            mp.start();
            container.setBackgroundColor(Color.GREEN);

        } else {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong);
            mp.start();
            container.setBackgroundColor(Color.RED);

        }
        new CountDownTimer(2000, 1000) {
            public void onFinish() {
                if (qId < questionsCount) {
                    currentQ = questionList.get(qId);
                    setQuestionView();
                } else {
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    Bundle b = new Bundle();
                    score = ((score/questionsCount)*100);
                    b.putFloat("score", score); //Your score
                    intent.putExtras(b); //Put your score to your next Intent
                    startActivity(intent);
                    finish();
                }
            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();


    }
}
