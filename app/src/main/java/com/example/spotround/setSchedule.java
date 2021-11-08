package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.spotround.databinding.ActivitySetScheduleBinding;
import com.example.spotround.modle.Schedule;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Locale;

public class setSchedule extends AppCompatActivity {
    ActivitySetScheduleBinding binding;
    private DatePickerDialog datePickerDialog;
    int hour, minute;
    FirebaseFirestore fireStore;
    DocumentReference reference;
    Schedule schedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDatePicker();
        fireStore = FirebaseFirestore.getInstance();
        reference = fireStore.collection("Schedule").document("schedule");
        binding.updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedule = new Schedule(binding.date.getText().toString(),binding.applicationFillingStart.getText().toString(),binding.applicationFillingEnd.getText().toString(),binding.round1Start.getText().toString(),binding.round1End.getText().toString(),binding.R1Result.getText().toString(),binding.round2Start.getText().toString(),binding.round2End.getText().toString(),binding.R2Result.getText().toString(),binding.round3Start.getText().toString(),binding.round3End.getText().toString(),binding.R3Result.getText().toString());
                reference.set(schedule);
            }
        });




        binding.date.setText("Date: "+getTodaysDate());

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

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

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
                binding.date.setText("Date: "+date);

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
        return getMonthFormat(month)+"/"+day+ "/"+year;
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
            return "JUNE";
        if(month == 7)
            return "JULY";
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