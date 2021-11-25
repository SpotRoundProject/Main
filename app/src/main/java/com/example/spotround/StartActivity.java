package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.spotround.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {
    ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.StartActivitybtnRegister.setOnClickListener(v -> startActivity(new Intent(StartActivity.this , SignUpActivity.class)));

        binding.StartActivitybtnLogin.setOnClickListener(v -> startActivity(new Intent(StartActivity.this , LoginActivity.class)));
        
        binding.helpurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://spotroundproject.github.io/SpotRoundDocumentation.github.io/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}