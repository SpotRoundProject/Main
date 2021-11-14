package com.example.spotround;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotround.databinding.ActivityApplyBinding;
import com.example.spotround.modle.Application;
import com.example.spotround.modle.LoginCredentials;
import com.example.spotround.modle.StudentInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Apply extends AppCompatActivity {
    ActivityApplyBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore fireStore;
    private DocumentReference reference;
    private FirebaseDatabase database;
    private boolean flagPhoneNoVerified = true;
    private boolean flagPaymentDone = false;
    private String uid;
    private Application application;
    private String phoneNo, otp, mVerificationId;
    private LoginCredentials loginCredentials;
    private ProgressDialog progressDialog;
    int sec = 120;
    private StudentInfo info;
    String [] sCode = {"Select seat code","Computer Science and Engineering","Information Technology","Mechanical Engineering","Electronics Engineering","Electrical Engineering","Civil Engineering"};
    String [] sType = {"Select seat Type", "gopen", "lopen", "gobc", "lobc", "gnt1", "lnt1", "gnt2", "lnt2", "gnt3", "lnt3", "gvj", "lvj", "gst", "lst", "gsc", "lsc"};
    ArrayAdapter adapter1, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        uid = auth.getCurrentUser().getUid();
        Log.d("Uid1..................",uid);
        binding.seatType.setVisibility(View.GONE);
        binding.seatCode.setVisibility(View.GONE);

        binding.progressBar2.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(Apply.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Verifying Application");

        adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,sCode);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.seatCode.setAdapter(adapter1);

        adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,sType);
        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.seatType.setAdapter(adapter2);

        database.getReference().child("User").child(uid).orderByKey().
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loginCredentials = snapshot.getValue(LoginCredentials.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });

        binding.ApplyActivitybtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if(!checkInformation()){
                    progressDialog.hide();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    return;
                }
                if(!isPhoneNoVerified()) {
                    Toast.makeText(Apply.this, "Please verify phone no", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    return;
                }
                if(!isChecked()) {
                    Toast.makeText(Apply.this, "Check the box proceed", Toast.LENGTH_SHORT).show();
                    binding.ApplyActivityCheckBox.setError("Click here");
                    progressDialog.hide();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    return;
                }
                long rank = Long.parseLong(binding.ApplyActivityCETRank.getText().toString());

                application = new Application(binding.ApplyActivityName.getText().toString(),
                        binding.ApplyActivityApplicationId.getText().toString(), rank,
                        binding.ApplyActivityPhoneNo.getText().toString(),"","", flagPaymentDone,
                        binding.ApplyActivityCheckBox.isChecked(),binding.ApplyActivityCapSeat.isChecked());

                if(binding.ApplyActivityCapSeat.isChecked()) {
                    if(!binding.seatCode.getSelectedItem().toString().equals("Select seat code") &&
                            !binding.seatType.getSelectedItem().toString().equals("Select seat Type")) {
                        application.setSeatCode(binding.seatCode.getSelectedItem().toString());
                        application.setSeatType(binding.seatType.getSelectedItem().toString());
                    }
                    else {
                        Toast.makeText(Apply.this, "Select seat type and code", Toast.LENGTH_LONG).show();
                        progressDialog.hide();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        return;
                    }
                }
                verifyInformation(application);
            }
        });

        binding.ApplyActivitybtnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNo = binding.ApplyActivityPhoneNo.getText().toString();
                if(phoneNo.length() == 10) {
                    phoneNo = "+91" + phoneNo;
                    Log.d("phoneNO",phoneNo);
                    countDownTimer();
                    binding.ApplyActivityOTP.setFocusable(true);
                    binding.ApplyActivityOTP.setText("");
                    binding.ApplyActivityPhoneNo.setFocusable(false);
                    binding.ApplyActivitybtnVerifyOTP.setEnabled(false);
                    sendVerificationCode(phoneNo);
                }
                else {
                    binding.ApplyActivityPhoneNo.setError("Should not empty");
                }
            }
        });

        binding.ApplyActivitybtnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = binding.ApplyActivityOTP.getText().toString();
                if(otp.length() == 6) {
                    binding.ApplyActivityOTP.setFocusable(false);
                    binding.ApplyActivitybtnVerifyOTP.setVisibility(View.GONE);
                    binding.progressBar2.setVisibility(View.VISIBLE);
                    binding.progressBar2.setClickable(false);
                    binding.ApplyActivityPhoneNo.setFocusable(false);
                    verifyCode(otp);
                }
                else
                    binding.ApplyActivityOTP.setError("Must br 6 digit");
            }
        });

        binding.ApplyActivitybtnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.ApplyActivitybtnRegister.getText().toString().equals("Registered")) {
                    Intent intent = new Intent(Apply.this, PaymentActivity.class);
                    intent.putExtra("Application", application);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(Apply.this, "Please Register First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.ApplyActivityCapSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.ApplyActivityCapSeat.isChecked()) {
                    binding.seatType.setVisibility(View.VISIBLE);
                    binding.seatCode.setVisibility(View.VISIBLE);
                }
                else {
                    binding.seatType.setVisibility(View.GONE);
                    binding.seatCode.setVisibility(View.GONE);
                }
            }
        });
    }

    private void sendVerificationCode(String phoneNo) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNo)            // Phone number to verify
                        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(Apply.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    boolean checkInformation() {
        if(binding.ApplyActivityName.getText().toString().isEmpty()) {
            binding.ApplyActivityName.setError("Name should not empty");
            return false;
        }

        if(binding.ApplyActivityApplicationId.getText().toString().isEmpty()) {
            binding.ApplyActivityApplicationId.setError("Application ID should not empty");
            return false;
        }

        if(binding.ApplyActivityCETRank.getText().toString().isEmpty()) {
            binding.ApplyActivityCETRank.setError("Rank should not empty");
            return false;
        }

        return true;
    }

    boolean isPhoneNoVerified() {
        return flagPhoneNoVerified;
    }

    boolean isPaymentDone() {
        if(application != null)
            flagPaymentDone = application.isPayment();
        return flagPaymentDone;
    }

    boolean isChecked() {
        return binding.ApplyActivityCheckBox.isChecked();
    }

    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

            // after getting credential we are
            // calling sign in method.
            signInWithPhoneAuthCredential(credential);
        }
        catch (Exception e){
            Log.d("Apply", e.getMessage());
        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Apply", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Log.d("Apply", user.getUid());
                            signInWithEmailAndPassword();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("Apply", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("Apply", "onVerificationCompleted:" + credential);
            binding.ApplyActivityOTP.setText(credential.getSmsCode());
            binding.ApplyActivitybtnVerifyOTP.setEnabled(false);
            binding.ApplyActivitybtnVerifyOTP.setVisibility(View.GONE);
            binding.progressBar2.setVisibility(View.VISIBLE);
            binding.ApplyActivityOTP.setEnabled(false);
            signInWithPhoneAuthCredential(credential);

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("Apply", "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Toast.makeText(Apply.this, "Invalid phone No", Toast.LENGTH_SHORT).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Toast.makeText(Apply.this, "Try again tomorrow", Toast.LENGTH_SHORT).show();
            }

            // Show a message and update the UI
            binding.ApplyActivityOTP.setFocusable(false);
            binding.ApplyActivityOTP.setText("");
            binding.ApplyActivityPhoneNo.setFocusable(true);
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId, token);
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("Apply", "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            Toast.makeText(Apply.this, "OTP send", Toast.LENGTH_SHORT).show();
            binding.ApplyActivitybtnVerifyOTP.setEnabled(true);
        }
    };

    private void signInWithEmailAndPassword() {
        auth.signOut();
        auth.signInWithEmailAndPassword(loginCredentials.getEmail(), loginCredentials.getPassword()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d("Apply", "registered successfully");
                            binding.ApplyActivitybtnVerifyOTP.setText("Verified");
                            binding.progressBar2.setVisibility(View.GONE);
                            binding.ApplyActivitybtnVerifyOTP.setVisibility(View.VISIBLE);
                            binding.ApplyActivityPhoneNo.setFocusable(true);
                            flagPhoneNoVerified = true;

                            Toast.makeText(Apply.this, "registered successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void countDownTimer() {
        binding.ApplyActivitybtnSendOTP.setEnabled(false);
        binding.ApplyActivityPhoneNo.setFocusable(false);
        binding.ApplyActivitybtnSendOTP.setText("2:00");
        sec = 120;
        CountDownTimer timer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                changeTime();
            }

            @Override
            public void onFinish() {
                binding.ApplyActivitybtnSendOTP.setEnabled(true);
                binding.ApplyActivitybtnSendOTP.setText("Resend");
                binding.ApplyActivityPhoneNo.setFocusable(true);
            }
        }.start();
    }

    void changeTime() {
        sec -= 1;
        String time = sec + " sec";
        binding.ApplyActivitybtnSendOTP.setText(time);
    }

    void verifyInformation(Application application) {
        reference = fireStore.collection("StudentInfo").document(application.getRank() + "");
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    info = documentSnapshot.toObject(StudentInfo.class);
                    Log.d("StudentInfo", documentSnapshot.getId() + info.toString());

                    if (!binding.ApplyActivityApplicationId.getText().toString().equals(info.getApplicationId())) {
                        Toast.makeText(Apply.this, "Record not found", Toast.LENGTH_SHORT).show();
                        /*binding.ApplyActivityCETRank.setError("Invalid Application ID");*/
                        return;
                    }
                    else if (!binding.ApplyActivityApplicationId.getText().toString().equals(info.getApplicationId())) {
                        Toast.makeText(Apply.this, "Record not found", Toast.LENGTH_SHORT).show();
                        /*binding.ApplyActivityCETRank.setError("Invalid Application ID");*/
                        return;
                    }
                    String name = binding.ApplyActivityName.getText().toString();
                    String[] nameList = name.toLowerCase().split(" ");
                    for (String test : nameList) {
                        if (!application.getName().toLowerCase().contains(test)) {
                            Toast.makeText(Apply.this, "Record not found", Toast.LENGTH_SHORT).show();
                            binding.ApplyActivityCETRank.setError("Invalid Name");
                            return;
                        }
                    }
                    if(!application.getSeatCode().equals("Select seat code") && !application.getSeatType().equals("Select seat Type")) {
                        reference = fireStore.collection("Application").document(uid);
                        reference.set(application);
                        binding.ApplyActivitybtnRegister.setText("Registered");
                        binding.ApplyActivitybtnRegister.setEnabled(false);
                        binding.ApplyActivityName.setError(null);
                        binding.ApplyActivityCETRank.setError(null);
                        binding.ApplyActivityApplicationId.setError(null);
                    }
                    else {
                        Toast.makeText(Apply.this, "Information not correct", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.hide();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                } else {
                    Toast.makeText(Apply.this, "Record Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }
}
