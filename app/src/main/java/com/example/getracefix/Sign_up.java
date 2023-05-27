package com.example.getracefix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Sign_up extends AppCompatActivity implements View.OnClickListener {
    TextView Sign_up;
    EditText Email_sign , Password_sign , carModel , plateNumber , phoneNumber ;
    String email , password , phoneNum , carMo , plateNum ;
    ImageButton goBtn;
    Intent Go , ToSignUp;
    ProfileNote PN1;

    private String userId;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore mFireStore;
    private DocumentReference docPoint;
    private static String TAG = "Sign_up";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Sign_up = (TextView) findViewById(R.id.SignUp_tv);

        Email_sign = (EditText) findViewById(R.id.email_sign_up_et);

        Password_sign = (EditText) findViewById(R.id.Password_signup_et);

        carModel = (EditText)findViewById(R.id.carmodel_et);

        plateNumber = (EditText)findViewById(R.id.platenumber_et);

        phoneNumber = (EditText)findViewById(R.id.phonenumber_et);


        goBtn = (ImageButton)findViewById(R.id.Go_btn);
        goBtn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();

         Go = getIntent();
         ToSignUp = getIntent();
    }

    @Override
    public void onClick(View v) {
        email = Email_sign.getText().toString();
        password = Password_sign.getText().toString();
        phoneNum = phoneNumber.getText().toString();
        carMo = carModel.getText().toString();
        plateNum = plateNumber.getText().toString();
        signUpUser(email, password);
        if (phoneNum.isEmpty() || carMo.isEmpty() || plateNum.isEmpty()) {
            Toast.makeText(Sign_up.this , "Please Fill All Fields!" , Toast.LENGTH_SHORT).show();

        }
        else {
            newDocument();
            Go  = new Intent(Sign_up.this, MainActivity.class);
            startActivity(Go);
        }

    }
    private void signUpUser(String email, String password) {
        if(email.isEmpty()){
            Toast.makeText(Sign_up.this, "Enter Email!", Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty()){
            Toast.makeText(Sign_up.this, "Enter Password!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                insertUser();
                            } else {
                                 // If sign in fails, display a message to the user.
                                Toast.makeText(Sign_up.this, "Sign up failed: " + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void insertUser() {
        user = mAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest newUser = new UserProfileChangeRequest.Builder().setDisplayName(email).build();
        }   //insert into firebase

    }
        void newDocument() {
            //New User Fields insert
            PN1 = new ProfileNote(carMo , plateNum , phoneNum);
            docPoint = mFireStore.collection("Users").document(email);
            docPoint.set(PN1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Sign_up.this, "Comment saved", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "DocumentSnapshot successfully created!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Sign_up.this, "Error!", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }


}



