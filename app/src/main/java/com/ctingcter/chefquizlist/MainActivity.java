package com.ctingcter.chefquizlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Question> questionList;
    float score;
    int qId;
    int questionsCount;
    Question currentQ;
    ImageView ImageAnswer1, ImageAnswer2, ImageAnswer3;
    TextView Question_TV, Answer1_TV, Answer2_TV, Answer3_TV, Name_TV;
    RelativeLayout container;
    LinearLayout textQuestion, imageQuestion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question(1, "Which of these is romesco?", R.drawable.q4_1, R.drawable.q4_2, R.drawable.q4_3, R.drawable.q4_2, 0));
        questions.add(new Question(2, "Which of these is the name of a knife?", "pane", "flat", "deba", "deba"));
        questions.add(new Question(3, "Which of these is a fish?", "Turnip", "Turbot", "fillet", "Turbot"));
        questions.add(new Question(4, "Which chef owns The Fat Duck?", "Heston Blumenthal", "Gordon Ramsay", "Donald Duck", "Heston Blumenthal"));
        questions.add(new Question(5, "Which of these is Ferran Adria?", R.drawable.ferran, R.drawable.ramsay, R.drawable.rene, R.drawable.ferran, 0));
        questions.add(new Question(6, "Mayonaisse is...", "an emulsification", "a mother sauce", "a dairy product", "an emulsification"));
        questions.add(new Question(7, "What is a brunoise?", "A sauce", "A cake", "A knife cut", "A knife cut"));
        questions.add(new Question(8, "Gazpacho is what?", "Chilled tomato soup", "A type of pasta", "A rice dish", "Chilled tomato soup"));
        questions.add(new Question(9, "What country is Gordon Ramsay from?", R.drawable.eng, R.drawable.wales, R.drawable.scot, R.drawable.scot, 0));

        questionList = questions;
        questionsCount = questions.size();
        currentQ = questionList.get(qId);
        Question_TV = (TextView) findViewById(R.id.Question_TV);
        ImageAnswer1 = (ImageView) findViewById(R.id.imageAnswer1);
        ImageAnswer2 = (ImageView) findViewById(R.id.imageAnswer2);
        ImageAnswer3 = (ImageView) findViewById(R.id.imageAnswer3);
        Answer1_TV = (TextView) findViewById(R.id.Answer1_TV);
        Answer2_TV = (TextView) findViewById(R.id.Answer2_TV);
        Answer3_TV = (TextView) findViewById(R.id.Answer3_TV);
        Name_TV = (TextView) findViewById(R.id.name_TV);
        container = (RelativeLayout) findViewById(R.id.container);
        imageQuestion = (LinearLayout) findViewById(R.id.imageQuestion);
        textQuestion = (LinearLayout) findViewById(R.id.textQuestion);
        Answer1_TV.setOnClickListener(answerListener);
        Answer2_TV.setOnClickListener(answerListener);
        Answer3_TV.setOnClickListener(answerListener);
        ImageAnswer1.setOnClickListener(answerListener);
        ImageAnswer2.setOnClickListener(answerListener);
        ImageAnswer3.setOnClickListener(answerListener);
        setQuestionView();

        Bundle b = getIntent().getExtras();
        String name = b.getString("name");
        Name_TV.setText("Hello " + name);
    }

    private void setQuestionView() {
        container.setBackgroundColor(Color.WHITE);
        if (currentQ.hasImage()) {
            Question_TV.setText(currentQ.getQuestion());
            imageQuestion.setVisibility(View.VISIBLE);
            textQuestion.setVisibility(View.GONE);
            ImageAnswer1.setImageResource(currentQ.getImageAnswer1());
            ImageAnswer2.setImageResource(currentQ.getImageAnswer2());
            ImageAnswer3.setImageResource(currentQ.getImageAnswer3());
            qId++;
        } else {
            textQuestion.setVisibility(View.VISIBLE);
            imageQuestion.setVisibility(View.GONE);
            Question_TV.setText(currentQ.getQuestion());
            Answer1_TV.setText(currentQ.getAnswer1());
            Answer2_TV.setText(currentQ.getAnswer2());
            Answer3_TV.setText(currentQ.getAnswer3());
            qId++;

        }

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
                case R.id.imageAnswer1:
                    checkImageAnswer(currentQ.getImageAnswer1());
                    break;
                case R.id.imageAnswer2:
                    checkImageAnswer(currentQ.getImageAnswer2());
                    break;
                case R.id.imageAnswer3:
                    checkImageAnswer(currentQ.getImageAnswer3());
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

    public void checkImageAnswer(int answer) {

        if (currentQ.getImageCorrect() == (answer)) {
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
