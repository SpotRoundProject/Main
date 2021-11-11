package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.spotround.modle.Application;
import com.example.spotround.modle.NewCategory;
import com.example.spotround.modle.Preference;
import com.example.spotround.modle.StudentInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ComputeResult extends AppCompatActivity {
    public static int a=0;


    ProgressDialog progressDialog;
    private  int vacancies=0;
    NewCategory cse,it,en,el,cv,me;
    Application application;
    StudentInfo info;
    Preference preference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_result);

        getVacancy(new FirestoreCallback(){
            @Override
            public void onCallback() {
                FirebaseFirestore.getInstance().collection("Application")
                        .orderBy("rank")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                        {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots)
                            {
                                for(QueryDocumentSnapshot doc1:queryDocumentSnapshots)
                                {
                                    application=doc1.toObject(Application.class);
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
                                            Log.d("Info", info.toString());
                                            FirebaseFirestore.getInstance().collection("Preference/Round/Round 1/").document(doc1.getId())
                                                    .get()
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                                                            preference =  documentSnapshot.toObject(Preference.class);
                                                            Log.d("Preference", preference.toString());

                                                            int pref=0;
                                                            int check=0;
                                                            String seat="";
                                                            String type="";
                                                            Map<String, NewCategory>vacancy = new HashMap<>();
                                                            vacancy.put("Computer Science and Engineering",cse);
                                                            vacancy.put("Information Technology",it);
                                                            vacancy.put("Electrical Engineering",el);
                                                            vacancy.put("Electronics Engineering",en);
                                                            vacancy.put("Civil Engineering",cv);
                                                            vacancy.put("Mechanical Engineering",me);

                                                            //Map<String,Integer>update=new HashMap<String,Integer>();
                                                            int access=1;
                                                            if(info.getCaste().equals("Open"))
                                                            {

                                                                //System.out.println("Hi");
                                                                access=vacancy.get(preference.getPreference1()).getGopens();
                                                                //control=myp1.get(first);
                                                                if(access!=0)
                                                                {
                                                                    seat=preference.getPreference1();
                                                                    type="gopens";
                                                                    pref=1;
                                                                    //update.clear();
                                                                    //update.put("gopens",access-1);
                                                                    vacancy.get(preference.getPreference1()).setGopens(access-1);
                                                                    //control.put("gopens",access-1);
                                                                    //myp1.put(first,control);
                                                                }
                                                                else
                                                                {
                                                                    access=vacancy.get(preference.getPreference2()).getGopens();
                                                                    //control=myp1.get(second);
                                                                    if(access!=0)
                                                                    {
                                                                        seat=preference.getPreference2();
                                                                        type="gopens";
                                                                        pref=2;
                                                                        vacancy.get(preference.getPreference2()).setGopens(access-1);
                                                                        //update.clear();
                                                                        //update.put("gopens",access-1);
                                                                        //control.put("gopens",access-1);
                                                                        //myp1.put(second,control);
                                                                    }
                                                                    else
                                                                    {
                                                                        access=vacancy.get(preference.getPreference3()).getGopens();
                                                                        // access=myp1.get(third).get("gopens");
                                                                        //control=myp1.get(third);
                                                                        if(access!=0)
                                                                        {
                                                                            seat=preference.getPreference3();
                                                                            type="gopens";
                                                                            pref=3;
                                                                            vacancy.get(preference.getPreference3()).setGopens(access-1);
                                                                            //update.clear();
                                                                            //update.put("gopens",access-1);
                                                                            //control.put("gopens",access-1);
                                                                            //myp1.put(third,control);
                                                                        }
                                                                        else
                                                                        {
                                                                            access=vacancy.get(preference.getPreference4()).getGopens();
                                                                            //access=myp1.get(forth).get("gopens");
                                                                            //control=myp1.get(forth);
                                                                            if(access!=0)
                                                                            {
                                                                                seat=preference.getPreference4();
                                                                                type="gopens";
                                                                                pref=4;
                                                                                vacancy.get(preference.getPreference4()).setGopens(access-1);
                                                                                //update.clear();
                                                                                //update.put("gopens",access-1);
                                                                                //control.put("gopens",access-1);
                                                                                //myp1.put(forth,control);
                                                                            }
                                                                            else
                                                                            {
                                                                                access=vacancy.get(preference.getPreference5()).getGopens();
                                                                                // access=myp1.get(fifth).get("gopens");
                                                                                //control=myp1.get(fifth);
                                                                                if(access!=0)
                                                                                {
                                                                                    seat=preference.getPreference5();
                                                                                    type="gopens";
                                                                                    pref=5;
                                                                            /*update.clear();
                                                                            update.put("gopens",access-1);
                                                                            control.put("gopens",access-1);
                                                                            myp1.put(fifth,control);*/
                                                                                    vacancy.get(preference.getPreference5()).setGopens(access-1);
                                                                                }
                                                                                else
                                                                                {
                                                                                    access=vacancy.get(preference.getPreference6()).getGopens();
                                                                                    //access=myp1.get(sixth).get("gopens");
                                                                                    // control=myp1.get(sixth);
                                                                                    if(access!=0)
                                                                                    {
                                                                                        seat=preference.getPreference6();
                                                                                        type="gopens";
                                                                                        pref=6;
                                                                               /* update.clear();
                                                                                update.put("gopens",access-1);
                                                                                control.put("gopens",access-1);
                                                                                myp1.put(sixth,control);*/
                                                                                        vacancy.get(preference.getPreference6()).setGopens(access-1);
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        //System.out.println("Sorry.You have not alloted any seat");
                                                                                    }
                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                }

                                                            }
                                                            else
                                                            {
                                                                access=vacancy.get(preference.getPreference1()).getGopens();
                                                                //access=myp1.get(first).get("gopens");
                                                                //control=myp1.get(first);
                                                                if(access!=0)
                                                                {
                                                                    seat=preference.getPreference1();
                                                                    type="gopens";
                                                                    pref=1;
                                                            /*update.clear();
                                                            update.put("gopens",access-1);
                                                            control.put("gopens",access-1);
                                                            myp1.put(first,control);*/
                                                                    //control2=contro;
                                                                    vacancy.get(preference.getPreference1()).setGopens(access-1);
                                                                }
                                                                else
                                                                {
                                                                    Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_LONG).show();
                                                                    //access=myp1.get(second).get("gopens");
                                                                    access=vacancy.get(preference.getPreference2()).getGopens();
                                                                    //control=myp1.get(second);
                                                                    if(access!=0)
                                                                    {
                                                                        seat=preference.getPreference2();
                                                                        type="gopens";
                                                                        pref=2;
                                                                /*update.clear();
                                                                control.put("gopens",access-1);
                                                                myp1.put(second,control);
                                                                control2=control;*/
                                                                        vacancy.get(preference.getPreference2()).setGopens(access-1);
                                                                    }
                                                                    else
                                                                    {
                                                                /*access=myp1.get(third).get("gopens");
                                                                control=myp1.get(third);*/
                                                                        access=vacancy.get(preference.getPreference3()).getGopens();
                                                                        if(access!=0)
                                                                        {
                                                                            seat=preference.getPreference3();
                                                                            type="gopens";
                                                                            pref=3;
                                                                    /*update.clear();
                                                                    update.put("gopens",access-1);
                                                                    control.put("gopens",access-1);
                                                                    myp1.put(third,control);
                                                                    control2=control;*/
                                                                            vacancy.get(preference.getPreference3()).setGopens(access-1);
                                                                        }
                                                                        else
                                                                        {
                                                                            access=vacancy.get(preference.getPreference4()).getGopens();
                                                                   /* access=myp1.get(forth).get("gopens");
                                                                    control=myp1.get(forth);*/
                                                                            if(access!=0)
                                                                            {
                                                                                seat=preference.getPreference4();
                                                                                type="gopens";
                                                                                pref=4;
                                                                        /*update.clear();
                                                                        update.put("gopens",access-1);
                                                                        control.put("gopens",access-1);
                                                                        myp1.put(forth,control);
                                                                        control2=control;*/
                                                                                vacancy.get(preference.getPreference4()).setGopens(access-1);
                                                                            }
                                                                            else
                                                                            {
                                                                        /*access=myp1.get(fifth).get("gopens");
                                                                        control=myp1.get(fifth);*/
                                                                                access=vacancy.get(preference.getPreference5()).getGopens();
                                                                                if(access!=0)
                                                                                {
                                                                                    seat=preference.getPreference5();
                                                                                    type="gopens";
                                                                                    pref=5;
                                                                           /* update.clear();
                                                                            update.put("gopens",access-1);
                                                                            control.put("gopens",access-1);
                                                                            myp1.put(fifth,control);
                                                                            control2=control;*/
                                                                                    vacancy.get(preference.getPreference5()).setGopens(access-1);
                                                                                }
                                                                                else
                                                                                {
                                                                            /*access=myp1.get(sixth).get("gopens");
                                                                            control=myp1.get(sixth);*/
                                                                                    access=vacancy.get(preference.getPreference6()).getGopens();
                                                                                    if(access!=0)
                                                                                    {
                                                                                        seat=preference.getPreference6();
                                                                                        type="gopens";
                                                                                        pref=6;
                                                                               /* update.clear();
                                                                                update.put("gopens",access-1);
                                                                                control.put("gopens",access-1);
                                                                                myp1.put(sixth,control);
                                                                                control2=control;*/
                                                                                        vacancy.get(preference.getPreference6()).setGopens(access-1);
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        check+=1;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }

                                                                    }

                                                                }
                                                                if(info.getGender().equals("F")&&(info.getCaste().equals("Open")))
                                                                {
                                                           /* access=myp1.get(first).get("lopens");
                                                            control=myp1.get(first);*/
                                                                    access=vacancy.get(preference.getPreference1()).getLopens();
                                                                    if(access!=0)
                                                                    {
                                                                        if(seat.equals(""))
                                                                        {
                                                                            seat=preference.getPreference1();
                                                                            type="lopens";
                                                                            pref=1;
                                                                    /*update.clear();
                                                                    update.put("lopens",access-1);
                                                                    control.put("lopens",access-1);
                                                                    myp1.put(first,control);*/
                                                                            vacancy.get(preference.getPreference1()).setLopens(access-1);
                                                                        }
                                                                        else if(seat.equals(preference.getPreference1()))
                                                                        {
                                                                            //int solve=control2.get("gopens");
                                                                            int solve=vacancy.get(seat).getGopens();
                                                                            vacancy.get(seat).setGopens(solve+1);
                                                                            //control.put("gopens",solve+1);
                                                                            seat=preference.getPreference1();
                                                                            type="lopens";
                                                                            pref=1;
                                                                  /*  update.clear();
                                                                    update.put("lopens",access-1);
                                                                    control.put("lopens",access-1);
                                                                    myp1.put(first,control);*/
                                                                            vacancy.get(preference.getPreference1()).setLopens(access-1);
                                                                        }
                                                                        else if(!seat.equals(""))
                                                                        {
                                                                            // int solve=control2.get("gopens");
                                                                            int solve=vacancy.get(seat).getGopens();
                                                                            vacancy.get(seat).setGopens(solve+1);
                                                                            // control2.put("gopens",solve+1);
                                                                            //myp1.put(seat,control2);
                                                                            seat=preference.getPreference1();
                                                                            type="lopens";
                                                                            pref=1;
                                                                    /*update.clear();
                                                                    update.put("lopens",access-1);
                                                                    control.put("lopens",access-1);
                                                                    myp1.put(first,control);*/
                                                                            vacancy.get(seat).setLopens((access-1));
                                                                        }



                                                                    }
                                                                    else
                                                                    {
                                                              /* access=myp1.get(second).get("lopens");
                                                                control=myp1.get(second); */
                                                                        access=vacancy.get(preference.getPreference2()).getLopens();
                                                                        if(access!=0)
                                                                        {
                                                                            if(seat.equals(""))
                                                                            {
                                                                                seat=preference.getPreference2();
                                                                                type="lopens";
                                                                                pref=2;
                                                                       /* update.clear();
                                                                        update.put("lopens",access-1);
                                                                        control.put("lopens",access-1);
                                                                        myp1.put(second,control);*/
                                                                                vacancy.get(preference.getPreference2()).setLopens(access-1);
                                                                            }

                                                                            else if(seat.equals(preference.getPreference2()))
                                                                            {
                                                                        /*
                                                                        int solve=control2.get("gopens");
                                                                        control.put("gopens",solve+1);*/
                                                                                int solve=vacancy.get(seat).getGopens();
                                                                                vacancy.get(seat).setGopens(solve+1);
                                                                                seat=preference.getPreference2();
                                                                                type="lopens";
                                                                                pref=2;
                                                                        /*update.clear();
                                                                        update.put("lopens",access-1);
                                                                        control.put("lopens",access-1);
                                                                        myp1.put(second,control);*/
                                                                                vacancy.get(preference.getPreference2()).setLopens(access-1);
                                                                            }
                                                                            else if(pref<2)
                                                                            {
                                                                                Log.d("ComputeResult","Do Nothing Eat Five star");
                                                                            }
                                                                            else if(!seat.equals(""))
                                                                            {
                                                                       /* int solve=control2.get("gopens");
                                                                        control2.put("gopens",solve+1);*/
                                                                                int solve=vacancy.get(seat).getGopens();
                                                                                vacancy.get(seat).setGopens(solve+1);
                                                                                /*myp1.put(seat,control2);*/
                                                                                seat=preference.getPreference2();
                                                                                type="lopens";
                                                                                pref=2;
                                                                      /*  update.clear();
                                                                        update.put("lopens",access-1);
                                                                        control.put("lopens",access-1);
                                                                        myp1.put(second,control);*/
                                                                                vacancy.get(preference.getPreference2()).setLopens(access-1);
                                                                            }

                                                                        }
                                                                        else
                                                                        {
                                                                   /* access=myp1.get(third).get("lopens");
                                                                    control=myp1.get(third);*/
                                                                            access=vacancy.get(preference.getPreference3()).getLopens();
                                                                            if(access!=0)
                                                                            {
                                                                                if(seat.equals(""))
                                                                                {
                                                                                    seat=preference.getPreference3();
                                                                                    type="lopens";
                                                                                    pref=3;
                                                                            /*update.clear();
                                                                            update.put("lopens",access-1);
                                                                            control.put("lopens",access-1);
                                                                            myp1.put(third,control);*/
                                                                                    vacancy.get(preference.getPreference3()).setLopens(access-1);
                                                                                }
                                                                                else if(seat.equals(preference.getPreference3()))
                                                                                {
                                                                            /*int solve=control2.get("gopens");
                                                                            control.put("gopens",solve+1);*/
                                                                                    int solve=vacancy.get(seat).getGopens();
                                                                                    vacancy.get(seat).setGopens(solve+1);
                                                                                    seat=preference.getPreference3();
                                                                                    type="lopens";
                                                                                    pref=3;
                                                                           /* update.clear();
                                                                            update.put("lopens",access-1);
                                                                            control.put("lopens",access-1);
                                                                            myp1.put(third,control);*/
                                                                                    vacancy.get(preference.getPreference3()).setLopens(access-1);
                                                                                }
                                                                                else if(pref<3)
                                                                                {}
                                                                                else if(!seat.equals(""))
                                                                                {
                                                                            /*int solve=control2.get("gopens");
                                                                            control2.put("gopens",solve+1);*/
                                                                                    int solve=vacancy.get(seat).getGopens();
                                                                                    vacancy.get(seat).setGopens(solve+1);
                                                                                    // myp1.put(seat,control2);
                                                                                    seat=preference.getPreference3();
                                                                                    type="lopens";
                                                                                    pref=3;
                                                                           /* update.clear();
                                                                            update.put("lopens",access-1);
                                                                            control.put("lopens",access-1);
                                                                            myp1.put(third,control);*/
                                                                                    vacancy.get(preference.getPreference3()).setLopens(access-1);
                                                                                }

                                                                            }
                                                                            else
                                                                            {
                                                                        /*access=myp1.get(forth).get("lopens");
                                                                        control=myp1.get(forth);*/
                                                                                access=vacancy.get(preference.getPreference4()).getLopens();
                                                                                if(access!=0)
                                                                                {
                                                                                    if(seat.equals(""))
                                                                                    {
                                                                                        seat=preference.getPreference4();
                                                                                        type="lopens";
                                                                                        pref=4;
                                                                               /* update.clear();
                                                                                update.put("lopens",access-1);
                                                                                control.put("lopens",access-1);
                                                                                myp1.put(forth,control);*/
                                                                                        vacancy.get(preference.getPreference4()).setLopens(access-1);
                                                                                    }
                                                                                    else if(seat.equals(preference.getPreference4()))
                                                                                    {
                                                                                /*int solve=control2.get("gopens");
                                                                                control.put("gopens",solve+1);*/
                                                                                        int solve=vacancy.get(seat).getGopens();
                                                                                        vacancy.get(seat).setGopens(solve+1);
                                                                                        seat=preference.getPreference4();
                                                                                        type="lopens";
                                                                                        pref=4;
                                                                              /*  update.clear();
                                                                                update.put("lopens",access-1);
                                                                                control.put("lopens",access-1);
                                                                                myp1.put(forth,control);*/
                                                                                        vacancy.get(preference.getPreference4()).setLopens(access-1);
                                                                                    }
                                                                                    else if(pref<4)
                                                                                    {}
                                                                                    else if(!seat.equals(""))
                                                                                    {
                                                                              /*  int solve=control2.get("gopens");
                                                                                control2.put("gopens",solve+1);*/
                                                                                        int solve=vacancy.get(seat).getGopens();
                                                                                        vacancy.get(seat).setGopens(solve+1);
                                                                                        //myp1.put(seat,control2);
                                                                                        seat=preference.getPreference4();
                                                                                        type="lopens";
                                                                                        pref=4;
                                                                                /*update.clear();
                                                                                update.put("lopens",access-1);
                                                                                control.put("lopens",access-1);
                                                                                myp1.put(forth,control);*/
                                                                                        vacancy.get(preference.getPreference4()).setLopens(access-1);
                                                                                    }
                                                                                }
                                                                                else
                                                                                {
                                                                            /*access=myp1.get(fifth).get("lopens");
                                                                            control=myp1.get(fifth);*/
                                                                                    access=vacancy.get(preference.getPreference5()).getLopens();
                                                                                    if(access!=0)
                                                                                    {
                                                                                        if(seat.equals(""))
                                                                                        {
                                                                                            seat=preference.getPreference5();
                                                                                            type="lopens";
                                                                                            pref=5;
                                                                                   /* update.clear();
                                                                                    update.put("lopens",access-1);
                                                                                    control.put("lopens",access-1);
                                                                                    myp1.put(fifth,control);*/
                                                                                            vacancy.get(preference.getPreference5()).setLopens(access-1);
                                                                                        }
                                                                                        else if(seat.equals(preference.getPreference5()))
                                                                                        {
                                                                                  /*  int solve=control2.get("gopens");
                                                                                    control.put("gopens",solve+1);*/
                                                                                            int solve=vacancy.get(seat).getGopens();
                                                                                            vacancy.get(seat).setGopens(solve+1);
                                                                                            seat=preference.getPreference5();
                                                                                            type="lopens";
                                                                                            pref=5;
                                                                                   /* update.clear();
                                                                                    update.put("lopens",access-1);
                                                                                    control.put("lopens",access-1);
                                                                                    myp1.put(fifth,control);*/
                                                                                            vacancy.get(preference.getPreference5()).setLopens(access-1);
                                                                                        }
                                                                                        else if(pref<5)
                                                                                        {}
                                                                                        else if(!seat.equals(""))
                                                                                        {
                                                                                    /*int solve=control2.get("gopens");
                                                                                    control2.put("gopens",solve+1);*/
                                                                                            int solve=vacancy.get(seat).getGopens();
                                                                                            vacancy.get(seat).setGopens(solve+1);
                                                                                            //myp1.put(seat,control2);
                                                                                            seat=preference.getPreference5();
                                                                                            type="lopens";
                                                                                            pref=5;
                                                                                   /* update.clear();
                                                                                    update.put("lopens",access-1);
                                                                                    control.put("lopens",access-1);
                                                                                    myp1.put(fifth,control);*/
                                                                                            vacancy.get(preference.getPreference5()).setLopens(access-1);
                                                                                        }
                                                                                    }
                                                                                    else
                                                                                    {
                                                                               /* access=myp1.get(sixth).get("lopens");
                                                                                control=myp1.get(sixth);*/
                                                                                        access=vacancy.get(preference.getPreference6()).getLopens();
                                                                                        if(access!=0)
                                                                                        {
                                                                                            if(seat.equals(""))
                                                                                            {
                                                                                                seat=preference.getPreference6();
                                                                                                type="lopens";
                                                                                                pref=6;
                                                                                        /*update.clear();
                                                                                        update.put("lopens",access-1);
                                                                                        control.put("lopens",access-1);
                                                                                        myp1.put(sixth,control);*/
                                                                                                vacancy.get(preference.getPreference6()).setLopens(access-1);
                                                                                            }
                                                                                            else if(seat.equals(preference.getPreference6()))
                                                                                            {
                                                                                        /*int solve=control2.get("gopens");
                                                                                        control.put("gopens",solve+1);*/
                                                                                                int solve=vacancy.get(seat).getGopens();
                                                                                                vacancy.get(seat).setGopens(solve+1);
                                                                                                seat=preference.getPreference6();
                                                                                                type="lopens";
                                                                                                pref=6;
                                                                                        /*update.clear();
                                                                                        update.put("lopens",access-1);
                                                                                        control.put("lopens",access-1);
                                                                                        myp1.put(sixth,control);*/
                                                                                                vacancy.get(preference.getPreference6()).setLopens(access-1);
                                                                                            }
                                                                                            else if(pref<6)
                                                                                            {}
                                                                                            else if(!seat.equals(""))
                                                                                            {
                                                                                        /*int solve=control2.get("gopens");
                                                                                        control2.put("gopens",solve+1);*/
                                                                                                int solve=vacancy.get(seat).getGopens();
                                                                                                vacancy.get(seat).setGopens(solve+1);
                                                                                                //myp1.put(seat,control2);
                                                                                                seat=preference.getPreference6();
                                                                                                type="lopens";
                                                                                                pref=6;
                                                                                       /* update.clear();
                                                                                        update.put("lopens",access-1);
                                                                                        control.put("lopens",access-1);
                                                                                        myp1.put(sixth,control);*/
                                                                                                vacancy.get(preference.getPreference6()).setLopens(access-1);
                                                                                            }
                                                                                        }
                                                                                        else
                                                                                        {
                                                                                            check+=1;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }

                                                                        }
                                                                    }

                                                                }
                                                                if(!info.getCaste().equals("Open"))
                                                                {
                                                                    String l="g";
                                                                    l=l+info.getCaste().toLowerCase();
                                                                    l=l+"s";
                                                                    //System.out.println(l);
                                                            /*access=myp1.get(first).get(l);
                                                            control=myp1.get(first);*/
                                                                    access = vacancy.get(preference.getPreference1()).getCasteVacancy(l);
                                                                    if(access!=0)
                                                                    {
                                                                        if(seat.equals(""))
                                                                        {
                                                                            seat=preference.getPreference1();
                                                                            type=l;
                                                                            pref=1;
                                                                            /*update.clear();*/
                                                                    /*update.put("gopens",access-1);
                                                                    control.put(l,access-1);
                                                                    myp1.put(first,control);
                                                                    control2=control;*/
                                                                            vacancy.get(preference.getPreference1()).DecCasteVacancy(l);
                                                                        }
                                                                        else if(seat.equals(preference.getPreference1()))
                                                                        {
                                                                    /*System.out.println(type);
                                                                    int solve=control2.get(type);
                                                                    control.put(type,solve+1);*/
                                                                            vacancy.get(preference.getPreference1()).IncCasteVacancy(type);
                                                                            seat=preference.getPreference1();
                                                                            type=l;
                                                                            pref=1;
                                                                    /*update.clear();
                                                                    update.put(type,access-1);
                                                                    control.put(type,access-1);
                                                                    myp1.put(first,control);*/
                                                                            vacancy.get(preference.getPreference1()).DecCasteVacancy(type);
                                                                        }
                                                                        else if(!seat.equals(""))
                                                                        {
                                                                    /*int solve=control2.get(type);
                                                                    control2.put(type,solve+1);
                                                                    myp1.put(seat,control2);*/
                                                                            vacancy.get(preference.getPreference1()).IncCasteVacancy(type);
                                                                            seat=preference.getPreference1();
                                                                            type=l;
                                                                            pref=1;
                                                                    /*update.clear();
                                                                    update.put(type,access-1);
                                                                    control.put(type,access-1);
                                                                    myp1.put(first,control);
                                                                    control2=control;*/
                                                                            vacancy.get(preference.getPreference1()).DecCasteVacancy(type);
                                                                        }


                                                                    }
                                                                    else
                                                                    {
                                                                /*access=myp1.get(second).get(l);
                                                                control=myp1.get(second);*/
                                                                        access = vacancy.get(preference.getPreference1()).getCasteVacancy(l);
                                                                        if(access!=0)
                                                                        {
                                                                            if(seat.equals(""))
                                                                            {
                                                                                seat=preference.getPreference2();
                                                                                type=l;
                                                                                pref=2;
                                                                        /*update.clear();
                                                                        update.put(l,access-1);
                                                                        control.put(l,access-1);
                                                                        myp1.put(second,control);
                                                                        control2=control;*/
                                                                                vacancy.get(preference.getPreference2()).DecCasteVacancy(type);
                                                                            }
                                                                            else if(seat.equals(preference.getPreference2()))
                                                                            {
                                                                        /*int solve=control2.get(type);
                                                                        control.put(type,solve+1);*/
                                                                                vacancy.get(preference.getPreference2()).IncCasteVacancy(type);
                                                                                seat=preference.getPreference2();
                                                                                type=l;
                                                                                //pref=3;
                                                                                pref=2;
                                                                        /*update.clear();
                                                                        update.put(type,access-1);
                                                                        control.put(type,access-1);
                                                                        myp1.put(second,control);*/
                                                                                vacancy.get(preference.getPreference2()).DecCasteVacancy(type);
                                                                            }
                                                                            else if(pref<2&&!seat.equals(""))
                                                                            {}
                                                                            else if(!seat.equals(""))
                                                                            {
                                                                        /*int solve=control2.get(type);
                                                                        control2.put(type,solve+1);
                                                                        myp1.put(seat,control2);*/
                                                                                vacancy.get(preference.getPreference2()).IncCasteVacancy(type);
                                                                                seat=preference.getPreference2();
                                                                                type=l;
                                                                                //pref=3;
                                                                                pref =2;
                                                                        /*update.clear();
                                                                        update.put(type,access-1);
                                                                        control.put(type,access-1);
                                                                        myp1.put(second,control);*/
                                                                                vacancy.get(preference.getPreference2()).DecCasteVacancy(type);
                                                                            }

                                                                        }
                                                                        else
                                                                        {
                                                                    /*access=myp1.get(third).get(l);
                                                                    control=myp1.get(third);*/
                                                                            access=vacancy.get(preference.getPreference3()).getCasteVacancy(l);
                                                                            if(access!=0)
                                                                            {
                                                                                if(seat.equals(""))
                                                                                {
                                                                                    seat=preference.getPreference3();
                                                                                    type=l;
                                                                                    pref=3;
                                                                            /*update.clear();
                                                                            update.put(l,access-1);
                                                                            control.put(l,access-1);
                                                                            myp1.put(third,control);
                                                                            control2=control;*/
                                                                                    vacancy.get(preference.getPreference3()).DecCasteVacancy(type);
                                                                                }
                                                                                else if(seat.equals(preference.getPreference3()))
                                                                                {
                                                                            /*int solve=control2.get(type);
                                                                            control.put(type,solve+1);*/
                                                                                    vacancy.get(preference.getPreference3()).IncCasteVacancy(type);
                                                                                    //seat=forth;
                                                                                    seat = preference.getPreference3();
                                                                                    type=l;
                                                                                    pref=3;
                                                                            /*update.clear();
                                                                            update.put(type,access-1);
                                                                            control.put(type,access-1);
                                                                            myp1.put(third,control);*/
                                                                                    vacancy.get(preference.getPreference3()).DecCasteVacancy(type);
                                                                                }
                                                                                else if(pref<3&&!seat.equals(""))
                                                                                {}
                                                                                else if(!seat.equals(""))
                                                                                {
                                                                            /*int solve=control2.get(type);
                                                                            control2.put(type,solve+1);
                                                                            myp1.put(seat,control2);*/
                                                                                    vacancy.get(preference.getPreference3()).IncCasteVacancy(type);
                                                                                    seat=preference.getPreference3();
                                                                                    type=l;
                                                                                    pref=3;
                                                                            /*update.clear();
                                                                            update.put(type,access-1);
                                                                            control.put(type,access-1);
                                                                            myp1.put(third,control);*/
                                                                                    vacancy.get(preference.getPreference3()).DecCasteVacancy(type);
                                                                                }

                                                                            }
                                                                            else
                                                                            {
                                                                        /*access=myp1.get(forth).get(l);
                                                                        control=myp1.get(forth);*/
                                                                                access = vacancy.get(preference.getPreference4()).getCasteVacancy(l);
                                                                                if(access!=0)
                                                                                {
                                                                                    if(seat.equals(""))
                                                                                    {
                                                                                        seat=preference.getPreference4();
                                                                                        type=l;
                                                                                        pref=4;
                                                                                /*update.clear();
                                                                                update.put(l,access-1);
                                                                                control.put(l,access-1);
                                                                                myp1.put(forth,control);
                                                                                control2=control;*/
                                                                                        vacancy.get(preference.getPreference4()).DecCasteVacancy(type);
                                                                                    }
                                                                                    else if(seat.equals(preference.getPreference4()))
                                                                                    {
                                                                                /*int solve=control2.get(type);
                                                                                control.put(type,solve+1);*/
                                                                                        vacancy.get(preference.getPreference4()).IncCasteVacancy(type);
                                                                                        seat=preference.getPreference4();
                                                                                        type=l;
                                                                                        pref=4;
                                                                                /*update.clear();
                                                                                update.put(type,access-1);
                                                                                control.put(type,access-1);
                                                                                myp1.put(forth,control);*/
                                                                                        vacancy.get(preference.getPreference4()).DecCasteVacancy(type);
                                                                                    }
                                                                                    else if(pref<4&&!seat.equals(""))
                                                                                    {}
                                                                                    else if(!seat.equals(""))
                                                                                    {
                                                                                /*int solve=control2.get(type);
                                                                                control2.put(type,solve+1);
                                                                                myp1.put(seat,control2);*/
                                                                                        vacancy.get(preference.getPreference4()).IncCasteVacancy(type);
                                                                                        seat=preference.getPreference4();
                                                                                        type=l;
                                                                                        pref=4;
                                                                                /*update.clear();
                                                                                update.put(type,access-1);
                                                                                control.put(type,access-1);
                                                                                myp1.put(forth,control);*/
                                                                                        vacancy.get(preference.getPreference4()).DecCasteVacancy(type);
                                                                                    }

                                                                                }
                                                                                else
                                                                                {
                                                                            /*access=myp1.get(fifth).get(l);
                                                                            control=myp1.get(fifth);*/
                                                                                    access = vacancy.get(preference.getPreference5()).getCasteVacancy(l);
                                                                                    if(access!=0)
                                                                                    {
                                                                                        if(seat.equals(""))
                                                                                        {
                                                                                            seat=preference.getPreference5();
                                                                                            type=l;
                                                                                            pref=5;
                                                                                    /*update.clear();
                                                                                    update.put(l,access-1);
                                                                                    control.put(l,access-1);
                                                                                    myp1.put(fifth,control);
                                                                                    control2=control;*/
                                                                                            vacancy.get(preference.getPreference5()).DecCasteVacancy(type);
                                                                                        }
                                                                                        else if(seat.equals(preference.getPreference5()))
                                                                                        {
                                                                                    /*int solve=control2.get(type);
                                                                                    control.put(type,solve+1);*/
                                                                                            vacancy.get(preference.getPreference5()).IncCasteVacancy(type);
                                                                                            seat=preference.getPreference5();
                                                                                            type=l;
                                                                                            pref=5;
                                                                                    /*update.clear();
                                                                                    update.put(type,access-1);
                                                                                    control.put(type,access-1);
                                                                                    myp1.put(fifth,control);*/
                                                                                            vacancy.get(preference.getPreference5()).DecCasteVacancy(type);
                                                                                        }
                                                                                        else if(pref<5&&!seat.equals(""))
                                                                                        {}
                                                                                        else if(!seat.equals(""))
                                                                                        {
                                                                                    /*int solve=control2.get(type);
                                                                                    control2.put(type,solve+1);
                                                                                    myp1.put(seat,control2);*/
                                                                                            vacancy.get(preference.getPreference5()).IncCasteVacancy(type);
                                                                                            seat=preference.getPreference5();
                                                                                            type=l;
                                                                                            pref=5;
                                                                                    /*update.clear();
                                                                                    update.put(type,access-1);
                                                                                    control.put(type,access-1);
                                                                                    myp1.put(fifth,control);*/
                                                                                            vacancy.get(preference.getPreference5()).DecCasteVacancy(type);
                                                                                        }

                                                                                    }
                                                                                    else
                                                                                    {
                                                                                /*access=myp1.get(sixth).get(l);
                                                                                control=myp1.get(sixth);*/
                                                                                        access = vacancy.get(preference.getPreference6()).getCasteVacancy(l);
                                                                                        if(access!=0)
                                                                                        {
                                                                                            if(seat.equals(""))
                                                                                            {
                                                                                                seat=preference.getPreference6();
                                                                                                type=l;
                                                                                                pref=6;
                                                                                        /*update.clear();
                                                                                        update.put(l,access-1);
                                                                                        control.put(l,access-1);
                                                                                        myp1.put(sixth,control);
                                                                                        control2=control;*/
                                                                                                vacancy.get(preference.getPreference6()).DecCasteVacancy(type);
                                                                                            }
                                                                                            else if(seat.equals(preference.getPreference6()))
                                                                                            {
                                                                                        /*int solve=control2.get(type);
                                                                                        control.put(type,solve+1);*/
                                                                                                vacancy.get(preference.getPreference6()).IncCasteVacancy(type);
                                                                                                seat=preference.getPreference6();
                                                                                                type=l;
                                                                                                pref=6;
                                                                                        /*update.clear();
                                                                                        update.put(type,access-1);
                                                                                        control.put(type,access-1);
                                                                                        myp1.put(sixth,control);*/
                                                                                                vacancy.get(preference.getPreference6()).DecCasteVacancy(type);
                                                                                            }
                                                                                            else if(pref<6&&!seat.equals(""))
                                                                                            {}
                                                                                            else if(!seat.equals(""))
                                                                                            {
                                                                                        /*int solve=control2.get(type);
                                                                                        control2.put(type,solve+1);
                                                                                        myp1.put(seat,control2);*/
                                                                                                vacancy.get(preference.getPreference6()).IncCasteVacancy(type);
                                                                                                seat=preference.getPreference6();
                                                                                                type=l;
                                                                                                pref=6;
                                                                                        /*update.clear();
                                                                                        update.put(type,access-1);
                                                                                        control.put(type,access-1);
                                                                                        myp1.put(sixth,control);*/
                                                                                                vacancy.get(preference.getPreference6()).DecCasteVacancy(type);
                                                                                            }

                                                                                        }
                                                                                        else
                                                                                        {
                                                                                            check+=1;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                        //System.out.println("Student has alloted "+seat+" as "+type+".With preference"+pref);
                                                                    }
                                                                    //System.out.println("Student has alloted "+seat+" as "+type+".With preference"+pref);

                                                                }
                                                                if(!info.getCaste().equals("Open")&&info.getGender().equals("F"))
                                                                {
                                                                    String l="l"+info.getCaste().toLowerCase();
                                                                    l=l+"s";
                                                            /*access=myp1.get(first).get(l);
                                                            control=myp1.get(first);*/
                                                                    access = vacancy.get(preference.getPreference1()).getCasteVacancy(l);
                                                                    if(access!=0)
                                                                    {
                                                                        if(seat.equals(""))

                                                                        {
                                                                            seat=preference.getPreference1();
                                                                            type=l;
                                                                            pref=1;
                                                                            /*update.clear();
                                                                            update.put("gopens",access-1);*/
                                                                    /*control.put(l,access-1);
                                                                    myp1.put(first,control);
                                                                    control2=control;*/
                                                                            vacancy.get(preference.getPreference1()).DecCasteVacancy(l);
                                                                        }
                                                                        if(seat.equals(preference.getPreference1()))
                                                                        {
                                                                    /*int solve=control2.get(type);
                                                                    control.put(type,solve+1);*/
                                                                            vacancy.get(preference.getPreference1()).IncCasteVacancy(type);
                                                                            seat=preference.getPreference1();
                                                                            type=l;
                                                                            pref=1;
                                                                    /*update.clear();
                                                                    update.put(type,access-1);
                                                                    control.put(type,access-1);
                                                                    myp1.put(first,control);*/
                                                                            vacancy.get(preference.getPreference1()).DecCasteVacancy(type);
                                                                        }
                                                                        else if(!seat.equals(""))
                                                                        {
                                                                    /*int solve=control2.get(type);
                                                                    control2.put(type,solve+1);
                                                                    myp1.put(seat,control2);*/
                                                                            vacancy.get(preference.getPreference1()).IncCasteVacancy(type);
                                                                            seat=preference.getPreference1();
                                                                            type=l;
                                                                            pref=1;
                                                                    /*update.clear();
                                                                    update.put(type,access-1);
                                                                    control.put(type,access-1);
                                                                    myp1.put(first,control);
                                                                    control2=control;*/
                                                                            vacancy.get(preference.getPreference1()).DecCasteVacancy(type);
                                                                        }
                                                                    }
                                                                    else
                                                                    {
                                                                /*access=myp1.get(second).get(l);
                                                                control=myp1.get(second);*/
                                                                        access = vacancy.get(preference.getPreference2()).getCasteVacancy(l);
                                                                        if(access!=0)
                                                                        {
                                                                            if(seat.equals(""))
                                                                            {
                                                                                seat=preference.getPreference2();
                                                                                type=l;
                                                                                pref=2;
                                                                        /*update.clear();
                                                                        update.put(l,access-1);
                                                                        control.put(l,access-1);
                                                                        myp1.put(second,control);
                                                                        control2=control;*/
                                                                                vacancy.get(preference.getPreference2()).DecCasteVacancy(type);
                                                                            }
                                                                            else if(seat.equals(preference.getPreference2()))
                                                                            {
                                                                        /*int solve=control2.get(type);
                                                                        control.put(type,solve+1);*/
                                                                                vacancy.get(preference.getPreference2()).IncCasteVacancy(type);
                                                                                seat=preference.getPreference2();
                                                                                type=l;
                                                                                pref=2;
                                                                        /*update.clear();
                                                                        update.put(type,access-1);
                                                                        control.put(type,access-1);
                                                                        myp1.put(second,control);*/
                                                                                vacancy.get(preference.getPreference2()).DecCasteVacancy(type);
                                                                            }
                                                                            else if(pref<2&&!seat.equals(""))
                                                                            {}
                                                                            else if(!seat.equals(""))
                                                                            {
                                                                        /*int solve=control2.get(type);
                                                                        control2.put(type,solve+1);
                                                                        myp1.put(seat,control2);*/
                                                                                vacancy.get(preference.getPreference2()).IncCasteVacancy(type);
                                                                                seat=preference.getPreference2();
                                                                                type=l;
                                                                                pref=2;
                                                                        /*update.clear();
                                                                        update.put(type,access-1);
                                                                        control.put(type,access-1);
                                                                        myp1.put(second,control);*/
                                                                                vacancy.get(preference.getPreference2()).DecCasteVacancy(type);
                                                                            }
                                                                        }
                                                                        else
                                                                        {
                                                                    /*access=myp1.get(third).get(l);
                                                                    control=myp1.get(third);*/
                                                                            access = vacancy.get(preference.getPreference3()).getCasteVacancy(l);
                                                                            if(access!=0)
                                                                            {
                                                                                if(seat.equals(""))
                                                                                {
                                                                                    seat=preference.getPreference3();
                                                                                    type=l;
                                                                                    pref=3;
                                                                            /*update.clear();
                                                                            update.put(l,access-1);
                                                                            control.put(l,access-1);
                                                                            myp1.put(third,control);
                                                                            control2=control;*/
                                                                                    vacancy.get(preference.getPreference3()).DecCasteVacancy(type);
                                                                                }
                                                                                else if(seat.equals(preference.getPreference3()))
                                                                                {
                                                                            /*int solve=control2.get(type);
                                                                            control.put(type,solve+1);*/
                                                                                    vacancy.get(preference.getPreference3()).IncCasteVacancy(type);
                                                                                    seat=preference.getPreference3();
                                                                                    type=l;
                                                                                    pref=3;
                                                                            /*update.clear();
                                                                            update.put(type,access-1);
                                                                            control.put(type,access-1);
                                                                            myp1.put(third,control);*/
                                                                                    vacancy.get(preference.getPreference3()).DecCasteVacancy(type);
                                                                                }
                                                                                else if(pref<3&&!seat.equals(""))
                                                                                {}
                                                                                else if(!seat.equals(""))
                                                                                {
                                                                            /*int solve=control2.get(type);
                                                                            control2.put(type,solve+1);
                                                                            myp1.put(seat,control2);*/
                                                                                    vacancy.get(preference.getPreference3()).IncCasteVacancy(type);
                                                                                    seat=preference.getPreference3();
                                                                                    type=l;
                                                                                    pref=5;
                                                                            /*update.clear();
                                                                            update.put(type,access-1);
                                                                            control.put(type,access-1);
                                                                            myp1.put(third,control);*/
                                                                                    vacancy.get(preference.getPreference3()).DecCasteVacancy(type);
                                                                                }
                                                                            }
                                                                            else
                                                                            {

                                                                        /*access=myp1.get(forth).get(l);
                                                                        control=myp1.get(forth);*/
                                                                                access = vacancy.get(preference.getPreference4()).getCasteVacancy(l);
                                                                                if(access!=0)
                                                                                {
                                                                                    if(seat.equals(""))
                                                                                    {
                                                                                        seat=preference.getPreference4();
                                                                                        type=l;
                                                                                        pref=4;
                                                                                /*update.clear();
                                                                                update.put(l,access-1);
                                                                                control.put(l,access-1);
                                                                                myp1.put(forth,control);
                                                                                control2=control;*/
                                                                                        vacancy.get(preference.getPreference4()).DecCasteVacancy(type);
                                                                                    }
                                                                                    else if(seat.equals(preference.getPreference4()))
                                                                                    {
                                                                                        vacancy.get(preference.getPreference4()).IncCasteVacancy(type);
                                                                                        seat=preference.getPreference4();
                                                                                        type=l;
                                                                                        pref=4;
                                                                                /*update.clear();
                                                                                update.put(type,access-1);
                                                                                control.put(type,access-1);
                                                                                myp1.put(forth,control);*/
                                                                                        vacancy.get(preference.getPreference4()).DecCasteVacancy(type);
                                                                                    }
                                                                                    else if(pref<4&&!seat.equals(""))
                                                                                    {}
                                                                                    else if(!seat.equals(""))
                                                                                    {
                                                                                /*int solve=control2.get(type);
                                                                                control2.put(type,solve+1);
                                                                                myp1.put(seat,control2);*/
                                                                                        vacancy.get(preference.getPreference4()).IncCasteVacancy(type);
                                                                                        seat=preference.getPreference4();
                                                                                        type=l;                                                                                          pref=4;
                                                                                /*update.clear();
                                                                                update.put(type,access-1);
                                                                                control.put(type,access-1);
                                                                                myp1.put(forth,control);*/
                                                                                        vacancy.get(preference.getPreference4()).DecCasteVacancy(type);
                                                                                    }
                                                                                }
                                                                                else
                                                                                {
                                                                            /*access=myp1.get(fifth).get(l);
                                                                            control=myp1.get(fifth);*/
                                                                                    access = vacancy.get(preference.getPreference5()).getCasteVacancy(l);
                                                                                    if(access!=0)
                                                                                    {
                                                                                        if(seat.equals(""))
                                                                                        {
                                                                                            seat=preference.getPreference5();
                                                                                            type=l;
                                                                                            pref=5;
                                                                                    /*update.clear();
                                                                                    update.put(l,access-1);
                                                                                    control.put(l,access-1);
                                                                                    myp1.put(fifth,control);
                                                                                    control2=control;*/
                                                                                            vacancy.get(preference.getPreference5()).DecCasteVacancy(type);
                                                                                        }
                                                                                        else if(seat.equals(preference.getPreference5()))
                                                                                        {
                                                                                    /*int solve=control2.get(type);
                                                                                    control.put(type,solve+1);*/
                                                                                            vacancy.get(preference.getPreference5()).IncCasteVacancy(type);
                                                                                            seat=preference.getPreference5();
                                                                                            type=l;
                                                                                            pref=5;
                                                                                    /*update.clear();
                                                                                    update.put(type,access-1);
                                                                                    control.put(type,access-1);
                                                                                    myp1.put(fifth,control);*/
                                                                                            vacancy.get(preference.getPreference5()).DecCasteVacancy(type);
                                                                                        }
                                                                                        else if(pref<5&&!seat.equals(""))
                                                                                        {}
                                                                                        else if(!seat.equals(""))
                                                                                        {
                                                                                    /*int solve=control2.get(type);
                                                                                    control2.put(type,solve+1);
                                                                                    myp1.put(seat,control2);*/
                                                                                            vacancy.get(preference.getPreference5()).IncCasteVacancy(type);
                                                                                            seat=preference.getPreference5();
                                                                                            type=l;
                                                                                            pref=5;
                                                                                    /*update.clear();
                                                                                    update.put(type,access-1);
                                                                                    control.put(type,access-1);
                                                                                    myp1.put(fifth,control);*/
                                                                                            vacancy.get(preference.getPreference5()).DecCasteVacancy(type);
                                                                                        }
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                /*access=myp1.get(sixth).get(l);
                                                                                control=myp1.get(sixth);*/
                                                                                        access = vacancy.get(preference.getPreference6()).getCasteVacancy(l);
                                                                                        if(access!=0)
                                                                                        {
                                                                                            if(seat.equals(""))
                                                                                            {
                                                                                                seat=preference.getPreference6();
                                                                                                type=l;
                                                                                                pref=6;
                                                                                        /*update.clear();
                                                                                        update.put(l,access-1);
                                                                                        control.put(l,access-1);
                                                                                        myp1.put(sixth,control);
                                                                                        control2=control;*/
                                                                                                vacancy.get(preference.getPreference6()).DecCasteVacancy(type);
                                                                                            }
                                                                                            else if(seat.equals(preference.getPreference6()))
                                                                                            {
                                                                                        /*int solve=control2.get(type);
                                                                                        control.put(type,solve+1);*/
                                                                                                vacancy.get(preference.getPreference6()).IncCasteVacancy(type);
                                                                                                seat=preference.getPreference6();
                                                                                                type=l;
                                                                                                pref=6;
                                                                                        /*update.clear();
                                                                                        update.put(type,access-1);
                                                                                        control.put(type,access-1);
                                                                                        myp1.put(sixth,control);*/
                                                                                                vacancy.get(preference.getPreference6()).DecCasteVacancy(type);
                                                                                            }
                                                                                            else if(pref<6&&!seat.equals(""))
                                                                                            {}
                                                                                            else if(!seat.equals(""))
                                                                                            {
                                                                                        /*int solve=control2.get(type);
                                                                                        control2.put(type,solve+1);
                                                                                        myp1.put(seat,control2);*/
                                                                                                vacancy.get(preference.getPreference6()).IncCasteVacancy(type);
                                                                                                seat=preference.getPreference6();
                                                                                                type=l;
                                                                                                pref=6;
                                                                                        /*update.clear();
                                                                                        update.put(type,access-1);
                                                                                        control.put(type,access-1);
                                                                                        myp1.put(sixth,control);*/
                                                                                                vacancy.get(preference.getPreference6()).DecCasteVacancy(type);
                                                                                            }
                                                                                        }
                                                                                        else
                                                                                        {
                                                                                            check+=1;
                                                                                            //System.out.println("Sorry ! You have not alloted seat");
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }

                                                                        }

                                                                    }
                                                                }
                                                            }

                                                            if(info.getGender().equals("F")&&(check==4))
                                                            {
                                                                String Id1=doc1.getId().toString();
                                                                //FirebaseFirestore.getInstance().collection("Student Info").document(Id1).update("status1","Sorry!You have not allotted seat");

                                                            }
                                                            else if(info.getGender().equals("F")&&info.getCaste().equals("Open")&&check==2)
                                                            {
                                                                String Id1=doc1.getId().toString();
                                                                //FirebaseFirestore.getInstance().collection("StudentInfo").document(Id1).update("status1","Sorry!You have not allotted seat");
                                                            }
                                                            else if(info.getGender().equals("M")&&(check==2)&&!info.getCaste().equals("Open"))
                                                            {
                                                                String Id1=doc1.getId().toString();
                                                                //FirebaseFirestore.getInstance().collection("StudentInfo").document(Id1).update("status1","Sorry!You have not allotted seat");
                                                            }
                                                            else if(info.getGender().equals("M")&&check==1&&info.getCaste().equals("Open"))
                                                            {
                                                                String Id1=doc1.getId().toString();
                                                                //FirebaseFirestore.getInstance().collection("StudentInfo").document(Id1).update("status1","Sorry!You have not allotted seat");
                                                            }
                                                            else
                                                            {
                                                                String Id1=doc1.getId().toString();
                                                                //FirebaseFirestore.getInstance().collection("StudentInfo").document(Id1).update("status1","Student alloted "+seat+" as "+type+". "+"Alloted preference is "+pref);
                                                                Toast.makeText(getApplicationContext(),"Student alloted "+seat+" as "+type+". "+"Alloted preference is "+pref,Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    });

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });






    }

    private void getVacancy(FirestoreCallback firestoreCallback)
    {
        FirebaseFirestore.getInstance().collection("Vacancy").document("Round").collection("Round 1").document("Civil Engineering")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot)
                    {
                        cv=documentSnapshot.toObject(NewCategory.class);
                        vacancies++;
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
                                        vacancies++;
                                        Log.d("CSE", cse.toString());
                                        FirebaseFirestore.getInstance().collection("Vacancy")
                                                .document("Round").collection("Round 1").document("Electrical Engineering")
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                    {
                                                        el=documentSnapshot.toObject(NewCategory.class);
                                                        vacancies++;
                                                        Log.d("EL", el.toString());

                                                        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                                                                .collection("Round 1").document("Electronics Engineering")
                                                                .get()
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                                    {
                                                                        en=documentSnapshot.toObject(NewCategory.class);
                                                                        vacancies++;
                                                                        Log.d("EN", en.toString());
                                                                        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                                                                                .collection("Round 1").document("Information Technology")
                                                                                .get()
                                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                    @Override
                                                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                                                    {
                                                                                        it=documentSnapshot.toObject(NewCategory.class);
                                                                                        vacancies++;
                                                                                        Log.d("IT", it.toString());
                                                                                        FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                                                                                                .collection("Round 1").document("Mechanical Engineering")
                                                                                                .get()
                                                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(DocumentSnapshot documentSnapshot)
                                                                                                    {
                                                                                                        me=documentSnapshot.toObject(NewCategory.class);
                                                                                                        vacancies++;
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

    private interface FirestoreCallback {
        void onCallback();
    }
}


/*
package com.example.spotround;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class ComputeResult extends AppCompatActivity {
    public static int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_result);
        DatabaseReference dr1 = FirebaseDatabase.getInstance().getReference("Vacancy");
        FirebaseFirestore.getInstance()
                .collection("StudentInfo")
                .orderBy("rank");
        Task<QuerySnapshot> dr=FirebaseFirestore.getInstance().collection("StudentInfo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
        {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot snapshot:snapshotList)
                {
                    FirebaseDatabase db=FirebaseDatabase.getInstance();
                    DatabaseReference root=db.getReference("Vacancy");
                    String name2= Objects.requireNonNull(snapshot.get("name")).toString();
                    String first= Objects.requireNonNull(snapshot.get("first_preference")).toString();
                    String second=Objects.requireNonNull(snapshot.get("second_preference").toString());
                    String third= Objects.requireNonNull(snapshot.get("third_preference")).toString();
                    String forth=snapshot.get("forth_preference").toString();
                    String fifth=snapshot.get("fifth_preference").toString();
                    String sixth=snapshot.get("sixth_preference").toString();

                    DocumentReference dref=FirebaseFirestore.getInstance().collection("Vacancy").document(first);
                    dref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable  DocumentSnapshot value, @Nullable  FirebaseFirestoreException error) {
                            String abc1=value.getString("gopens");
                            int a1=Integer.parseInt(abc1);
                            if(a1!=0)
                            {
                                int a=Integer.parseInt(abc1);
                                a=a-1;
                                abc1=Integer.toString(a);
                                String at=snapshot.getId().toString();
                                FirebaseFirestore.getInstance().collection("StudentInfo").document(at).update("status1","Prefernce is alloted:-"+first);
                                FirebaseFirestore.getInstance().collection("Vacancy").document(first).update("gopens",abc1);
                            }
                            else
                            {
                                DocumentReference dref1=FirebaseFirestore.getInstance().collection("Vacancy").document(second);
                                dref1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value1, @Nullable FirebaseFirestoreException error) {
                                        String abc2=value1.getString("gopens");
                                        int b1=Integer.parseInt(abc2);
                                        if(b1!=0)
                                        {
                                            int b=Integer.parseInt(abc2);
                                            b=b-1;
                                            abc2=Integer.toString(b);
                                            String at1=snapshot.getId().toString();
                                            FirebaseFirestore.getInstance().collection("StudentInfo").document(at1).update("status1","Prefernce is alloted:-"+second);
                                            FirebaseFirestore.getInstance().collection("Vacancy").document(second).update("gopens",abc2);
                                        }
                                        else
                                        {
                                            DocumentReference dref2=FirebaseFirestore.getInstance().collection("Vacancy").document(third);
                                            dref2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable DocumentSnapshot value2, @Nullable FirebaseFirestoreException error) {
                                                    String abc3=value2.getString("gopens");
                                                    int c1=Integer.parseInt(abc3);
                                                    if(c1!=0)
                                                    {
                                                        int c=Integer.parseInt(abc3);
                                                        c=c-1;
                                                        abc3=Integer.toString(c);
                                                        String at2=snapshot.getId().toString();
                                                        FirebaseFirestore.getInstance().collection("StudentInfo").document(at2).update("status1","Prefernce is alloted:-"+third);
                                                        FirebaseFirestore.getInstance().collection("Vacancy").document(third).update("gopens",abc3);
                                                    }
                                                    else
                                                    {
                                                        DocumentReference dref3=FirebaseFirestore.getInstance().collection("Vacancy").document(forth);
                                                        dref3.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onEvent(@Nullable DocumentSnapshot value3, @Nullable  FirebaseFirestoreException error) {
                                                                String abc4=value3.getString("gopens");
                                                                int d1=Integer.parseInt(abc4);
                                                                if(d1!=0)
                                                                {
                                                                    int d=Integer.parseInt(abc4);
                                                                    d=d-1;
                                                                    abc4=Integer.toString(d);
                                                                    String at3=snapshot.getId().toString();
                                                                    FirebaseFirestore.getInstance().collection("StudentInfo").document(at3).update("status1","Prefernce is alloted:-"+forth);
                                                                    FirebaseFirestore.getInstance().collection("Vacancy").document(forth).update("gopens",abc4);
                                                                }
                                                                else
                                                                {
                                                                    DocumentReference dref4=FirebaseFirestore.getInstance().collection("Vacancy").document(fifth);
                                                                    dref4.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onEvent(@Nullable DocumentSnapshot value4, @Nullable FirebaseFirestoreException error) {
                                                                            String abc5=value4.getString("gopens");
                                                                            int e1=Integer.parseInt(abc5);
                                                                            if(e1!=0)
                                                                            {
                                                                                int e=Integer.parseInt(abc5);
                                                                                e=e-1;
                                                                                abc5=Integer.toString(e);
                                                                                String at4=snapshot.getId().toString();
                                                                                FirebaseFirestore.getInstance().collection("StudentInfo").document(at4).update("status1","Prefernce is alloted:"+fifth);
                                                                                FirebaseFirestore.getInstance().collection("Vacancy").document(fifth).update("gopens",abc5);

                                                                            }
                                                                            else
                                                                            {
                                                                                DocumentReference dref5=FirebaseFirestore.getInstance().collection("Vacancy").document(sixth);
                                                                                dref5.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                                                    @Override
                                                                                    public void onEvent(@Nullable DocumentSnapshot value5, @Nullable  FirebaseFirestoreException error) {

                                                                                        String abc6=value5.getString("gopens");
                                                                                        int f6=Integer.parseInt(abc6);
                                                                                        if(f6!=0)
                                                                                        {
                                                                                            int f=Integer.parseInt(abc6);
                                                                                            f=f-1;
                                                                                            abc6=Integer.toString(f);
                                                                                            String at5=snapshot.getId().toString();
                                                                                            FirebaseFirestore.getInstance().collection("StudentInfo").document(at5).update("status1","Prefernce is alloted:-"+sixth);
                                                                                            FirebaseFirestore.getInstance().collection("Vacancy").document(sixth).update("gopens",abc6);
                                                                                        }
                                                                                        else
                                                                                        {
                                                                                            Toast.makeText(ComputeResult.this,"No seat is allocated",Toast.LENGTH_LONG).show();
                                                                                        }
                                                                                    }
                                                                                });
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }

                                    }
                                });
                            }
                            Toast.makeText(ComputeResult.this,abc1,Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
    }

    private void update(String def)
    {
        a=Integer.valueOf(def);
    }
}*/