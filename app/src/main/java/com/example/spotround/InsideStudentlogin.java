package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InsideStudentlogin extends AppCompatActivity {
    private Button profile;
    private Button apply;
    private Button result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_studentlogin);
        profile=findViewById(R.id.profile);
        //apply=findViewById(R.id.Apply);
        result=findViewById(R.id.result);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),updateprofile.class));
            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ComputeResult.class));
            }
        });

    }
}