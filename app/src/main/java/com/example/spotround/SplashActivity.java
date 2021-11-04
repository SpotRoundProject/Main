package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.example.spotround.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        binding.SplashActivityLogo.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mainlogoanimation));
        binding.SplashActivitySublogo.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.sublogoanimation));
        new Handler().postDelayed(() -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if(auth.getCurrentUser() != null)
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            else
                startActivity(new Intent(getApplicationContext(),StartActivity.class));
            finish();
        }, 3000);
    }
}