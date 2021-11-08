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
            Toast.makeText(getApplicationContext(),"All fiels are necessary",Toast.LENGTH_LONG).show();
        }
        else
        {
            NewCategory fill=new NewCategory(gopens,gobcs,gscs,gsts,gnt1s,gvj1,gnt2s,gnt3s,lopens,lobcs,lscs,lsts,lvjs,lnt1s,lnt2s,lnt3s);
            FirebaseFirestore.getInstance().collection("Vacancy").document("Round").collection(r).document(b).set(fill);
            Toast.makeText(UpdateVacancy.this, "Vacancy updated successfully", Toast.LENGTH_SHORT).show();
            tgopen.setText("");
            tgobc.setText("");
            tgsc.setText("");
            tgst.setText("");
            tgvj.setText("");
            tgnt1.setText("");
            tgnt2.setText("");
            tgnt3.setText("");
            tlopen.setText("");
            tlobc.setText("");
            tlsc.setText("");
            tlst.setText("");
            tlvj.setText("");
            tlnt1.setText("");
            tlnt2.setText("");
            tlnt3.setText("");
        }

    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}