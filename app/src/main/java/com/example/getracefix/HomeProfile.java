package com.example.getracefix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeProfile extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Button Logout , Race , Join;
    Intent Go , GoRace , signout , menuintent , joinRace;
    private FirebaseAuth mAuth;
    ToggleButton serviceMusic;
    SharedPreferences sp;
    boolean isServiceOn = false;
    TextView carModelInfo , phoneNumberInfo , plateNumberInfo;
    String mCarModelInfo , mPhoneNumberInfo, mPlateNumberInfo, email;
    //DocumentSnapshot documentSnapshot;
    private DocumentReference profileRef;
    private FirebaseFirestore mFireStore;
    private MediaPlayerService mediaPlayerService;

    BroadCastBattery broadCastBattery;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        broadCastBattery=new BroadCastBattery();

        sp = getSharedPreferences("musicService", 0);

        carModelInfo = (TextView)findViewById(R.id.carmodelinfo_tv);
        phoneNumberInfo = (TextView)findViewById(R.id.phonenumber_tv);
        plateNumberInfo = (TextView)findViewById(R.id.platenumber_tv);

        serviceMusic = (ToggleButton)findViewById(R.id.toggleButton);
        serviceMusic.setOnCheckedChangeListener(this);

        mFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();



        Join = (Button)findViewById(R.id.joinRace_btn);
        Join.setOnClickListener(this);

        Race = (Button)findViewById(R.id.Race_btn);
        Race.setOnClickListener(this);

        Logout = (Button)findViewById(R.id.Logout_btn);
        Logout.setOnClickListener(this);

        GoRace = getIntent();
        Go = getIntent();
        showProfile();

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
              startFlash();
            }
        },3000); //sleep 3000 milliSeconds before flashing the button
    }

      public void startFlash(){
        //Animation creation

          Animation mAnimation = new AlphaAnimation(1,0);

          mAnimation.setDuration(600);

          mAnimation.setInterpolator(new LinearInterpolator());

          mAnimation.setRepeatCount(10);

          mAnimation.setRepeatMode(Animation.REVERSE);

          Join.startAnimation(mAnimation);


      }
    private class BroadCastBattery extends BroadcastReceiver

    {



        @Override

        public void onReceive(Context context, Intent intent) {

            int battery = intent.getIntExtra("level",0);
                      if (battery < 15) {
                          Toast.makeText(HomeProfile.this , "Battery Low , Charge Your Phone!" , Toast.LENGTH_SHORT).show();
                      }



        }

    }



    @Override

    protected void onResume() {

        super.onResume();

        registerReceiver(broadCastBattery,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }



    @Override

    protected void onPause() {

        super.onPause();

        unregisterReceiver(broadCastBattery);

    }



    @Override
       public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homemenu,menu);
            return true ;
       }

       @Override
       public boolean onOptionsItemSelected(MenuItem item)
       {
           switch (item.getItemId()){
               case R.id.about:
               menuintent = new Intent(HomeProfile.this , About_Us.class) ;
               startActivity(menuintent);
               return true;

               case R.id.guide:
                   menuintent = new Intent(HomeProfile.this , Guide.class) ;
                   startActivity(menuintent);
                   return true;

               case R.id.profile:
                   menuintent = new Intent(HomeProfile.this , Edit_Profile.class) ;
                   startActivity(menuintent);
                   return true;


               default:
                   return super.onOptionsItemSelected(item);

           }
       }
    @Override
    public void onClick(View v) {
        if (v == Race){
            GoRace = new Intent(this , RacePlan.class);
            startActivity(GoRace);

        }
        if(v == Logout){
            mAuth.getInstance().signOut();
            signout = new Intent(this , MainActivity.class);
            finish();
            startActivity(signout);
        }



        if (v == Join){
            joinRace = new Intent(this , Lobby.class);
            startActivity(joinRace);

        }


    }
    private void changeMusicServiceStatus(String stat)
    {
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("serviceOn",stat);
        editor.commit();
    }
    private boolean isMusicServiceOn()
    {

        String status = sp.getString("serviceOn", null);
        if( status == null )
        {
            changeMusicServiceStatus("no");
            return false;
        }
        else if( status.equals("yes"))
            return true;
        else
            return false;
    }

    private void showProfile() {
        email = mAuth.getCurrentUser().getEmail();
        profileRef = mFireStore.collection("Users").document(email);
        profileRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mCarModelInfo = documentSnapshot.getString("mCarModel");
                mPhoneNumberInfo = documentSnapshot.getString("mPhoneNumber");
                mPlateNumberInfo = documentSnapshot.getString("mPlateNumber");

                carModelInfo.setText(mCarModelInfo);
                phoneNumberInfo.setText(mPhoneNumberInfo);
                plateNumberInfo.setText(mPlateNumberInfo);

            }
        });
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
           if (isChecked && !isMusicServiceOn()) {
               startService(new Intent(this, MediaPlayerService.class));
               changeMusicServiceStatus("yes");
           } if(!isChecked){
               stopService(new Intent(HomeProfile.this, MediaPlayerService.class));
               changeMusicServiceStatus("no");

           }
       }
    }

