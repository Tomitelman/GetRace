package com.example.getracefix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Lobby extends AppCompatActivity implements View.OnClickListener {
 final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1 ;
    CollectionReference collectionReference;
     RecyclerView races;
     FirebaseFirestore firebaseFirestore;
     FirebaseAuth mAuth;
     ListAdapter listAdapter;
     String joinPhone, orgPhone, orgCar, origin, destination, email , joinCar, orgMessage;
     DocumentReference documentReference , raceReference;//pointers
    String message = "Hi";
     Button Return;
     Intent goHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Return = (Button)findViewById(R.id.button2);
        Return.setOnClickListener(this);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        races = (RecyclerView)findViewById(R.id.raceview_rec);

        email = mAuth.getCurrentUser().getEmail();
        collectionReference = firebaseFirestore.collection("Races");
      setUpRecyclerView();
    }
    //send SMS , Asking Permission
    private void sendSms(String phoneNumber, String message){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, null, null);
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phoneNumber, null, message, null, null);
            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    //set the recyclerview and get the data from race collection
    private void setUpRecyclerView() {
        Query query = collectionReference.orderBy("carModel", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Race> options = new FirestoreRecyclerOptions.Builder<Race>()
                .setQuery(query, Race.class)
                .build();

        listAdapter = new ListAdapter(options);

        races.setHasFixedSize(true);
        races.setLayoutManager(new LinearLayoutManager(this));
        races.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                orgCar = listAdapter.getItem(position).getCarModel();
                orgPhone = listAdapter.getItem(position).getPhoneNumber();
                origin = listAdapter.getItem(position).getOrigin();
                destination = listAdapter.getItem(position).getDestination();
                documentReference = firebaseFirestore.collection("Users").document(email);
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        joinPhone = documentSnapshot.getString("mPhoneNumber");
                        joinCar = documentSnapshot.getString("mCarModel");
                        message = "Hello, You are joined successfully to race from "+origin+" To "+destination+"\n"+"Organizer details are: Phone "+orgPhone+", His car model is: "+orgCar+" Good Luck!";
                        orgMessage = "Hello, Someone has joined to your race, contact him via phone "+joinPhone+"\n"+"Your rival car model is: "+joinCar+"\n"+"Good Luck!";


                        sendSms(joinPhone, message);
                        sendSms(orgPhone, orgMessage);
                    }
                });

            }

        });
    }
            @Override
            protected void onStart() {
                super.onStart();
                listAdapter.startListening();
            }

            @Override
            protected void onStop() {
                super.onStop();
                listAdapter.stopListening();
            }


    @Override
    public void onClick(View v) {
        if (v == Return){
            goHome = new Intent(Lobby.this , HomeProfile.class);
            startActivity(goHome);
        }

    }
}

