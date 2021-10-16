package com.example.spotround;

import androidx.annotation.NonNull;
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
        DatabaseReference dr1=FirebaseDatabase.getInstance().getReference("Vacancy");
        FirebaseFirestore.getInstance()
                .collection("StudentInfo")
                .orderBy("state_merit_rank");
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
}