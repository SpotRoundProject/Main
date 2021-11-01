package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.spotround.databinding.ActivitySignUpBinding;
import com.example.spotround.modle.LoginCredentials;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseDatabase database;
    boolean flag = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("New Account");
        progressDialog.setMessage("Creating new Account");

        binding.SignUpActivitybtnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                if(binding.SignUpActivityEmail.getText().toString().isEmpty()) {
                    binding.SignUpActivityEmail.setError("Enter Email");
                    progressDialog.hide();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    return;
                }
                if(binding.SignUpActivityPassword.getText().toString().isEmpty()) {
                    binding.SignUpActivityPassword.setError("Enter password");
                    progressDialog.hide();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    return;
                }
                if(!(checkEmail() && checkPassword())){
                    progressDialog.hide();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    return;
                }

                auth.createUserWithEmailAndPassword(binding.SignUpActivityEmail.getText().toString(),
                        binding.SignUpActivityPassword.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    String uid = task.getResult().getUser().getUid();
                                    progressDialog.setMessage("Account Created Successfully");

                                    auth.getCurrentUser().sendEmailVerification().
                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                LoginCredentials user = new LoginCredentials(binding.SignUpActivityEmail.getText().toString(),
                                                        binding.SignUpActivityPassword.getText().toString());
                                                database.getReference().child("User").child(uid).setValue(user);
                                                progressDialog.hide();

                                                Toast.makeText(SignUpActivity.this, "Please check email " +
                                                        "for verification", Toast.LENGTH_SHORT).show();
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                            }
                                            else {
                                                Toast.makeText(SignUpActivity.this, task.getException().getMessage()
                                                        , Toast.LENGTH_SHORT).show();
                                                progressDialog.hide();
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            }
                                        }

                                    });
                                    binding.SignUpActivityEmail.setText("");
                                    binding.SignUpActivityPassword.setText("");
                                }
                                else
                                {
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    progressDialog.hide();
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    binding.SignUpActivityPassword.setText("");
                                }

                            }
                        });
            }
        });

        binding.SignUpActivityPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (binding.SignUpActivityPassword.getRight() -
                        binding.SignUpActivityPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                    if(!flag) {
                        binding.SignUpActivityPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        binding.SignUpActivityPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,
                                R.drawable.ic_hide_password,0);
                    }
                    else {
                        binding.SignUpActivityPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        binding.SignUpActivityPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                R.drawable.ic_show_password, 0);
                    }
                    flag = !flag;
                    return true;
                }
            }
            return false;
        });
    }

    protected  boolean checkEmail() {
        String email = binding.SignUpActivityEmail.getText().toString();
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);

    }

    protected  boolean checkPassword() {
        String password = binding.SignUpActivityPassword.getText().toString();
        int iPasswordScore = 0;

        if( password.length() < 6 ) {
            binding.SignUpActivityPassword.setError("must be greater than 6 digits");
            return false;
        }
        else
            iPasswordScore += 2;

        //if it contains one digit, add 2 to total score
        if( password.matches("(?=.*[0-9]).*") )
            iPasswordScore += 2;

        //if it contains one lower case letter, add 2 to total score
        if( password.matches("(?=.*[a-z]).*") )
            iPasswordScore += 2;

        //if it contains one upper case letter, add 2 to total score
        if( password.matches("(?=.*[A-Z]).*") )
            iPasswordScore += 2;

        //if it contains one special character, add 2 to total score
        if( password.matches("(?=.*[~!@#$%^&*()_-]).*") )
            iPasswordScore += 2;

        if(iPasswordScore < 10)
        {
            binding.SignUpActivityPassword.setError("must contain aA-zZ, 0-9, ~!@#$%^&*()_-");
            return  false;
        }
        return  true;
    }
}