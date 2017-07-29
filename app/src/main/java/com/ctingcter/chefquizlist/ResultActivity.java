package com.ctingcter.chefquizlist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by CTingCTer on 29/07/2017.
 */

public class ResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

//get text view
        TextView t=(TextView)findViewById(R.id.textResult);
//get score
        Bundle b = getIntent().getExtras();
        float score= b.getFloat("score");
        int score1 = (int)score;

        if (score1 < 50) {
            t.setText(score1 + "%...Better luck next time");
        } else if (score1 > 50 && score1 < 70 ) {
            t.setText(score1 + "%!  Good Effort!");
        } else if (score1 > 70 && score1 < 90) {
            t.setText(score1 + "% You must be a professional!");
        } else {
            t.setText(score1 + "% WOW!");
        }

    }

}
