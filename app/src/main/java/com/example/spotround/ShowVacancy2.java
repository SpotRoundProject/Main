package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotround.databinding.ActivityShowVacancy2Binding;
import com.example.spotround.modle.NewCategory;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class ShowVacancy2 extends AppCompatActivity
{
    ActivityShowVacancy2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowVacancy2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                .collection("Round 1").document("Information Technology").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        NewCategory category = documentSnapshot.toObject(NewCategory.class);
                        binding.text3.setText(category.getGopens() + "");
                        if (!(category.getGopens() == 0))
                            binding.text3.setTextColor(Color.GREEN);
                        else
                            binding.text3.setTextColor(Color.RED);

                        binding.text8.setText(category.getLopens() + "");
                        if (!(category.getLopens() == 0))
                            binding.text8.setTextColor(Color.GREEN);
                        else
                            binding.text8.setTextColor(Color.RED);

                        binding.text9.setText(category.getGobcs() + "");
                        if (!(category.getGobcs() == 0))
                            binding.text9.setTextColor(Color.GREEN);
                        else
                            binding.text9.setTextColor(Color.RED);

                        binding.text10.setText(category.getLobcs() + "");
                        if (!(category.getLobcs() == 0))
                            binding.text10.setTextColor(Color.GREEN);
                        else
                            binding.text10.setTextColor(Color.RED);

                        binding.text11.setText(category.getGnt1s() + "");
                        if (!(category.getGnt1s() == 0))
                            binding.text11.setTextColor(Color.GREEN);
                        else
                            binding.text11.setTextColor(Color.RED);

                        binding.text16.setText(category.getLnt1s() + "");
                        if (!(category.getLnt1s() == 0))
                            binding.text16.setTextColor(Color.GREEN);
                        else
                            binding.text16.setTextColor(Color.RED);

                        binding.text17.setText(category.getGnt2s() + "");
                        if (!(category.getGnt2s() == 0))
                            binding.text17.setTextColor(Color.GREEN);
                        else
                            binding.text17.setTextColor(Color.RED);

                        binding.text18.setText(category.getLnt2s() + "");
                        if (!(category.getLnt2s() == 0))
                            binding.text18.setTextColor(Color.GREEN);
                        else
                            binding.text18.setTextColor(Color.RED);

                        binding.text23.setText(category.getGnt3s() + "");
                        if (!(category.getGnt3s() == 0))
                            binding.text23.setTextColor(Color.GREEN);
                        else
                            binding.text23.setTextColor(Color.RED);

                        binding.text24.setText(category.getLnt3s() + "");
                        if (!(category.getLnt3s() == 0))
                            binding.text24.setTextColor(Color.GREEN);
                        else
                            binding.text24.setTextColor(Color.RED);

                        binding.text25.setText(category.getGvjs() + "");
                        if (!(category.getGvjs() == 0))
                            binding.text25.setTextColor(Color.GREEN);
                        else
                            binding.text25.setTextColor(Color.RED);

                        binding.text26.setText(category.getLvjs() + "");
                        if (!(category.getLvjs() == 0))
                            binding.text26.setTextColor(Color.GREEN);
                        else
                            binding.text26.setTextColor(Color.RED);

                        binding.text31.setText(category.getGsts() + "");
                        if (!(category.getGsts() == 0))
                            binding.text31.setTextColor(Color.GREEN);
                        else
                            binding.text31.setTextColor(Color.RED);

                        binding.text32.setText(category.getLsts() + "");
                        if (!(category.getLsts() == 0))
                            binding.text32.setTextColor(Color.GREEN);
                        else
                            binding.text32.setTextColor(Color.RED);

                        binding.text33.setText(category.getGscs() + "");
                        if (!(category.getGscs() == 0))
                            binding.text33.setTextColor(Color.GREEN);
                        else
                            binding.text33.setTextColor(Color.RED);

                        binding.text29.setText(category.getLscs() + "");
                        if (!(category.getLscs() == 0))
                            binding.text29.setTextColor(Color.GREEN);
                        else
                            binding.text29.setTextColor(Color.RED);


                    }
                });

        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                .collection("Round 1").document("Computer Science and Engineering").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        NewCategory category = documentSnapshot.toObject(NewCategory.class);
                        binding.csetext3.setText(category.getGopens() + "");
                        if (!(category.getGopens() == 0))
                            binding.csetext3.setTextColor(Color.GREEN);
                        else
                            binding.csetext3.setTextColor(Color.RED);

                        binding.csetext8.setText(category.getLopens() + "");
                        if (!(category.getLopens() == 0))
                            binding.csetext8.setTextColor(Color.GREEN);
                        else
                            binding.csetext8.setTextColor(Color.RED);

                        binding.csetext9.setText(category.getGobcs() + "");
                        if (!(category.getGobcs() == 0))
                            binding.csetext9.setTextColor(Color.GREEN);
                        else
                            binding.csetext9.setTextColor(Color.RED);

                        binding.csetext10.setText(category.getLobcs() + "");
                        if (!(category.getLobcs() == 0))
                            binding.csetext10.setTextColor(Color.GREEN);
                        else
                            binding.csetext10.setTextColor(Color.RED);

                        binding.csetext11.setText(category.getGnt1s() + "");
                        if (!(category.getGnt1s() == 0))
                            binding.csetext11.setTextColor(Color.GREEN);
                        else
                            binding.csetext11.setTextColor(Color.RED);

                        binding.csetext16.setText(category.getLnt1s() + "");
                        if (!(category.getLnt1s() == 0))
                            binding.csetext16.setTextColor(Color.GREEN);
                        else
                            binding.csetext16.setTextColor(Color.RED);

                        binding.csetext17.setText(category.getGnt2s() + "");
                        if (!(category.getGnt2s() == 0))
                            binding.csetext17.setTextColor(Color.GREEN);
                        else
                            binding.csetext17.setTextColor(Color.RED);

                        binding.csetext18.setText(category.getLnt2s() + "");
                        if (!(category.getLnt2s() == 0))
                            binding.csetext18.setTextColor(Color.GREEN);
                        else
                            binding.csetext18.setTextColor(Color.RED);

                        binding.csetext23.setText(category.getGnt3s() + "");
                        if (!(category.getGnt3s() == 0))
                            binding.csetext23.setTextColor(Color.GREEN);
                        else
                            binding.csetext23.setTextColor(Color.RED);

                        binding.csetext24.setText(category.getLnt3s() + "");
                        if (!(category.getLnt3s() == 0))
                            binding.csetext24.setTextColor(Color.GREEN);
                        else
                            binding.csetext24.setTextColor(Color.RED);

                        binding.csetext25.setText(category.getGvjs() + "");
                        if (!(category.getGvjs() == 0))
                            binding.csetext25.setTextColor(Color.GREEN);
                        else
                            binding.csetext25.setTextColor(Color.RED);

                        binding.csetext26.setText(category.getLvjs() + "");
                        if (!(category.getLvjs() == 0))
                            binding.csetext26.setTextColor(Color.GREEN);
                        else
                            binding.csetext26.setTextColor(Color.RED);

                        binding.csetext31.setText(category.getGsts() + "");
                        if (!(category.getGsts() == 0))
                            binding.csetext31.setTextColor(Color.GREEN);
                        else
                            binding.csetext31.setTextColor(Color.RED);

                        binding.csetext32.setText(category.getLsts() + "");
                        if (!(category.getLsts() == 0))
                            binding.csetext32.setTextColor(Color.GREEN);
                        else
                            binding.csetext32.setTextColor(Color.RED);

                        binding.csetext33.setText(category.getGscs() + "");
                        if (!(category.getGscs() == 0))
                            binding.csetext33.setTextColor(Color.GREEN);
                        else
                            binding.csetext33.setTextColor(Color.RED);

                        binding.csetext29.setText(category.getLscs() + "");
                        if (!(category.getLscs() == 0))
                            binding.csetext29.setTextColor(Color.GREEN);
                        else
                            binding.csetext29.setTextColor(Color.RED);


                    }
                });

        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                .collection("Round 1").document("Electronics Engineering").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        NewCategory category = documentSnapshot.toObject(NewCategory.class);
                        binding.entext3.setText(category.getGopens() + "");
                        if (!(category.getGopens() == 0))
                            binding.entext3.setTextColor(Color.GREEN);
                        else
                            binding.entext3.setTextColor(Color.RED);

                        binding.entext8.setText(category.getLopens() + "");
                        if (!(category.getLopens() == 0))
                            binding.entext8.setTextColor(Color.GREEN);
                        else
                            binding.entext8.setTextColor(Color.RED);

                        binding.entext9.setText(category.getGobcs() + "");
                        if (!(category.getGobcs() == 0))
                            binding.entext9.setTextColor(Color.GREEN);
                        else
                            binding.entext9.setTextColor(Color.RED);

                        binding.entext10.setText(category.getLobcs() + "");
                        if (!(category.getLobcs() == 0))
                            binding.entext10.setTextColor(Color.GREEN);
                        else
                            binding.entext10.setTextColor(Color.RED);

                        binding.entext11.setText(category.getGnt1s() + "");
                        if (!(category.getGnt1s() == 0))
                            binding.entext11.setTextColor(Color.GREEN);
                        else
                            binding.entext11.setTextColor(Color.RED);

                        binding.entext16.setText(category.getLnt1s() + "");
                        if (!(category.getLnt1s() == 0))
                            binding.entext16.setTextColor(Color.GREEN);
                        else
                            binding.entext16.setTextColor(Color.RED);

                        binding.entext17.setText(category.getGnt2s() + "");
                        if (!(category.getGnt2s() == 0))
                            binding.entext17.setTextColor(Color.GREEN);
                        else
                            binding.entext17.setTextColor(Color.RED);

                        binding.entext18.setText(category.getLnt2s() + "");
                        if (!(category.getLnt2s() == 0))
                            binding.entext18.setTextColor(Color.GREEN);
                        else
                            binding.entext18.setTextColor(Color.RED);

                        binding.entext23.setText(category.getGnt3s() + "");
                        if (!(category.getGnt3s() == 0))
                            binding.entext23.setTextColor(Color.GREEN);
                        else
                            binding.entext23.setTextColor(Color.RED);

                        binding.entext24.setText(category.getLnt3s() + "");
                        if (!(category.getLnt3s() == 0))
                            binding.entext24.setTextColor(Color.GREEN);
                        else
                            binding.entext24.setTextColor(Color.RED);

                        binding.entext25.setText(category.getGvjs() + "");
                        if (!(category.getGvjs() == 0))
                            binding.entext25.setTextColor(Color.GREEN);
                        else
                            binding.entext25.setTextColor(Color.RED);

                        binding.entext26.setText(category.getLvjs() + "");
                        if (!(category.getLvjs() == 0))
                            binding.entext26.setTextColor(Color.GREEN);
                        else
                            binding.entext26.setTextColor(Color.RED);

                        binding.entext31.setText(category.getGsts() + "");
                        if (!(category.getGsts() == 0))
                            binding.entext31.setTextColor(Color.GREEN);
                        else
                            binding.entext31.setTextColor(Color.RED);

                        binding.entext32.setText(category.getLsts() + "");
                        if (!(category.getLsts() == 0))
                            binding.entext32.setTextColor(Color.GREEN);
                        else
                            binding.entext32.setTextColor(Color.RED);

                        binding.entext33.setText(category.getGscs() + "");
                        if (!(category.getGscs() == 0))
                            binding.entext33.setTextColor(Color.GREEN);
                        else
                            binding.entext33.setTextColor(Color.RED);

                        binding.entext29.setText(category.getLscs() + "");
                        if (!(category.getLscs() == 0))
                            binding.entext29.setTextColor(Color.GREEN);
                        else
                            binding.entext29.setTextColor(Color.RED);


                    }
                });

        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                .collection("Round 1").document("Electrical Engineering").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        NewCategory category = documentSnapshot.toObject(NewCategory.class);
                        binding.eltext3.setText(category.getGopens() + "");
                        if (!(category.getGopens() == 0))
                            binding.eltext3.setTextColor(Color.GREEN);
                        else
                            binding.eltext3.setTextColor(Color.RED);

                        binding.eltext8.setText(category.getLopens() + "");
                        if (!(category.getLopens() == 0))
                            binding.eltext8.setTextColor(Color.GREEN);
                        else
                            binding.eltext8.setTextColor(Color.RED);

                        binding.eltext9.setText(category.getGobcs() + "");
                        if (!(category.getGobcs() == 0))
                            binding.eltext9.setTextColor(Color.GREEN);
                        else
                            binding.eltext9.setTextColor(Color.RED);

                        binding.eltext10.setText(category.getLobcs() + "");
                        if (!(category.getLobcs() == 0))
                            binding.eltext10.setTextColor(Color.GREEN);
                        else
                            binding.eltext10.setTextColor(Color.RED);

                        binding.eltext11.setText(category.getGnt1s() + "");
                        if (!(category.getGnt1s() == 0))
                            binding.eltext11.setTextColor(Color.GREEN);
                        else
                            binding.eltext11.setTextColor(Color.RED);

                        binding.eltext16.setText(category.getLnt1s() + "");
                        if (!(category.getLnt1s() == 0))
                            binding.eltext16.setTextColor(Color.GREEN);
                        else
                            binding.eltext16.setTextColor(Color.RED);

                        binding.eltext17.setText(category.getGnt2s() + "");
                        if (!(category.getGnt2s() == 0))
                            binding.eltext17.setTextColor(Color.GREEN);
                        else
                            binding.eltext17.setTextColor(Color.RED);

                        binding.eltext18.setText(category.getLnt2s() + "");
                        if (!(category.getLnt2s() == 0))
                            binding.eltext18.setTextColor(Color.GREEN);
                        else
                            binding.eltext18.setTextColor(Color.RED);

                        binding.eltext23.setText(category.getGnt3s() + "");
                        if (!(category.getGnt3s() == 0))
                            binding.eltext23.setTextColor(Color.GREEN);
                        else
                            binding.eltext23.setTextColor(Color.RED);

                        binding.eltext24.setText(category.getLnt3s() + "");
                        if (!(category.getLnt3s() == 0))
                            binding.eltext24.setTextColor(Color.GREEN);
                        else
                            binding.eltext24.setTextColor(Color.RED);

                        binding.eltext25.setText(category.getGvjs() + "");
                        if (!(category.getGvjs() == 0))
                            binding.eltext25.setTextColor(Color.GREEN);
                        else
                            binding.eltext25.setTextColor(Color.RED);

                        binding.eltext26.setText(category.getLvjs() + "");
                        if (!(category.getLvjs() == 0))
                            binding.eltext26.setTextColor(Color.GREEN);
                        else
                            binding.eltext26.setTextColor(Color.RED);

                        binding.eltext31.setText(category.getGsts() + "");
                        if (!(category.getGsts() == 0))
                            binding.eltext31.setTextColor(Color.GREEN);
                        else
                            binding.eltext31.setTextColor(Color.RED);

                        binding.eltext32.setText(category.getLsts() + "");
                        if (!(category.getLsts() == 0))
                            binding.eltext32.setTextColor(Color.GREEN);
                        else
                            binding.eltext32.setTextColor(Color.RED);

                        binding.eltext33.setText(category.getGscs() + "");
                        if (!(category.getGscs() == 0))
                            binding.eltext33.setTextColor(Color.GREEN);
                        else
                            binding.eltext33.setTextColor(Color.RED);

                        binding.eltext29.setText(category.getLscs() + "");
                        if (!(category.getLscs() == 0))
                            binding.eltext29.setTextColor(Color.GREEN);
                        else
                            binding.eltext29.setTextColor(Color.RED);


                    }
                });

        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                .collection("Round 1").document("Civil Engineering").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        NewCategory category = documentSnapshot.toObject(NewCategory.class);
                        binding.cvtext3.setText(category.getGopens() + "");
                        if (!(category.getGopens() == 0))
                            binding.cvtext3.setTextColor(Color.GREEN);
                        else
                            binding.cvtext3.setTextColor(Color.RED);

                        binding.cvtext8.setText(category.getLopens() + "");
                        if (!(category.getLopens() == 0))
                            binding.cvtext8.setTextColor(Color.GREEN);
                        else
                            binding.cvtext8.setTextColor(Color.RED);

                        binding.cvtext9.setText(category.getGobcs() + "");
                        if (!(category.getGobcs() == 0))
                            binding.cvtext9.setTextColor(Color.GREEN);
                        else
                            binding.cvtext9.setTextColor(Color.RED);

                        binding.cvtext10.setText(category.getLobcs() + "");
                        if (!(category.getLobcs() == 0))
                            binding.cvtext10.setTextColor(Color.GREEN);
                        else
                            binding.cvtext10.setTextColor(Color.RED);

                        binding.cvtext11.setText(category.getGnt1s() + "");
                        if (!(category.getGnt1s() == 0))
                            binding.cvtext11.setTextColor(Color.GREEN);
                        else
                            binding.cvtext11.setTextColor(Color.RED);

                        binding.cvtext16.setText(category.getLnt1s() + "");
                        if (!(category.getLnt1s() == 0))
                            binding.cvtext16.setTextColor(Color.GREEN);
                        else
                            binding.cvtext16.setTextColor(Color.RED);

                        binding.cvtext17.setText(category.getGnt2s() + "");
                        if (!(category.getGnt2s() == 0))
                            binding.cvtext17.setTextColor(Color.GREEN);
                        else
                            binding.cvtext17.setTextColor(Color.RED);

                        binding.cvtext18.setText(category.getLnt2s() + "");
                        if (!(category.getLnt2s() == 0))
                            binding.cvtext18.setTextColor(Color.GREEN);
                        else
                            binding.cvtext18.setTextColor(Color.RED);

                        binding.cvtext23.setText(category.getGnt3s() + "");
                        if (!(category.getGnt3s() == 0))
                            binding.cvtext23.setTextColor(Color.GREEN);
                        else
                            binding.cvtext23.setTextColor(Color.RED);

                        binding.cvtext24.setText(category.getLnt3s() + "");
                        if (!(category.getLnt3s() == 0))
                            binding.cvtext24.setTextColor(Color.GREEN);
                        else
                            binding.cvtext24.setTextColor(Color.RED);

                        binding.cvtext25.setText(category.getGvjs() + "");
                        if (!(category.getGvjs() == 0))
                            binding.cvtext25.setTextColor(Color.GREEN);
                        else
                            binding.cvtext25.setTextColor(Color.RED);

                        binding.cvtext26.setText(category.getLvjs() + "");
                        if (!(category.getLvjs() == 0))
                            binding.cvtext26.setTextColor(Color.GREEN);
                        else
                            binding.cvtext26.setTextColor(Color.RED);

                        binding.cvtext31.setText(category.getGsts() + "");
                        if (!(category.getGsts() == 0))
                            binding.cvtext31.setTextColor(Color.GREEN);
                        else
                            binding.cvtext31.setTextColor(Color.RED);

                        binding.cvtext32.setText(category.getLsts() + "");
                        if (!(category.getLsts() == 0))
                            binding.cvtext32.setTextColor(Color.GREEN);
                        else
                            binding.cvtext32.setTextColor(Color.RED);

                        binding.cvtext33.setText(category.getGscs() + "");
                        if (!(category.getGscs() == 0))
                            binding.cvtext33.setTextColor(Color.GREEN);
                        else
                            binding.cvtext33.setTextColor(Color.RED);

                        binding.cvtext29.setText(category.getLscs() + "");
                        if (!(category.getLscs() == 0))
                            binding.cvtext29.setTextColor(Color.GREEN);
                        else
                            binding.cvtext29.setTextColor(Color.RED);


                    }
                });

        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                .collection("Round 1").document("Mechanical Engineering").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        NewCategory category = documentSnapshot.toObject(NewCategory.class);
                        binding.metext3.setText(category.getGopens() + "");
                        if (!(category.getGopens() == 0))
                            binding.metext3.setTextColor(Color.GREEN);
                        else
                            binding.metext3.setTextColor(Color.RED);

                        binding.metext8.setText(category.getLopens() + "");
                        if (!(category.getLopens() == 0))
                            binding.metext8.setTextColor(Color.GREEN);
                        else
                            binding.metext8.setTextColor(Color.RED);

                        binding.metext9.setText(category.getGobcs() + "");
                        if (!(category.getGobcs() == 0))
                            binding.metext9.setTextColor(Color.GREEN);
                        else
                            binding.metext9.setTextColor(Color.RED);

                        binding.metext10.setText(category.getLobcs() + "");
                        if (!(category.getLobcs() == 0))
                            binding.metext10.setTextColor(Color.GREEN);
                        else
                            binding.metext10.setTextColor(Color.RED);

                        binding.metext11.setText(category.getGnt1s() + "");
                        if (!(category.getGnt1s() == 0))
                            binding.metext11.setTextColor(Color.GREEN);
                        else
                            binding.metext11.setTextColor(Color.RED);

                        binding.metext16.setText(category.getLnt1s() + "");
                        if (!(category.getLnt1s() == 0))
                            binding.metext16.setTextColor(Color.GREEN);
                        else
                            binding.metext16.setTextColor(Color.RED);

                        binding.metext17.setText(category.getGnt2s() + "");
                        if (!(category.getGnt2s() == 0))
                            binding.metext17.setTextColor(Color.GREEN);
                        else
                            binding.metext17.setTextColor(Color.RED);

                        binding.metext18.setText(category.getLnt2s() + "");
                        if (!(category.getLnt2s() == 0))
                            binding.metext18.setTextColor(Color.GREEN);
                        else
                            binding.metext18.setTextColor(Color.RED);

                        binding.metext23.setText(category.getGnt3s() + "");
                        if (!(category.getGnt3s() == 0))
                            binding.metext23.setTextColor(Color.GREEN);
                        else
                            binding.metext23.setTextColor(Color.RED);

                        binding.metext24.setText(category.getLnt3s() + "");
                        if (!(category.getLnt3s() == 0))
                            binding.metext24.setTextColor(Color.GREEN);
                        else
                            binding.metext24.setTextColor(Color.RED);

                        binding.metext25.setText(category.getGvjs() + "");
                        if (!(category.getGvjs() == 0))
                            binding.metext25.setTextColor(Color.GREEN);
                        else
                            binding.metext25.setTextColor(Color.RED);

                        binding.metext26.setText(category.getLvjs() + "");
                        if (!(category.getLvjs() == 0))
                            binding.metext26.setTextColor(Color.GREEN);
                        else
                            binding.metext26.setTextColor(Color.RED);

                        binding.metext31.setText(category.getGsts() + "");
                        if (!(category.getGsts() == 0))
                            binding.metext31.setTextColor(Color.GREEN);
                        else
                            binding.metext31.setTextColor(Color.RED);

                        binding.metext32.setText(category.getLsts() + "");
                        if (!(category.getLsts() == 0))
                            binding.metext32.setTextColor(Color.GREEN);
                        else
                            binding.metext32.setTextColor(Color.RED);

                        binding.metext33.setText(category.getGscs() + "");
                        if (!(category.getGscs() == 0))
                            binding.metext33.setTextColor(Color.GREEN);
                        else
                            binding.metext33.setTextColor(Color.RED);

                        binding.metext29.setText(category.getLscs() + "");
                        if (!(category.getLscs() == 0))
                            binding.metext29.setTextColor(Color.GREEN);
                        else
                            binding.metext29.setTextColor(Color.RED);


                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}