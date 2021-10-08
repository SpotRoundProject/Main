package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.spotround.databinding.ActivityRegisterBinding;
import com.example.spotround.modle.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private FirebaseAuth auth;
    private  static  int count=0;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseUser fuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root1 = db.getReference("StudentInfo");

        ArrayAdapter<String>myname = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.yn));
        myname.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.RegisterActivityYesNo.setAdapter(myname);
        binding.RegisterActivityYesNo.setSelection(1);


        binding.StartActivitybtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = binding.RegisterActivityEmail.getText().toString();
                String txt_password = binding.RegisterActivityPassword.getText().toString();
                String ph = binding.RegisterActivityPhoneNo.getText().toString();
                String Ai = binding.RegisterActivityApplicationId.getText().toString();
                if(TextUtils.isEmpty(txt_email ) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(ph) || TextUtils.isEmpty(Ai)) {
                    Toast.makeText(RegisterActivity.this,"Empty credential",Toast.LENGTH_SHORT).show();
                }
                else if(txt_password.length()<6)
                {
                    Toast.makeText(RegisterActivity.this,"Password too short",Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(txt_email,txt_password);
                }
            }
        });
        

    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull  Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    String yesno = binding.RegisterActivityYesNo.getSelectedItem().toString();
                    String ph = binding.RegisterActivityPhoneNo.getText().toString();
                    String Ai = binding.RegisterActivityApplicationId.getText().toString();
                    Toast.makeText(RegisterActivity.this,"Wait for moment",Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getApplicationContext(),StartActivity.class));
                    User user = new User();
                    String id1 = task.getResult().getUser().getUid();
                    count=count+1;
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference root = db.getReference("StudentInfo");
                    root.child("RegisterInfo").child("Type").child("User").child(id1).setValue(user);
                   // startActivity(new Intent(RegisterActivity.this,Mobveri.class));
                    auth = FirebaseAuth.getInstance();
                    fuser = auth.getCurrentUser();
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegisterActivity.this, "verification email has been sent", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),StartActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "onFailure: email not sent" + e.getMessage());
                        }
                    });

                    
                }
                else
                {
                    count=0;
                    Toast.makeText(RegisterActivity.this,"Register failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}