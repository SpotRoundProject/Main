package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.spotround.databinding.ActivityComputeResultBinding;
import com.example.spotround.datetime.DateTime;
import com.example.spotround.modle.Application;
import com.example.spotround.modle.NewCategory;
import com.example.spotround.modle.Preference;
import com.example.spotround.modle.Result;
import com.example.spotround.modle.Schedule;
import com.example.spotround.modle.StudentInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComputeResult extends AppCompatActivity {
    public static int a=0;

    ActivityComputeResultBinding binding;
    ProgressDialog progressDialog;
    NewCategory cse,it,en,el,cv,me, selectBranch;
    Application application;
    StudentInfo info;
    Preference preference;
    Map<String, NewCategory>vacancy = new HashMap<>();
    Map<Long, Application>applicationList = new HashMap<>();
    Map<Long, StudentInfo>studentInfoList = new HashMap<>();
    Map<Long, Preference>preferenceList = new HashMap<>();
    List<Long> rankList = new ArrayList<>();
    int pref=0;
    int check=0;
    String seat="";
    String type="";
    int access=1;
    private long applicationSize, currentApplications;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Schedule schedule;
    Calendar local, r1R, r2R, r3R, r1R10, r2R10, r3R10;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComputeResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        schedule = (Schedule) getIntent().getSerializableExtra("Schedule");
        local = Calendar.getInstance();
        local.set(DateTime.getYear(), DateTime.getMonth(), DateTime.getDate(), DateTime.getHr(), DateTime.getMin());
        r1R = Calendar.getInstance();
        r2R = Calendar.getInstance();
        r3R = Calendar.getInstance();
        r1R10 = Calendar.getInstance();
        r2R10 = Calendar.getInstance();
        r3R10 = Calendar.getInstance();
        r1R.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getR1Result().substring(0, 2)), Integer.parseInt(schedule.getR1Result().substring(3, 5)));
        r2R.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getR2Result().substring(0, 2)), Integer.parseInt(schedule.getR2Result().substring(3, 5)));
        r3R.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getR3Result().substring(0, 2)), Integer.parseInt(schedule.getR3Result().substring(3, 5)));
        r1R10.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getR1Result().substring(0, 2)), Integer.parseInt(schedule.getR1Result().substring(3, 5)));
        r1R10.add(Calendar.MINUTE, 10);
        r2R10.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getR2Result().substring(0, 2)), Integer.parseInt(schedule.getR2Result().substring(3, 5)));
        r2R10.add(Calendar.MINUTE, 10);
        r3R10.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getR3Result().substring(0, 2)), Integer.parseInt(schedule.getR3Result().substring(3, 5)));
        r3R10.add(Calendar.MINUTE, 10);


        progressDialog = new ProgressDialog(ComputeResult.this);
        progressDialog.setTitle("Round 1");
        progressDialog.setMessage("Fetching Vacancy");

        binding.Round1.setEnabled(true);
        binding.Round2.setEnabled(true);
        binding.Round3.setEnabled(true);

        LocalDate scheduleDate = LocalDate.of(schedule.getYear(), schedule.getMonth(), schedule.getDateInt());
        if(DateTime.getLocalDate().equals(scheduleDate)) {
            if (local.after(r1R) && local.before(r1R10)) {
                FirebaseFirestore.getInstance().collection("RoundResult").document("Round1").
                        get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            boolean result = (boolean) documentSnapshot.get("R");
                            if (result) {
                                binding.Round1.setEnabled(false);
                                binding.Round1.setText("Seats Allotted");
                            } else {
                                binding.Round1.setEnabled(true);
                            }
                        }
                    }
                });
            }


            if (local.after(r2R) && local.before(r2R10)) {
                FirebaseFirestore.getInstance().collection("RoundResult").document("Round2").
                        get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            boolean result = (boolean) documentSnapshot.get("R");
                            if (result) {
                                binding.Round2.setEnabled(false);
                                binding.Round2.setText("Seats Allotted");
                            } else {
                                binding.Round2.setEnabled(true);
                            }
                        }
                    }
                });
            }

            if (local.after(r3R) && local.before(r3R10)) {
                FirebaseFirestore.getInstance().collection("RoundResult").document("Round3").
                        get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            boolean result = (boolean) documentSnapshot.get("R");
                            if (result) {
                                binding.Round3.setEnabled(false);
                                binding.Round3.setText("Seats Allotted");
                            } else {
                                binding.Round3.setEnabled(true);
                            }
                        }
                    }
                });
            }
        }


        binding.Round1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vacancy.clear();
                applicationList.clear();
                studentInfoList.clear();
                preferenceList.clear();
                rankList.clear();
                binding.Round1.setEnabled(false);
                progressDialog.setTitle("Round 1");
                progressDialog.setMessage("Fetching Vacancy");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                getVacancyRound1(new FirestoreCallback(){
                    @Override
                    public void onCallback() {
                        progressDialog.setMessage("Fetching Student Information");
                        getStudentInfoRound1(new FirestoreCallback() {
                            @Override
                            public void onCallback() {
                                progressDialog.setMessage("Allotting seats");
                                generateResultRound1();
                            }
                        });
                    }
                });
            }
        });

        binding.Round2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vacancy.clear();
                applicationList.clear();
                studentInfoList.clear();
                preferenceList.clear();
                rankList.clear();
                binding.Round2.setEnabled(false);
                progressDialog.setTitle("Round 2");
                progressDialog.setMessage("Fetching Vacancy");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                getVacancyRound2(new FirestoreCallback() {
                    @Override
                    public void onCallback() {
                        progressDialog.setMessage("Fetching Student Information");
                        getStudentInfoRound2(new FirestoreCallback() {
                            @Override
                            public void onCallback() {
                                progressDialog.setMessage("Allotting seats");
                                generateResultRound2();
                            }
                        });
                    }
                });
            }
        });

        binding.Round3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vacancy.clear();
                applicationList.clear();
                studentInfoList.clear();
                preferenceList.clear();
                rankList.clear();
                binding.Round2.setEnabled(false);
                progressDialog.setTitle("Round 3");
                progressDialog.setMessage("Fetching Vacancy");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                getVacancyRound3(new FirestoreCallback() {
                    @Override
                    public void onCallback() {
                        progressDialog.setMessage("Fetching Student Information");
                        getStudentInfoRound3(new FirestoreCallback() {
                            @Override
                            public void onCallback() {
                                progressDialog.setMessage("Allotting seats");
                                generateResultRound3();
                            }
                        });
                    }
                });
            }
        });

    }

    private interface FirestoreCallback {
        void onCallback();
    }

    //Round1

    private void getVacancyRound1(FirestoreCallback firestoreCallback) {
        FirebaseFirestore.getInstance().collection("Vacancy")
                .document("Round").collection("Round 1")
                .document("Civil Engineering")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot)
                    {
                        cv=documentSnapshot.toObject(NewCategory.class);
                        Log.d("Civil", cv.toString());
                        FirebaseFirestore.getInstance().collection("Vacancy")
                                .document("Round").collection("Round 1")
                                .document("Computer Science and Engineering")
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                    {
                                        cse=documentSnapshot.toObject(NewCategory.class);
                                        Log.d("CSE", cse.toString());
                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                .document("Round").collection("Round 1")
                                                .document("Electrical Engineering")
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                    {
                                                        el=documentSnapshot.toObject(NewCategory.class);
                                                        Log.d("EL", el.toString());

                                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                                .document("Round").collection("Round 1")
                                                                .document("Electronics Engineering")
                                                                .get()
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                                    {
                                                                        en=documentSnapshot.toObject(NewCategory.class);
                                                                        Log.d("EN", en.toString());
                                                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                                                .document("Round").collection("Round 1")
                                                                                .document("Information Technology")
                                                                                .get()
                                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                    @Override
                                                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                                                    {
                                                                                        it=documentSnapshot.toObject(NewCategory.class);
                                                                                        Log.d("IT", it.toString());
                                                                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                                                                .document("Round").collection("Round 1")
                                                                                                .document("Mechanical Engineering")
                                                                                                .get()
                                                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                                                                    {
                                                                                                        me=documentSnapshot.toObject(NewCategory.class);
                                                                                                        Log.d("ME", me.toString());
                                                                                                        vacancy.put("Computer Science and Engineering",cse);
                                                                                                        vacancy.put("Information Technology",it);
                                                                                                        vacancy.put("Electrical Engineering",el);
                                                                                                        vacancy.put("Electronics Engineering",en);
                                                                                                        vacancy.put("Civil Engineering",cv);
                                                                                                        vacancy.put("Mechanical Engineering",me);
                                                                                                        firestoreCallback.onCallback();
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                });
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    private void getStudentInfoRound1(FirestoreCallback firestoreCallback) {
        currentApplications = 0;
        FirebaseFirestore.getInstance().collection("Application")
                .orderBy("rank")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots)
                    {
                        applicationSize = queryDocumentSnapshots.size();
                        for(QueryDocumentSnapshot doc1:queryDocumentSnapshots)
                        {
                            application=doc1.toObject(Application.class);

                            applicationList.put(application.getRank(), application);
                            Log.d("application", application.toString());
                            FirebaseFirestore.getInstance().collection("StudentInfo").document(application.getRank() + "")
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                                    info = documentSnapshot.toObject(StudentInfo.class);

                                    if(info.getCaste().equals("SEBC"))
                                        info.setCaste("Open");
                                    else if(info.getCaste().equals("SBC"))
                                        info.setCaste("OBC");
                                    studentInfoList.put(info.getRank(), info);
                                    Log.d("Info", info.toString());
                                    currentApplications ++;
                                }
                            });

                        }
                        untilDataFetch1(firestoreCallback);
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_LONG).show();

            }
        });
    }

    private void untilDataFetch1(FirestoreCallback firestoreCallback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while(currentApplications != applicationSize);
                getPreferenceRound1(firestoreCallback);
            }
        });
    }

    private void getPreferenceRound1(FirestoreCallback firestoreCallback) {
        FirebaseFirestore.getInstance().collection("Preference/Round/Round 1/")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<DocumentSnapshot> list = task.getResult().getDocuments();
                            for(int i = 0; i < list.size(); i++) {
                                rankList.add(Long.parseLong(list.get(i).getId()));
                                preferenceList.put(Long.parseLong(list.get(i).getId()), list.get(i).toObject(Preference.class));
                            }
                            Collections.sort(rankList);
                            firestoreCallback.onCallback();
                        }
                    }
                });
    }

    private void generateResultRound1() {
        for(Long rank:rankList) {
            pref = 0;
            seat = "";
            type = "";
            preference = preferenceList.get(rank);
            preference.check();
            application = applicationList.get(rank);
            info = studentInfoList.get(rank);
            Log.d(rank+"",preference.toString());
            check = 0;
            if (info.getCaste().equals("Open")) {

                access = vacancy.get(preference.getPreference1()).getGopens();
                if (access != 0) {
                    seat = preference.getPreference1();
                    type = "gopens";
                    pref = 1;
                    vacancy.get(preference.getPreference1()).setGopens(access - 1);
                } else {
                    access = vacancy.get(preference.getPreference2()).getGopens();
                    if (access != 0) {
                        seat = preference.getPreference2();
                        type = "gopens";
                        pref = 2;
                        vacancy.get(preference.getPreference2()).setGopens(access - 1);
                    } else {
                        access = vacancy.get(preference.getPreference3()).getGopens();
                        if (access != 0) {
                            seat = preference.getPreference3();
                            type = "gopens";
                            pref = 3;
                            vacancy.get(preference.getPreference3()).setGopens(access - 1);
                        } else {
                            access = vacancy.get(preference.getPreference4()).getGopens();
                            if (access != 0) {
                                seat = preference.getPreference4();
                                type = "gopens";
                                pref = 4;
                                vacancy.get(preference.getPreference4()).setGopens(access - 1);
                            } else {
                                access = vacancy.get(preference.getPreference5()).getGopens();
                                if (access != 0) {
                                    seat = preference.getPreference5();
                                    type = "gopens";
                                    pref = 5;
                                    vacancy.get(preference.getPreference5()).setGopens(access - 1);
                                } else {
                                    access = vacancy.get(preference.getPreference6()).getGopens();
                                    if (access != 0) {
                                        seat = preference.getPreference6();
                                        type = "gopens";
                                        pref = 6;
                                        vacancy.get(preference.getPreference6()).setGopens(access - 1);
                                    } else {
                                        Log.d("Round1","Sorry.You have not allotted any seat");
                                    }
                                }
                            }
                        }

                    }
                }

            } else {
                access = vacancy.get(preference.getPreference1()).getGopens();
                if (access != 0) {
                    seat = preference.getPreference1();
                    type = "gopens";
                    pref = 1;
                    vacancy.get(preference.getPreference1()).setGopens(access - 1);
                } else {
                    Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_LONG).show();
                    access = vacancy.get(preference.getPreference2()).getGopens();
                    if (access != 0) {
                        seat = preference.getPreference2();
                        type = "gopens";
                        pref = 2;
                        vacancy.get(preference.getPreference2()).setGopens(access - 1);
                    } else {
                        access = vacancy.get(preference.getPreference3()).getGopens();
                        if (access != 0) {
                            seat = preference.getPreference3();
                            type = "gopens";
                            pref = 3;
                            vacancy.get(preference.getPreference3()).setGopens(access - 1);
                        } else {
                            access = vacancy.get(preference.getPreference4()).getGopens();
                            if (access != 0) {
                                seat = preference.getPreference4();
                                type = "gopens";
                                pref = 4;
                                vacancy.get(preference.getPreference4()).setGopens(access - 1);
                            } else {
                                access = vacancy.get(preference.getPreference5()).getGopens();
                                if (access != 0) {
                                    seat = preference.getPreference5();
                                    type = "gopens";
                                    pref = 5;
                                    vacancy.get(preference.getPreference5()).setGopens(access - 1);
                                } else {
                                    access = vacancy.get(preference.getPreference6()).getGopens();
                                    if (access != 0) {
                                        seat = preference.getPreference6();
                                        type = "gopens";
                                        pref = 6;
                                        vacancy.get(preference.getPreference6()).setGopens(access - 1);
                                    } else {
                                        check += 1;
                                    }
                                }
                            }
                        }

                    }

                }
                if (info.getGender().equals("F") && (info.getCaste().equals("Open"))) {
                    access = vacancy.get(preference.getPreference1()).getLopens();
                    if (access != 0) {
                        if (seat.equals("")) {
                            seat = preference.getPreference1();
                            type = "lopens";
                            pref = 1;
                            vacancy.get(preference.getPreference1()).setLopens(access - 1);
                        } else if (seat.equals(preference.getPreference1())) {
                            int solve = vacancy.get(seat).getGopens();
                            vacancy.get(seat).setGopens(solve + 1);
                            seat = preference.getPreference1();
                            type = "lopens";
                            pref = 1;
                            vacancy.get(preference.getPreference1()).setLopens(access - 1);
                        } else if (!seat.equals("")) {
                            int solve = vacancy.get(seat).getGopens();
                            vacancy.get(seat).setGopens(solve + 1);
                            seat = preference.getPreference1();
                            type = "lopens";
                            pref = 1;
                            vacancy.get(seat).setLopens((access - 1));
                        }
                    } else {
                        access = vacancy.get(preference.getPreference2()).getLopens();
                        if (access != 0) {
                            if (seat.equals("")) {
                                seat = preference.getPreference2();
                                type = "lopens";
                                pref = 2;
                                vacancy.get(preference.getPreference2()).setLopens(access - 1);
                            } else if (seat.equals(preference.getPreference2())) {
                                int solve = vacancy.get(seat).getGopens();
                                vacancy.get(seat).setGopens(solve + 1);
                                seat = preference.getPreference2();
                                type = "lopens";
                                pref = 2;
                                vacancy.get(preference.getPreference2()).setLopens(access - 1);
                            } else if (pref < 2) {
                                Log.d("ComputeResult", "Seat already allotted");
                            } else if (!seat.equals("")) {
                                int solve = vacancy.get(seat).getGopens();
                                vacancy.get(seat).setGopens(solve + 1);
                                seat = preference.getPreference2();
                                type = "lopens";
                                pref = 2;
                                vacancy.get(preference.getPreference2()).setLopens(access - 1);
                            }

                        } else {
                            access = vacancy.get(preference.getPreference3()).getLopens();
                            if (access != 0) {
                                if (seat.equals("")) {
                                    seat = preference.getPreference3();
                                    type = "lopens";
                                    pref = 3;
                                    vacancy.get(preference.getPreference3()).setLopens(access - 1);
                                } else if (seat.equals(preference.getPreference3())) {
                                    int solve = vacancy.get(seat).getGopens();
                                    vacancy.get(seat).setGopens(solve + 1);
                                    seat = preference.getPreference3();
                                    type = "lopens";
                                    pref = 3;
                                    vacancy.get(preference.getPreference3()).setLopens(access - 1);
                                } else if (pref < 3) {
                                    Log.d("ComputeResult", "Seat already allotted");
                                } else if (!seat.equals("")) {
                                    int solve = vacancy.get(seat).getGopens();
                                    vacancy.get(seat).setGopens(solve + 1);
                                    seat = preference.getPreference3();
                                    type = "lopens";
                                    pref = 3;
                                    vacancy.get(preference.getPreference3()).setLopens(access - 1);
                                }

                            } else {
                                access = vacancy.get(preference.getPreference4()).getLopens();
                                if (access != 0) {
                                    if (seat.equals("")) {
                                        seat = preference.getPreference4();
                                        type = "lopens";
                                        pref = 4;
                                        vacancy.get(preference.getPreference4()).setLopens(access - 1);
                                    } else if (seat.equals(preference.getPreference4())) {
                                        int solve = vacancy.get(seat).getGopens();
                                        vacancy.get(seat).setGopens(solve + 1);
                                        seat = preference.getPreference4();
                                        type = "lopens";
                                        pref = 4;
                                        vacancy.get(preference.getPreference4()).setLopens(access - 1);
                                    } else if (pref < 4) {
                                        Log.d("ComputeResult", "Seat already allotted");
                                    } else if (!seat.equals("")) {
                                        int solve = vacancy.get(seat).getGopens();
                                        vacancy.get(seat).setGopens(solve + 1);
                                        seat = preference.getPreference4();
                                        type = "lopens";
                                        pref = 4;
                                        vacancy.get(preference.getPreference4()).setLopens(access - 1);
                                    }
                                } else {
                                    access = vacancy.get(preference.getPreference5()).getLopens();
                                    if (access != 0) {
                                        if (seat.equals("")) {
                                            seat = preference.getPreference5();
                                            type = "lopens";
                                            pref = 5;
                                            vacancy.get(preference.getPreference5()).setLopens(access - 1);
                                        } else if (seat.equals(preference.getPreference5())) {
                                            int solve = vacancy.get(seat).getGopens();
                                            vacancy.get(seat).setGopens(solve + 1);
                                            seat = preference.getPreference5();
                                            type = "lopens";
                                            pref = 5;
                                            vacancy.get(preference.getPreference5()).setLopens(access - 1);
                                        } else if (pref < 5) {
                                            Log.d("ComputeResult", "Seat already allotted");
                                        } else if (!seat.equals("")) {
                                            int solve = vacancy.get(seat).getGopens();
                                            vacancy.get(seat).setGopens(solve + 1);
                                            seat = preference.getPreference5();
                                            type = "lopens";
                                            pref = 5;
                                            vacancy.get(preference.getPreference5()).setLopens(access - 1);
                                        }
                                    } else {
                                        access = vacancy.get(preference.getPreference6()).getLopens();
                                        if (access != 0) {
                                            if (seat.equals("")) {
                                                seat = preference.getPreference6();
                                                type = "lopens";
                                                pref = 6;
                                                vacancy.get(preference.getPreference6()).setLopens(access - 1);
                                            } else if (seat.equals(preference.getPreference6())) {
                                                int solve = vacancy.get(seat).getGopens();
                                                vacancy.get(seat).setGopens(solve + 1);
                                                seat = preference.getPreference6();
                                                type = "lopens";
                                                pref = 6;
                                                vacancy.get(preference.getPreference6()).setLopens(access - 1);
                                            } else if (pref < 6) {
                                                Log.d("ComputeResult", "Seat already allotted");
                                            } else if (!seat.equals("")) {
                                                int solve = vacancy.get(seat).getGopens();
                                                vacancy.get(seat).setGopens(solve + 1);
                                                seat = preference.getPreference6();
                                                type = "lopens";
                                                pref = 6;
                                                vacancy.get(preference.getPreference6()).setLopens(access - 1);
                                            }
                                        } else {
                                            check += 1;
                                        }
                                    }
                                }
                            }

                        }
                    }

                }
                if (!info.getCaste().equals("Open")) {
                    String l = "g";
                    l = l + info.getCaste().toLowerCase();
                    l = l + "s";
                    access = vacancy.get(preference.getPreference1()).getCasteVacancy(l);
                    if (access != 0) {
                        if (seat.equals("")) {
                            seat = preference.getPreference1();
                            type = l;
                            pref = 1;
                            vacancy.get(preference.getPreference1()).DecCasteVacancy(l);
                        } else if (seat.equals(preference.getPreference1())) {
                            vacancy.get(preference.getPreference1()).IncCasteVacancy(type);
                            seat = preference.getPreference1();
                            type = l;
                            pref = 1;
                            vacancy.get(preference.getPreference1()).DecCasteVacancy(type);
                        } else if (!seat.equals("")) {
                            vacancy.get(preference.getPreference1()).IncCasteVacancy(type);
                            seat = preference.getPreference1();
                            type = l;
                            pref = 1;
                            vacancy.get(preference.getPreference1()).DecCasteVacancy(type);
                        }
                    } else {
                        access = vacancy.get(preference.getPreference1()).getCasteVacancy(l);
                        if (access != 0) {
                            if (seat.equals("")) {
                                seat = preference.getPreference2();
                                type = l;
                                pref = 2;
                                vacancy.get(preference.getPreference2()).DecCasteVacancy(type);
                            } else if (seat.equals(preference.getPreference2())) {
                                vacancy.get(preference.getPreference2()).IncCasteVacancy(type);
                                seat = preference.getPreference2();
                                type = l;
                                pref = 2;
                                vacancy.get(preference.getPreference2()).DecCasteVacancy(type);
                            } else if (pref < 2 && !seat.equals("")) {
                                Log.d("ComputeResult", "Seat already allotted");
                            } else if (!seat.equals("")) {
                                vacancy.get(preference.getPreference2()).IncCasteVacancy(type);
                                seat = preference.getPreference2();
                                type = l;
                                pref = 2;
                                vacancy.get(preference.getPreference2()).DecCasteVacancy(type);
                            }
                        } else {
                            access = vacancy.get(preference.getPreference3()).getCasteVacancy(l);
                            if (access != 0) {
                                if (seat.equals("")) {
                                    seat = preference.getPreference3();
                                    type = l;
                                    pref = 3;
                                    vacancy.get(preference.getPreference3()).DecCasteVacancy(type);
                                } else if (seat.equals(preference.getPreference3())) {
                                    vacancy.get(preference.getPreference3()).IncCasteVacancy(type);
                                    seat = preference.getPreference3();
                                    type = l;
                                    pref = 3;
                                    vacancy.get(preference.getPreference3()).DecCasteVacancy(type);
                                } else if (pref < 3 && !seat.equals("")) {
                                    Log.d("ComputeResult", "Seat already allotted");
                                } else if (!seat.equals("")) {
                                    vacancy.get(preference.getPreference3()).IncCasteVacancy(type);
                                    seat = preference.getPreference3();
                                    type = l;
                                    pref = 3;
                                    vacancy.get(preference.getPreference3()).DecCasteVacancy(type);
                                }
                            } else {
                                access = vacancy.get(preference.getPreference4()).getCasteVacancy(l);
                                if (access != 0) {
                                    if (seat.equals("")) {
                                        seat = preference.getPreference4();
                                        type = l;
                                        pref = 4;
                                        vacancy.get(preference.getPreference4()).DecCasteVacancy(type);
                                    } else if (seat.equals(preference.getPreference4())) {
                                        vacancy.get(preference.getPreference4()).IncCasteVacancy(type);
                                        seat = preference.getPreference4();
                                        type = l;
                                        pref = 4;
                                        vacancy.get(preference.getPreference4()).DecCasteVacancy(type);
                                    } else if (pref < 4 && !seat.equals("")) {
                                        Log.d("ComputeResult", "Seat already allotted");
                                    } else if (!seat.equals("")) {
                                        vacancy.get(preference.getPreference4()).IncCasteVacancy(type);
                                        seat = preference.getPreference4();
                                        type = l;
                                        pref = 4;
                                        vacancy.get(preference.getPreference4()).DecCasteVacancy(type);
                                    }
                                } else {
                                    access = vacancy.get(preference.getPreference5()).getCasteVacancy(l);
                                    if (access != 0) {
                                        if (seat.equals("")) {
                                            seat = preference.getPreference5();
                                            type = l;
                                            pref = 5;
                                            vacancy.get(preference.getPreference5()).DecCasteVacancy(type);
                                        } else if (seat.equals(preference.getPreference5())) {
                                            vacancy.get(preference.getPreference5()).IncCasteVacancy(type);
                                            seat = preference.getPreference5();
                                            type = l;
                                            pref = 5;
                                            vacancy.get(preference.getPreference5()).DecCasteVacancy(type);
                                        } else if (pref < 5 && !seat.equals("")) {
                                            Log.d("ComputeResult", "Seat already allotted");
                                        } else if (!seat.equals("")) {
                                            vacancy.get(preference.getPreference5()).IncCasteVacancy(type);
                                            seat = preference.getPreference5();
                                            type = l;
                                            pref = 5;
                                            vacancy.get(preference.getPreference5()).DecCasteVacancy(type);
                                        }
                                    } else {
                                        access = vacancy.get(preference.getPreference6()).getCasteVacancy(l);
                                        if (access != 0) {
                                            if (seat.equals("")) {
                                                seat = preference.getPreference6();
                                                type = l;
                                                pref = 6;
                                                vacancy.get(preference.getPreference6()).DecCasteVacancy(type);
                                            } else if (seat.equals(preference.getPreference6())) {
                                                vacancy.get(preference.getPreference6()).IncCasteVacancy(type);
                                                seat = preference.getPreference6();
                                                type = l;
                                                pref = 6;
                                                vacancy.get(preference.getPreference6()).DecCasteVacancy(type);
                                            } else if (pref < 6 && !seat.equals("")) {
                                                Log.d("ComputeResult", "Seat already allotted");
                                            } else if (!seat.equals("")) {
                                                vacancy.get(preference.getPreference6()).IncCasteVacancy(type);
                                                seat = preference.getPreference6();
                                                type = l;
                                                pref = 6;
                                                vacancy.get(preference.getPreference6()).DecCasteVacancy(type);
                                            }
                                        } else {
                                            check += 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!info.getCaste().equals("Open") && info.getGender().equals("F")) {
                    String l = "l" + info.getCaste().toLowerCase();
                    l = l + "s";
                    access = vacancy.get(preference.getPreference1()).getCasteVacancy(l);
                    if (access != 0) {
                        if (seat.equals("")) {
                            seat = preference.getPreference1();
                            type = l;
                            pref = 1;
                            vacancy.get(preference.getPreference1()).DecCasteVacancy(l);
                        }
                        if (seat.equals(preference.getPreference1())) {
                            vacancy.get(preference.getPreference1()).IncCasteVacancy(type);
                            seat = preference.getPreference1();
                            type = l;
                            pref = 1;
                            vacancy.get(preference.getPreference1()).DecCasteVacancy(type);
                        } else if (!seat.equals("")) {
                            vacancy.get(preference.getPreference1()).IncCasteVacancy(type);
                            seat = preference.getPreference1();
                            type = l;
                            pref = 1;
                            vacancy.get(preference.getPreference1()).DecCasteVacancy(type);
                        }
                    } else {
                        access = vacancy.get(preference.getPreference2()).getCasteVacancy(l);
                        if (access != 0) {
                            if (seat.equals("")) {
                                seat = preference.getPreference2();
                                type = l;
                                pref = 2;
                                vacancy.get(preference.getPreference2()).DecCasteVacancy(type);
                            } else if (seat.equals(preference.getPreference2())) {
                                vacancy.get(preference.getPreference2()).IncCasteVacancy(type);
                                seat = preference.getPreference2();
                                type = l;
                                pref = 2;
                                vacancy.get(preference.getPreference2()).DecCasteVacancy(type);
                            } else if (pref < 2 && !seat.equals("")) {
                                Log.d("ComputeResult", "Seat already allotted");
                            } else if (!seat.equals("")) {
                                vacancy.get(preference.getPreference2()).IncCasteVacancy(type);
                                seat = preference.getPreference2();
                                type = l;
                                pref = 2;
                                vacancy.get(preference.getPreference2()).DecCasteVacancy(type);
                            }
                        } else {
                            access = vacancy.get(preference.getPreference3()).getCasteVacancy(l);
                            if (access != 0) {
                                if (seat.equals("")) {
                                    seat = preference.getPreference3();
                                    type = l;
                                    pref = 3;
                                    vacancy.get(preference.getPreference3()).DecCasteVacancy(type);
                                } else if (seat.equals(preference.getPreference3())) {
                                    vacancy.get(preference.getPreference3()).IncCasteVacancy(type);
                                    seat = preference.getPreference3();
                                    type = l;
                                    pref = 3;
                                    vacancy.get(preference.getPreference3()).DecCasteVacancy(type);
                                } else if (pref < 3 && !seat.equals("")) {
                                    Log.d("ComputeResult", "Seat already allotted");
                                } else if (!seat.equals("")) {
                                    vacancy.get(preference.getPreference3()).IncCasteVacancy(type);
                                    seat = preference.getPreference3();
                                    type = l;
                                    pref = 5;
                                    vacancy.get(preference.getPreference3()).DecCasteVacancy(type);
                                }
                            } else {
                                access = vacancy.get(preference.getPreference4()).getCasteVacancy(l);
                                if (access != 0) {
                                    if (seat.equals("")) {
                                        seat = preference.getPreference4();
                                        type = l;
                                        pref = 4;
                                        vacancy.get(preference.getPreference4()).DecCasteVacancy(type);
                                    } else if (seat.equals(preference.getPreference4())) {
                                        vacancy.get(preference.getPreference4()).IncCasteVacancy(type);
                                        seat = preference.getPreference4();
                                        type = l;
                                        pref = 4;
                                        vacancy.get(preference.getPreference4()).DecCasteVacancy(type);
                                    } else if (pref < 4 && !seat.equals("")) {
                                        Log.d("ComputeResult", "Seat already allotted");
                                    } else if (!seat.equals("")) {
                                        vacancy.get(preference.getPreference4()).IncCasteVacancy(type);
                                        seat = preference.getPreference4();
                                        type = l;
                                        pref = 4;
                                        vacancy.get(preference.getPreference4()).DecCasteVacancy(type);
                                    }
                                } else {
                                    access = vacancy.get(preference.getPreference5()).getCasteVacancy(l);
                                    if (access != 0) {
                                        if (seat.equals("")) {
                                            seat = preference.getPreference5();
                                            type = l;
                                            pref = 5;
                                            vacancy.get(preference.getPreference5()).DecCasteVacancy(type);
                                        } else if (seat.equals(preference.getPreference5())) {
                                            vacancy.get(preference.getPreference5()).IncCasteVacancy(type);
                                            seat = preference.getPreference5();
                                            type = l;
                                            pref = 5;
                                            vacancy.get(preference.getPreference5()).DecCasteVacancy(type);
                                        } else if (pref < 5 && !seat.equals("")) {
                                            Log.d("ComputeResult", "Seat already allotted");
                                        } else if (!seat.equals("")) {
                                            vacancy.get(preference.getPreference5()).IncCasteVacancy(type);
                                            seat = preference.getPreference5();
                                            type = l;
                                            pref = 5;
                                            vacancy.get(preference.getPreference5()).DecCasteVacancy(type);
                                        }
                                    } else {
                                        access = vacancy.get(preference.getPreference6()).getCasteVacancy(l);
                                        if (access != 0) {
                                            if (seat.equals("")) {
                                                seat = preference.getPreference6();
                                                type = l;
                                                pref = 6;
                                                vacancy.get(preference.getPreference6()).DecCasteVacancy(type);
                                            } else if (seat.equals(preference.getPreference6())) {
                                                vacancy.get(preference.getPreference6()).IncCasteVacancy(type);
                                                seat = preference.getPreference6();
                                                type = l;
                                                pref = 6;
                                                vacancy.get(preference.getPreference6()).DecCasteVacancy(type);
                                            } else if (pref < 6 && !seat.equals("")) {
                                                Log.d("ComputeResult", "Seat already allotted");
                                            } else if (!seat.equals("")) {
                                                vacancy.get(preference.getPreference6()).IncCasteVacancy(type);
                                                seat = preference.getPreference6();
                                                type = l;
                                                pref = 6;
                                                vacancy.get(preference.getPreference6()).DecCasteVacancy(type);
                                            }
                                        } else {
                                            check += 1;
                                        }
                                    }
                                }
                            }

                        }

                    }
                }
            }


            if (info.getGender().equals("F") && (check == 4)) {
                Log.d("Round 1", "Seat not allotted");

            } else if (info.getGender().equals("F") && info.getCaste().equals("Open") && check == 2) {
                Log.d("Round 1", "Seat not allotted");
            } else if (info.getGender().equals("M") && (check == 2) && !info.getCaste().equals("Open")) {
                Log.d("Round 1", "Seat not allotted");
            } else if (info.getGender().equals("M") && check == 1 && info.getCaste().equals("Open")) {
                Log.d("Round 1", "Seat not allotted");
            } else {
                if(pref != 0) {
                    Result result = new Result(pref + "", type, seat);
                    FirebaseFirestore.getInstance().collection("Result").document(application.getRank() + "").set(result);
                    Toast.makeText(getApplicationContext(), "Student allotted " + seat + " as " + type + ". " + "Allotted preference is " + pref, Toast.LENGTH_LONG).show();
                }
            }
        }
        progressDialog.setMessage("Updating Vacancy");
        updateVacancyRound2();
    }

    private void updateVacancyRound2() {
        cse.setRound2();
        it.setRound2();
        me.setRound2();
        cv.setRound2();
        el.setRound2();
        en.setRound2();
        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 2").document("Computer Science and Engineering").set(cse);
        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 2").document("Information Technology").set(it);
        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 2").document("Civil Engineering").set(cv);
        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 2").document("Electronics Engineering").set(en);
        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 2").document("Electrical Engineering").set(el);
        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 2").document("Mechanical Engineering").set(me);
        Map<String, Boolean> rresult = new HashMap<>();
        rresult.put("R", true);
        FirebaseFirestore.getInstance().collection("RoundResult").document("Round1").set(rresult).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        new AlertDialog.Builder(ComputeResult.this)
                                .setTitle("Round 1")
                                .setMessage("Seats allotted Successfully")

                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {}
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                });
        binding.Round1.setText("Seats Allotted");
        progressDialog.hide();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //Round2

    private void getVacancyRound2(FirestoreCallback firestoreCallback) {
        FirebaseFirestore.getInstance().collection("Vacancy")
                .document("Round").collection("Round 2")
                .document("Civil Engineering")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot)
                    {
                        cv=documentSnapshot.toObject(NewCategory.class);
                        Log.d("Civil", cv.toString());
                        FirebaseFirestore.getInstance().collection("Vacancy")
                                .document("Round").collection("Round 2")
                                .document("Computer Science and Engineering")
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                    {
                                        cse=documentSnapshot.toObject(NewCategory.class);
                                        Log.d("CSE", cse.toString());
                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                .document("Round").collection("Round 2")
                                                .document("Electrical Engineering")
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                    {
                                                        el=documentSnapshot.toObject(NewCategory.class);
                                                        Log.d("EL", el.toString());

                                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                                .document("Round").collection("Round 2")
                                                                .document("Electronics Engineering")
                                                                .get()
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                                    {
                                                                        en=documentSnapshot.toObject(NewCategory.class);
                                                                        Log.d("EN", en.toString());
                                                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                                                .document("Round").collection("Round 2")
                                                                                .document("Information Technology")
                                                                                .get()
                                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                    @Override
                                                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                                                    {
                                                                                        it=documentSnapshot.toObject(NewCategory.class);
                                                                                        Log.d("IT", it.toString());
                                                                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                                                                .document("Round").collection("Round 2")
                                                                                                .document("Mechanical Engineering")
                                                                                                .get()
                                                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                                                                    {
                                                                                                        me=documentSnapshot.toObject(NewCategory.class);
                                                                                                        Log.d("ME", me.toString());
                                                                                                        vacancy.put("Computer Science and Engineering",cse);
                                                                                                        vacancy.put("Information Technology",it);
                                                                                                        vacancy.put("Electrical Engineering",el);
                                                                                                        vacancy.put("Electronics Engineering",en);
                                                                                                        vacancy.put("Civil Engineering",cv);
                                                                                                        vacancy.put("Mechanical Engineering",me);
                                                                                                        firestoreCallback.onCallback();
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                });
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    private void getStudentInfoRound2(FirestoreCallback firestoreCallback) {
        currentApplications = 0;
        FirebaseFirestore.getInstance().collection("Application")
                .orderBy("rank")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots)
                    {
                        applicationSize = queryDocumentSnapshots.size();
                        for(QueryDocumentSnapshot doc1:queryDocumentSnapshots)
                        {
                            application=doc1.toObject(Application.class);
                            applicationList.put(application.getRank(), application);
                            Log.d("application", application.toString());
                            FirebaseFirestore.getInstance().collection("StudentInfo").document(application.getRank() + "")
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                                    info = documentSnapshot.toObject(StudentInfo.class);

                                    if(info.getCaste().equals("SEBC"))
                                        info.setCaste("Open");
                                    else if(info.getCaste().equals("SBC"))
                                        info.setCaste("OBC");
                                    studentInfoList.put(info.getRank(), info);
                                    Log.d("Info", info.toString());
                                    currentApplications ++;
                                }
                            });

                        }
                        untilDataFetch2(firestoreCallback);
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_LONG).show();

            }
        });
    }

    private void untilDataFetch2(FirestoreCallback firestoreCallback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while(currentApplications != applicationSize);
                getPreferenceRound2(firestoreCallback);
            }
        });
    }

    private void getPreferenceRound2(FirestoreCallback firestoreCallback) {
        FirebaseFirestore.getInstance().collection("Preference/Round/Round 2/")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<DocumentSnapshot> list = task.getResult().getDocuments();
                            for(int i = 0; i < list.size(); i++) {
                                rankList.add(Long.parseLong(list.get(i).getId()));
                                preferenceList.put(Long.parseLong(list.get(i).getId()), list.get(i).toObject(Preference.class));
                            }
                            Collections.sort(rankList);
                            firestoreCallback.onCallback();
                        }
                    }
                });
    }

    private void generateResultRound2() {
        for(Long rank:rankList) {
            pref = 0;
            seat = "";
            type = "";
            preference = preferenceList.get(rank);
            preference.check();
            application = applicationList.get(rank);
            info = studentInfoList.get(rank);
            check = 0;
            access = vacancy.get(preference.getPreference1()).getGopens();
            if (access != 0) {
                seat = preference.getPreference1();
                type = "gopens";
                pref = 1;
                vacancy.get(preference.getPreference1()).setGopens(access - 1);
            } else {
                access = vacancy.get(preference.getPreference2()).getGopens();
                if (access != 0) {
                    seat = preference.getPreference2();
                    type = "gopens";
                    pref = 2;
                    vacancy.get(preference.getPreference2()).setGopens(access - 1);
                } else {
                    access = vacancy.get(preference.getPreference3()).getGopens();
                    if (access != 0) {
                        seat = preference.getPreference3();
                        type = "gopens";
                        pref = 3;
                        vacancy.get(preference.getPreference3()).setGopens(access - 1);
                    } else {
                        access = vacancy.get(preference.getPreference4()).getGopens();
                        if (access != 0) {
                            seat = preference.getPreference4();
                            type = "gopens";
                            pref = 4;
                            vacancy.get(preference.getPreference4()).setGopens(access - 1);
                        } else {
                            access = vacancy.get(preference.getPreference5()).getGopens();
                            if (access != 0) {
                                seat = preference.getPreference5();
                                type = "gopens";
                                pref = 5;
                                vacancy.get(preference.getPreference5()).setGopens(access - 1);
                            } else {
                                access = vacancy.get(preference.getPreference6()).getGopens();
                                if (access != 0) {
                                    seat = preference.getPreference6();
                                    type = "gopens";
                                    pref = 6;
                                    vacancy.get(preference.getPreference6()).setGopens(access - 1);
                                } else {
                                    check += 1;
                                }
                            }
                        }
                    }

                }
            }

            if (check == 0 && pref != 0) {
                Result result = new Result(pref + "", type, seat);
                FirebaseFirestore.getInstance().collection("Result").document(application.getRank() + "").set(result);
                Toast.makeText(getApplicationContext(), "Student allotted " + seat + " as " + type + ". " + "Allotted preference is " + pref, Toast.LENGTH_LONG).show();
            }
        }
        progressDialog.setMessage("Updating Vacancy");
        updateVacancyRound3();
    }

    private void updateVacancyRound3() {
        cse.setRound2();
        it.setRound2();
        me.setRound2();
        cv.setRound2();
        el.setRound2();
        en.setRound2();
        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 3").document("Computer Science and Engineering").set(cse);
        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 3").document("Information Technology").set(it);
        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 3").document("Civil Engineering").set(cv);
        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 3").document("Electronics Engineering").set(en);
        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 3").document("Electrical Engineering").set(el);
        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 3").document("Mechanical Engineering").set(me);
        Map<String, Boolean> rresult = new HashMap<>();
        rresult.put("R", true);
        FirebaseFirestore.getInstance().collection("RoundResult").document("Round2").set(rresult).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        new AlertDialog.Builder(ComputeResult.this)
                                .setTitle("Round 2")
                                .setMessage("Seats allotted Successfully")

                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {}
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                });
        binding.Round2.setText("Seats Allotted");
        progressDialog.hide();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //Round3

    private void getVacancyRound3(FirestoreCallback firestoreCallback) {
        FirebaseFirestore.getInstance().collection("Vacancy")
                .document("Round").collection("Round 3")
                .document("Civil Engineering")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot)
                    {
                        cv=documentSnapshot.toObject(NewCategory.class);
                        Log.d("Civil", cv.toString());
                        FirebaseFirestore.getInstance().collection("Vacancy")
                                .document("Round").collection("Round 3")
                                .document("Computer Science and Engineering")
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                    {
                                        cse=documentSnapshot.toObject(NewCategory.class);
                                        Log.d("CSE", cse.toString());
                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                .document("Round").collection("Round 3")
                                                .document("Electrical Engineering")
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                    {
                                                        el=documentSnapshot.toObject(NewCategory.class);
                                                        Log.d("EL", el.toString());

                                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                                .document("Round").collection("Round 3")
                                                                .document("Electronics Engineering")
                                                                .get()
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                                    {
                                                                        en=documentSnapshot.toObject(NewCategory.class);
                                                                        Log.d("EN", en.toString());
                                                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                                                .document("Round").collection("Round 3")
                                                                                .document("Information Technology")
                                                                                .get()
                                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                    @Override
                                                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                                                    {
                                                                                        it=documentSnapshot.toObject(NewCategory.class);
                                                                                        Log.d("IT", it.toString());
                                                                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                                                                .document("Round").collection("Round 3")
                                                                                                .document("Mechanical Engineering")
                                                                                                .get()
                                                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                                                                    {
                                                                                                        me=documentSnapshot.toObject(NewCategory.class);
                                                                                                        Log.d("ME", me.toString());
                                                                                                        firestoreCallback.onCallback();
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                });
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    private void getStudentInfoRound3(FirestoreCallback firestoreCallback) {
        currentApplications = 0;
        FirebaseFirestore.getInstance().collection("Application")
                .orderBy("rank")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots)
                    {
                        applicationSize = queryDocumentSnapshots.size();
                        for(QueryDocumentSnapshot doc1:queryDocumentSnapshots)
                        {
                            application=doc1.toObject(Application.class);
                            applicationList.put(application.getRank(), application);
                            Log.d("application", application.toString());
                            FirebaseFirestore.getInstance().collection("StudentInfo").document(application.getRank() + "")
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                                    info = documentSnapshot.toObject(StudentInfo.class);

                                    if(info.getCaste().equals("SEBC"))
                                        info.setCaste("Open");
                                    else if(info.getCaste().equals("SBC"))
                                        info.setCaste("OBC");
                                    studentInfoList.put(info.getRank(), info);
                                    Log.d("Info", info.toString());
                                    currentApplications ++;
                                }
                            });

                        }
                        untilDataFetch3(firestoreCallback);
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_LONG).show();

            }
        });
    }

    private void untilDataFetch3(FirestoreCallback firestoreCallback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while(currentApplications != applicationSize);
                getPreferenceRound3(firestoreCallback);
            }
        });
    }

    private void getPreferenceRound3(FirestoreCallback firestoreCallback) {
        FirebaseFirestore.getInstance().collection("Preference/Round/Round 3/")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<DocumentSnapshot> list = task.getResult().getDocuments();
                            for(int i = 0; i < list.size(); i++) {
                                rankList.add(Long.parseLong(list.get(i).getId()));
                                preferenceList.put(Long.parseLong(list.get(i).getId()), list.get(i).toObject(Preference.class));
                            }
                            Collections.sort(rankList);
                            firestoreCallback.onCallback();
                        }
                    }
                });
    }

    private void generateResultRound3() {
        for(Long rank:rankList) {
            pref = 0;
            seat = "";
            type = "";
            preference = preferenceList.get(rank);
            preference.check();
            application = applicationList.get(rank);
            info = studentInfoList.get(rank);
            check = 0;
            access = vacancy.get(preference.getPreference1()).getGopens();
            if (access != 0) {
                seat = preference.getPreference1();
                type = "gopens";
                pref = 1;
                vacancy.get(preference.getPreference1()).setGopens(access - 1);
            } else {
                access = vacancy.get(preference.getPreference2()).getGopens();
                if (access != 0) {
                    seat = preference.getPreference2();
                    type = "gopens";
                    pref = 2;
                    vacancy.get(preference.getPreference2()).setGopens(access - 1);
                } else {
                    access = vacancy.get(preference.getPreference3()).getGopens();
                    if (access != 0) {
                        seat = preference.getPreference3();
                        type = "gopens";
                        pref = 3;
                        vacancy.get(preference.getPreference3()).setGopens(access - 1);
                    } else {
                        access = vacancy.get(preference.getPreference4()).getGopens();
                        if (access != 0) {
                            seat = preference.getPreference4();
                            type = "gopens";
                            pref = 4;
                            vacancy.get(preference.getPreference4()).setGopens(access - 1);
                        } else {
                            access = vacancy.get(preference.getPreference5()).getGopens();
                            if (access != 0) {
                                seat = preference.getPreference5();
                                type = "gopens";
                                pref = 5;
                                vacancy.get(preference.getPreference5()).setGopens(access - 1);
                            } else {
                                access = vacancy.get(preference.getPreference6()).getGopens();
                                if (access != 0) {
                                    seat = preference.getPreference6();
                                    type = "gopens";
                                    pref = 6;
                                    vacancy.get(preference.getPreference6()).setGopens(access - 1);
                                } else {
                                    check += 1;
                                }
                            }
                        }
                    }

                }
            }

            if (check == 0 && pref != 0) {
                Result result = new Result(pref + "", type, seat);
                FirebaseFirestore.getInstance().collection("Result").document(application.getRank() + "").set(result);
                Toast.makeText(getApplicationContext(), "Student allotted " + seat + " as " + type + ". " + "Allotted preference is " + pref, Toast.LENGTH_LONG).show();
            }
        }
        Map<String, Boolean> rresult = new HashMap<>();
        rresult.put("R", true);
        FirebaseFirestore.getInstance().collection("RoundResult").document("Round3").set(rresult).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {
                        new AlertDialog.Builder(ComputeResult.this)
                        .setTitle("Round 3")
                        .setMessage("Seats allotted Successfully")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                    }
                });
        binding.Round3.setText("Seats Allotted");
        progressDialog.hide();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}