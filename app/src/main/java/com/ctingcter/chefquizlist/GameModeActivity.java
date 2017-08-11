package com.ctingcter.chefquizlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by CTingCTer on 10/08/2017.
 */

public class GameModeActivity extends AppCompatActivity {
    TextView clock, threelives, both, freeforall;
    String loggedIn = "false";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    int gameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmode);

        clock = (TextView) findViewById(R.id.clock_TV);
        threelives = (TextView) findViewById(R.id.threelives_TV);
        both = (TextView) findViewById(R.id.both_TV);
        freeforall = (TextView) findViewById(R.id.freePlay_TV);

        clock.setOnClickListener(gameModeListener);
        threelives.setOnClickListener(gameModeListener);
        both.setOnClickListener(gameModeListener);
        freeforall.setOnClickListener(gameModeListener);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        loggedIn = sharedPref.getString("loggedIn", loggedIn);

        try {
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            Bundle b = getIntent().getExtras();
            loggedIn = b.getString("loggedIn");
        } catch (Exception e) {
            if (mFirebaseUser == null && loggedIn.equals("false")) {
                loadLogInView();
            }
        }

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

    private void loadLogInView() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private View.OnClickListener gameModeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.clock_TV:
                    // Intent intent_all = new Intent(QuizCategoryActivity.this, MainActivity.class);
                    Intent intent_clock = new Intent(GameModeActivity.this, QuizCategoryActivity.class);
                    Bundle b = new Bundle();
                    gameMode = 1;
                    b.putInt("gameMode", gameMode);
                    loggedIn = "true";
                    b.putString("loggedIn", loggedIn);
                    intent_clock.putExtras(b);
                    startActivity(intent_clock);
                    break;
                case R.id.threelives_TV:
                    // Intent intent_all = new Intent(QuizCategoryActivity.this, MainActivity.class);
                    Intent intent_lives = new Intent(GameModeActivity.this, QuizCategoryActivity.class);
                    Bundle c = new Bundle();
                    gameMode = 2;
                    c.putInt("gameMode", gameMode); //Your score
                    loggedIn = "true";
                    c.putString("loggedIn", loggedIn);
                    intent_lives.putExtras(c); //Put your score to your next Intent
                    startActivity(intent_lives);
                    break;
                case R.id.both_TV:
                    // Intent intent_all = new Intent(QuizCategoryActivity.this, MainActivity.class);
                    Intent intent_both = new Intent(GameModeActivity.this, QuizCategoryActivity.class);
                    Bundle d = new Bundle();
                    int gameMode = 3;
                    d.putInt("gameMode", gameMode); //Your score
                    loggedIn = "true";
                    d.putString("loggedIn", loggedIn);
                    intent_both.putExtras(d); //Put your score to your next Intent
                    startActivity(intent_both);
                    break;
                case R.id.freePlay_TV:
                    // Intent intent_all = new Intent(QuizCategoryActivity.this, MainActivity.class);
                    Intent intent_freePlay = new Intent(GameModeActivity.this, QuizCategoryActivity.class);
                    Bundle e = new Bundle();
                    gameMode = 4;
                    loggedIn = "true";
                    e.putString("loggedIn", loggedIn);
                    e.putInt("gameMode", gameMode); //Your score
                    intent_freePlay.putExtras(e); //Put your score to your next Intent
                    startActivity(intent_freePlay);
                    break;
            }
        }
    };
}