package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdVacancyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText gopen,gobc,gst,gsc,gvj,gnt1,gnt2,gnt3,lopen,lobc,lsc,lst,lvj,lnt1,lnt2,lnt3,ews,pwd,def;
    private Button save;
    private Spinner branch;
    String br;
    String[] branches={"Select Branch","Computer Science and Engineering","Information Technology","Mechanical Engineering","Electronics Engineering","Electrical Engineering","Civil Engineering"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upd_vacancy);
        gopen = findViewById(R.id.gopenedtxt);
        gobc = findViewById(R.id.gobcedtxt);
        gsc = findViewById(R.id.gscedtxt);
        gst = findViewById(R.id.gstedtxt);
        gvj = findViewById(R.id.gvjedtxt);
        gnt1 = findViewById(R.id.gnt1edtxt);
        gnt2 = findViewById(R.id.gnt2edtxt);
        gnt3 = findViewById(R.id.gnt3edtxt);
        lopen = findViewById(R.id.lopenedtxt);
        lobc = findViewById(R.id.lobcedtxt);
        lsc = findViewById(R.id.lscedtxt);
        lst = findViewById(R.id.lstedtxt);
        lvj = findViewById(R.id.lvjedtxt);
        lnt1 = findViewById(R.id.lnt1edtxt);
        lnt2 = findViewById(R.id.lnt2edtxt);
        lnt3 = findViewById(R.id.lnt3edtxt);
        ews = findViewById(R.id.ewsedtxt);
        pwd = findViewById(R.id.pwdedtxt);
        def = findViewById(R.id.defedtxt);

        save = findViewById(R.id.savebutton);
        branch = findViewById(R.id.spinnerbranch);
        branch.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, branches);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branch.setAdapter(ad);
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        DatabaseReference node=db.getReference("Vacancy");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(br=="Select Branch")
                    Toast.makeText(UpdVacancyActivity.this, "Please select a branch", Toast.LENGTH_SHORT).show();
                else
                {
                    String gopens=gopen.getText().toString().trim();
                    String gobcs=gobc.getText().toString().trim();
                    String gscs=gsc.getText().toString().trim();
                    String gsts=gst.getText().toString().trim();
                    String gvj1=gvj.getText().toString().trim();
                    String gnt1s=gnt1.getText().toString().trim();
                    String gnt2s=gnt2.getText().toString().trim();
                    String gnt3s=gnt3.getText().toString().trim();
                    String lopens=lopen.getText().toString().trim();
                    String lobcs=lobc.getText().toString().trim();
                    String lscs=lsc.getText().toString().trim();
                    String lsts=lst.getText().toString().trim();
                    String lvjs=lvj.getText().toString().trim();
                    String lnt1s=lnt1.getText().toString().trim();
                    String lnt2s=lnt2.getText().toString().trim();
                    String lnt3s=lnt3.getText().toString().trim();
                    String ewss=ews.getText().toString().trim();
                    String pwds=pwd.getText().toString().trim();
                    String defs=def.getText().toString().trim();
                    Category obj=new Category(gopens,gobcs,gscs,gsts,gvj1,gnt1s,gnt2s,gnt3s,lopens,lobcs,lscs,lsts,lvjs,lnt1s,lnt2s,lnt3s,ewss,pwds,defs);
                    node.child(br).setValue(obj);
                    DocumentReference dref= FirebaseFirestore.getInstance().collection("Vacancy").document(br);
                    dref.set(obj);
                    //Toast.makeText(UpdVacancyActivity.this,gopens,Toast.LENGTH_LONG).show();
                    Toast.makeText(UpdVacancyActivity.this, "Vacancy updated successfully", Toast.LENGTH_SHORT).show();
                    gopen.setText("");
                    gobc.setText("");
                    gsc.setText("");
                    gst.setText("");
                    gvj.setText("");
                    gnt1.setText("");
                    gnt2.setText("");
                    gnt3.setText("");
                    lopen.setText("");
                    lobc.setText("");
                    lsc.setText("");
                    lst.setText("");
                    lvj.setText("");
                    lnt1.setText("");
                    lnt2.setText("");
                    lnt3.setText("");
                    ews.setText("");
                    pwd.setText("");
                    def.setText("");
                }
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        br=branch.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}