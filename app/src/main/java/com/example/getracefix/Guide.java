package com.example.getracefix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Guide extends AppCompatActivity implements View.OnClickListener {

    Intent Back;
     Button Return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        Return = (Button)findViewById(R.id.button);
        Return.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == Return) {
            Back = new Intent(Guide.this , HomeProfile.class);
            startActivity(Back);
        }
    }
}
