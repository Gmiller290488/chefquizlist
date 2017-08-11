package com.ctingcter.chefquizlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.R.attr.password;
import static com.google.firebase.auth.FirebaseAuth.getInstance;

/**
 * Created by CTingCTer on 02/08/2017.
 */

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{
    SignInButton signInButton;
    TextView signUpLink;
    GoogleApiClient mGoogleApiClient;
    Button emailLogInButton;
    protected FirebaseAuth mFirebaseAuth;
    protected EditText emailEditText;
    protected EditText passwordEditText;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListened */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        emailEditText = (EditText) findViewById(R.id.emailField);
        passwordEditText = (EditText) findViewById(R.id.passwordField);
        signInButton = (SignInButton) findViewById(R.id.sign_in_google_btn);
        signUpLink = (TextView) findViewById(R.id.signUp_Link);
        emailLogInButton = (Button) findViewById(R.id.logIn_btn);

        signInButton.setOnClickListener(this);
        signUpLink.setOnClickListener(this);
        emailLogInButton.setOnClickListener(this);

        }



    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.sign_in_google_btn:
                signInGoogle();
                break;
            case R.id.signUp_Link:
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.logIn_btn:
                signInEmail();



        }
    }

    private void signInEmail() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        email = email.trim();
        password = password.trim();

        if (email.isEmpty() || password.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
            builder.setMessage(R.string.login_error_message)
                    .setTitle(R.string.login_error_title)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SignInActivity.this, GameModeActivity.class);
                                // Need to sort this out
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                                builder.setMessage(task.getException().getMessage())
                                        .setTitle(R.string.login_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
        }
    }


    private void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Intent intent = new Intent(SignInActivity.this, GameModeActivity.class);
            Bundle b = new Bundle();
            String loggedIn = "true";
            b.putString("loggedIn", loggedIn);
            intent.putExtras(b);


            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

