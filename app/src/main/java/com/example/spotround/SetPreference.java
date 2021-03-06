package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.spotround.databinding.ActivitySetPreferenceBinding;
import com.example.spotround.databinding.ProgressBarDialogBinding;
import com.example.spotround.datetime.DateTime;
import com.example.spotround.modle.Application;
import com.example.spotround.modle.Preference;
import com.example.spotround.modle.Schedule;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
    Schedule schedule;
    int clickable = 1;
    Calendar r1S, r1E, r2S, r2E, r3S, r3E, local;
    String round = "Round 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetPreferenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(SetPreference.this);

        SharedPreferences mPrefs = getSharedPreferences("com.example.spotround", MODE_PRIVATE);
        Gson gson = new Gson();
        if(mPrefs.getBoolean("flagSchedule", false)) {
            String json = mPrefs.getString("Schedule", "");
            schedule = gson.fromJson(json, Schedule.class);
            Log.d("Schedule Error", schedule.toString());
        }

        application = (Application)getIntent().getSerializableExtra("Application");
        local = Calendar.getInstance();
        local.set(DateTime.getYear(), DateTime.getMonth() +1, DateTime.getDate(), DateTime.getHr(), DateTime.getMin());
        r1S = Calendar.getInstance();
        r1E = Calendar.getInstance();
        r2S = Calendar.getInstance();
        r2E = Calendar.getInstance();
        r3S = Calendar.getInstance();
        r3E = Calendar.getInstance();
        r1S.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound1Start().substring(0, 2)), Integer.parseInt(schedule.getRound1Start().substring(3, 5)));
        r1E.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound1End().substring(0, 2)), Integer.parseInt(schedule.getRound1End().substring(3, 5)));
        r2S.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound2Start().substring(0, 2)), Integer.parseInt(schedule.getRound2Start().substring(3, 5)));
        r2E.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound2End().substring(0, 2)), Integer.parseInt(schedule.getRound2End().substring(3, 5)));
        r3S.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound3Start().substring(0, 2)), Integer.parseInt(schedule.getRound3Start().substring(3, 5)));
        r3E.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound3End().substring(0, 2)), Integer.parseInt(schedule.getRound3End().substring(3, 5)));


        if(local.after(r1S) && local.before(r1E)) {
            round = "Round 1";
            binding.currentRound.setText(round);
        }
        else if(local.after(r2S) && local.before(r2E)) {
            round = "Round 2";
            binding.currentRound.setText(round);
        }
        else if(local.after(r3S) && local.before(r3E)) {
            round = "Round 3";
            binding.currentRound.setText(round);
        }
        else if(local.before(r1S) ||local.after(r3E)) {
            binding.btnSubmitPreference.setEnabled(false);
            round = "Not Started";
            binding.currentRound.setText(round);
        }


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

        binding.Preference2.setEnabled(false);
        binding.Preference3.setEnabled(false);
        binding.Preference4.setEnabled(false);
        binding.Preference5.setEnabled(false);
        binding.Preference6.setEnabled(false);


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

                reference = fireStore.collection("Preference").document("Round").collection(round).document(application.getRank() + "");

                if(!check()) {
                    Toast.makeText(SetPreference.this, "Select Valid Branch", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    return;
                }
                Preference preference = new Preference(binding.Preference1.getSelectedItem().toString(),
                        binding.Preference2.getSelectedItem().toString(), binding.Preference3.getSelectedItem().toString(),
                        binding.Preference4.getSelectedItem().toString(), binding.Preference5.getSelectedItem().toString(),
                        binding.Preference6.getSelectedItem().toString(), application.isClgSeat(),application.getSeatType(), application.getSeatCode());
                reference.set(preference);

                if(round.equals("Round 2") || round.equals("Round 3"))
                    binding.btnSubmitPreference.setText("Registered");
                else
                    binding.btnSubmitPreference.setText("Submitted");
                binding.btnSubmitPreference.setEnabled(false);
                progressDialog.hide();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });

        binding.Preference1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    if(!binding.btnSubmitPreference.getText().toString().equals("Submitted")) {
                        binding.Preference2.setEnabled(true);
                        clickable = 2;
                    }
                }
                else {
                    clickable = 1;
                    binding.Preference2.setEnabled(false);
                    binding.Preference3.setEnabled(false);
                    binding.Preference4.setEnabled(false);
                    binding.Preference5.setEnabled(false);
                    binding.Preference6.setEnabled(false);
                    binding.Preference2.setSelection(0);
                    binding.Preference3.setSelection(0);
                    binding.Preference4.setSelection(0);
                    binding.Preference5.setSelection(0);
                    binding.Preference6.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.Preference2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    binding.Preference3.setEnabled(true);
                    clickable = 3;
                }
                else {
                    clickable = 2;
                    binding.Preference3.setEnabled(false);
                    binding.Preference4.setEnabled(false);
                    binding.Preference5.setEnabled(false);
                    binding.Preference6.setEnabled(false);
                    binding.Preference3.setSelection(0);
                    binding.Preference4.setSelection(0);
                    binding.Preference5.setSelection(0);
                    binding.Preference6.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.Preference3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    binding.Preference4.setEnabled(true);
                    clickable = 4;
                }
                else {
                    clickable = 3;
                    binding.Preference4.setEnabled(false);
                    binding.Preference5.setEnabled(false);
                    binding.Preference6.setEnabled(false);
                    binding.Preference4.setSelection(0);
                    binding.Preference5.setSelection(0);
                    binding.Preference6.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.Preference4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    binding.Preference5.setEnabled(true);
                    clickable = 5;
                }
                else {
                    clickable = 4;
                    binding.Preference5.setEnabled(false);
                    binding.Preference6.setEnabled(false);
                    binding.Preference5.setSelection(0);
                    binding.Preference6.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.Preference5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    binding.Preference6.setEnabled(true);
                    clickable = 6;
                }
                else {
                    clickable = 5;
                    binding.Preference6.setEnabled(false);
                    binding.Preference6.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
                reference = fireStore.collection("Preference").document("Round").collection("Round 1").document(application.getRank() + "");
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
                                    if(round.equals("Round 2")) {
                                        FirebaseFirestore.getInstance().collection("Preference").document("Round").collection("Round 2").
                                                document(application.getRank() + "").get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                                                if(documentSnapshot.exists()) {
                                                    binding.btnSubmitPreference.setEnabled(false);
                                                    binding.btnSubmitPreference.setText("Registered");
                                                }
                                                else {
                                                    binding.btnSubmitPreference.setEnabled(true);
                                                    binding.btnSubmitPreference.setText("Register");
                                                }
                                                progressDialog.hide();
                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            }
                                        });
                                    }
                                    else if(round.equals("Round 3")) {
                                        FirebaseFirestore.getInstance().collection("Preference").document("Round").collection("Round 3").
                                                document(application.getRank() + "").get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                                                        if(documentSnapshot.exists()) {
                                                            binding.btnSubmitPreference.setEnabled(false);
                                                            binding.btnSubmitPreference.setText("Registered");
                                                        }
                                                        else {
                                                            binding.btnSubmitPreference.setEnabled(true);
                                                            binding.btnSubmitPreference.setText("Register");
                                                        }
                                                        progressDialog.hide();
                                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                    }
                                                });
                                    }
                                }
                            });
                        }
                        else{
                            if(!round.equals("Not Started"))
                                binding.btnSubmitPreference.setEnabled(true);
                            progressDialog.hide();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }

                    }
                });
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

    boolean check() {
        String pref1, pref2, pref3, pref4, pref5, pref6;
        pref1 = binding.Preference1.getSelectedItem().toString();
        pref2 = binding.Preference2.getSelectedItem().toString();
        pref3 = binding.Preference3.getSelectedItem().toString();
        pref4 = binding.Preference4.getSelectedItem().toString();
        pref5 = binding.Preference5.getSelectedItem().toString();
        pref6 = binding.Preference6.getSelectedItem().toString();

        if(clickable == 1)
            return false;
        if(clickable == 2)
            return true;
        if(clickable == 3)
            return !pref1.equals(pref2);
        if(clickable == 4) {
            return !pref1.equals(pref2) && !pref2.equals(pref3) && !pref3.equals(pref1)
         ;
        }
        if(clickable == 5) {
            return !pref1.equals(pref2) && !pref1.equals(pref3) && !pref1.equals(pref4) &&
                    !pref2.equals(pref3) && !pref2.equals(pref4) && !pref3.equals(pref4);
        }
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
}