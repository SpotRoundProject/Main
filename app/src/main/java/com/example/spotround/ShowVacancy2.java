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

import com.example.spotround.modle.NewCategory;
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
        FirebaseFirestore.getInstance().collection("Vacancy").document("Round").
                collection(r).document(b).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                NewCategory category = documentSnapshot.toObject(NewCategory.class);
               tgopen.setText(category.getGopens());
               if(!(category.getGopens() == 0))
                   tgopen.setTextColor(Color.GREEN);
               else
                   tgopen.setTextColor(Color.RED);

                tlopen.setText(category.getLopens());
                if(!(category.getLopens() == 0))
                    tlopen.setTextColor(Color.GREEN);
                else
                    tlopen.setTextColor(Color.RED);

                tgobc.setText(category.getGobcs());
                if(!(category.getGobcs() == 0))
                    tgobc.setTextColor(Color.GREEN);
                else
                    tgobc.setTextColor(Color.RED);

                tlobc.setText(category.getLobcs());
                if(!(category.getLobcs() == 0))
                    tlobc.setTextColor(Color.GREEN);
                else
                    tlobc.setTextColor(Color.RED);

                tgnt1.setText(category.getGnt1s());
                if(!(category.getGnt1s() == 0))
                    tgnt1.setTextColor(Color.GREEN);
                else
                    tgnt1.setTextColor(Color.RED);

                tlnt1.setText(category.getLnt1s());
                if(!(category.getLnt1s() == 0))
                    tlnt1.setTextColor(Color.GREEN);
                else
                    tlnt1.setTextColor(Color.RED);

                tgnt2.setText(category.getGnt2s());
                if(!(category.getGnt2s() == 0))
                    tgnt2.setTextColor(Color.GREEN);
                else
                    tgnt2.setTextColor(Color.RED);

                tlnt2.setText(category.getLnt2s());
                if(!(category.getLnt2s() == 0))
                    tlnt2.setTextColor(Color.GREEN);
                else
                    tlnt2.setTextColor(Color.RED);

                tgnt3.setText(category.getGnt3s());
                if(!(category.getGnt3s() == 0))
                    tgnt3.setTextColor(Color.GREEN);
                else
                    tgnt3.setTextColor(Color.RED);

                tlnt3.setText(category.getLnt3s());
                if(!(category.getLnt3s() == 0))
                    tlnt3.setTextColor(Color.GREEN);
                else
                    tlnt3.setTextColor(Color.RED);

                tgvj.setText(category.getGvjs());
                if(!(category.getGvjs() == 0))
                    tgvj.setTextColor(Color.GREEN);
                else
                    tgvj.setTextColor(Color.RED);

                tlvj.setText(category.getLvjs());
                if(!(category.getLvjs() == 0))
                    tlvj.setTextColor(Color.GREEN);
                else
                    tlvj.setTextColor(Color.RED);

                tgst.setText(category.getGsts());
                if(!(category.getGsts() == 0))
                    tgst.setTextColor(Color.GREEN);
                else
                    tgst.setTextColor(Color.RED);

                tlst.setText(category.getLsts());
                if(!(category.getLsts() == 0))
                    tlst.setTextColor(Color.GREEN);
                else
                    tlst.setTextColor(Color.RED);

                tgsc.setText(category.getGscs());
                if(!(category.getGscs() == 0))
                    tgsc.setTextColor(Color.GREEN);
                else
                    tgsc.setTextColor(Color.RED);

                tlsc.setText(category.getLscs());
                if(!(category.getLscs() == 0))
                    tlsc.setTextColor(Color.GREEN);
                else
                    tlsc.setTextColor(Color.RED);


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
                    Toast.makeText(ShowVacancy2.this,"Please select valid Branch and Round Number.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    showme(branchs,rounds);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}