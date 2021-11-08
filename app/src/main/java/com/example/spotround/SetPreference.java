package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.spotround.databinding.ActivitySetPreferenceBinding;
import com.example.spotround.databinding.ProgressBarDialogBinding;
import com.example.spotround.modle.Application;
import com.example.spotround.modle.Preference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SetPreference extends AppCompatActivity {
    ActivitySetPreferenceBinding binding;
    String uid;
    FirebaseFirestore fireStore;
    DocumentReference reference;
    FirebaseAuth auth;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Preference pre = null;
    ProgressDialog progressDialog;
    Application application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetPreferenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(SetPreference.this);

        application = (Application)getIntent().getSerializableExtra("Application");

        fireStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();

        String [] sBranch = {"Select Branch","Computer Science and Engineering","Information Technology","Mechanical Engineering","Electronics Engineering","Electrical Engineering","Civil Engineering"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,sBranch);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.Preference1.setAdapter(adapter);
        binding.Preference2.setAdapter(adapter);
        binding.Preference3.setAdapter(adapter);
        binding.Preference4.setAdapter(adapter);
        binding.Preference5.setAdapter(adapter);
        binding.Preference6.setAdapter(adapter);
        binding.btnSubmitPreference.setEnabled(false);


        binding.btnSubmitPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Submitting Preference");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if(!check()) {
                    Toast.makeText(SetPreference.this, "Select valid branch", Toast.LENGTH_LONG).show();
                }
                else {
                    reference = fireStore.collection("Round1Preference").document(uid);
                    Preference preference = new Preference(binding.Preference1.getSelectedItem().toString(),
                            binding.Preference2.getSelectedItem().toString(), binding.Preference3.getSelectedItem().toString(),
                            binding.Preference4.getSelectedItem().toString(), binding.Preference5.getSelectedItem().toString(),
                            binding.Preference6.getSelectedItem().toString(), false,"", "");
                    reference.set(preference);
                }

                progressDialog.hide();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });

        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Checking Information");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                reference = fireStore.collection("Round1Preference").document(uid);
                reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            pre = documentSnapshot.toObject(Preference.class);
                            Log.d("Preference", pre + "");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.Preference1.setEnabled(false);
                                    binding.Preference2.setEnabled(false);
                                    binding.Preference3.setEnabled(false);
                                    binding.Preference4.setEnabled(false);
                                    binding.Preference5.setEnabled(false);
                                    binding.Preference6.setEnabled(false);
                                    binding.btnSubmitPreference.setEnabled(false);
                                    binding.btnSubmitPreference.setText("Submitted");
                                    binding.Preference1.setSelection(Arrays.asList(sBranch).indexOf(pre.getPreference1()));
                                    binding.Preference2.setSelection(Arrays.asList(sBranch).indexOf(pre.getPreference2()));
                                    binding.Preference3.setSelection(Arrays.asList(sBranch).indexOf(pre.getPreference3()));
                                    binding.Preference4.setSelection(Arrays.asList(sBranch).indexOf(pre.getPreference4()));
                                    binding.Preference5.setSelection(Arrays.asList(sBranch).indexOf(pre.getPreference5()));
                                    binding.Preference6.setSelection(Arrays.asList(sBranch).indexOf(pre.getPreference6()));
                                }
                            });
                        }
                        else{
                            binding.btnSubmitPreference.setEnabled(true);
                        }
                        progressDialog.hide();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                });
            }
        });
    }

    boolean check() {
        String pref1, pref2, pref3, pref4, pref5, pref6;
        pref1 = binding.Preference1.getSelectedItem().toString();
        pref2 = binding.Preference2.getSelectedItem().toString();
        pref3 = binding.Preference3.getSelectedItem().toString();
        pref4 = binding.Preference4.getSelectedItem().toString();
        pref5 = binding.Preference5.getSelectedItem().toString();
        pref6 = binding.Preference6.getSelectedItem().toString();

        if(!pref1.equals(pref2) && !pref1.equals(pref3) && !pref1.equals(pref4) && !pref1.equals(pref5) && !pref1.equals(pref6)) {
            if(!pref2.equals(pref3) && !pref2.equals(pref4) && !pref2.equals(pref5) && !pref2.equals(pref6)) {
                if(!pref3.equals(pref4) && !pref3.equals(pref5) && !pref3.equals(pref6)) {
                    if(!pref4.equals(pref5) && !pref4.equals(pref6)) {
                        return !pref5.equals(pref6);
                    }
                    else return false;
                }
                else return false;
            }
            else return false;
        }
        else return false;
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