package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class updateprofile extends AppCompatActivity {
    private EditText name1;
    private EditText dob;
    private Spinner gender;
    private Spinner caste2;
    private Spinner ews;
    private Spinner pwd;
    private Spinner home;
    private  Spinner orphan;
    private Spinner defense;
    private EditText ssc;
    private EditText hsc;
    private EditText pcm;
    private EditText smn;
    private EditText cet;
    private Button save;
    private FirebaseFirestore mFirestore;

    private Spinner first1;
    private Spinner second1;
    private Spinner third1;
    private Spinner forth1;
    private Spinner fifth1;
    private Spinner sixth1;
    private static  int count=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);
        name1=findViewById(R.id.name1);
        dob=findViewById(R.id.name2);
        gender=findViewById(R.id.gender2);
        caste2=findViewById(R.id.caste1);
        ews=findViewById(R.id.yn1);
        pwd=findViewById(R.id.yn2);
        home=findViewById(R.id.yn4);
        orphan=findViewById(R.id.yn5);
        defense=findViewById(R.id.yn6);
        ssc=findViewById(R.id.ssc);
        hsc=findViewById(R.id.Hsc);
        pcm=findViewById(R.id.pcm);
        smn=findViewById(R.id.smn);
        cet=findViewById(R.id.cetp);
        save=findViewById(R.id.save1);
        first1=findViewById(R.id.firstp);
        second1=findViewById(R.id.secondp);
        third1=findViewById(R.id.thirdp);
        forth1=findViewById(R.id.fourthp);
        fifth1=findViewById(R.id.fifthp);
        sixth1=findViewById(R.id.sixthp);
        mFirestore=FirebaseFirestore.getInstance();


        ArrayAdapter<String> gender1=new ArrayAdapter<String>(updateprofile.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.gender5));
        gender1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(gender1);
        ArrayAdapter<String> myadapter=new ArrayAdapter<String>(updateprofile.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.name));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        caste2.setAdapter(myadapter);
        ArrayAdapter<String> yesno=new ArrayAdapter<String>(updateprofile.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.yn));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ews.setAdapter(yesno);
        pwd.setAdapter(yesno);
        home.setAdapter(yesno);
        orphan.setAdapter(yesno);
        defense.setAdapter(yesno);
        ArrayAdapter<String>pref=new ArrayAdapter<>(updateprofile.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.preference));
        pref.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        first1.setAdapter(pref);
        second1.setAdapter(pref);
        third1.setAdapter(pref);
        forth1.setAdapter(pref);
        fifth1.setAdapter(pref);
        sixth1.setAdapter(pref);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(name1.getText().toString().equals("")||dob.getText().toString().equals("")||ssc.getText().toString().equals("")||hsc.getText().toString().equals("")||ssc.getText().toString().equals("")||pcm.getText().toString().equals("")||smn.getText().toString().equals("")||cet.getText().toString().equals(""))
                {
                    Toast.makeText(updateprofile.this,"All fields are compulsory to fill",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),InsideStudentlogin.class));
                    finish();
                }
                else
                {
                    count=1;
                    String txt_name=name1.getText().toString();
                    String txt_dob=dob.getText().toString();
                    String txt_gender=gender.getSelectedItem().toString();
                    String txt_caste2=caste2.getSelectedItem().toString();
                    String txt_ews=ews.getSelectedItem().toString();
                    String txt_pwd=pwd.getSelectedItem().toString();
                    String txt_home=home.getSelectedItem().toString();
                    String txt_orphan=orphan.getSelectedItem().toString();
                    String txt_defense=defense.getSelectedItem().toString();
                    String txt_hsc=hsc.getText().toString();
                    String txt_ssc=ssc.getText().toString();
                    String txt_pcm=pcm.getText().toString();
                    String txt_cet=cet.getText().toString();
                    String txt_smn=smn.getText().toString();
                    String txt_first=first1.getSelectedItem().toString();
                    String txt_second=second1.getSelectedItem().toString();
                    String txt_third=third1.getSelectedItem().toString();
                    String txt_forth=forth1.getSelectedItem().toString();
                    String txt_fifth=fifth1.getSelectedItem().toString();
                    String txt_sixth=sixth1.getSelectedItem().toString();
                    String txt_status="wait";
                    String id1= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                    ProfileInfo p=new ProfileInfo(txt_name,txt_dob,txt_gender,txt_caste2,txt_ews,txt_pwd,txt_home,txt_orphan,txt_defense,txt_hsc,txt_ssc,txt_pcm,txt_smn,txt_cet,txt_first,txt_second,txt_third,txt_forth,txt_fifth,txt_sixth,txt_status);
                //mFirestore.collection("StudentInfo").add(p);
                    DocumentReference db=mFirestore.collection("StudentInfo").document(id1);
                    db.set(p);
                    Toast.makeText(updateprofile.this,"Applied Successfully now make Payment",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),PaymentActivity.class));

                }
            }
        });


    }
}