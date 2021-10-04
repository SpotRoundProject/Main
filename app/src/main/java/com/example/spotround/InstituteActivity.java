package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstituteActivity extends AppCompatActivity {
    private Button update, candidate, result;

    public InstituteActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute);
        update=findViewById(R.id.updvacanbutton);
        candidate=findViewById(R.id.viewcandbutton);
        result=findViewById(R.id.viewresultbutton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstituteActivity.this, UpdVacancyActivity.class));
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