package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotround.databinding.ActivityShowVacancy2Binding;
import com.example.spotround.datetime.DateTime;
import com.example.spotround.modle.NewCategory;
import com.example.spotround.modle.Schedule;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.w3c.dom.Text;
import java.util.Calendar;

public class ShowVacancy2 extends AppCompatActivity {
    ActivityShowVacancy2Binding binding;
    Schedule schedule;
    Calendar local, r1S, r1E, r2S, r2E, r3S, r3E;
    SharedPreferences mPrefs;
    String round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowVacancy2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mPrefs = getSharedPreferences("com.example.spotround", MODE_PRIVATE);
        Gson gson = new Gson();
        if(mPrefs.getBoolean("flagSchedule", false)) {
            String json = mPrefs.getString("Schedule", "");
            schedule = gson.fromJson(json, Schedule.class);
            Log.d("Schedule Error", schedule.toString());
        }

        local = Calendar.getInstance();
        local.set(DateTime.getYear(), DateTime.getMonth() + 1, DateTime.getDate(), DateTime.getHr(), DateTime.getMin());
        r1S = Calendar.getInstance();
        r1E = Calendar.getInstance();
        r2S = Calendar.getInstance();
        r2E = Calendar.getInstance();
        r3S = Calendar.getInstance();
        r3E = Calendar.getInstance();
        r1S.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound1Start().substring(0, 2)), Integer.parseInt(schedule.getRound1Start().substring(3, 5)));
        r1E.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound1End().substring(0, 2)), Integer.parseInt(schedule.getRound1End().substring(3, 5)));
        r2S.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound2Start().substring(0, 2)), Integer.parseInt(schedule.getRound2Start().substring(3, 5)));
        r2E.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound2End().substring(0, 2)), Integer.parseInt(schedule.getRound2End().substring(3, 5)));
        r3S.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound3Start().substring(0, 2)), Integer.parseInt(schedule.getRound3Start().substring(3, 5)));
        r3E.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound3End().substring(0, 2)), Integer.parseInt(schedule.getRound3End().substring(3, 5)));

        if(local.after(r1S) && local.before(r1E)) {
            round = "Round 1";
            binding.round.setText(round);
        }
        else if(local.after(r2S) && local.before(r2E)) {
            round = "Round 2";
            binding.round.setText(round);
        }
        else if(local.after(r3S) && local.before(r3E)) {
            round = "Round 3";
            binding.round.setText(round);
        }
        else if(local.before(r1S) || local.after(r3E)) {
            round = "None";
            binding.round.setText(round);
        }

        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                .collection(round).document("Information Technology").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            NewCategory category = documentSnapshot.toObject(NewCategory.class);
                            binding.ITGopen.setText(category.getGopens() + "");
                            if (!(category.getGopens() == 0))
                                binding.ITGopen.setTextColor(Color.GREEN);
                            else
                                binding.ITGopen.setTextColor(Color.RED);

                            binding.ITLopen.setText(category.getLopens() + "");
                            if (!(category.getLopens() == 0))
                                binding.ITLopen.setTextColor(Color.GREEN);
                            else
                                binding.ITLopen.setTextColor(Color.RED);

                            binding.ITGobc.setText(category.getGobcs() + "");
                            if (!(category.getGobcs() == 0))
                                binding.ITGobc.setTextColor(Color.GREEN);
                            else
                                binding.ITGobc.setTextColor(Color.RED);

                            binding.ITLobc.setText(category.getLobcs() + "");
                            if (!(category.getLobcs() == 0))
                                binding.ITLobc.setTextColor(Color.GREEN);
                            else
                                binding.ITLobc.setTextColor(Color.RED);

                            binding.ITGnt1.setText(category.getGnt1s() + "");
                            if (!(category.getGnt1s() == 0))
                                binding.ITGnt1.setTextColor(Color.GREEN);
                            else
                                binding.ITGnt1.setTextColor(Color.RED);

                            binding.ITLnt1.setText(category.getLnt1s() + "");
                            if (!(category.getLnt1s() == 0))
                                binding.ITLnt1.setTextColor(Color.GREEN);
                            else
                                binding.ITLnt1.setTextColor(Color.RED);

                            binding.ITGnt2.setText(category.getGnt2s() + "");
                            if (!(category.getGnt2s() == 0))
                                binding.ITGnt2.setTextColor(Color.GREEN);
                            else
                                binding.ITGnt2.setTextColor(Color.RED);

                            binding.ITLnt2.setText(category.getLnt2s() + "");
                            if (!(category.getLnt2s() == 0))
                                binding.ITLnt2.setTextColor(Color.GREEN);
                            else
                                binding.ITLnt2.setTextColor(Color.RED);

                            binding.ITGnt3.setText(category.getGnt3s() + "");
                            if (!(category.getGnt3s() == 0))
                                binding.ITGnt3.setTextColor(Color.GREEN);
                            else
                                binding.ITGnt3.setTextColor(Color.RED);

                            binding.ITLnt3.setText(category.getLnt3s() + "");
                            if (!(category.getLnt3s() == 0))
                                binding.ITLnt3.setTextColor(Color.GREEN);
                            else
                                binding.ITLnt3.setTextColor(Color.RED);

                            binding.ITGvj.setText(category.getGvjs() + "");
                            if (!(category.getGvjs() == 0))
                                binding.ITGvj.setTextColor(Color.GREEN);
                            else
                                binding.ITGvj.setTextColor(Color.RED);

                            binding.ITLvj.setText(category.getLvjs() + "");
                            if (!(category.getLvjs() == 0))
                                binding.ITLvj.setTextColor(Color.GREEN);
                            else
                                binding.ITLvj.setTextColor(Color.RED);

                            binding.ITGst.setText(category.getGsts() + "");
                            if (!(category.getGsts() == 0))
                                binding.ITGst.setTextColor(Color.GREEN);
                            else
                                binding.ITGst.setTextColor(Color.RED);

                            binding.ITLst.setText(category.getLsts() + "");
                            if (!(category.getLsts() == 0))
                                binding.ITLst.setTextColor(Color.GREEN);
                            else
                                binding.ITLst.setTextColor(Color.RED);

                            binding.ITGsc.setText(category.getGscs() + "");
                            if (!(category.getGscs() == 0))
                                binding.ITGsc.setTextColor(Color.GREEN);
                            else
                                binding.ITGsc.setTextColor(Color.RED);

                            binding.ITLsc.setText(category.getLscs() + "");
                            if (!(category.getLscs() == 0))
                                binding.ITLsc.setTextColor(Color.GREEN);
                            else
                                binding.ITLsc.setTextColor(Color.RED);

                        }
                    }
                });

        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                .collection(round).document("Computer Science and Engineering").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            NewCategory category = documentSnapshot.toObject(NewCategory.class);
                            binding.CSEGopen.setText(category.getGopens() + "");
                            if (!(category.getGopens() == 0))
                                binding.CSEGopen.setTextColor(Color.GREEN);
                            else
                                binding.CSEGopen.setTextColor(Color.RED);

                            binding.CSELopen.setText(category.getLopens() + "");
                            if (!(category.getLopens() == 0))
                                binding.CSELopen.setTextColor(Color.GREEN);
                            else
                                binding.CSELopen.setTextColor(Color.RED);

                            binding.CSEGobc.setText(category.getGobcs() + "");
                            if (!(category.getGobcs() == 0))
                                binding.CSEGobc.setTextColor(Color.GREEN);
                            else
                                binding.CSEGobc.setTextColor(Color.RED);

                            binding.CSELobc.setText(category.getLobcs() + "");
                            if (!(category.getLobcs() == 0))
                                binding.CSELobc.setTextColor(Color.GREEN);
                            else
                                binding.CSELobc.setTextColor(Color.RED);

                            binding.CSEGnt1.setText(category.getGnt1s() + "");
                            if (!(category.getGnt1s() == 0))
                                binding.CSEGnt1.setTextColor(Color.GREEN);
                            else
                                binding.CSEGnt1.setTextColor(Color.RED);

                            binding.CSELnt1.setText(category.getLnt1s() + "");
                            if (!(category.getLnt1s() == 0))
                                binding.CSELnt1.setTextColor(Color.GREEN);
                            else
                                binding.CSELnt1.setTextColor(Color.RED);

                            binding.CSEGnt2.setText(category.getGnt2s() + "");
                            if (!(category.getGnt2s() == 0))
                                binding.CSEGnt2.setTextColor(Color.GREEN);
                            else
                                binding.CSEGnt2.setTextColor(Color.RED);

                            binding.CSELnt2.setText(category.getLnt2s() + "");
                            if (!(category.getLnt2s() == 0))
                                binding.CSELnt2.setTextColor(Color.GREEN);
                            else
                                binding.CSELnt2.setTextColor(Color.RED);

                            binding.CSEGnt3.setText(category.getGnt3s() + "");
                            if (!(category.getGnt3s() == 0))
                                binding.CSEGnt3.setTextColor(Color.GREEN);
                            else
                                binding.CSEGnt3.setTextColor(Color.RED);

                            binding.CSELnt3.setText(category.getLnt3s() + "");
                            if (!(category.getLnt3s() == 0))
                                binding.CSELnt3.setTextColor(Color.GREEN);
                            else
                                binding.CSELnt3.setTextColor(Color.RED);

                            binding.CSEGvj.setText(category.getGvjs() + "");
                            if (!(category.getGvjs() == 0))
                                binding.CSEGvj.setTextColor(Color.GREEN);
                            else
                                binding.CSEGvj.setTextColor(Color.RED);

                            binding.CSELvj.setText(category.getLvjs() + "");
                            if (!(category.getLvjs() == 0))
                                binding.CSELvj.setTextColor(Color.GREEN);
                            else
                                binding.CSELvj.setTextColor(Color.RED);

                            binding.CSEGst.setText(category.getGsts() + "");
                            if (!(category.getGsts() == 0))
                                binding.CSEGst.setTextColor(Color.GREEN);
                            else
                                binding.CSEGst.setTextColor(Color.RED);

                            binding.CSELst.setText(category.getLsts() + "");
                            if (!(category.getLsts() == 0))
                                binding.CSELst.setTextColor(Color.GREEN);
                            else
                                binding.CSELst.setTextColor(Color.RED);

                            binding.CSEGsc.setText(category.getGscs() + "");
                            if (!(category.getGscs() == 0))
                                binding.CSEGsc.setTextColor(Color.GREEN);
                            else
                                binding.CSEGsc.setTextColor(Color.RED);

                            binding.CSELsc.setText(category.getLscs() + "");
                            if (!(category.getLscs() == 0))
                                binding.CSELsc.setTextColor(Color.GREEN);
                            else
                                binding.CSELsc.setTextColor(Color.RED);

                        }
                    }
                });

        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                .collection(round).document("Electronics Engineering").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            NewCategory category = documentSnapshot.toObject(NewCategory.class);
                            binding.ENGopen.setText(category.getGopens() + "");
                            if (!(category.getGopens() == 0))
                                binding.ENGopen.setTextColor(Color.GREEN);
                            else
                                binding.ENGopen.setTextColor(Color.RED);

                            binding.ENLopen.setText(category.getLopens() + "");
                            if (!(category.getLopens() == 0))
                                binding.ENLopen.setTextColor(Color.GREEN);
                            else
                                binding.ENLopen.setTextColor(Color.RED);

                            binding.ENGobc.setText(category.getGobcs() + "");
                            if (!(category.getGobcs() == 0))
                                binding.ENGobc.setTextColor(Color.GREEN);
                            else
                                binding.ENGobc.setTextColor(Color.RED);

                            binding.ENLobc.setText(category.getLobcs() + "");
                            if (!(category.getLobcs() == 0))
                                binding.ENLobc.setTextColor(Color.GREEN);
                            else
                                binding.ENLobc.setTextColor(Color.RED);

                            binding.ENGnt1.setText(category.getGnt1s() + "");
                            if (!(category.getGnt1s() == 0))
                                binding.ENGnt1.setTextColor(Color.GREEN);
                            else
                                binding.ENGnt1.setTextColor(Color.RED);

                            binding.ENLnt1.setText(category.getLnt1s() + "");
                            if (!(category.getLnt1s() == 0))
                                binding.ENLnt1.setTextColor(Color.GREEN);
                            else
                                binding.ENLnt1.setTextColor(Color.RED);

                            binding.ENGnt2.setText(category.getGnt2s() + "");
                            if (!(category.getGnt2s() == 0))
                                binding.ENGnt2.setTextColor(Color.GREEN);
                            else
                                binding.ENGnt2.setTextColor(Color.RED);

                            binding.ENLnt2.setText(category.getLnt2s() + "");
                            if (!(category.getLnt2s() == 0))
                                binding.ENLnt2.setTextColor(Color.GREEN);
                            else
                                binding.ENLnt2.setTextColor(Color.RED);

                            binding.ENGnt3.setText(category.getGnt3s() + "");
                            if (!(category.getGnt3s() == 0))
                                binding.ENGnt3.setTextColor(Color.GREEN);
                            else
                                binding.ENGnt3.setTextColor(Color.RED);

                            binding.ENLnt3.setText(category.getLnt3s() + "");
                            if (!(category.getLnt3s() == 0))
                                binding.ENLnt3.setTextColor(Color.GREEN);
                            else
                                binding.ENLnt3.setTextColor(Color.RED);

                            binding.ENGvj.setText(category.getGvjs() + "");
                            if (!(category.getGvjs() == 0))
                                binding.ENGvj.setTextColor(Color.GREEN);
                            else
                                binding.ENGvj.setTextColor(Color.RED);

                            binding.ENLvj.setText(category.getLvjs() + "");
                            if (!(category.getLvjs() == 0))
                                binding.ENLvj.setTextColor(Color.GREEN);
                            else
                                binding.ENLvj.setTextColor(Color.RED);

                            binding.ENGst.setText(category.getGsts() + "");
                            if (!(category.getGsts() == 0))
                                binding.ENGst.setTextColor(Color.GREEN);
                            else
                                binding.ENGst.setTextColor(Color.RED);

                            binding.ENLst.setText(category.getLsts() + "");
                            if (!(category.getLsts() == 0))
                                binding.ENLst.setTextColor(Color.GREEN);
                            else
                                binding.ENLst.setTextColor(Color.RED);

                            binding.ENGsc.setText(category.getGscs() + "");
                            if (!(category.getGscs() == 0))
                                binding.ENGsc.setTextColor(Color.GREEN);
                            else
                                binding.ENGsc.setTextColor(Color.RED);

                            binding.ENLsc.setText(category.getLscs() + "");
                            if (!(category.getLscs() == 0))
                                binding.ENLsc.setTextColor(Color.GREEN);
                            else
                                binding.ENLsc.setTextColor(Color.RED);

                        }
                    }
                });

        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                .collection(round).document("Electrical Engineering").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            NewCategory category = documentSnapshot.toObject(NewCategory.class);
                            binding.ELGopen.setText(category.getGopens() + "");
                            if (!(category.getGopens() == 0))
                                binding.ELGopen.setTextColor(Color.GREEN);
                            else
                                binding.ELGopen.setTextColor(Color.RED);

                            binding.ELLopen.setText(category.getLopens() + "");
                            if (!(category.getLopens() == 0))
                                binding.ELLopen.setTextColor(Color.GREEN);
                            else
                                binding.ELLopen.setTextColor(Color.RED);

                            binding.ELGobc.setText(category.getGobcs() + "");
                            if (!(category.getGobcs() == 0))
                                binding.ELGobc.setTextColor(Color.GREEN);
                            else
                                binding.ELGobc.setTextColor(Color.RED);

                            binding.ELLobc.setText(category.getLobcs() + "");
                            if (!(category.getLobcs() == 0))
                                binding.ELLobc.setTextColor(Color.GREEN);
                            else
                                binding.ELLobc.setTextColor(Color.RED);

                            binding.ELGnt1.setText(category.getGnt1s() + "");
                            if (!(category.getGnt1s() == 0))
                                binding.ELGnt1.setTextColor(Color.GREEN);
                            else
                                binding.ELGnt1.setTextColor(Color.RED);

                            binding.ELLnt1.setText(category.getLnt1s() + "");
                            if (!(category.getLnt1s() == 0))
                                binding.ELLnt1.setTextColor(Color.GREEN);
                            else
                                binding.ELLnt1.setTextColor(Color.RED);

                            binding.ELGnt2.setText(category.getGnt2s() + "");
                            if (!(category.getGnt2s() == 0))
                                binding.ELGnt2.setTextColor(Color.GREEN);
                            else
                                binding.ELGnt2.setTextColor(Color.RED);

                            binding.ELLnt2.setText(category.getLnt2s() + "");
                            if (!(category.getLnt2s() == 0))
                                binding.ELLnt2.setTextColor(Color.GREEN);
                            else
                                binding.ELLnt2.setTextColor(Color.RED);

                            binding.ELGnt3.setText(category.getGnt3s() + "");
                            if (!(category.getGnt3s() == 0))
                                binding.ENGnt3.setTextColor(Color.GREEN);
                            else
                                binding.ENGnt3.setTextColor(Color.RED);

                            binding.ELLnt3.setText(category.getLnt3s() + "");
                            if (!(category.getLnt3s() == 0))
                                binding.ELGnt3.setTextColor(Color.GREEN);
                            else
                                binding.ELGnt3.setTextColor(Color.RED);

                            binding.ELGvj.setText(category.getGvjs() + "");
                            if (!(category.getGvjs() == 0))
                                binding.ELGvj.setTextColor(Color.GREEN);
                            else
                                binding.ELGvj.setTextColor(Color.RED);

                            binding.ELLvj.setText(category.getLvjs() + "");
                            if (!(category.getLvjs() == 0))
                                binding.ELLvj.setTextColor(Color.GREEN);
                            else
                                binding.ELLvj.setTextColor(Color.RED);

                            binding.ELGst.setText(category.getGsts() + "");
                            if (!(category.getGsts() == 0))
                                binding.ELGst.setTextColor(Color.GREEN);
                            else
                                binding.ELGst.setTextColor(Color.RED);

                            binding.ELLst.setText(category.getLsts() + "");
                            if (!(category.getLsts() == 0))
                                binding.ELLst.setTextColor(Color.GREEN);
                            else
                                binding.ELLst.setTextColor(Color.RED);

                            binding.ELGsc.setText(category.getGscs() + "");
                            if (!(category.getGscs() == 0))
                                binding.ELGsc.setTextColor(Color.GREEN);
                            else
                                binding.ELGsc.setTextColor(Color.RED);

                            binding.ELLsc.setText(category.getLscs() + "");
                            if (!(category.getLscs() == 0))
                                binding.ELLsc.setTextColor(Color.GREEN);
                            else
                                binding.ELLsc.setTextColor(Color.RED);

                        }
                    }
                });

        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                .collection(round).document("Civil Engineering").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            NewCategory category = documentSnapshot.toObject(NewCategory.class);
                            binding.CVGopen.setText(category.getGopens() + "");
                            if (!(category.getGopens() == 0))
                                binding.CVGopen.setTextColor(Color.GREEN);
                            else
                                binding.CVGopen.setTextColor(Color.RED);

                            binding.CVLopen.setText(category.getLopens() + "");
                            if (!(category.getLopens() == 0))
                                binding.CVLopen.setTextColor(Color.GREEN);
                            else
                                binding.CVLopen.setTextColor(Color.RED);

                            binding.CVGobc.setText(category.getGobcs() + "");
                            if (!(category.getGobcs() == 0))
                                binding.CVGobc.setTextColor(Color.GREEN);
                            else
                                binding.CVGobc.setTextColor(Color.RED);

                            binding.CVLobc.setText(category.getLobcs() + "");
                            if (!(category.getLobcs() == 0))
                                binding.CVLobc.setTextColor(Color.GREEN);
                            else
                                binding.CVLobc.setTextColor(Color.RED);

                            binding.CVGnt1.setText(category.getGnt1s() + "");
                            if (!(category.getGnt1s() == 0))
                                binding.CVGnt1.setTextColor(Color.GREEN);
                            else
                                binding.CVGnt1.setTextColor(Color.RED);

                            binding.CVLnt1.setText(category.getLnt1s() + "");
                            if (!(category.getLnt1s() == 0))
                                binding.CVLnt1.setTextColor(Color.GREEN);
                            else
                                binding.CVLnt1.setTextColor(Color.RED);

                            binding.CVGnt2.setText(category.getGnt2s() + "");
                            if (!(category.getGnt2s() == 0))
                                binding.CVGnt2.setTextColor(Color.GREEN);
                            else
                                binding.CVGnt2.setTextColor(Color.RED);

                            binding.CVLnt2.setText(category.getLnt2s() + "");
                            if (!(category.getLnt2s() == 0))
                                binding.CVLnt2.setTextColor(Color.GREEN);
                            else
                                binding.CVLnt2.setTextColor(Color.RED);

                            binding.CVGnt3.setText(category.getGnt3s() + "");
                            if (!(category.getGnt3s() == 0))
                                binding.CVGnt3.setTextColor(Color.GREEN);
                            else
                                binding.CVGnt3.setTextColor(Color.RED);

                            binding.CVLnt3.setText(category.getLnt3s() + "");
                            if (!(category.getLnt3s() == 0))
                                binding.CVLnt3.setTextColor(Color.GREEN);
                            else
                                binding.CVLnt3.setTextColor(Color.RED);

                            binding.CVGvj.setText(category.getGvjs() + "");
                            if (!(category.getGvjs() == 0))
                                binding.CVGvj.setTextColor(Color.GREEN);
                            else
                                binding.CVGvj.setTextColor(Color.RED);

                            binding.CVLvj.setText(category.getLvjs() + "");
                            if (!(category.getLvjs() == 0))
                                binding.CVLvj.setTextColor(Color.GREEN);
                            else
                                binding.CVLvj.setTextColor(Color.RED);

                            binding.CVGst.setText(category.getGsts() + "");
                            if (!(category.getGsts() == 0))
                                binding.CVGst.setTextColor(Color.GREEN);
                            else
                                binding.CVGst.setTextColor(Color.RED);

                            binding.CVLst.setText(category.getLsts() + "");
                            if (!(category.getLsts() == 0))
                                binding.CVLst.setTextColor(Color.GREEN);
                            else
                                binding.CVLst.setTextColor(Color.RED);

                            binding.CVGsc.setText(category.getGscs() + "");
                            if (!(category.getGscs() == 0))
                                binding.CVGsc.setTextColor(Color.GREEN);
                            else
                                binding.CVGsc.setTextColor(Color.RED);

                            binding.CVLsc.setText(category.getLscs() + "");
                            if (!(category.getLscs() == 0))
                                binding.CVLsc.setTextColor(Color.GREEN);
                            else
                                binding.CVLsc.setTextColor(Color.RED);

                        }
                    }
                });

        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                .collection(round).document("Mechanical Engineering").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            NewCategory category = documentSnapshot.toObject(NewCategory.class);
                            binding.MEGopen.setText(category.getGopens() + "");
                            if (!(category.getGopens() == 0))
                                binding.MEGopen.setTextColor(Color.GREEN);
                            else
                                binding.MEGopen.setTextColor(Color.RED);

                            binding.MELopen.setText(category.getLopens() + "");
                            if (!(category.getLopens() == 0))
                                binding.MELopen.setTextColor(Color.GREEN);
                            else
                                binding.MELopen.setTextColor(Color.RED);

                            binding.MEGobc.setText(category.getGobcs() + "");
                            if (!(category.getGobcs() == 0))
                                binding.MEGobc.setTextColor(Color.GREEN);
                            else
                                binding.MEGobc.setTextColor(Color.RED);

                            binding.MELobc.setText(category.getLobcs() + "");
                            if (!(category.getLobcs() == 0))
                                binding.MELobc.setTextColor(Color.GREEN);
                            else
                                binding.MELobc.setTextColor(Color.RED);

                            binding.MEGnt1.setText(category.getGnt1s() + "");
                            if (!(category.getGnt1s() == 0))
                                binding.MEGnt1.setTextColor(Color.GREEN);
                            else
                                binding.MEGnt1.setTextColor(Color.RED);

                            binding.MELnt1.setText(category.getLnt1s() + "");
                            if (!(category.getLnt1s() == 0))
                                binding.MELnt1.setTextColor(Color.GREEN);
                            else
                                binding.MELnt1.setTextColor(Color.RED);

                            binding.MEGnt2.setText(category.getGnt2s() + "");
                            if (!(category.getGnt2s() == 0))
                                binding.MEGnt2.setTextColor(Color.GREEN);
                            else
                                binding.MEGnt2.setTextColor(Color.RED);

                            binding.MELnt2.setText(category.getLnt2s() + "");
                            if (!(category.getLnt2s() == 0))
                                binding.MELnt2.setTextColor(Color.GREEN);
                            else
                                binding.MELnt2.setTextColor(Color.RED);

                            binding.MEGnt3.setText(category.getGnt3s() + "");
                            if (!(category.getGnt3s() == 0))
                                binding.MEGnt3.setTextColor(Color.GREEN);
                            else
                                binding.MEGnt3.setTextColor(Color.RED);

                            binding.MELnt3.setText(category.getLnt3s() + "");
                            if (!(category.getLnt3s() == 0))
                                binding.MELnt3.setTextColor(Color.GREEN);
                            else
                                binding.MELnt3.setTextColor(Color.RED);

                            binding.MEGvj.setText(category.getGvjs() + "");
                            if (!(category.getGvjs() == 0))
                                binding.MEGvj.setTextColor(Color.GREEN);
                            else
                                binding.MEGvj.setTextColor(Color.RED);

                            binding.MELvj.setText(category.getLvjs() + "");
                            if (!(category.getLvjs() == 0))
                                binding.MELvj.setTextColor(Color.GREEN);
                            else
                                binding.MELvj.setTextColor(Color.RED);

                            binding.MEGst.setText(category.getGsts() + "");
                            if (!(category.getGsts() == 0))
                                binding.MEGst.setTextColor(Color.GREEN);
                            else
                                binding.MEGst.setTextColor(Color.RED);

                            binding.MELst.setText(category.getLsts() + "");
                            if (!(category.getLsts() == 0))
                                binding.MELst.setTextColor(Color.GREEN);
                            else
                                binding.MELst.setTextColor(Color.RED);

                            binding.MEGsc.setText(category.getGscs() + "");
                            if (!(category.getGscs() == 0))
                                binding.MEGsc.setTextColor(Color.GREEN);
                            else
                                binding.MEGsc.setTextColor(Color.RED);

                            binding.MELsc.setText(category.getLscs() + "");
                            if (!(category.getLscs() == 0))
                                binding.MELsc.setTextColor(Color.GREEN);
                            else
                                binding.MELsc.setTextColor(Color.RED);

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