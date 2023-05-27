package com.example.getracefix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class About_Us extends AppCompatActivity implements View.OnClickListener {
    Intent Back1;
    Button Return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__us);

        Return = (Button)findViewById(R.id.return_btn);
        Return.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == Return){
            Back1 = new Intent(About_Us.this , HomeProfile.class);
            startActivity(Back1);
        }
    }
}
