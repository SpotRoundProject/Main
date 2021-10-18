package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ShowVacancy extends AppCompatActivity {
    TableLayout stk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vacancy);
        stk=findViewById(R.id.table1);
        TextView tv1=new TextView(this);
        TableRow tb=new TableRow(this);
        TextView tv=new TextView(this);
        FirebaseFirestore.getInstance().collection("Vacancy").document("Civil Engineering").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {

                String tmp=documentSnapshot.getString("gopens");
                tv.setText(tmp);
                tv1.setText("gopens");
                tb.addView(tv1);
                tv.setTextColor(Color.BLACK);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("lopens");
                tv1.setText("gopens");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("gobcs");
                tv1.setText("gobcs");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("lobcs");
                tv1.setText("lobcs");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("gnt1s");
                tv1.setText("gnt1s");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("lnt1s");
                tv1.setText("lnt1s");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("gnt2s");
                tv1.setText("gnt2s");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("lnt2s");
                tv1.setText("lnt2s");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("gnt3s");
                tv1.setText("gnt3s");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("lnt3s");
                tv1.setText("lnt3s");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("gvjs");
                tv1.setText("gvjs");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("lvjs");
                tv1.setText("lvjs");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("gsts");
                tv1.setText("gsts");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("lsts");
                tv1.setText("lsts");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("gscs");
                tv1.setText("gscs");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("lscs");
                tv1.setText("lscs");
                tb.addView(tv1);
                tv.setText(tmp);
                tb.addView(tv);
                stk.addView(tb);

            }
        });
    }
}