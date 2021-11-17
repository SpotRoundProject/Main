package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.spotround.databinding.ActivityResultBinding;
import com.example.spotround.modle.Application;
import com.example.spotround.modle.JavaMailAPI;
import com.example.spotround.modle.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding binding;
    DocumentReference reference;
    FirebaseFirestore fireStore;
    FirebaseAuth auth;
    Result result;
    ProgressDialog progressDialog;
    Application application;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(ResultActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Fetching data");

        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        FirebaseFirestore.getInstance().collection("Application").document(auth.getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    application = documentSnapshot.toObject(Application.class);
                    reference = fireStore.collection("Result").document(application.getRank() + "");

                    reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if(documentSnapshot.exists()) {
                                result = documentSnapshot.toObject(Result.class);
                                if(result.getPreferenceNo().contains("1"))
                                    binding.Reject.setEnabled(false);
                                binding.choiceAllotted.setText(result.getChoiceCode());
                                binding.PreferenceAllotted.setText(result.getPreferenceNo());
                                binding.seatType.setText(result.checkType());
                                progressDialog.hide();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }
                            else {
                                FirebaseFirestore.getInstance().collection("SeatAccepted")
                                        .document(application.getRank() + "").get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                                                if(documentSnapshot.exists()) {
                                                    binding.Reject.setEnabled(false);
                                                    binding.freeze.setEnabled(false);
                                                    binding.freeze.setText("Seat Accepted");
                                                    result = documentSnapshot.toObject(Result.class);
                                                    binding.seatType.setText(result.checkType());
                                                    binding.PreferenceAllotted.setText(result.getPreferenceNo());
                                                    binding.choiceAllotted.setText(result.getChoiceCode());
                                                }
                                                else {
                                                    binding.choiceAllotted.setText("Not Allotted");
                                                    binding.Reject.setEnabled(false);
                                                    binding.freeze.setEnabled(false);
                                                }
                                                progressDialog.hide();
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            }
                                        });
                            }
                        }
                    });
                }
                else {
                    binding.freeze.setEnabled(false);
                    binding.Reject.setEnabled(false);
                    progressDialog.hide();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }
        });


        binding.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("Result").document(application.getRank() + "")
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        binding.Reject.setText("Seat Rejected");
                        binding.Reject.setEnabled(false);
                        binding.freeze.setEnabled(false);
                    }
                });
            }
        });

        binding.freeze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Seat Accepted");
                progressDialog.show();
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                sendMail();

                FirebaseFirestore.getInstance().collection("SeatAccepted")
                        .document(application.getRank() + "").set(result)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void unused) {
                                binding.freeze.setEnabled(false);
                                binding.Reject.setEnabled(false);
                                FirebaseFirestore.getInstance().collection("Result")
                                        .document(application.getRank() + "").delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(@NonNull Void unused) {
                                        if(application.isClgSeat()) {
                                            FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                                                    .collection("Round 2").document(application.getSeatCode())
                                                    .update("gopens" , FieldValue.increment(1))
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            binding.freeze.setText("Seat Accepted");
                                                            new AlertDialog.Builder(ResultActivity.this)
                                                                    .setTitle("Seat Accepted")
                                                                    .setMessage("You accepted " + result.getChoiceCode() + " seat")

                                                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int which) {}
                                                                    })
                                                                    .setNegativeButton(android.R.string.no, null)
                                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                                    .show();
                                                            progressDialog.hide();
                                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                        }
                                                    });
                                        }
                                        else{
                                            binding.freeze.setText("Seat Accepted");
                                            new AlertDialog.Builder(ResultActivity.this)
                                                    .setTitle("Seat Accepted")
                                                    .setMessage("You accepted " + result.getChoiceCode() + " seat")

                                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {}
                                                    })
                                                    .setNegativeButton(android.R.string.no, null)
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                            progressDialog.hide();
                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        }
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ResultActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }
                        });
            }
        });
    }

    private void sendMail() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String from = "spotroundapp@gmail.com";
                String password = "jcdinstcslswlnzi";
                String mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String message = "You accepted " + result.getChoiceCode() + " seat";
                String subject = "Spot round Seat Allotment";
                String date = "Nov 17, 2021";

                try {
                    JavaMailAPI sender = new JavaMailAPI(getBaseContext(), from, password);
                    sender.sendSeatAcceptedMail(subject,"Hi",date,application.getApplicationId() ,from, mail, result);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        });
    }
}