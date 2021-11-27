package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.spotround.databinding.ActivityNewAdminBinding;
import com.example.spotround.modle.LoginCredentials;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewAdmin extends AppCompatActivity {
    ActivityNewAdminBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(NewAdmin.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Admin Access");

        binding.accessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                String email = binding.email.getText().toString();

                auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if(task.isSuccessful()) {
                            if (task.getResult().getSignInMethods().size() == 0){
                                Toast.makeText(NewAdmin.this, "Account not exist", Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }else {
                                database.getReference().child("User").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String id = null;
                                        LoginCredentials admin = null;
                                        if(snapshot.getChildrenCount() == 0) {
                                            progressDialog.hide();
                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            return;
                                        }
                                        for(DataSnapshot snap : snapshot.getChildren()) {
                                            id = snap.getKey();
                                            admin = snap.getValue(LoginCredentials.class);
                                        }
                                        database.getReference().child("User").child(id).removeValue();
                                        database.getReference().child("Admin").child(id).setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    Toast.makeText(NewAdmin.this, "Admin Access given to " + email, Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    Toast.makeText(NewAdmin.this, "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                                progressDialog.hide();
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {}
                                });
                            }
                        }
                        else {
                            Toast.makeText(NewAdmin.this, "Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                    }
                });
                binding.email.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressDialog.dismiss();
    }
}