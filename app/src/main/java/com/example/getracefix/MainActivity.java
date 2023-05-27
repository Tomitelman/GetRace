package com.example.getracefix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent1, Go, GoRace;
    TextView Welcome;
    EditText emailText, passwordText;
    Button Login, Sign_up;
    String email, password;
    private FirebaseAuth mAuth;
    private FirebaseUser user;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Welcome = (TextView) findViewById(R.id.welcome_TV);

        emailText = (EditText) findViewById(R.id.Email_et);
        passwordText = (EditText) findViewById(R.id.Password_et);

        Login = (Button) findViewById(R.id.login_btn);
        Login.setOnClickListener(this);

        Sign_up = (Button) findViewById(R.id.signup_btn);
        Sign_up.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        intent1 = getIntent();
        GoRace = getIntent();





    }


    @Override
    public void onClick(View v) {
        if (v == Sign_up){
            Intent ToSignUp = new Intent(MainActivity.this, Sign_up.class);
              startActivity(ToSignUp);
        }
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        loginUser(email, password);
    }




    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        updateUI(user);
    }

    private void loginUser(String email, String password) {
        if(email.equals("")){
            Toast.makeText(MainActivity.this, "Enter Email!!",
                    Toast.LENGTH_SHORT).show();
        }
        else if(password.equals("")){
            Toast.makeText(MainActivity.this, "Enter Password!!",
                    Toast.LENGTH_SHORT).show();
        } //first check of the data if correct or not inserted
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                updateUI(user);
                                Intent ToProfile = new Intent(MainActivity.this, HomeProfile.class);
                                finish();
                                startActivity(ToProfile);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Authentication failed: " + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    private void updateUI(FirebaseUser user) {

        /*-------- Check if user is already logged in or not--------*/
        if (user != null) {
            /*------------ If user's email is verified then access login -----------*/
            if(user.isEmailVerified()){
                Toast.makeText(MainActivity.this, "Login Success!",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Your Email is not verified.",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }



    }