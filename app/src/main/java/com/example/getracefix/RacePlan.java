package com.example.getracefix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RacePlan extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Intent GoRace , home ;
    Spinner s1,s2 , s3;
     Button Go , Home;
     String Destination , Origin , Email , phoneNumber , carModel, plateNumber ;
     Race Race1;
     private FirebaseFirestore mFireStore;
     private FirebaseAuth mAuth;
     private DocumentReference documentReference , raceReference;
    private static String TAG = "RacePlan";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_plan);

        Home = (Button)findViewById(R.id.button3);
        Home.setOnClickListener(this);

        Go = (Button)findViewById(R.id.Go_btn);
        Go.setOnClickListener(this);

            mAuth = FirebaseAuth.getInstance();
            mFireStore = FirebaseFirestore.getInstance();

            s1 = (Spinner)findViewById(R.id.spinner1);
            s2 = (Spinner)findViewById(R.id.spinner2);
            s3 = (Spinner)findViewById(R.id.spinner3);
            s1.setOnItemSelectedListener(this);


        GoRace = getIntent();
    }

//conditional spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sp1= String.valueOf(s1.getSelectedItem());
        if(sp1.contentEquals("Tiberias")) {
            List<String> list = new ArrayList<String>();
            list.add("Paz Junction");
            list.add("Golani");
            list.add("Central Station");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            s2.setAdapter(dataAdapter);
            s3.setAdapter(dataAdapter);
        }

        if(sp1.contentEquals("Haifa")) {
            List<String> list = new ArrayList<String>();
            list.add("Technion");
            list.add("University");
            list.add("Bay Center");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            s2.setAdapter(dataAdapter2);
            s3.setAdapter(dataAdapter2);
        }
        if(sp1.contentEquals("Tel Aviv")) {
            List<String> list = new ArrayList<String>();
            list.add("Ben Gurion Airport");
            list.add("Azrieli");
            list.add("Gordon Beach");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            s2.setAdapter(dataAdapter2);
            s3.setAdapter(dataAdapter2);
        }
        if(sp1.contentEquals("Eilat")) {
            List<String> list = new ArrayList<String>();
            list.add("Ice Mall");
            list.add("Timmna Airport");
            list.add("Sea Mall");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            s2.setAdapter(dataAdapter2);
            s3.setAdapter(dataAdapter2);
        }

                   }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {
        if (v == Go) {
             newDocument();
             home = new Intent(RacePlan.this , HomeProfile.class);
             startActivity(home);

        }

        if (v == Home){
            home = new Intent(RacePlan.this , HomeProfile.class);
            startActivity(home);
        }

    }

    //Creating New Race...
    void newDocument() {
        Email = mAuth.getCurrentUser().getEmail();

        documentReference = mFireStore.collection("Users").document(Email);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                carModel = documentSnapshot.getString("mCarModel");
                plateNumber = documentSnapshot.getString("mPlateNumber");
                phoneNumber = documentSnapshot.getString("mPhoneNumber");
                Origin = String.valueOf(s2.getSelectedItem());
                Destination = String.valueOf(s3.getSelectedItem());
                Race1 = new Race(Origin, Destination, Email, phoneNumber, carModel, plateNumber);
                raceReference = mFireStore.collection("Races").document(Email);
                raceReference.set(Race1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RacePlan.this, "Comment saved", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "DocumentSnapshot successfully created!");
                            }
                        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RacePlan.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
   });



  }
}