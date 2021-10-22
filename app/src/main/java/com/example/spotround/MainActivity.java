package com.example.spotround;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotround.databinding.ActivityMainBinding;
import com.example.spotround.modle.Application;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore fireStore;
    DocumentReference reference;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    String uid;
    Application application = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        fireStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        if(auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        binding.MainActivityApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(application == null) {
                    Intent intent = new Intent(MainActivity.this, Apply.class);
                    startActivity(intent);
                }
                if(!application.isPayment()) {
                    Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
                    intent.putExtra("Application", application);
                    startActivity(intent);
                }
                else
                    startActivity(new Intent(MainActivity.this, SetPreference.class));
            }
        });
        binding.SeatsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowVacancy2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                reference = fireStore.collection("Application").document(uid);
                reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            application = documentSnapshot.toObject(Application.class);
                            Log.d("OnStart", application.toString());
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}