 package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.spotround.databinding.ActivitySetScheduleBinding;
import com.example.spotround.modle.Schedule;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class setSchedule extends AppCompatActivity {
    ActivitySetScheduleBinding binding;
    private DatePickerDialog datePickerDialog;
    int hour, minute;
    FirebaseFirestore fireStore;
    DocumentReference reference;
    Schedule schedule;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(setSchedule.this);
        progressDialog.setTitle("Schedule");
        progressDialog.setMessage("Uploading Schedule");


        FirebaseFirestore.getInstance().collection("Schedule").document("schedule")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    schedule = documentSnapshot.toObject(Schedule.class);
                    String date = schedule.getDate();
                    binding.date.setText(date);
                    binding.applicationFillingStart.setText(schedule.getApplicationFillingStart());
                    binding.applicationFillingEnd.setText(schedule.getApplicationFillingEnd());
                    binding.round1Start.setText(schedule.getRound1Start());
                    binding.round1End.setText(schedule.getRound1End());
                    binding.R1Result.setText(schedule.getR1Result());
                    binding.round2Start.setText(schedule.getRound2Start());
                    binding.round2End.setText(schedule.getRound2End());
                    binding.R2Result.setText(schedule.getR2Result());
                    binding.round3Start.setText(schedule.getRound3Start());
                    binding.round3End.setText(schedule.getRound3End());
                    binding.R3Result.setText(schedule.getR3Result());
                }
            }
        });


        initDatePicker();
        fireStore = FirebaseFirestore.getInstance();
        reference = fireStore.collection("Schedule").document("schedule");
        binding.updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                schedule = new Schedule(binding.date.getText().toString(), binding.applicationFillingStart.getText().toString(),
                        binding.applicationFillingEnd.getText().toString(), binding.round1Start.getText().toString(),
                        binding.round1End.getText().toString(), binding.R1Result.getText().toString(),
                        binding.round2Start.getText().toString(), binding.round2End.getText().toString(),
                        binding.R2Result.getText().toString(), binding.round3Start.getText().toString(),
                        binding.round3End.getText().toString(), binding.R3Result.getText().toString());

                if(check(schedule)) {
                    reference.set(schedule).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(@NonNull Void unused) {
                            progressDialog.hide();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                    });
                }
                else {
                    progressDialog.hide();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }
        });




        binding.date.setText(getTodaysDate());

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        binding.applicationFillingStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker( "applicationFillingStart");
            }
        });
        binding.applicationFillingEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker( "applicationFillingEnd");
            }
        });
        binding.round1Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker( "round1Start");
            }
        });
        binding.round1End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker( "round1End");
            }
        });
        binding.R1Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker( "R1Result");
            }
        });
        binding.round2Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker( "round2Start");
            }
        });
        binding.round2End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker( "round2End");
            }
        });
        binding.R2Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker( "R2Result");
            }
        });
        binding.round3Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker( "round3Start");
            }
        });
        binding.round3End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker( "round3End");
            }
        });
        binding.R3Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker( "R3Result");
            }
        });


    }

    private boolean check(Schedule schedule)
    {
        /*SimpleDateFormat stf=new SimpleDateFormat("HH:mm");
        try {
            Date appstart=stf.parse(schedule.getApplicationFillingStart());
            Date appEnd=stf.parse(schedule.getApplicationFillingEnd());
            Date round1start=stf.parse(schedule.getRound1Start());
            Date round3start=stf.parse(schedule.getRound3Start());
            Date round2start=stf.parse(schedule.getRound2Start());
            Date round1end=stf.parse(schedule.getRound1End());
            Date round2end=stf.parse(schedule.getRound2End());
            Date round3end=stf.parse(schedule.getRound3End());
            Date round1result=stf.parse(schedule.getR1Result());
            Date round2result=stf.parse(schedule.getR2Result());
            Date round3result=stf.parse(schedule.getR3Result());
            long a=round1result.getTime();
            if((appEnd.getTime()-appstart.getTime())>0)
            {
                if(round1start.getTime()-appEnd.getTime()>540000)
                {
                    if(round1end.getTime()-round1start.getTime()>0)
                    {
                        if(round1result.getTime()-round1end.getTime()>540000)
                        {
                            if(round2start.getTime()-round1result.getTime()>540000)
                            {
                                if(round2end.getTime()-round2start.getTime()>0)
                                {
                                    if(round2result.getTime()-round2end.getTime()>540000)
                                    {
                                        if(round3start.getTime()-round2result.getTime()>540000)
                                        {
                                            if(round3end.getTime()-round3start.getTime()>0)
                                            {
                                                if(round3result.getTime()-round3end.getTime()>540000)
                                                {
                                                    return true;
                                                }
                                                else
                                                {
                                                    Toast.makeText(getApplicationContext(),"Reound3Result must be  10 min after Round3End",Toast.LENGTH_LONG).show();
                                                    return false;
                                                }

                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(),"Reound3End  must be after  Round3start",Toast.LENGTH_LONG).show();
                                                return false;
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"Reound3Start must be  10 min after Round2Result",Toast.LENGTH_LONG).show();
                                            return false;
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Reound2Result must be  10 min after Round2End",Toast.LENGTH_LONG).show();
                                        return false;
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Round2End must be after Round2Start",Toast.LENGTH_LONG).show();
                                    return false;
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Reound2Start must be  10 min after Round1Result",Toast.LENGTH_LONG).show();
                                return false;
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Reound1Result must be  10 min after Round1End",Toast.LENGTH_LONG).show();
                            return false;
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Round1End must be after Round1Start",Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Round1Start must be  10 min after ApplicationEnd",Toast.LENGTH_LONG).show();
                    return false;
                }

            }
            else
            {
                Toast.makeText(getApplicationContext(),"ApplincationEnd must be after ApplicationStart",Toast.LENGTH_LONG).show();
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return true;
    }

    public void popTimePicker(String button)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                switch (button)
                {
                    case "applicationFillingStart" : binding.applicationFillingStart.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                                                    break;
                    case "applicationFillingEnd"   : binding.applicationFillingEnd.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                                                    break;
                    case "round1Start"              : binding.round1Start.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                        break;
                    case "round1End"              : binding.round1End.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                        break;
                    case "round2Start"              : binding.round2Start.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                        break;
                    case "round2End"              : binding.round2End.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                        break;
                    case "R1Result"              : binding.R1Result.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                        break;
                    case "round3Start"              : binding.round3Start.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                        break;
                    case "R2Result"              : binding.R2Result.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                        break;
                    case "round3End"              : binding.round3End.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                        break;
                    case "R3Result"              : binding.R3Result.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                        break;
                }
                binding.applicationFillingStart.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };


        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, false);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }


    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year =cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month=month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day , month,year);

    }
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year , int month, int day)
            {
                month = month+1;
                String date = makeDateString(day, month, year);
                binding.date.setText(date);

            }
        } ;
        Calendar cal = Calendar.getInstance();
        int year =cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year ,month ,day);
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month)+","+day+ " "+year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        return "JAN";
    }


}