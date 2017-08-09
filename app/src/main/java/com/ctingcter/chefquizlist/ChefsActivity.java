package com.ctingcter.chefquizlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
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

import static android.view.View.GONE;

/**
 * Created by CTingCTer on 07/08/2017.
 */

public class ChefsActivity extends AppCompatActivity {
    List<Question> questionList;
    float score;
    int qId;
    int questionsCount;
    int soundOff;
    int timeLeft = 0;
    int lives = 4;
    Question currentQ;
    ImageView ImageAnswer1, ImageAnswer2, ImageAnswer3;
    TextView Question_TV, Answer1_TV, Answer2_TV, Answer3_TV, Name_TV, Timer_TV, Lives1_TV, Lives2_TV, Lives3_TV;
    RelativeLayout container;
    LinearLayout textQuestion, imageQuestion, innerContainer, LivesContainer;
    FirebaseAuth mFirebaseAuth;
    CountDownTimer timer;

    /**
     * Handles playback of all the sound files
     */
    private MediaPlayer mp;
    /**
     * Handles audio focus when playing a sound file
     */
    private AudioManager mAudioManager;


    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mp.pause();
                mp.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mp.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

            ArrayList<Question> questions = new ArrayList<Question>();
            questions.add(new Question(1, "Which chef owns The Fat Duck?", "Heston Blumenthal", "Gordon Ramsay", "Donald Duck", "Heston Blumenthal", "chefs"));
            questions.add(new Question(2, "Which of these is Ferran Adria?", R.drawable.ferran, R.drawable.ramsay, R.drawable.rene, R.drawable.ferran, 0, "chefs"));
            questions.add(new Question(3, "Which chef owns D.O.M in Sao Paulo?", "Virgilio Martínez Véliz", "Joan Roca", "Alex Atala", "Alex Atala", "chefs"));
            questions.add(new Question(4, "Which of these chefs is Albert Roux?", R.drawable.michelrouxjnr, R.drawable.albert, R.drawable.michel, R.drawable.albert, 0, "chefs"));
            questions.add(new Question(5, "Which of these chefs have the most michelin stars? (2017)", R.drawable.robuchon, R.drawable.keller, R.drawable.ducasse, R.drawable.robuchon, 0, "chefs"));
            questions.add(new Question(6, "Which chef has two establishments in Marlow?", "Heston Blumenthal", "Natahan Outlaw", "Tom Kerridge", "Tom Kerridge", "chefs"));
            questions.add(new Question(7, "Which legendary chef owned 3 michelin starred \"La Tante Claire\"?", "Marco Pierre White", "Pierre Koffman", "Nico Ladenis", "Pierre Koffman", "chefs"));
            questions.add(new Question(8, "Which of these restaurants is NOT owned by Heston Blumenthal?", "The Crowne", "The Owls Head", "The Perfectionists Cafe", "The Owls Head", "chefs"));
            questions.add(new Question(9, "Which of these chefs doesn't have a restaurant in Manchester?", R.drawable.clifford, R.drawable.byrne, R.drawable.reid, R.drawable.clifford, 0 ,"chefs"));
            questions.add(new Question(10, "Which chef owns \"64 degrees\"?", "Tommy Banks", "Michael Bremner", "Simon Hulstone", "Simon Hulstone", "chefs"));



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
        LivesContainer = (LinearLayout) findViewById(R.id.LivesContainer);
        Lives1_TV = (TextView) findViewById(R.id.Lives1_TV);
        Lives2_TV = (TextView) findViewById(R.id.Lives2_TV);
        Lives3_TV = (TextView) findViewById(R.id.Lives3_TV);
            container = (RelativeLayout) findViewById(R.id.container);
            innerContainer = (LinearLayout) findViewById(R.id.innerContainer);
            imageQuestion = (LinearLayout) findViewById(R.id.imageQuestion);
            textQuestion = (LinearLayout) findViewById(R.id.textQuestion);
            Answer1_TV.setOnClickListener(answerListener);
            Answer2_TV.setOnClickListener(answerListener);
            Answer3_TV.setOnClickListener(answerListener);
            ImageAnswer1.setOnClickListener(answerListener);
            ImageAnswer2.setOnClickListener(answerListener);
            ImageAnswer3.setOnClickListener(answerListener);


            mFirebaseAuth = FirebaseAuth.getInstance();
            setQuestionView();
        }


    private void loadLogInView() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void checkLives(int lives) {
        if (lives == 3) {
            Lives3_TV.setVisibility(GONE);
        } else if (lives == 2) {
            Lives2_TV.setVisibility(GONE);
        } else if (lives == 1) {
            Lives1_TV.setVisibility(GONE);
        }
        if (lives == 0) {
            Intent intent = new Intent(ChefsActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            score = ((score / questionsCount) * 100);
            b.putFloat("score", score); //Your score
            intent.putExtras(b); //Put your score to your next Intent
            startActivity(intent);
            finish();
        }
    }


    private void setQuestionView() {


        innerContainer.setBackgroundColor(getResources().getColor(R.color.containerColour));
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
            if (soundOff == 0) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.right);
                    mp.start();
                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mp.setOnCompletionListener(mCompletionListener);
                }
                innerContainer.setBackgroundColor(getResources().getColor(R.color.correctColour));

            }
        } else if (!currentQ.getCorrectanswer().equals(answer)) {
            if (soundOff == 0) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong);
                    mp.start();
                    mp.setOnCompletionListener(mCompletionListener);
                    lives--;
                    checkLives(lives);
                }
            }
            innerContainer.setBackgroundColor(getResources().getColor(R.color.wrongColour));


        }
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                if (qId < questionsCount) {
                    currentQ = questionList.get(qId);
                    setQuestionView();
                } else {
                    Intent intent = new Intent(ChefsActivity.this, ResultActivity.class);
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
            if (soundOff == 0) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.right);
                    mp.start();
                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mp.setOnCompletionListener(mCompletionListener);
                }
                innerContainer.setBackgroundColor(getResources().getColor(R.color.correctColour));

            }
        } else if (currentQ.getImageCorrect() != (answer)) {
            if (soundOff == 0) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong);
                    mp.start();
                    mp.setOnCompletionListener(mCompletionListener);
                    lives--;
                    checkLives(lives);
                }
            }
            innerContainer.setBackgroundColor(getResources().getColor(R.color.wrongColour));

        }
        new CountDownTimer(2000, 1000) {
            public void onFinish() {
                if (qId < questionsCount) {
                    currentQ = questionList.get(qId);
                    setQuestionView();
                } else {
                    Intent intent = new Intent(ChefsActivity.this, ResultActivity.class);
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
        if (id == R.id.action_soundOff) {
            if (soundOff == 0) {
                soundOff = 1;
            } else if (soundOff == 1) {
                soundOff = 0;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mp != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mp.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mp = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        String timeLeftString = Timer_TV.getText().toString();
        String[] separated = timeLeftString.split(":");
        separated[1] = separated[1].trim();
        timeLeft = Integer.parseInt(separated[1]);
        timer.cancel();
        timer = null;
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("timeLeftSave", timeLeft);
        editor.commit();


    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        timeLeft = sharedPref.getInt("timeLeftSave", timeLeft);
        updateTimer(timeLeft);
    }

    private void updateTimer(int timeLeft) {
        if (timeLeft == 0) {
            timer = new CountDownTimer((questionsCount * 6) * 1000 + 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    int seconds = (int) (millisUntilFinished / 1000);
                    Timer_TV.setText("TIME : " + String.format("%02d", seconds));

                }

                public void onFinish() {
                    Intent intent = new Intent(ChefsActivity.this, ResultActivity.class);
                    Bundle b = new Bundle();
                    score = ((score / questionsCount) * 100);
                    b.putFloat("score", score); //Your score
                    intent.putExtras(b); //Put your score to your next Intent
                    startActivity(intent);
                    finish();
                }
            }.start();
        } else {
            timer = new CountDownTimer(timeLeft * 1000 + 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    int seconds = (int) (millisUntilFinished / 1000);
                    Timer_TV.setText("TIME : " + String.format("%02d", seconds));

                }

                public void onFinish() {
                    Intent intent = new Intent(ChefsActivity.this, ResultActivity.class);
                    Bundle b = new Bundle();
                    score = ((score / questionsCount) * 100);
                    b.putFloat("score", score); //Your score
                    intent.putExtras(b); //Put your score to your next Intent
                    startActivity(intent);
                    finish();
                }
            }.start();

        }
    }
}


