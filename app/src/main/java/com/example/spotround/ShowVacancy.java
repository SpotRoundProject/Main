package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowVacancy extends AppCompatActivity {
    public void showv(String s)
    {
        TextView branch=new TextView(this);
        TableRow tbbr=new TableRow(this);
        TableLayout stk;
        stk=findViewById(R.id.table1);
        TextView tv1=new TextView(this);
        TableRow tb=new TableRow(this);
        TableRow tb1=new TableRow(this);
        TableRow tb2=new TableRow(this);
        TableRow tb3=new TableRow(this);
        TableRow tb4=new TableRow(this);
        TableRow tb5=new TableRow(this);
        TableRow tb6=new TableRow(this);
        TableRow tb7=new TableRow(this);
        TableRow tb8=new TableRow(this);
        TableRow tb9=new TableRow(this);
        TableRow tb10=new TableRow(this);
        TableRow tb11=new TableRow(this);
        TableRow tb12=new TableRow(this);
        TableRow tb13=new TableRow(this);
        TableRow tb14=new TableRow(this);
        TableRow tb15=new TableRow(this);
        TextView tv=new TextView(this);
        TextView tv2=new TextView(this);
        TextView tv3=new TextView(this);
        TextView tv4=new TextView(this);
        TextView tv5=new TextView(this);
        TextView tv6=new TextView(this);
        TextView tv7=new TextView(this);
        TextView tv8=new TextView(this);
        TextView tv9=new TextView(this);
        TextView tv10=new TextView(this);
        TextView tv11=new TextView(this);
        TextView tv12=new TextView(this);
        TextView tv13=new TextView(this);
        TextView tv14=new TextView(this);
        TextView tv15=new TextView(this);
        TextView tv16=new TextView(this);
        TextView tv17=new TextView(this);
        TextView tv18=new TextView(this);
        TextView tv19=new TextView(this);
        TextView tv20=new TextView(this);
        TextView tv21=new TextView(this);
        TextView tv22=new TextView(this);
        TextView tv23=new TextView(this);
        TextView tv24=new TextView(this);
        TextView tv25=new TextView(this);
        TextView tv26=new TextView(this);
        TextView tv27=new TextView(this);
        TextView tv28=new TextView(this);
        TextView tv29=new TextView(this);
        TextView tv30=new TextView(this);
        TextView tv31=new TextView(this);
        FirebaseFirestore.getInstance().collection("Vacancy").document(s).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                branch.setText(s);
                tbbr.addView(branch);
                stk.addView(tbbr);
                String tmp2;
                String tmp=documentSnapshot.getString("gopens");
                tv.setText(tmp);
                tv1.setText(" gopens ");
                tb.addView(tv1);
                if(!(s.equals("Civil Engineering")))
                tv.setTextColor(Color.GREEN);
                tb.addView(tv);
                stk.addView(tb);
                tmp=documentSnapshot.getString("lopens");
                tmp2=" lopens ";
                tv2.setText(tmp2);
                tb1.addView(tv2);
                tv3.setText(tmp);
                tb1.addView(tv3);
                stk.addView(tb1);
                tmp=documentSnapshot.getString("gobcs");
                tv4.setText(" gobcs ");
                tb2.addView(tv4);
                tv5.setText(tmp);
                tb2.addView(tv5);
                stk.addView(tb2);
                tmp=documentSnapshot.getString("lobcs");
                tv6.setText(" lobcs ");
                tb3.addView(tv6);
                tv7.setText(tmp);
                tb3.addView(tv7);
                stk.addView(tb3);
                tmp=documentSnapshot.getString("gnt1s");
                tv8.setText(" gnt1s ");
                tb4.addView(tv8);
                tv9.setText(tmp);
                tb4.addView(tv9);
                stk.addView(tb4);
                tmp=documentSnapshot.getString("lnt1s");
                tv10.setText(" lnt1s ");
                tb5.addView(tv10);
                tv11.setText(tmp);
                tb5.addView(tv11);
                stk.addView(tb5);
                tmp=documentSnapshot.getString("gnt2s");
                tv12.setText(" gnt2s ");
                tb6.addView(tv12);
                tv13.setText(tmp);
                tb6.addView(tv13);
                stk.addView(tb6);
                tmp=documentSnapshot.getString("lnt2s");
                tv14.setText(" lnt2s ");
                tb7.addView(tv14);
                tv15.setText(tmp);
                tb7.addView(tv15);
                stk.addView(tb7);
                tmp=documentSnapshot.getString("gnt3s");
                tv16.setText( "gnt3s ");
                tb8.addView(tv16);
                tv17.setText(tmp);
                tb8.addView(tv17);
                stk.addView(tb8);
                tmp=documentSnapshot.getString("lnt3s");
                tv18.setText(" lnt3s ");
                tb9.addView(tv18);
                tv19.setText(tmp);
                tb9.addView(tv19);
                stk.addView(tb9);
                tmp=documentSnapshot.getString("gvjs");
                tv20.setText(" gvjs ");
                tb10.addView(tv20);
                tv21.setText(tmp);
                tb10.addView(tv21);
                stk.addView(tb10);
                tmp=documentSnapshot.getString("lvjs");
                tv22.setText(" lvjs ");
                tb11.addView(tv22);
                tv23.setText(tmp);
                tb11.addView(tv23);
                stk.addView(tb11);
                tmp=documentSnapshot.getString("gsts");
                tv24.setText(" gsts ");
                tb12.addView(tv24);
                tv25.setText(tmp);
                tb12.addView(tv25);
                stk.addView(tb12);
                tmp=documentSnapshot.getString("lsts");
                tv26.setText(" lsts ");
                tb13.addView(tv26);
                tv27.setText(tmp);
                tb13.addView(tv27);
                stk.addView(tb13);
                tmp=documentSnapshot.getString("gscs");
                tv28.setText(" gscs ");
                tb14.addView(tv28);
                tv29.setText(tmp);
                tb14.addView(tv29);
                stk.addView(tb14);
                tmp=documentSnapshot.getString("lscs");
                tv30.setText(" lscs ");
                tb15.addView(tv30);
                tv31.setText(tmp);
                tb15.addView(tv31);
                stk.addView(tb15);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vacancy);
        showv("Civil Engineering");
        showv("Information Technology");
        showv("Computer Science and Engineering");
        showv("Mechanical Engineering");
        showv("Electronics Engineering");
        showv("Electrical Engineering");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}