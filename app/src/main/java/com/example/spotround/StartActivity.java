package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spotround.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {
    ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.StartActivitybtnRegister.setOnClickListener(v -> startActivity(new Intent(StartActivity.this , RegisterActivity.class)));

        binding.StartActivitybtnLogin.setOnClickListener(v -> startActivity(new Intent(StartActivity.this , LoginActivity.class)));
        
       /* binding.StartActivitybtnInstituteLogin.setOnClickListener(v -> startActivity(new Intent(StartActivity.this , ILoginActivity.class)));*/
    }
}