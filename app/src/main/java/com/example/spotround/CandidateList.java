  package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.spotround.adapter.alloted_seats_recycler_view;
import com.example.spotround.adapter.candidate_list_recycler_view;
import com.example.spotround.databinding.ActivityCandidateListBinding;
import com.example.spotround.modle.Allotedseats;
import com.example.spotround.modle.Application;
import com.example.spotround.modle.CandidateListObj;
import com.example.spotround.modle.Result;
import com.example.spotround.modle.StudentInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

  public class CandidateList extends AppCompatActivity {
    ActivityCandidateListBinding binding;
    List<CandidateListObj> list;
    List<Allotedseats>list1;
    List<Allotedseats>list2;
    StudentInfo info;
    alloted_seats_recycler_view adapter2;
    alloted_seats_recycler_view adapter1;
    candidate_list_recycler_view adapter;
    Map<String,Result>map1;
    Map<String,Application>map2;
    Map<String,StudentInfo>map3;
    Map<String,Result>acceptedseat;
    FirebaseFirestore fireStore;
    DocumentReference studentInfoReference, resultReference;
    CollectionReference collectionReference;
    ProgressDialog progressDialog;
      ExecutorService executorService = Executors.newSingleThreadExecutor();
    private long currentApplicationSize,applicationsize;

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
        list1=new ArrayList<>();
        list2=new ArrayList<>();
        map1=new HashMap<>();
        map2=new HashMap<>();
        map3=new HashMap<>();
        acceptedseat=new HashMap<>();
        adapter = new candidate_list_recycler_view(list, this);
        adapter1 = new alloted_seats_recycler_view(list1,this);
        adapter2=new alloted_seats_recycler_view(list2,this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView1.setAdapter(adapter1);
        binding.recyclerView2.setAdapter(adapter2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(this);
        LinearLayoutManager layoutManager2=new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView1.setLayoutManager(layoutManager1);
        binding.recyclerView2.setLayoutManager(layoutManager2);
        collectionReference = fireStore.collection("Application");

        /*progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);*/
       /* getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/

       /* collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                                    candidateListObj.setRank(info.getRank() + "");
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
        });*/
        readData(new FirebaseCallback() {
            @Override
            public void onCallback(Map<String, Result> resultMap) {

               readData3(new FirebaseCallbak3() {
                   @Override
                   public void onCallback3() {

                   for(Map.Entry<String,Result>entry:map1.entrySet())
                   {
                       Allotedseats allotedseats=new Allotedseats();
                       allotedseats.setPreference(entry.getValue().getPreferenceNo().toString());
                       allotedseats.setSeat(entry.getValue().getChoiceCode().toString());
                       allotedseats.setSeat_type(entry.getValue().getSeatType());
                       allotedseats.setName(map2.get(entry.getKey().toString()).getName());
                       allotedseats.setCaste(map3.get(entry.getKey().toString()).getCaste());
                       allotedseats.setCetPercentage(map3.get(entry.getKey().toString()).getPCMPercentile());
                       allotedseats.setRank(entry.getKey().toString());
                       allotedseats.setApplicationId(map3.get(entry.getKey().toString()).getApplicationId());
                       list1.add(allotedseats);


                   }
                   for(Map.Entry<String,Application>entry:map2.entrySet())
                   {
                       CandidateListObj candidateListObj = new CandidateListObj();
                       candidateListObj.setRank(entry.getValue().getRank()+"");
                       candidateListObj.setApplicationId(entry.getValue().getApplicationId());
                       candidateListObj.setName(entry.getValue().getName());
                       candidateListObj.setCetPercentage(map3.get(entry.getKey().toString()).getPCMPercentile());
                       candidateListObj.setCaste(map3.get(entry.getKey().toString()).getCaste());
                       Log.d("CandidateList",candidateListObj.toString());
                       list.add(candidateListObj);

                   }
                   readData4(new FirebaseCallbak3() {
                       @Override
                       public void onCallback3() {
                           for(Map.Entry<String,Result>entry:acceptedseat.entrySet())
                           {
                               Allotedseats allotedseats=new Allotedseats();
                               allotedseats.setPreference(entry.getValue().getPreferenceNo().toString());
                               allotedseats.setSeat(entry.getValue().getChoiceCode().toString());
                               allotedseats.setSeat_type(entry.getValue().getSeatType());
                               allotedseats.setName(map2.get(entry.getKey().toString()).getName());
                               allotedseats.setCaste(map3.get(entry.getKey().toString()).getCaste());
                               allotedseats.setCetPercentage(map3.get(entry.getKey().toString()).getPCMPercentile());
                               allotedseats.setRank(entry.getKey().toString());
                               allotedseats.setApplicationId(map3.get(entry.getKey().toString()).getApplicationId());
                               list2.add(allotedseats);
                               adapter2.notifyDataSetChanged();
                           }
                       }
                   });

                   Log.d("Application",map2.toString());
                   Log.d("StudentInfo",map3.toString());
                   Log.d("Result",map1.toString());
                   Log.d("HHIIII",list2.toString());
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               adapter1.notifyDataSetChanged();
                              adapter.notifyDataSetChanged();
                           }
                       });
                   }
               });
            }
        });

    }
    private void readData4(FirebaseCallbak3 firebaseCallbak3)
    {
        FirebaseFirestore.getInstance().collection("SeatAccepted")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                        {
                            Result result=queryDocumentSnapshot.toObject(Result.class);
                            acceptedseat.put(queryDocumentSnapshot.getId(),result);
                        }
                        firebaseCallbak3.onCallback3();
                    }
                });
    }
    private void readData3(FirebaseCallbak3 firebaseCallbak3)
    {
        currentApplicationSize=0;
        FirebaseFirestore.getInstance().collection("Application")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        applicationsize=queryDocumentSnapshots.size();

                        for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                        {
                            Application application=queryDocumentSnapshot.toObject(Application.class);
                            String rank1=application.getRank()+"";
                            map2.put(rank1,application);
                            FirebaseFirestore.getInstance().collection("StudentInfo").document(application.getRank()+"")
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                                            info = documentSnapshot.toObject(StudentInfo.class);
                                           /* if (info.getCaste().equals("SEBC"))
                                                info.setCaste("Open");
                                            else if (info.getCaste().equals("SBC"))
                                                info.setCaste("OBC");*/
                                            map3.put(info.getRank() + "", info);
                                            currentApplicationSize++;
                                        }
                                    });
                        }
                        check(firebaseCallbak3);
                    }
                });
    }
    private void check(FirebaseCallbak3 firebaseCallbak3)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while(currentApplicationSize!=applicationsize);
                firebaseCallbak3.onCallback3();

            }
        });
    }
    private void readData(FirebaseCallback firebaseCallback)
    {
        FirebaseFirestore.getInstance().collection("Result")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                        {
                            Result result=queryDocumentSnapshot.toObject(Result.class);
                            map1.put(queryDocumentSnapshot.getId(),result);
                        }
                        firebaseCallback.onCallback(map1);
                    }
                });
    }
    public void readData1(FirebaseCallback1 firebaseCallback1)
    {
        FirebaseFirestore.getInstance().collection("Application")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        Map<String,Application>applicationMap2=new HashMap<>();
                        for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                        {
                            Application application=queryDocumentSnapshot.toObject(Application.class);
                            String rank1=application.getRank()+"";
                            applicationMap2.put(rank1,application);
                        }
                        firebaseCallback1.onCallback1(applicationMap2);
                    }
                });
    }
    public void readData2(FirebaseCallback2 firebaseCallback2)
    {
        FirebaseFirestore.getInstance().collection("StudentInfo")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                             Map<String,StudentInfo>stringStudentInfoMap1=new HashMap<>();
                             for(QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                             {
                                 StudentInfo studentInfo=queryDocumentSnapshot.toObject(StudentInfo.class);
                                 String rank2=studentInfo.getRank()+"";
                                 stringStudentInfoMap1.put(rank2,studentInfo);
                             }
                             firebaseCallback2.onCallback2(stringStudentInfoMap1);
                    }
                });
    }
    public interface FirebaseCallback {
                void onCallback(Map<String, Result>resultMap);
    }
    public interface FirebaseCallback1
    {
        void onCallback1(Map<String,Application>applicationMap);
    }
    public interface FirebaseCallback2
    {
        void onCallback2(Map<String,StudentInfo>stringStudentInfoMap);
    }
    public interface FirebaseCallbak3
    {
        void onCallback3();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressDialog.dismiss();
    }
}