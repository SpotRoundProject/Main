package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.spotround.databinding.ActivitySetPreferenceBinding;
import com.example.spotround.modle.Preference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SetPreference extends AppCompatActivity {
    ActivitySetPreferenceBinding binding;
    String uid;
    FirebaseFirestore fireStore;
    DocumentReference reference;
    FirebaseAuth auth;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetPreferenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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


        binding.btnSubmitPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check()) {
                    Toast.makeText(SetPreference.this, "Select valid branch", Toast.LENGTH_LONG).show();
                    return;
                }
                reference = fireStore.collection("Round1Preference").document(uid);
                Preference preference = new Preference(binding.Preference1.getSelectedItem().toString(),
                        binding.Preference2.getSelectedItem().toString(), binding.Preference3.getSelectedItem().toString(),
                        binding.Preference4.getSelectedItem().toString(), binding.Preference5.getSelectedItem().toString(),
                        binding.Preference6.getSelectedItem().toString());
                reference.set(preference);
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                reference = fireStore.collection("Round1Preference").document(uid);
                reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Preference pre = null;
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
                                }
                            });
                        }
                    }
                });
            }
        });

        /*binding.Preference1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    selected[position - 1] = true;
                    if(lastSelected[0] != -1)
                        selected[lastSelected[0]] = false;
                    lastSelected[0] = position - 1;
                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> parent) {}
        });

        binding.Preference2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    selected[position - 1] = true;
                    if(lastSelected[1] != -1)
                        selected[lastSelected[1]] = false;
                    lastSelected[1] = position - 1;
                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> parent) {}
        });

        binding.Preference3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    selected[position - 1] = true;
                    if(lastSelected[2] != -1)
                        selected[lastSelected[2]] = false;
                    lastSelected[2] = position - 1;
                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> parent) {}
        });

        binding.Preference4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    selected[position - 1] = true;
                    if(lastSelected[3] != -1)
                        selected[lastSelected[3]] = false;
                    lastSelected[3] = position - 1;
                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> parent) {}
        });

        binding.Preference5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    selected[position - 1] = true;
                    if(lastSelected[4] != -1)
                        selected[lastSelected[4]] = false;
                    lastSelected[4] = position - 1;
                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> parent) {}
        });

        binding.Preference6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    selected[position - 1] = true;
                    if(lastSelected[5] != -1)
                        selected[lastSelected[5]] = false;
                    lastSelected[5] = position - 1;
                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> parent) {}
        });*/
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
                        if(!pref5.equals(pref6))
                            return true;
                        else return false;
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