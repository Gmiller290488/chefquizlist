package com.ctingcter.chefquizlist;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CTingCTer on 07/08/2017.
 */

public class ChefsActivity extends AppCompatActivity {
        List<Question> questionList;
        float score;
        int qId;
        int questionsCount;
        Question currentQ;
        ImageView ImageAnswer1, ImageAnswer2, ImageAnswer3;
        TextView Question_TV, Answer1_TV, Answer2_TV, Answer3_TV, Name_TV;
        RelativeLayout container;
        LinearLayout textQuestion, imageQuestion;
        FirebaseAuth mFirebaseAuth;
        String mCategory;

    /* Need to implement a way to check which category was selected and thus which questions to show */

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ArrayList<Question> questions = new ArrayList<Question>();
            questions.add(new Question(1, "Which chef owns The Fat Duck?", "Heston Blumenthal", "Gordon Ramsay", "Donald Duck", "Heston Blumenthal", "chefs"));
            questions.add(new Question(2, "Which of these is Ferran Adria?", R.drawable.ferran, R.drawable.ramsay, R.drawable.rene, R.drawable.ferran, 0, "chefs"));



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

            mFirebaseAuth = FirebaseAuth.getInstance();
            Toast.makeText(this, mCategory, Toast.LENGTH_LONG).show();
            setQuestionView();


        }

        private void loadLogInView() {
            Intent intent = new Intent(this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
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

            } else if (!currentQ.getCorrectanswer().equals(answer)) {
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
                        Intent intent = new Intent(com.ctingcter.chefquizlist.ChefsActivity.this, ResultActivity.class);
                        Bundle b = new Bundle();
                        score = ((score / questionsCount) * 100);
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
                        Intent intent = new Intent(com.ctingcter.chefquizlist.ChefsActivity.this, ResultActivity.class);
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

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_logout) {
                mFirebaseAuth.signOut();
                loadLogInView();
            }

            return super.onOptionsItemSelected(item);
        }
    }


