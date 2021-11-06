package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.spotround.databinding.ActivityResultBinding;
import com.example.spotround.modle.Result;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding binding;
    DocumentReference reference;
    FirebaseFirestore fireStore;
    FirebaseAuth auth;
    Result result;
    ProgressDialog progressDialog;

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

        reference = fireStore.collection("Result").document(auth.getCurrentUser().getUid());

        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                if(documentSnapshot.exists()) {
                    result = documentSnapshot.toObject(Result.class);
                    if(result.getPreferenceNo().contains("1"))
                        binding.betterment.setEnabled(false);
                    binding.choiceAllotted.setText(result.getChoiceCode());
                    binding.PreferenceAllotted.setText(result.getPreferenceNo());
                    binding.seatType.setText(result.getSeatType());
                }
                else {
                    binding.choiceAllotted.setText("Not Allotted");
                    binding.betterment.setEnabled(false);
                    binding.freeze.setEnabled(false);
                }
                progressDialog.hide();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });

        binding.betterment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.freeze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}