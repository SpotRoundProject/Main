package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spotround.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

        ArrayAdapter<String> loginType = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.
                simple_list_item_1,getResources().getStringArray(R.array.LoginType));
        loginType.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.LoginActivityType.setAdapter(loginType);
        binding.LoginActivityType.setSelection(0);

        binding.LoginActivitybtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                String text_email = binding.LoginActivityEmail.getText().toString();
                String text_password = binding.LoginActivityPassword.getText().toString();

                auth.signInWithEmailAndPassword(text_email,text_password).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            if(auth.getCurrentUser().isEmailVerified()) {

                                String type = binding.LoginActivityType.getSelectedItem().toString();
                                String id = auth.getCurrentUser().getUid();

                                if(type.equals("Admin")) {

                                    database.getReference().child("Admin").orderByKey().equalTo(id).
                                            addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.getChildrenCount() == 1) {
                                                Toast.makeText(LoginActivity.this,"Login Successfully",
                                                        Toast.LENGTH_SHORT).show();

                                                progressDialog.hide();
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                                Intent intent = new Intent(LoginActivity.this, InstituteActivity.class);
                                                startActivity(intent);
                                            }
                                            else {
                                                progressDialog.hide();
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                Toast.makeText(LoginActivity.this, "No admin account with this credentials",
                                                        Toast.LENGTH_SHORT).show();
                                                auth.signOut();
                                            }
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
                                            if(snapshot.getChildrenCount() == 1) {
                                                Toast.makeText(LoginActivity.this,"Login Successfully",
                                                        Toast.LENGTH_SHORT).show();

                                                progressDialog.hide();
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                            else {
                                                progressDialog.hide();
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                Toast.makeText(LoginActivity.this, "No user account with this credentials",
                                                        Toast.LENGTH_SHORT).show();
                                                auth.signOut();
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
                            }
                        }
                        else {
                            progressDialog.hide();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(LoginActivity.this, "Incorrect email or password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}