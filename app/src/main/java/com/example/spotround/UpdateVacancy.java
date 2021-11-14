package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotround.databinding.ActivityUpdateVacancyBinding;
import com.example.spotround.modle.NewCategory;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateVacancy extends AppCompatActivity {
    Spinner sb;
    Spinner sr;
    Button bs;
    ActivityUpdateVacancyBinding binding;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateVacancyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String [] sbranch={"Select Branch","Computer Science and Engineering","Information Technology","Mechanical Engineering","Electronics Engineering","Electrical Engineering","Civil Engineering"};
        String [] sround={"Select Round","Round 1","Round 2","Round 3"};
        sb=findViewById(R.id.spinner2);
        bs=findViewById(R.id.bs);
        ArrayAdapter ac = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,sbranch);
        ac.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sb.setAdapter(ac);
        sr=findViewById(R.id.spinner);
        ArrayAdapter ar = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,sround);
        ar.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sr.setAdapter(ar);
        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String branchs=sb.getSelectedItem().toString();
                String rounds=sr.getSelectedItem().toString();
                if(branchs.equals("Select Branch")||rounds.equals("Select Round"))
                {
                    Toast.makeText(getApplicationContext(),"Please select valid Branch and Round Number.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    showMe(branchs,rounds);

                }

            }
        });
    }

    public void showMe(String b,String r) {
        EditText tgopen = findViewById(R.id.text3);
        EditText tlopen = findViewById(R.id.text8);
        EditText tgobc = findViewById(R.id.text9);
        EditText tlobc = findViewById(R.id.text10);
        EditText tgnt1 = findViewById(R.id.text11);
        EditText tlnt1 = findViewById(R.id.text16);
        EditText tgnt2 = findViewById(R.id.text17);
        EditText tlnt2 = findViewById(R.id.text18);
        EditText tgnt3 = findViewById(R.id.text23);
        EditText tlnt3 = findViewById(R.id.text24);
        EditText tgvj = findViewById(R.id.text25);
        EditText tlvj = findViewById(R.id.text26);
        EditText tgst = findViewById(R.id.text31);
        EditText tlst = findViewById(R.id.text32);
        EditText tgsc = findViewById(R.id.text33);
        EditText tlsc = findViewById(R.id.text29);
        String gopens=tgopen.getText().toString().trim();
        String gobcs=tgobc.getText().toString().trim();
        String gscs=tgsc.getText().toString().trim();
        String gsts=tgst.getText().toString().trim();
        String gvj1=tgvj.getText().toString().trim();
        String gnt1s=tgnt1.getText().toString().trim();
        String gnt2s=tgnt2.getText().toString().trim();
        String gnt3s=tgnt3.getText().toString().trim();
        String lopens=tlopen.getText().toString().trim();
        String lobcs=tlobc.getText().toString().trim();
        String lscs=tlsc.getText().toString().trim();
        String lsts=tlst.getText().toString().trim();
        String lvjs=tlvj.getText().toString().trim();
        String lnt1s=tlnt1.getText().toString().trim();
        String lnt2s=tlnt2.getText().toString().trim();
        String lnt3s=tlnt3.getText().toString().trim();
        if(gopens.equals("")||gobcs.equals("")||gscs.equals("")||gsts.equals("")||gvj1.equals("")||gnt1s.equals("")||gnt2s.equals("")||gnt3s.equals("")||lopens.equals("")||lobcs.equals("")||lscs.equals("")||lsts.equals("")||lnt1s.equals("")||lvjs.equals("")||lnt2s.equals("")||lnt3s.equals(""))
        {
            Toast.makeText(getApplicationContext(),"All fields are necessary",Toast.LENGTH_LONG).show();
        }
        else
        {
            NewCategory fill=new NewCategory(Integer.parseInt(gopens), Integer.parseInt(gobcs), Integer.parseInt(gscs),
                    Integer.parseInt(gsts), Integer.parseInt(gnt1s), Integer.parseInt(gvj1), Integer.parseInt(gnt2s),
                            Integer.parseInt(gnt3s), Integer.parseInt(lopens), Integer.parseInt(lobcs), Integer.parseInt(lscs),
                                    Integer.parseInt(lsts), Integer.parseInt(lvjs), Integer.parseInt(lnt1s), Integer.parseInt(lnt2s),
                                            Integer.parseInt(lnt3s));
            FirebaseFirestore.getInstance().collection("Vacancy").document("Round").collection(r).document(b).set(fill);
            Toast.makeText(UpdateVacancy.this, "Vacancy updated successfully", Toast.LENGTH_SHORT).show();
            tgopen.setText("0");
            tgobc.setText("0");
            tgsc.setText("0");
            tgst.setText("0");
            tgvj.setText("0");
            tgnt1.setText("0");
            tgnt2.setText("0");
            tgnt3.setText("0");
            tlopen.setText("0");
            tlobc.setText("0");
            tlsc.setText("0");
            tlst.setText("0");
            tlvj.setText("0");
            tlnt1.setText("0");
            tlnt2.setText("0");
            tlnt3.setText("0");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}