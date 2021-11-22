package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.example.spotround.databinding.ActivitySplashBinding;
import com.example.spotround.datetime.DateTime;
import com.example.spotround.modle.Schedule;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    SharedPreferences prefs = null;
    SharedPreferences schedulePref = null;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        DateTime.initialize();

        prefs = getSharedPreferences("com.example.spotround", MODE_PRIVATE);
        schedulePref = getSharedPreferences("com.example.spotround", MODE_PRIVATE);

        getSchedule();

        binding.SplashActivityLogo.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mainlogoanimation));
        binding.SplashActivitySublogo.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.sublogoanimation));
        new Handler().postDelayed(() -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if(auth.getCurrentUser() != null) {
                if(prefs.getBoolean("mode", true))
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                else
                    startActivity(new Intent(getApplicationContext(), InstituteActivity.class));
            }
            else {
                startActivity(new Intent(getApplicationContext(), StartActivity.class));
            }
            finish();
        }, 3000);
    }

    private void getSchedule() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        FirebaseFirestore.getInstance().collection("Schedule").document("schedule")
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                                Schedule schedule = documentSnapshot.toObject(Schedule.class);
                                Log.d("    Schedule", schedule.toString());
                                SharedPreferences.Editor prefsEditor = schedulePref.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(schedule);
                                prefsEditor.putString("Schedule", json);
                                prefsEditor.putBoolean("flagSchedule", true);
                                prefsEditor.apply();
                            }
                        });
                    }
                });
            }
        }, 60000);

    }
}