package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotround.databinding.ActivityInstituteBinding;
import com.example.spotround.datetime.DateTime;
import com.example.spotround.modle.Result;
import com.example.spotround.modle.Schedule;
import com.example.spotround.modle.StudentInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;
import com.tom_roush.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InstituteActivity extends AppCompatActivity {
    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private DocumentReference reference;
    FirebaseAuth auth;
    int SELECT_PDF = 200;
    Dialog dialog;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private static boolean read_permission_flag = false;
    ProgressBar text;
    TextView text2;
    ActivityInstituteBinding binding;
    PopupMenu popupMenu;
    String fileName;

    Schedule schedule;
    Calendar local, r1R, r2S, r2R, r3S;
    SharedPreferences mPrefs;
    String round;

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    ExecutorService executorServiceUpdateInfo = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstituteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mPrefs = getSharedPreferences("com.example.spotround", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("Schedule", "");
        schedule = gson.fromJson(json, Schedule.class);

        local = Calendar.getInstance();
        local.set(DateTime.getYear(), DateTime.getMonth(), DateTime.getDate(), DateTime.getHr(), DateTime.getMin());
        r1R = Calendar.getInstance();
        r2S = Calendar.getInstance();
        r2R = Calendar.getInstance();
        r3S = Calendar.getInstance();
        r1R.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getR1Result().substring(0, 2)), Integer.parseInt(schedule.getR1Result().substring(3, 5)));
        r2S.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound2Start().substring(0, 2)), Integer.parseInt(schedule.getRound2Start().substring(3, 5)));
        r2R.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getR2Result().substring(0, 2)), Integer.parseInt(schedule.getR2Result().substring(3, 5)));
        r3S.set(schedule.getYear(), schedule.getMonth(), schedule.getDateInt(), Integer.parseInt(schedule.getRound3Start().substring(0, 2)), Integer.parseInt(schedule.getRound3Start().substring(3, 5)));

        dialog = new Dialog(InstituteActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_bar_dialog);

        text = dialog.findViewById(R.id.IdProgressBar);
        text2 = dialog.findViewById(R.id.IdProgressPercentage);
        auth = FirebaseAuth.getInstance();

        popupMenu = new PopupMenu(InstituteActivity.this, binding.menu);

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

        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                if(!read_permission_flag)
                    Toast.makeText(InstituteActivity.this, "Can't access storage", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), SELECT_PDF);
            }
        });

        binding.viewCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstituteActivity.this, CandidateList.class));
            }
        });

        binding.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                popupMenu.show();
            }
        });

        binding.updateVacency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstituteActivity.this, UpdateVacancy.class));
            }
        });
        binding.setSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstituteActivity.this, setSchedule.class));
            }
        });
        binding.generateResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstituteActivity.this, ComputeResult.class);
                intent.putExtra("Schedule", schedule);
                startActivity(intent);
            }
        });

        binding.updateSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorServiceUpdateInfo.execute(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        LocalDate scheduleDate = LocalDate.of(schedule.getYear(), schedule.getMonth(), schedule.getDateInt());
                        if(DateTime.getLocalDate().equals(scheduleDate)) {
                            if (local.after(r1R) && local.before(r2S))
                                round = "Round 2";
                            else if (local.after(r2R) && local.before(r3S))
                                round = "Round 3";
                            else
                                round = "None";
                            FirebaseFirestore.getInstance().collection("Result").get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                                Result result = snapshot.toObject(Result.class);
                                                FirebaseFirestore.getInstance().collection("Vacancy/Round/" + round).document(result.getChoiceCode())
                                                        .update(result.getSeatType(), FieldValue.increment(1));
                                            }
                                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                                Result result = snapshot.toObject(Result.class);
                                                FirebaseFirestore.getInstance().collection("Result").document(snapshot.getId()).delete();
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELECT_PDF) {
            Uri selectedPdfUri = data.getData();
            if (null != selectedPdfUri) {
                fileName = data.getData().getPath();
                fileName = fileName.substring(fileName.lastIndexOf(":") + 1);
                fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName;

                new AlertDialog.Builder(InstituteActivity.this)
                        .setTitle("Alert")
                        .setMessage("Are you sure you want upload data from "+ "'" + fileName + "'")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                update(fileName,1);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else {
                Log.w("InstituteActivity", "Pdf not selected");
            }
        }
    }

    private void update(String fileName, int a) {
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setTitle("Uploading "+ fileName);
        dialog.show();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                PDFBoxResourceLoader.init(getApplicationContext());
                PDDocument document;

                try {
                    document = PDDocument.load(new File(fileName));
                    document.getClass();
                    if(!document.isEncrypted()) {
                        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                        stripper.setSortByPosition(true);
                        PDFTextStripper Tstripper = new PDFTextStripper();
                        int count = document.getPages().getCount();
                        setMaxCount(count);
                        for(int i = 1; i <= count; i++) {
                            Tstripper.setStartPage(i);
                            Tstripper.setEndPage(i);
                            String str = Tstripper.getText(document);
                            String a = str.substring(0, 522);
                            str = str.substring(521);
                            int index = str.indexOf("Published on");
                            str = str.substring(0, index);
                            main(str);
                            //Log.d("InstituteActivity","Uploading: " + progress + "%");
                            setProgress(i, count);
                        }
                    }
                }
                catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
                dialog.dismiss();
            }
        });
    }

    void setMaxCount(int count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setMax(count);
            }
        });
    }

    void setProgress(int i, int count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setProgress(i);
                text2.setText(i + "/" + count);
            }
        });
    }


    boolean check(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    boolean check1(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    void main(String line) {
        StringBuilder cover = new StringBuilder();
        Scanner scn = new Scanner(line);
        while (scn.hasNextLine()) {
            line = scn.nextLine();

            line = line.replace(" $", "");
            line = line.replace("NT 1 (NT-B)", "NT1");
            line = line.replace("NT 2 (NT-C)", "NT2");
            line = line.replace("NT 3 (NT-D)", "NT3");
            line = line.replace("DT/VJ", "VJ");
            line = line.replace(" Yes", "");
            line = line.replace(" PWD", "");
            line = line.replace(" DEF", "");

            String[] word = line.split(" ");
            if (word.length == 2 && line.contains("EN19")) {
                cover = new StringBuilder(line);
                //System.out.println("\n"+cover);
            } else if (word.length < 20 && !(line.contains("EN19"))) {
                int s1;
                s1 = cover.length();
                if (cover.charAt(s1 - 1) == ' ')
                    cover.append(line);
                else
                    cover.append(" ").append(line);
                if (line.contains("MHT-CET")) {
                    String[] word1 = cover.toString().split(" ");
                    int c = 2;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Rank : ", word1[0]);
                        }
                    });

                    int g = 0;
                    StringBuilder name = new StringBuilder();
                    while (g == 0) {
                        if (check1(word1[c])) {
                            g = 1;
                        } else
                            name.append(word1[c]).append(" ");
                        c++;
                    }
                    StudentInfo studentInfo = new StudentInfo(Long.parseLong(word1[0].trim()), word1[1], name.toString(), word1[c + 1],
                            word1[c + 2], word1[c + 3], word1[c + 4], word1[c + 5], word1[c + 6], word1[c + 9], word1[c + 10]);
                    Log.d("Name : ", name.toString());

                    reference = fireStore.collection("StudentInfo").document(studentInfo.getRank() + "");
                    reference.set(studentInfo);
                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Rank : ", word[0]);
                    }
                });
                int c1 = 2;
                int g1 = 0;
                StringBuilder name = new StringBuilder();
                while (g1 == 0) {
                    if (check1(word[c1])) {
                        g1 = 1;
                    } else
                        name.append(word[c1]).append(" ");
                    c1++;
                }

                StudentInfo studentInfo = new StudentInfo(Long.parseLong(word[0].trim()), word[1], name.toString(), word[c1 + 1], word[c1 + 2],
                        word[c1 + 3], word[c1 + 4], word[c1 + 5], word[c1 + 6], word[c1 + 10], word[c1 + 11]);

                reference = fireStore.collection("StudentInfo").document(studentInfo.getRank() + "");
                reference.set(studentInfo);
            }

        }
    }

    public void checkPermission(String permission, int requestCode) {
        if(ContextCompat.checkSelfPermission(InstituteActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(InstituteActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(InstituteActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            setFlag(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == STORAGE_PERMISSION_CODE) {
            if(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                setFlag(true);
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                setFlag(false);
            }
        }
    }

    void setFlag(boolean state) {
        read_permission_flag = state;
    }

    void logout() {
        ProgressDialog dialog = new ProgressDialog(InstituteActivity.this);
        dialog.setTitle("Logout");
        dialog.setMessage("Logging out of your account");
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        auth.signOut();
        dialog.dismiss();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Intent intent = new Intent(InstituteActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
