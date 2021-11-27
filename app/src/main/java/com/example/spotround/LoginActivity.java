package com.example.spotround;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotround.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    ActivityLoginBinding binding;
    ProgressDialog progressDialog;
    boolean flag = false;
    private SharedPreferences prefs = null;
    boolean flagAutoStart = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences("com.example.spotround", MODE_PRIVATE);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("LogIn");
        progressDialog.setMessage("Signing in please wait");

        ArrayAdapter loginType = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.LoginType));
        loginType.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.LoginActivityType.setAdapter(loginType);
        binding.LoginActivityType.setSelection(0);

        binding.LoginActivitybtnLogin.setOnClickListener(v -> {
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            String text_email = binding.LoginActivityEmail.getText().toString();
            String text_password = binding.LoginActivityPassword.getText().toString();

            auth.signInWithEmailAndPassword(text_email,text_password).
                    addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {

                            String type = binding.LoginActivityType.getSelectedItem().toString();
                            String id = auth.getCurrentUser().getUid();
                            if(type.equals("Admin"))
                            {

                                database.getReference().child("Admin").orderByKey().equalTo(id).
                                        addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                progressDialog.hide();
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                if(snapshot.getChildrenCount() == 1) {
                                                    Toast.makeText(LoginActivity.this,"Login Successfully",
                                                            Toast.LENGTH_SHORT).show();

                                                    prefs.edit().putBoolean("mode", false).apply();
                                                    Intent intent = new Intent(LoginActivity.this, InstituteActivity.class);
                                                    if(binding.LoginActivityEmail.getText().toString().equals("omkar.kulkarni@walchandsangli.ac.in"))
                                                        intent.putExtra("Access", 1);
                                                    startActivity(intent);
                                                    binding.LoginActivityEmail.setText("");
                                                    finish();
                                                }
                                                else {
                                                    Toast.makeText(LoginActivity.this, "No admin account with this credentials",
                                                            Toast.LENGTH_SHORT).show();
                                                    auth.signOut();
                                                }
                                                binding.LoginActivityPassword.setText("");
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            if(auth.getCurrentUser().isEmailVerified()) {


                                if(type.equals("User")) {
                                    database.getReference().child("User").orderByKey().equalTo(id).
                                            addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            progressDialog.hide();
                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            if(snapshot.getChildrenCount() == 1) {
                                                Toast.makeText(LoginActivity.this,"Login Successful",
                                                        Toast.LENGTH_SHORT).show();


                                                binding.LoginActivityPassword.setText("");
                                                binding.LoginActivityEmail.setText("");

                                                prefs.edit().putBoolean("mode", true).apply();
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();

                                            }
                                            else {
                                                Toast.makeText(LoginActivity.this, "No user account with this credentials",
                                                        Toast.LENGTH_SHORT).show();
                                                auth.signOut();
                                                binding.LoginActivityPassword.setText("");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                            else {
                                progressDialog.hide();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText(LoginActivity.this, "Please verify your email address",
                                        Toast.LENGTH_LONG).show();
                                auth.signOut();
                                binding.LoginActivityPassword.setText("");
                            }
                        }
                        else {
                            progressDialog.hide();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(LoginActivity.this, "Incorrect email or password",
                                    Toast.LENGTH_SHORT).show();
                            binding.LoginActivityPassword.setText("");
                        }
                    });
        });

        binding.LoginActivityPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (binding.LoginActivityPassword.getRight() -
                        binding.LoginActivityPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                    if(!flag) {
                        binding.LoginActivityPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        binding.LoginActivityPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,
                                R.drawable.ic_hide_password,0);
                    }
                    else {
                        binding.LoginActivityPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        binding.LoginActivityPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                R.drawable.ic_show_password, 0);
                    }
                    flag = !flag;
                    return true;
                }
            }
            return false;
        });

        binding.LoginActivityRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if(!flagAutoStart) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Auto Start")
                    .setMessage("Enable auto start from app setting to get notifications")

                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            flagAutoStart = true;
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            flagAutoStart = true;
                            Toast.makeText(LoginActivity.this, "You won't get any notifications", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this, StartActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }
}