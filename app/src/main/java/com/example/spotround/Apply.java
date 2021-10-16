package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.spotround.databinding.ActivityApplyBinding;

public class Apply extends AppCompatActivity {
    ActivityApplyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}