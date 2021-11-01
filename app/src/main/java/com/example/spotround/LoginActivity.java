package com.example.spotround;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotround.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                            if(auth.getCurrentUser().isEmailVerified()) {

                                String type = binding.LoginActivityType.getSelectedItem().toString();
                                String id = auth.getCurrentUser().getUid();

                                if(type.equals("Admin")) {

                                    database.getReference().child("Admin").orderByKey().equalTo(id).
                                            addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            progressDialog.hide();
                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            if(snapshot.getChildrenCount() == 1) {
                                                Toast.makeText(LoginActivity.this,"Login Successfully",
                                                        Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(LoginActivity.this, InstituteActivity.class);
                                                startActivity(intent);
                                                binding.LoginActivityEmail.setText("");
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
                                else {
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
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);

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

        binding.LoginActivityForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter your email to receive reset link.");
                passwordResetDialog.setView(resetMail);
                resetMail.setInputType(32); //TYPE_TEXT_VARIATION_EMAIL_ADDRESS

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract the mail and send reset link
                        String mail = resetMail.getText().toString();
                        auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Reset link has been sent to your mail :)", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error :( Reset link is not sent." + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //closing dialogue
                    }
                });
                passwordResetDialog.create().show();
            }
        });

        binding.LoginActivityRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}