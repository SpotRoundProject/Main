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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth auth;
    FirebaseUser fuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email= findViewById(R.id.pymtemail);
        password=findViewById(R.id.pymtphone);
        login=findViewById(R.id.StartActivitybtnLogin);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                auth=FirebaseAuth.getInstance();
                fuser=auth.getCurrentUser();
                //String ad=fuser.getEmail().toString();
                //Toast.makeText(LoginActivity.this,ad,Toast.LENGTH_LONG).show();

                {
                    String text_email=email.getText().toString();
                    String text_password=password.getText().toString();
                    FirebaseDatabase db=FirebaseDatabase.getInstance();
                    DatabaseReference root= db.getReference("StudentInfo");
                    auth.signInWithEmailAndPassword(text_email,text_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified())
                                {
                                    String id=task.getResult().getUser().getEmail();
                                    db.getReference("StudentInfo").child("RegisterInfo").child("Type").child("User").orderByChild("username").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener()
                                    {
                                        @Override
                                        public void onDataChange(@NonNull  DataSnapshot snapshot)
                                        {
                                            if(snapshot.hasChildren())
                                            {
                                                for(DataSnapshot dataSnapshot :snapshot.getChildren())
                                                {
                                                    Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                                                    //Log.d("User", dataSnapshot.getValue(User.class).toString());
                                                    Intent intent = new Intent(LoginActivity.this, InsideStudentlogin.class);
                                                    startActivity(intent);
                                                }
                                            }
                                            else
                                            {
                                                Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
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
                                    startActivity(new Intent(LoginActivity.this,StartActivity.class));
                                }
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    //String id1=FirebaseAuth.getInstance().getCurrentUser().getUid();

                }

            }
        });


    }

    private void loginuser(String email, String password) {
        auth.signInWithEmailAndPassword(email ,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this ,"Login Succesfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}



























    /*<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@drawable/back1"
        tools:context=".LoginActivity">

<EditText
        android:id="@+id/email"
                android:layout_width="291dp"
                android:layout_height="39dp"
                android:layout_alignParentTop="true"
                android:layout_alignParentEnd="true"
                android:layout_alignParentRight="true"
                android:background="@drawable/custom_input"
                android:hint="Email"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.866"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintVertical_bias="0.466" />

<EditText
        android:id="@+id/password"
                android:layout_width="291dp"
                android:layout_height="39dp"
                android:layout_alignParentEnd="true"
                android:layout_alignParentRight="true"
                android:layout_marginTop="60dp"
                android:background="@drawable/custom_input"
                android:hint="Password"
                android:password="true"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.866"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/email"
                />

<Button
        android:id="@+id/login"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_below="@id/password"
                android:layout_alignParentEnd="true"
                android:layout_alignParentRight="true"
                android:layout_marginTop="72dp"
                android:background="@drawable/custom_button"
                android:backgroundTint="#DB8BE8"
                android:text="Login"
                app:backgroundTint="#1F828F"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.498"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/password" />

<ImageView
        android:id="@+id/imageView2"
                android:layout_width="39dp"
                android:layout_height="39dp"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toStartOf="@+id/email"
                app:layout_constraintHorizontal_bias="0.814"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintVertical_bias="0.467"
                app:srcCompat="@drawable/user" />

<ImageView
        android:id="@+id/imageView3"
                android:layout_width="39dp"
                android:layout_height="39dp"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toStartOf="@+id/password"
                app:layout_constraintHorizontal_bias="0.815"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintVertical_bias="0.612"
                app:srcCompat="@drawable/padlock" />

<TextView
        android:id="@+id/textView3"
                android:layout_width="75dp"
                android:layout_height="30dp"
                android:autoSizeTextType="uniform"
                android:text=" Login as :-"
                android:textStyle="bold"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.199"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintVertical_bias="0.383" />

<Spinner
        android:id="@+id/spinner"
                android:layout_width="150dp"
                android:layout_height="30dp"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.678"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintVertical_bias="0.383" />

<ImageView
        android:id="@+id/imageView4"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.55"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintVertical_bias="0.16"
                app:srcCompat="@drawable/walchand" />

</androidx.constraintlayout.widget.ConstraintLayout>*/








//old

/*
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".LoginActivity">

<EditText
        android:id="@+id/pymtemail"
                android:layout_width="281dp"
                android:layout_height="wrap_content"
                android:layout_alignParentTop="true"
                android:layout_alignParentEnd="true"
                android:layout_alignParentRight="true"
                android:layout_marginTop="108dp"
                android:hint="Email"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

<EditText
        android:id="@+id/pymtphone"
                android:layout_width="281dp"
                android:layout_height="wrap_content"
                android:layout_below="@+id/pymtemail"
                android:layout_alignParentEnd="true"
                android:layout_alignParentRight="true"
                android:layout_marginTop="60dp"
                android:hint="Password"
                android:password="true"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/pymtemail" />

<Button
        android:id="@+id/StartActivitybtnStuentLogin"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_below="@id/pymtphone"
                android:layout_alignParentEnd="true"
                android:layout_alignParentRight="true"
                android:layout_marginTop="112dp"
                android:text="Login"
                app:backgroundTint="#009688"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.498"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/pymtphone" />

</androidx.constraintlayout.widget.ConstraintLayout>*/
