package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.spotround.databinding.ActivityScheduleBinding;
import com.example.spotround.modle.Preference;
import com.example.spotround.modle.Schedule;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScheduleActivity extends AppCompatActivity {
    ActivityScheduleBinding binding;
    Schedule schedule;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    ProgressDialog progressDialog;
    FirebaseFirestore fireStore;
    DocumentReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fireStore = FirebaseFirestore.getInstance();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                reference = fireStore.collection("Schedule").document("Schedule");

                reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            schedule = documentSnapshot.toObject(Schedule.class);
                            binding.applicationFillingStart.setText(schedule.getApplicationFillingStart());
                            binding.applicationFillingEnd.setText(schedule.getApplicationFillingEnd());
                            binding.round1Start.setText(schedule.getRound1Start());
                            binding.round1End.setText(schedule.getRound1End());
                            binding.R1Result.setText(schedule.getR1Result());
                            binding.round2Start.setText(schedule.getRound2Start());
                            binding.round2End.setText(schedule.getRound2End());
                            binding.R2Result.setText(schedule.getR2Result());
                            binding.round3Start.setText(schedule.getRound3Start());
                            binding.round3End.setText(schedule.getRound3End());
                            binding.R3Result.setText(schedule.getR3Result());
                        }
                        else {
                            Toast.makeText(ScheduleActivity.this, "No Data", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}