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
 * Created by CTingCTer on 07/08/2017.
 */

public class QuizCategoryActivity extends AppCompatActivity {

    TextView all_TV, chefs_TV, nations_TV, foods_TV;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUserEmail;
    private String mName;
    private String categoryChoice;
    String loggedIn = "false";
    int gameMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        all_TV = (TextView) findViewById(R.id.all_TV);
        chefs_TV = (TextView) findViewById(R.id.chefs_TV);
        nations_TV = (TextView) findViewById(R.id.nations_TV);
        foods_TV = (TextView) findViewById(R.id.foods_TV);

        all_TV.setOnClickListener(categoryListener);
        chefs_TV.setOnClickListener(categoryListener);
        nations_TV.setOnClickListener(categoryListener);
        foods_TV.setOnClickListener(categoryListener);

        Bundle a = getIntent().getExtras();
        gameMode = a.getInt("gameMode");
        Bundle c = getIntent().getExtras();
        gameMode = c.getInt("gameMode");
        Bundle d = getIntent().getExtras();
        gameMode = d.getInt("gameMode");
        Bundle e = getIntent().getExtras();
        gameMode = e.getInt("gameMode");

        try {
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            Bundle b = getIntent().getExtras();
            loggedIn = b.getString("loggedIn");
        } catch (Exception h) {
            if (mFirebaseUser == null && loggedIn.equals("false")) {
                loadLogInView();
            }
        }
    }


    private View.OnClickListener categoryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.all_TV:
                    // Intent intent_all = new Intent(QuizCategoryActivity.this, MainActivity.class);
                    Intent intent_all = new Intent(QuizCategoryActivity.this, MainActivity.class);
                    Bundle a = new Bundle();
                    a.putInt("gameMode", gameMode); //Your score
                    intent_all.putExtras(a); //Put your score to your next Intent
                    startActivity(intent_all);
                    break;
                case R.id.chefs_TV:
                    Intent intent_chefs = new Intent(QuizCategoryActivity.this, ChefsActivity.class);
                    Bundle c = new Bundle();
                    c.putInt("gameMode", gameMode);
                    intent_chefs.putExtras(c);
                    startActivity(intent_chefs);
                    break;
                case R.id.nations_TV:
                    Intent intent_nations = new Intent(QuizCategoryActivity.this, NationsActivity.class);
                    Bundle d = new Bundle();
                    d.putInt("gameMode", gameMode);
                    intent_nations.putExtras(d);
                    startActivity(intent_nations);
                    break;
                case R.id.foods_TV:
                    Intent intent_foods = new Intent(QuizCategoryActivity.this, FoodsActivity.class);
                    Bundle e = new Bundle();
                    e.putInt("gameMode", gameMode);
                    intent_foods.putExtras(e);
                    startActivity(intent_foods);
                    break;
            }
        }
    };

    private void loadLogInView() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        // SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("timeLeftSave", 0);
        editor.commit();
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
