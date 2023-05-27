package com.example.getracefix;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Edit_Profile extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore mFireStore;
    private FirebaseAuth mAuth;
    private DocumentReference profileRef;
    Button Finish;
    EditText phoneNum , carModel , plateNum ;
    String email, mPhoneNum , mCarModel , mPlateNum ;
    static String TAG = "Edit_Profile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);

        Finish = (Button)findViewById(R.id.finish_btn);
        Finish.setOnClickListener(this);

        phoneNum = (EditText)findViewById(R.id.phonenumber_et);
        carModel = (EditText)findViewById(R.id.carmodel_et);
        plateNum = (EditText)findViewById(R.id.plate_et);


       mFireStore = FirebaseFirestore.getInstance();
       mAuth = FirebaseAuth.getInstance();



    }

    @Override
    public void onClick(View v) {
        if (v == Finish) {


            //Dialog Creation
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Confimation");
            builder.setMessage("You Finished?");
            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // when user press Yes
                            updateDocument();


                            Intent editFinish = new Intent(Edit_Profile.this , HomeProfile.class);
                            startActivity(editFinish);


                            }

                    });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  //when user press no
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
     //updating the required fields
    void updateDocument() {
        mCarModel = carModel.getText().toString();
        mPhoneNum = phoneNum.getText().toString();
        mPlateNum = plateNum.getText().toString();
        email = mAuth.getCurrentUser().getEmail();
        profileRef = mFireStore.collection("Users").document(email);

        if (!mCarModel.isEmpty()) {
            profileRef.update("mCarModel", mCarModel);
            Toast.makeText(Edit_Profile.this, "Car Model Updated", Toast.LENGTH_SHORT).show();
        }
        if (!mPhoneNum.isEmpty()) {
            profileRef.update("mPhoneNumber", mPhoneNum);
            Toast.makeText(Edit_Profile.this, "Phone Number Updated", Toast.LENGTH_SHORT).show();
        }
        if (!mPlateNum.isEmpty()) {
            profileRef.update("mPlateNumber", mPlateNum);
            Toast.makeText(Edit_Profile.this, "Plate Number Updated", Toast.LENGTH_SHORT).show();
        }


        Log.d(TAG, "DocumentSnapshot successfully updated!");
    }

}
