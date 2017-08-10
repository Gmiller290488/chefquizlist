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

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Bundle b = getIntent().getExtras();
        if (mFirebaseUser == null && b == null) {
            loadLogInView();
        }

}

    private View.OnClickListener categoryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.all_TV:
                    Intent intent_all = new Intent(QuizCategoryActivity.this, MainActivity.class);
                    startActivity(intent_all);
                    break;
                case R.id.chefs_TV:
                    Intent intent_chefs = new Intent(QuizCategoryActivity.this, ChefsActivity.class);
                    startActivity(intent_chefs);
                    break;
                case R.id.nations_TV:
                    Intent intent_nations = new Intent(QuizCategoryActivity.this, NationsActivity.class);
                    startActivity(intent_nations);
                    break;
                case R.id.foods_TV:
                    Intent intent_foods = new Intent(QuizCategoryActivity.this, FoodsActivity.class);
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
