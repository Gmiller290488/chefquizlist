package com.ctingcter.chefquizlist;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    List<Question> questionList;
    float score;
    int qId;
    int questionsCount;
    Question currentQ;
    ImageView ImageAnswer1, ImageAnswer2, ImageAnswer3;
    TextView Question_TV, Answer1_TV, Answer2_TV, Answer3_TV, Name_TV, Timer_TV;
    RelativeLayout container;
    LinearLayout textQuestion, imageQuestion;
    FirebaseAuth mFirebaseAuth;
    int timeRemaining;

    /* Need to implement a way to check which category was selected and thus which questions to show */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(new Question(1, "Which chef owns The Fat Duck?", "Heston Blumenthal", "Gordon Ramsay", "Donald Duck", "Heston Blumenthal", "chefs"));
        questions.add(new Question(2, "Which of these is romesco?", R.drawable.q4_1, R.drawable.q4_2, R.drawable.q4_3, R.drawable.q4_2, 0, "foods"));
        questions.add(new Question(3, "Which of these is the name of a knife?", "pane", "flat", "deba", "deba", "all"));
        questions.add(new Question(4, "Which of these is a fish?", "Turnip", "Turbot", "fillet", "Turbot", "foods"));
        questions.add(new Question(5, "Which of these is Ferran Adria?", R.drawable.ferran, R.drawable.ramsay, R.drawable.rene, R.drawable.ferran, 0, "chefs"));
        questions.add(new Question(6, "Mayonaisse is...", "an emulsification", "a mother sauce", "a dairy product", "an emulsification", "foods"));
        questions.add(new Question(7, "What is a brunoise?", "A sauce", "A cake", "A knife cut", "A knife cut", "all"));
        questions.add(new Question(8, "Gazpacho is what?", "Chilled tomato soup", "A type of pasta", "A rice dish", "Chilled tomato soup", "foods"));
        questions.add(new Question(9, "What country is Gordon Ramsay from?", R.drawable.eng, R.drawable.wales, R.drawable.scot, R.drawable.scot, 0, "nations"));
        questions.add(new Question(10, "Which chef owns D.O.M in Sao Paulo?", "Virgilio Martínez Véliz", "Joan Roca", "Alex Atala", "Alex Atala", "chefs"));
        questions.add(new Question(11, "Which of these chefs is Albert Roux?", R.drawable.michelrouxjnr, R.drawable.albert, R.drawable.michel, R.drawable.albert, 0, "chefs"));
        questions.add(new Question(12, "Feta cheese is made from?", "Cow's milk", "Sheep's milk", "Goat's milk", "Sheep's milk", "food"));
        questions.add(new Question(13, "Which of these chefs have the most michelin stars? (2017)", R.drawable.robuchon, R.drawable.keller, R.drawable.ducasse, R.drawable.robuchon, 0, "chefs"));
        questions.add(new Question(14, "Which chef has two establishments in Marlow?", "Heston Blumenthal", "Natahan Outlaw", "Tom Kerridge", "Tom Kerridge", "chefs"));
        questions.add(new Question(15, "Which legendary chef owned 3 michelin starred \"La Tante Claire\"?", "Marco Pierre White", "Pierre Koffman", "Nico Ladenis", "Pierre Koffman", "chefs"));
        questions.add(new Question(16, "Which of these fruits is dorian?", R.drawable.dorian, R.drawable.dragon, R.drawable.spikymelon, R.drawable.dorian, 0, "foods"));
        questions.add(new Question(17, "Which of these restaurants is NOT owned by Heston Blumenthal?", "The Crowne", "The Owls Head", "The Perfectionists Cafe", "The Owls Head", "chefs"));
        questions.add(new Question(18, "Which of these fish is halibut?", R.drawable.lemonsole, R.drawable.halibut, R.drawable.turbot, R.drawable.halibut, 0, "foods"));
        questions.add(new Question(19, "Which of these chefs doesn't have a restaurant in Manchester?", R.drawable.clifford, R.drawable.byrne, R.drawable.reid, R.drawable.clifford, 0 ,"chefs"));
        questions.add(new Question(20, "Which chef owns \"64 degrees\"?", "Tommy Banks", "Michael Bremner", "Simon Hulstone", "Simon Hulstone", "chefs"));




        questionList = questions;
        questionsCount = questions.size();
        currentQ = questionList.get(qId);
        Timer_TV = (TextView) findViewById(R.id.Timer_TV);
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

        new CountDownTimer((questionsCount * 5) * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                seconds = seconds % 60;
                Timer_TV.setText("TIME : " + String.format("%02d", seconds));

            }

            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                Bundle b = new Bundle();
                score = ((score / questionsCount) * 100);
                b.putFloat("score", score); //Your score
                intent.putExtras(b); //Put your score to your next Intent
                startActivity(intent);
                finish();
            }
        }.start();
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
            new CountDownTimer(1500, 1000) {
                public void onFinish() {
                    if (qId < questionsCount) {
                        currentQ = questionList.get(qId);
                        setQuestionView();
                    } else {
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
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
