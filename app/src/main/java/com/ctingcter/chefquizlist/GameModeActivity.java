package com.ctingcter.chefquizlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by CTingCTer on 10/08/2017.
 */

public class GameModeActivity extends AppCompatActivity {
    TextView clock, threelives, both, freeforall;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

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

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Bundle b = getIntent().getExtras();
        if (mFirebaseUser == null && b == null) {
            loadLogInView();
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
                    int gameMode = 1;
                    b.putInt("gameMode", gameMode); //Your score
                    intent_clock.putExtras(b); //Put your score to your next Intent
                    startActivity(intent_clock);
                    break;

            }
        }
    };
}