package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class ShowVacancy2 extends AppCompatActivity
{
    Spinner sb;
    Spinner sr;
    Button bs;
    public void showme(String b,String r)
    {
        TextView tgopen=findViewById(R.id.text3);
        TextView tlopen=findViewById(R.id.text8);
        TextView tgobc=findViewById(R.id.text9);
        TextView tlobc=findViewById(R.id.text10);
        TextView tgnt1=findViewById(R.id.text11);
        TextView tlnt1=findViewById(R.id.text16);
        TextView tgnt2=findViewById(R.id.text17);
        TextView tlnt2=findViewById(R.id.text18);
        TextView tgnt3=findViewById(R.id.text23);
        TextView tlnt3=findViewById(R.id.text24);
        TextView tgvj=findViewById(R.id.text25);
        TextView tlvj=findViewById(R.id.text26);
        TextView tgst=findViewById(R.id.text31);
        TextView tlst=findViewById(R.id.text32);
        TextView tgsc=findViewById(R.id.text33);
        TextView tlsc=findViewById(R.id.text29);
        FirebaseFirestore.getInstance().collection("Vacancy").document("Round").collection(r).document(b).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
               String tmp3=documentSnapshot.getString("gopens");
               tgopen.setText(tmp3);
               if(!tmp3.equals("0"))
                   tgopen.setTextColor(Color.GREEN);
               tmp3="";
                tmp3=documentSnapshot.getString("lopens");
                tlopen.setText(tmp3);
                if(!tmp3.equals("0"))
                    tlopen.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("gobcs");
                tgobc.setText(tmp3);
                if(!tmp3.equals("0"))
                    tgobc.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("lobcs");
                tlobc.setText(tmp3);
                if(!tmp3.equals("0"))
                    tlobc.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("gnt1s");
                tgnt1.setText(tmp3);
                if(!tmp3.equals("0"))
                    tgnt1.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("lnt1s");
                tlnt1.setText(tmp3);
                if(!tmp3.equals("0"))
                    tlnt1.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("gnt2s");
                tgnt2.setText(tmp3);
                if(!tmp3.equals("0"))
                    tgnt2.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("lnt2s");
                tlnt2.setText(tmp3);
                if(!tmp3.equals("0"))
                    tlnt2.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("gnt3s");
                tgnt3.setText(tmp3);
                if(!tmp3.equals("0"))
                    tgnt3.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("lnt3s");
                tlnt3.setText(tmp3);
                if(!tmp3.equals("0"))
                    tlnt3.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("gvjs");
                tgvj.setText(tmp3);
                if(!tmp3.equals("0"))
                    tgvj.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("lvjs");
                tlvj.setText(tmp3);
                if(!tmp3.equals("0"))
                    tlvj.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("gsts");
                tgst.setText(tmp3);
                if(!tmp3.equals("0"))
                    tgst.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("lsts");
                tlst.setText(tmp3);
                if(!tmp3.equals("0"))
                    tlst.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("gscs");
                tgsc.setText(tmp3);
                if(!tmp3.equals("0"))
                    tgsc.setTextColor(Color.GREEN);
                tmp3="";
                tmp3=documentSnapshot.getString("lscs");
                tlsc.setText(tmp3);
                if(!tmp3.equals("0"))
                    tlsc.setTextColor(Color.GREEN);


            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vacancy2);

        String [] sbranch={"Select Branch","Computer Science and Engineering","Information Technology","Mechanical Engineering","Electronics Engineering","Electrical Engineering","Civil Engineering"};
        String [] sround={"Select Round","Round 1","Round 2","Round 3"};
        sb=findViewById(R.id.spinner2);
        bs=findViewById(R.id.bs);
        ArrayAdapter ab=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,sbranch);
        ab.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sb.setAdapter(ab);
        sr=findViewById(R.id.spinner);
        ArrayAdapter ar=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,sround);
        ar.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sr.setAdapter(ar);
        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String branchs=sb.getSelectedItem().toString();
                String rounds=sr.getSelectedItem().toString();
                if(branchs.equals("Select Branch")||rounds.equals("Select Round"))
                {
                    Toast.makeText(ShowVacancy2.this,"Please select valid Branch and Round Number.",Toast.LENGTH_LONG);
                }
                else
                {
                    showme(branchs,rounds);
                }

            }
        });



    }
}