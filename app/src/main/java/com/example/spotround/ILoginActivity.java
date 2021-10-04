package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ILoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilogin);
        email= findViewById(R.id.pymtemail);
        password=findViewById(R.id.pymtphone);
        login=findViewById(R.id.StartActivitybtnLogin);
        auth=FirebaseAuth.getInstance();
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               String text_email=email.getText().toString();
               String text_password=password.getText().toString();
               FirebaseDatabase db=FirebaseDatabase.getInstance();
               DatabaseReference root= db.getReference("StudentInfo");
               auth.signInWithEmailAndPassword(text_email,text_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful())
                       {
                           String id=task.getResult().getUser().getEmail();
                           db.getReference("StudentInfo").child("RegisterInfo").child("Type").child("admin").orderByChild("username").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull  DataSnapshot snapshot)
                               {
                                   if(snapshot.hasChildren()){
                                       for(DataSnapshot dataSnapshot :snapshot.getChildren()) {
                                           Toast.makeText(ILoginActivity.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                                           Intent intent = new Intent(ILoginActivity.this, InstituteActivity.class);
                                           startActivity(intent);
                                           finish();
                                           //Log.d("User", dataSnapshot.getValue(User.class).toString());
                                           //Intent intent = new Intent(ILoginActivity.this, InsideStudentlogin.class);
                                           //startActivity(intent);
                                       }
                                   }
                                   else{
                                       Toast.makeText(ILoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(ILoginActivity.this, StartActivity.class);
                                       startActivity(intent);
                                   }
                               }

                               @Override
                               public void onCancelled(@NonNull  DatabaseError error) {

                               }
                           });
                       }
                       else
                       {
                           Toast.makeText(ILoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });

               String id1=FirebaseAuth.getInstance().getCurrentUser().getUid();

           }
       });

    }
}