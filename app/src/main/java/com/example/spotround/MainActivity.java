package com.example.spotround;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
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
    ProgressDialog progressDialog;
    PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Checking Information");

        fireStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        if(auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        popupMenu = new PopupMenu(MainActivity.this, binding.menu);

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.mainactivitymenu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                String item  = (String) menuItem.getTitle();
                switch (item) {
                    case "Help" :
                        Toast.makeText(MainActivity.this, "You Clicked help", Toast.LENGTH_SHORT).show();
                        break;
                    case "Logout" :
                        logout();
                        finish();
                        break;
                }
                return true;
            }
        });

        binding.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Clicked menu", Toast.LENGTH_LONG).show();
                popupMenu.show();
            }
        });

        binding.MainActivityApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                getData();
            }
        });

        binding.SeatsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowVacancy2.class);
                startActivity(intent);
            }
        });

        binding.MainActivityResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });

        binding.Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
            }
        });
    }

    void getData() {
        reference = fireStore.collection("Application").document(uid);
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    application = documentSnapshot.toObject(Application.class);

                    Log.d("OnStart", application.toString());
                    if(!application.isPayment()) {
                        Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
                        intent.putExtra("Application", application);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, SetPreference.class);
                        intent.putExtra("Application", application);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Register", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Apply.class);
                    startActivity(intent);
                }
                progressDialog.hide();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    void logout() {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setTitle("Logout");
        dialog.setMessage("Logging out of your account");
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        auth.signOut();
        progressDialog.dismiss();
        dialog.dismiss();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}