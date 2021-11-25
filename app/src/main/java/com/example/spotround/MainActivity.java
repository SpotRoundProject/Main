package com.example.spotround;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotround.databinding.ActivityMainBinding;
import com.example.spotround.datetime.DateTime;
import com.example.spotround.modle.Application;
import com.example.spotround.modle.Schedule;
import com.example.spotround.notification.MyReceiver;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore fireStore;
    DocumentReference reference;
    String uid;
    Application application = null;
    ProgressDialog progressDialog;
    PopupMenu popupMenu;
    SharedPreferences mPrefs;
    Schedule schedule;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Checking Information");

        mPrefs = getSharedPreferences("com.example.spotround", MODE_PRIVATE);
        Gson gson = new Gson();
        if(mPrefs.getBoolean("flagSchedule", false)) {
            String json = mPrefs.getString("Schedule", "");
            schedule = gson.fromJson(json, Schedule.class);
            Log.d("Schedule Error", schedule.toString());
        }

        fireStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        if(auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        popupMenu = new PopupMenu(MainActivity.this, binding.menu);

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.mainactivitymenu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                String item  = (String) menuItem.getTitle();
                switch (item) {
                    case "Help" :
                        Uri uri = Uri.parse("https://spotroundproject.github.io/SpotRoundDocumentation.github.io/"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    case "Logout" :
                        logout();
                        finish();
                        break;
                }
                return true;
            }
        });

        binding.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Clicked menu", Toast.LENGTH_LONG).show();
                popupMenu.show();
            }
        });

        binding.MainActivityApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                LocalDate scheduleDate = LocalDate.of(schedule.getYear(), schedule.getMonth(), schedule.getDateInt());
                Log.d("Errorrrrrrrrrhewifhiw", DateTime.getLocalDate() +"" + scheduleDate);
                if(DateTime.getLocalDate().equals(scheduleDate)) {
                    getData();
                }
                else {
                    Toast.makeText(MainActivity.this, "Check Schedule", Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }
        });

        binding.SeatsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDate scheduleDate = LocalDate.of(schedule.getYear(), schedule.getMonth(), schedule.getDateInt());
                if(DateTime.getLocalDate().equals(scheduleDate)) {
                    Intent intent = new Intent(MainActivity.this, ShowVacancy2.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Check Schedule", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.MainActivityResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });

        binding.Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
            }
        });
        createNotificationChannel();
        setNotification();
    }

    void getData() {
        reference = fireStore.collection("Application").document(uid);
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Calendar local = Calendar.getInstance();
                local.clear();
                local.set(DateTime.getYear(), DateTime.getMonth()+1, DateTime.getDate(), DateTime.getHr(), DateTime.getMin());
                Calendar schStart = Calendar.getInstance();
                schStart.clear();
                schStart.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getApplicationFillingStart().substring(0,2)), Integer.parseInt(schedule.getApplicationFillingStart().substring(3,5)));
                Calendar schEnd = Calendar.getInstance();
                schEnd.clear();
                schEnd.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getApplicationFillingEnd().substring(0,2)), Integer.parseInt(schedule.getApplicationFillingEnd().substring(3,5)));

                if(documentSnapshot.exists()) {
                    application = documentSnapshot.toObject(Application.class);

                    Log.d("OnStart", application.toString());
                    if(!application.isPayment()) {
                        Log.d(local.toString(), schStart.toString()+ " " + schEnd.toString());
                        if(local.after(schStart) && local.before(schEnd)) {
                            Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
                            intent.putExtra("Application", application);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Please Check Schedule", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, SetPreference.class);
                        intent.putExtra("Application", application);
                        intent.putExtra("Schedule", schedule);
                        startActivity(intent);
                    }
                }
                else {
                    Log.d("Eoorrrrrr",local.toString() +"\n" + schStart.toString()+ "\n" + schEnd.toString());
                    if(local.after(schStart) && local.before(schEnd)) {
                        Toast.makeText(MainActivity.this, "Register", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Apply.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please Check Schedule .", Toast.LENGTH_SHORT).show();
                        Log.d("check time", local.after(schStart) +" "+ local.before(schEnd));
                    }

                }
                progressDialog.hide();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    private void logout() {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setTitle("Logout");
        dialog.setMessage("Logging out of your account");
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        auth.signOut();
        progressDialog.dismiss();
        dialog.dismiss();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setNotification() {
        mPrefs = getSharedPreferences("com.example.spotround", MODE_PRIVATE);
        Gson gson = new Gson();

        if(mPrefs.getBoolean("flagSchedule", false)) {
            String json = mPrefs.getString("Schedule", "");
            schedule = gson.fromJson(json, Schedule.class);

            Calendar cal = Calendar.getInstance();

            LocalDate scheduleDate = LocalDate.of(schedule.getYear(), schedule.getMonth(), schedule.getDateInt());
            Log.d(DateTime.getLocalDate().toString(), scheduleDate.toString());
            if(DateTime.getLocalDate().equals(scheduleDate)) {
                Log.d("Notification", "is set");


                AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
                Calendar cal1 = Calendar.getInstance();
                Intent myIntent1 = new Intent(this, MyReceiver.class);
                myIntent1.setAction("applicationFromFillingStart");
                PendingIntent pendingIntent1 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent1,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(schedule.getApplicationFillingStart().substring(0,2)));
                cal1.set(Calendar.MINUTE, Integer.parseInt(schedule.getApplicationFillingStart().substring(3,5)));
                Log.d(cal1.getTimeInMillis() +" ", cal.getTimeInMillis() + " ");

                AlarmManager alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                Calendar cal2 = Calendar.getInstance();
                Intent myIntent2 = new Intent(this, MyReceiver.class);
                myIntent1.setAction("Round1Start");
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(MainActivity.this, 1, myIntent2,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                cal2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(schedule.getRound1Start().substring(0,2)));
                cal2.set(Calendar.MINUTE, Integer.parseInt(schedule.getRound1Start().substring(3,5)));

                AlarmManager alarmManager3 = (AlarmManager) getSystemService(ALARM_SERVICE);
                Calendar cal3 = Calendar.getInstance();
                Intent myIntent3 = new Intent(this, MyReceiver.class);
                myIntent3.setAction("Round1Result");
                PendingIntent pendingIntent3 = PendingIntent.getBroadcast(MainActivity.this, 2, myIntent3,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                cal3.set(Calendar.HOUR_OF_DAY, Integer.parseInt(schedule.getR1Result().substring(0,2)));
                cal3.set(Calendar.MINUTE, Integer.parseInt(schedule.getR1Result().substring(3,5)));

                AlarmManager alarmManager4 = (AlarmManager) getSystemService(ALARM_SERVICE);
                Calendar cal4 = Calendar.getInstance();
                Intent myIntent4 = new Intent(this, MyReceiver.class);
                myIntent4.setAction("Round2Start");
                PendingIntent pendingIntent4 = PendingIntent.getBroadcast(MainActivity.this, 3, myIntent4,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                cal4.set(Calendar.HOUR_OF_DAY, Integer.parseInt(schedule.getRound2Start().substring(0,2)));
                cal4.set(Calendar.MINUTE, Integer.parseInt(schedule.getRound2Start().substring(3,5)));

                AlarmManager alarmManager5 = (AlarmManager) getSystemService(ALARM_SERVICE);
                Calendar cal5 = Calendar.getInstance();
                Intent myIntent5 = new Intent(this, MyReceiver.class);
                myIntent5.setAction("Round2Result");
                PendingIntent pendingIntent5 = PendingIntent.getBroadcast(MainActivity.this, 4, myIntent5,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                cal5.set(Calendar.HOUR_OF_DAY, Integer.parseInt(schedule.getRound2Start().substring(0,2)));
                cal5.set(Calendar.MINUTE, Integer.parseInt(schedule.getRound2Start().substring(3,5)));

                AlarmManager alarmManager6 = (AlarmManager) getSystemService(ALARM_SERVICE);
                Calendar cal6 = Calendar.getInstance();
                Intent myIntent6 = new Intent(this, MyReceiver.class);
                myIntent6.setAction("Round3Start");
                PendingIntent pendingIntent6 = PendingIntent.getBroadcast(MainActivity.this, 5, myIntent6,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                cal6.set(Calendar.HOUR_OF_DAY, Integer.parseInt(schedule.getRound3Start().substring(0,2)));
                cal6.set(Calendar.MINUTE, Integer.parseInt(schedule.getRound3Start().substring(3,5)));

                AlarmManager alarmManager7 = (AlarmManager) getSystemService(ALARM_SERVICE);
                Calendar cal7 = Calendar.getInstance();
                Intent myIntent7 = new Intent(this, MyReceiver.class);
                myIntent7.setAction("Round3Result");
                PendingIntent pendingIntent7 = PendingIntent.getBroadcast(MainActivity.this, 6, myIntent7,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                cal7.set(Calendar.HOUR_OF_DAY, Integer.parseInt(schedule.getR3Result().substring(0,2)));
                cal7.set(Calendar.MINUTE, Integer.parseInt(schedule.getR3Result().substring(3,5)));


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(cal.getTimeInMillis() < cal1.getTimeInMillis())
                        alarmManager1.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            cal1.getTimeInMillis(), pendingIntent1);
                    if(cal.getTimeInMillis() < cal2.getTimeInMillis())
                        alarmManager2.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            cal2.getTimeInMillis(), pendingIntent2);
                    if(cal.getTimeInMillis() < cal3.getTimeInMillis())
                        alarmManager3.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            cal3.getTimeInMillis(), pendingIntent3);
                    if(cal.getTimeInMillis() < cal4.getTimeInMillis())
                        alarmManager4.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            cal4.getTimeInMillis(), pendingIntent4);
                    if(cal.getTimeInMillis() < cal5.getTimeInMillis())
                        alarmManager5.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            cal5.getTimeInMillis(), pendingIntent5);
                    if(cal.getTimeInMillis() < cal6.getTimeInMillis())
                        alarmManager6.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            cal6.getTimeInMillis(), pendingIntent6);
                    if(cal.getTimeInMillis() < cal7.getTimeInMillis())
                        alarmManager7.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            cal7.getTimeInMillis(), pendingIntent7);

                } else {
                    if(cal.getTimeInMillis() < cal1.getTimeInMillis())
                        alarmManager1.setExact(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), pendingIntent1);
                    if(cal.getTimeInMillis() < cal2.getTimeInMillis())
                        alarmManager2.setExact(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(), pendingIntent2);
                    if(cal.getTimeInMillis() < cal3.getTimeInMillis())
                        alarmManager3.setExact(AlarmManager.RTC_WAKEUP, cal3.getTimeInMillis(), pendingIntent3);
                    if(cal.getTimeInMillis() < cal4.getTimeInMillis())
                        alarmManager4.setExact(AlarmManager.RTC_WAKEUP, cal4.getTimeInMillis(), pendingIntent4);
                    if(cal.getTimeInMillis() < cal5.getTimeInMillis())
                        alarmManager5.setExact(AlarmManager.RTC_WAKEUP, cal5.getTimeInMillis(), pendingIntent5);
                    if(cal.getTimeInMillis() < cal6.getTimeInMillis())
                        alarmManager6.setExact(AlarmManager.RTC_WAKEUP, cal6.getTimeInMillis(), pendingIntent6);
                    if(cal.getTimeInMillis() < cal7.getTimeInMillis())
                        alarmManager7.setExact(AlarmManager.RTC_WAKEUP, cal7.getTimeInMillis(), pendingIntent7);
                }
            }
        }
    }

    void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LemubitReminderChannel";
            String description = "Channel for Lemubit Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("notifyLemubit", name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


}