  package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.spotround.adapter.candidate_list_recycler_view;
import com.example.spotround.databinding.ActivityCandidateListBinding;
import com.example.spotround.modle.CandidateListObj;
import com.example.spotround.modle.StudentInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CandidateList extends AppCompatActivity {
    ActivityCandidateListBinding binding;
    List<CandidateListObj> list;
    candidate_list_recycler_view adapter;
    FirebaseFirestore fireStore;
    DocumentReference studentInfoReference, resultReference;
    CollectionReference collectionReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCandidateListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(CandidateList.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Getting Information");

        fireStore = FirebaseFirestore.getInstance();

        list = new ArrayList<>();

        adapter = new candidate_list_recycler_view(list, this);
        binding.recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        collectionReference = fireStore.collection("Application");

        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    Log.d("Candidate List", "for loop");
                    if (!queryDocumentSnapshots.isEmpty()) {
                        studentInfoReference = fireStore.document("StudentInfo/" + queryDocumentSnapshot.get("rank"));
                        studentInfoReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()) {
                                    StudentInfo info = documentSnapshot.toObject(StudentInfo.class);
                                    CandidateListObj candidateListObj = new CandidateListObj();
                                    candidateListObj.setRank(info.getRank());
                                    candidateListObj.setApplicationId(info.getApplicationId());
                                    candidateListObj.setName(info.getName());
                                    candidateListObj.setCetPercentage(info.getPCMPercentile());
                                    candidateListObj.setCaste(info.getCaste());
                                    candidateListObj.setSeat("Not Allotted");

                                    resultReference = fireStore.document("Result/" + info.getRank());
                                    resultReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()) {
                                                //candidateListObj.setSeat();
                                            } else {
                                                candidateListObj.setSeat("Not Allotted");
                                            }
                                        }
                                    });
                                    list.add(candidateListObj);
                                    adapter.notifyDataSetChanged();
                                    Log.d("Candidate List", list.toString());
                                }
                            }
                        });
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.hide();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressDialog.dismiss();
    }
}