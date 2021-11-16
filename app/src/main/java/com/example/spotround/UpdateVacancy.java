package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotround.databinding.ActivityUpdateVacancyBinding;
import com.example.spotround.databinding.ProgressBarDialogBinding;
import com.example.spotround.modle.NewCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;
import com.tom_roush.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateVacancy extends AppCompatActivity {
    ActivityUpdateVacancyBinding binding;
    int SELECT_PDF = 200;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private static boolean read_permission_flag = false;
    String fileName;
    ProgressDialog progressDialog;

    Map<String, NewCategory> vacancy;
    NewCategory cse, it, me, el, en, cv;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateVacancyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(UpdateVacancy.this);
        progressDialog.setTitle("Updating");
        progressDialog.setMessage("Uploading Vacancies");

        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.bs.setEnabled(false);
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 1").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful() && task.getResult().size() == 6) {
                                    FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                                            .collection("Round 1").document("Information Technology").get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    it = documentSnapshot.toObject(NewCategory.class);
                                                    FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                                                            .collection("Round 1").document("Computer Science and Engineering").get()
                                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                    cse = documentSnapshot.toObject(NewCategory.class);
                                                                    FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                                                                            .collection("Round 1").document("Civil Engineering").get()
                                                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                @Override
                                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                    cv = documentSnapshot.toObject(NewCategory.class);
                                                                                    FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                                                                                            .collection("Round 1").document("Electrical Engineering").get()
                                                                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                @Override
                                                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                    el = documentSnapshot.toObject(NewCategory.class);
                                                                                                    FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                                                                                                            .collection("Round 1").document("Electronics Engineering").get()
                                                                                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                                @Override
                                                                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                                    en = documentSnapshot.toObject(NewCategory.class);
                                                                                                                    FirebaseFirestore.getInstance().collection("Vacancy").document("Round")
                                                                                                                            .collection("Round 1").document("Mechanical Engineering").get()
                                                                                                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                                                @Override
                                                                                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                                                    me = documentSnapshot.toObject(NewCategory.class);
                                                                                                                                    setVacancy();
                                                                                                                                    binding.bs.setEnabled(true);
                                                                                                                                    progressDialog.hide();
                                                                                                                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
                                else {
                                    progressDialog.hide();
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            }
                        });
            }
        });

        binding.bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vacancy = new HashMap<>();
                cse = new NewCategory();
                it = new NewCategory();
                el = new NewCategory();
                en = new NewCategory();
                cv = new NewCategory();
                me = new NewCategory();
                vacancy.put("Computer Science and Engineering", cse);
                vacancy.put("Information Technology", it);
                vacancy.put("Mechanical Engineering", me);
                vacancy.put("Electrical Engineering", el);
                vacancy.put("Electronics Engineering", en);
                vacancy.put("Civil Engineering", cv);

                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                if(!read_permission_flag)
                    Toast.makeText(UpdateVacancy.this, "Can't access storage", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), SELECT_PDF);
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

                new AlertDialog.Builder(UpdateVacancy.this)
                        .setTitle("Alert")
                        .setMessage("Are you sure you want upload data from "+ "'" + fileName + "'")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog.show();
                                progressDialog.setCancelable(false);
                                progressDialog.setCanceledOnTouchOutside(false);
                                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                update(fileName);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else {
                Log.w("UpdateVacancy", "Pdf not selected");

            }
        }
    }

    void update(String fileName) {
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

                        for(int i = 1; i <= count; i++) {
                            Tstripper.setStartPage(i);
                            Tstripper.setEndPage(i);
                            String str = Tstripper.getText(document);

                            main(str);
                            Log.d("InstituteActivity", str);
                        }
                        Log.d("cse" , cse.toString());
                        Log.d("it" , it.toString());
                        Log.d("me" , me.toString());
                        Log.d("el" , el.toString());
                        Log.d("en" , en.toString());
                        Log.d("cv" , cv.toString());

                        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 1")
                                .document("Computer Science and Engineering").set(cse)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void unused) {
                                FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 1")
                                        .document("Information Technology").set(it)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(@NonNull Void unused) {
                                        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 1")
                                                .document("Mechanical Engineering").set(me)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(@NonNull Void unused) {
                                                FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 1")
                                                        .document("Civil Engineering").set(cv)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(@NonNull Void unused) {
                                                        FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 1")
                                                                .document("Electrical Engineering").set(el)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(@NonNull Void unused) {
                                                                FirebaseFirestore.getInstance().collection("Vacancy/Round/Round 1")
                                                                        .document("Electronics Engineering").set(en)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(@NonNull Void unused) {
                                                                        progressDialog.hide();
                                                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                                        setVacancy();
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
                }
                catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.hide();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                    });
                }
            }
        });
    }

    String check(String line) {
        switch (line) {
            case "Computer Science and Engineering":
                return "Computer Science and Engineering";
            case "Information Technology":
                return "Information Technology";
            case "Mechanical Engineering":
                return "Mechanical Engineering";
            case "Electrical Engineering":
                return "Electrical Engineering";
            case "Electronics Engineering":
                return "Electronics Engineering";
            case "Civil Engineering":
                return "Civil Engineering";
            default:
                return "false";
        }
    }

    void main(String line) {
        String cover;
        Scanner scn = new Scanner(line);
        String [] list;
        while (scn.hasNextLine()) {
            cover = scn.nextLine();
            //System.out.println(cover);
            String branch = check(cover);
            if(!branch.equals("false")) {
                NewCategory a = vacancy.get(branch);
                scn.nextLine();
                cover = scn.nextLine();
                list = cover.split(" ");
                a.setGopens(Integer.parseInt(list[0]));
                a.setLopens(Integer.parseInt(list[1]));
                a.setGobcs(Integer.parseInt(list[2]));
                a.setLobcs(Integer.parseInt(list[3]));

                scn.nextLine();
                cover = scn.nextLine();
                list = cover.split(" ");

                a.setGnt1s(Integer.parseInt(list[0]));
                a.setLnt1s(Integer.parseInt(list[1]));
                a.setGnt2s(Integer.parseInt(list[2]));
                a.setLnt2s(Integer.parseInt(list[3]));

                scn.nextLine();
                cover = scn.nextLine();
                list = cover.split(" ");
                a.setGnt3s(Integer.parseInt(list[0]));
                a.setLnt3s(Integer.parseInt(list[1]));
                a.setGscs(Integer.parseInt(list[2]));
                a.setLscs(Integer.parseInt(list[3]));

                scn.nextLine();
                cover = scn.nextLine();
                list = cover.split(" ");

                a.setGsts(Integer.parseInt(list[0]));
                a.setLsts(Integer.parseInt(list[1]));
                a.setGvjs(Integer.parseInt(list[2]));
                a.setLvjs(Integer.parseInt(list[3]));
            }
        }
    }



    public void checkPermission(String permission, int requestCode) {
        if(ContextCompat.checkSelfPermission(UpdateVacancy.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(UpdateVacancy.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(UpdateVacancy.this, "Permission Granted", Toast.LENGTH_SHORT).show();
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

    void setVacancy() {
        NewCategory category;

        //IT
        category = it;
        binding.text3.setText(category.getGopens() + "");
        if (!(category.getGopens() == 0))
            binding.text3.setTextColor(Color.GREEN);
        else
            binding.text3.setTextColor(Color.RED);

        binding.text8.setText(category.getLopens() + "");
        if (!(category.getLopens() == 0))
            binding.text8.setTextColor(Color.GREEN);
        else
            binding.text8.setTextColor(Color.RED);

        binding.text9.setText(category.getGobcs() + "");
        if (!(category.getGobcs() == 0))
            binding.text9.setTextColor(Color.GREEN);
        else
            binding.text9.setTextColor(Color.RED);

        binding.text10.setText(category.getLobcs() + "");
        if (!(category.getLobcs() == 0))
            binding.text10.setTextColor(Color.GREEN);
        else
            binding.text10.setTextColor(Color.RED);

        binding.text11.setText(category.getGnt1s() + "");
        if (!(category.getGnt1s() == 0))
            binding.text11.setTextColor(Color.GREEN);
        else
            binding.text11.setTextColor(Color.RED);

        binding.text16.setText(category.getLnt1s() + "");
        if (!(category.getLnt1s() == 0))
            binding.text16.setTextColor(Color.GREEN);
        else
            binding.text16.setTextColor(Color.RED);

        binding.text17.setText(category.getGnt2s() + "");
        if (!(category.getGnt2s() == 0))
            binding.text17.setTextColor(Color.GREEN);
        else
            binding.text17.setTextColor(Color.RED);

        binding.text18.setText(category.getLnt2s() + "");
        if (!(category.getLnt2s() == 0))
            binding.text18.setTextColor(Color.GREEN);
        else
            binding.text18.setTextColor(Color.RED);

        binding.text23.setText(category.getGnt3s() + "");
        if (!(category.getGnt3s() == 0))
            binding.text23.setTextColor(Color.GREEN);
        else
            binding.text23.setTextColor(Color.RED);

        binding.text24.setText(category.getLnt3s() + "");
        if (!(category.getLnt3s() == 0))
            binding.text24.setTextColor(Color.GREEN);
        else
            binding.text24.setTextColor(Color.RED);

        binding.text25.setText(category.getGvjs() + "");
        if (!(category.getGvjs() == 0))
            binding.text25.setTextColor(Color.GREEN);
        else
            binding.text25.setTextColor(Color.RED);

        binding.text26.setText(category.getLvjs() + "");
        if (!(category.getLvjs() == 0))
            binding.text26.setTextColor(Color.GREEN);
        else
            binding.text26.setTextColor(Color.RED);

        binding.text31.setText(category.getGsts() + "");
        if (!(category.getGsts() == 0))
            binding.text31.setTextColor(Color.GREEN);
        else
            binding.text31.setTextColor(Color.RED);

        binding.text32.setText(category.getLsts() + "");
        if (!(category.getLsts() == 0))
            binding.text32.setTextColor(Color.GREEN);
        else
            binding.text32.setTextColor(Color.RED);

        binding.text33.setText(category.getGscs() + "");
        if (!(category.getGscs() == 0))
            binding.text33.setTextColor(Color.GREEN);
        else
            binding.text33.setTextColor(Color.RED);

        binding.text29.setText(category.getLscs() + "");
        if (!(category.getLscs() == 0))
            binding.text29.setTextColor(Color.GREEN);
        else
            binding.text29.setTextColor(Color.RED);

        //cse
        category = cse;
        binding.csetext3.setText(category.getGopens() + "");
        if (!(category.getGopens() == 0))
            binding.csetext3.setTextColor(Color.GREEN);
        else
            binding.csetext3.setTextColor(Color.RED);

        binding.csetext8.setText(category.getLopens() + "");
        if (!(category.getLopens() == 0))
            binding.csetext8.setTextColor(Color.GREEN);
        else
            binding.csetext8.setTextColor(Color.RED);

        binding.csetext9.setText(category.getGobcs() + "");
        if (!(category.getGobcs() == 0))
            binding.csetext9.setTextColor(Color.GREEN);
        else
            binding.csetext9.setTextColor(Color.RED);

        binding.csetext10.setText(category.getLobcs() + "");
        if (!(category.getLobcs() == 0))
            binding.csetext10.setTextColor(Color.GREEN);
        else
            binding.csetext10.setTextColor(Color.RED);

        binding.csetext11.setText(category.getGnt1s() + "");
        if (!(category.getGnt1s() == 0))
            binding.csetext11.setTextColor(Color.GREEN);
        else
            binding.csetext11.setTextColor(Color.RED);

        binding.csetext16.setText(category.getLnt1s() + "");
        if (!(category.getLnt1s() == 0))
            binding.csetext16.setTextColor(Color.GREEN);
        else
            binding.csetext16.setTextColor(Color.RED);

        binding.csetext17.setText(category.getGnt2s() + "");
        if (!(category.getGnt2s() == 0))
            binding.csetext17.setTextColor(Color.GREEN);
        else
            binding.csetext17.setTextColor(Color.RED);

        binding.csetext18.setText(category.getLnt2s() + "");
        if (!(category.getLnt2s() == 0))
            binding.csetext18.setTextColor(Color.GREEN);
        else
            binding.csetext18.setTextColor(Color.RED);

        binding.csetext23.setText(category.getGnt3s() + "");
        if (!(category.getGnt3s() == 0))
            binding.csetext23.setTextColor(Color.GREEN);
        else
            binding.csetext23.setTextColor(Color.RED);

        binding.csetext24.setText(category.getLnt3s() + "");
        if (!(category.getLnt3s() == 0))
            binding.csetext24.setTextColor(Color.GREEN);
        else
            binding.csetext24.setTextColor(Color.RED);

        binding.csetext25.setText(category.getGvjs() + "");
        if (!(category.getGvjs() == 0))
            binding.csetext25.setTextColor(Color.GREEN);
        else
            binding.csetext25.setTextColor(Color.RED);

        binding.csetext26.setText(category.getLvjs() + "");
        if (!(category.getLvjs() == 0))
            binding.csetext26.setTextColor(Color.GREEN);
        else
            binding.csetext26.setTextColor(Color.RED);

        binding.csetext31.setText(category.getGsts() + "");
        if (!(category.getGsts() == 0))
            binding.csetext31.setTextColor(Color.GREEN);
        else
            binding.csetext31.setTextColor(Color.RED);

        binding.csetext32.setText(category.getLsts() + "");
        if (!(category.getLsts() == 0))
            binding.csetext32.setTextColor(Color.GREEN);
        else
            binding.csetext32.setTextColor(Color.RED);

        binding.csetext33.setText(category.getGscs() + "");
        if (!(category.getGscs() == 0))
            binding.csetext33.setTextColor(Color.GREEN);
        else
            binding.csetext33.setTextColor(Color.RED);

        binding.csetext29.setText(category.getLscs() + "");
        if (!(category.getLscs() == 0))
            binding.csetext29.setTextColor(Color.GREEN);
        else
            binding.csetext29.setTextColor(Color.RED);

        //el
        category = el;
        binding.eltext3.setText(category.getGopens() + "");
        if (!(category.getGopens() == 0))
            binding.eltext3.setTextColor(Color.GREEN);
        else
            binding.eltext3.setTextColor(Color.RED);

        binding.eltext8.setText(category.getLopens() + "");
        if (!(category.getLopens() == 0))
            binding.eltext8.setTextColor(Color.GREEN);
        else
            binding.eltext8.setTextColor(Color.RED);

        binding.eltext9.setText(category.getGobcs() + "");
        if (!(category.getGobcs() == 0))
            binding.eltext9.setTextColor(Color.GREEN);
        else
            binding.eltext9.setTextColor(Color.RED);

        binding.eltext10.setText(category.getLobcs() + "");
        if (!(category.getLobcs() == 0))
            binding.eltext10.setTextColor(Color.GREEN);
        else
            binding.eltext10.setTextColor(Color.RED);

        binding.eltext11.setText(category.getGnt1s() + "");
        if (!(category.getGnt1s() == 0))
            binding.eltext11.setTextColor(Color.GREEN);
        else
            binding.eltext11.setTextColor(Color.RED);

        binding.eltext16.setText(category.getLnt1s() + "");
        if (!(category.getLnt1s() == 0))
            binding.eltext16.setTextColor(Color.GREEN);
        else
            binding.eltext16.setTextColor(Color.RED);

        binding.eltext17.setText(category.getGnt2s() + "");
        if (!(category.getGnt2s() == 0))
            binding.eltext17.setTextColor(Color.GREEN);
        else
            binding.eltext17.setTextColor(Color.RED);

        binding.eltext18.setText(category.getLnt2s() + "");
        if (!(category.getLnt2s() == 0))
            binding.eltext18.setTextColor(Color.GREEN);
        else
            binding.eltext18.setTextColor(Color.RED);

        binding.eltext23.setText(category.getGnt3s() + "");
        if (!(category.getGnt3s() == 0))
            binding.eltext23.setTextColor(Color.GREEN);
        else
            binding.eltext23.setTextColor(Color.RED);

        binding.eltext24.setText(category.getLnt3s() + "");
        if (!(category.getLnt3s() == 0))
            binding.eltext24.setTextColor(Color.GREEN);
        else
            binding.eltext24.setTextColor(Color.RED);

        binding.eltext25.setText(category.getGvjs() + "");
        if (!(category.getGvjs() == 0))
            binding.eltext25.setTextColor(Color.GREEN);
        else
            binding.eltext25.setTextColor(Color.RED);

        binding.eltext26.setText(category.getLvjs() + "");
        if (!(category.getLvjs() == 0))
            binding.eltext26.setTextColor(Color.GREEN);
        else
            binding.eltext26.setTextColor(Color.RED);

        binding.eltext31.setText(category.getGsts() + "");
        if (!(category.getGsts() == 0))
            binding.eltext31.setTextColor(Color.GREEN);
        else
            binding.eltext31.setTextColor(Color.RED);

        binding.eltext32.setText(category.getLsts() + "");
        if (!(category.getLsts() == 0))
            binding.eltext32.setTextColor(Color.GREEN);
        else
            binding.eltext32.setTextColor(Color.RED);

        binding.eltext33.setText(category.getGscs() + "");
        if (!(category.getGscs() == 0))
            binding.eltext33.setTextColor(Color.GREEN);
        else
            binding.eltext33.setTextColor(Color.RED);

        binding.eltext29.setText(category.getLscs() + "");
        if (!(category.getLscs() == 0))
            binding.eltext29.setTextColor(Color.GREEN);
        else
            binding.eltext29.setTextColor(Color.RED);

        //cv
        category = cv;
        binding.cvtext3.setText(category.getGopens() + "");
        if (!(category.getGopens() == 0))
            binding.cvtext3.setTextColor(Color.GREEN);
        else
            binding.cvtext3.setTextColor(Color.RED);

        binding.cvtext8.setText(category.getLopens() + "");
        if (!(category.getLopens() == 0))
            binding.cvtext8.setTextColor(Color.GREEN);
        else
            binding.cvtext8.setTextColor(Color.RED);

        binding.cvtext9.setText(category.getGobcs() + "");
        if (!(category.getGobcs() == 0))
            binding.cvtext9.setTextColor(Color.GREEN);
        else
            binding.cvtext9.setTextColor(Color.RED);

        binding.cvtext10.setText(category.getLobcs() + "");
        if (!(category.getLobcs() == 0))
            binding.cvtext10.setTextColor(Color.GREEN);
        else
            binding.cvtext10.setTextColor(Color.RED);

        binding.cvtext11.setText(category.getGnt1s() + "");
        if (!(category.getGnt1s() == 0))
            binding.cvtext11.setTextColor(Color.GREEN);
        else
            binding.cvtext11.setTextColor(Color.RED);

        binding.cvtext16.setText(category.getLnt1s() + "");
        if (!(category.getLnt1s() == 0))
            binding.cvtext16.setTextColor(Color.GREEN);
        else
            binding.cvtext16.setTextColor(Color.RED);

        binding.cvtext17.setText(category.getGnt2s() + "");
        if (!(category.getGnt2s() == 0))
            binding.cvtext17.setTextColor(Color.GREEN);
        else
            binding.cvtext17.setTextColor(Color.RED);

        binding.cvtext18.setText(category.getLnt2s() + "");
        if (!(category.getLnt2s() == 0))
            binding.cvtext18.setTextColor(Color.GREEN);
        else
            binding.cvtext18.setTextColor(Color.RED);

        binding.cvtext23.setText(category.getGnt3s() + "");
        if (!(category.getGnt3s() == 0))
            binding.cvtext23.setTextColor(Color.GREEN);
        else
            binding.cvtext23.setTextColor(Color.RED);

        binding.cvtext24.setText(category.getLnt3s() + "");
        if (!(category.getLnt3s() == 0))
            binding.cvtext24.setTextColor(Color.GREEN);
        else
            binding.cvtext24.setTextColor(Color.RED);

        binding.cvtext25.setText(category.getGvjs() + "");
        if (!(category.getGvjs() == 0))
            binding.cvtext25.setTextColor(Color.GREEN);
        else
            binding.cvtext25.setTextColor(Color.RED);

        binding.cvtext26.setText(category.getLvjs() + "");
        if (!(category.getLvjs() == 0))
            binding.cvtext26.setTextColor(Color.GREEN);
        else
            binding.cvtext26.setTextColor(Color.RED);

        binding.cvtext31.setText(category.getGsts() + "");
        if (!(category.getGsts() == 0))
            binding.cvtext31.setTextColor(Color.GREEN);
        else
            binding.cvtext31.setTextColor(Color.RED);

        binding.cvtext32.setText(category.getLsts() + "");
        if (!(category.getLsts() == 0))
            binding.cvtext32.setTextColor(Color.GREEN);
        else
            binding.cvtext32.setTextColor(Color.RED);

        binding.cvtext33.setText(category.getGscs() + "");
        if (!(category.getGscs() == 0))
            binding.cvtext33.setTextColor(Color.GREEN);
        else
            binding.cvtext33.setTextColor(Color.RED);

        binding.cvtext29.setText(category.getLscs() + "");
        if (!(category.getLscs() == 0))
            binding.cvtext29.setTextColor(Color.GREEN);
        else
            binding.cvtext29.setTextColor(Color.RED);

        //me
        category = me;
        binding.metext3.setText(category.getGopens() + "");
        if (!(category.getGopens() == 0))
            binding.metext3.setTextColor(Color.GREEN);
        else
            binding.metext3.setTextColor(Color.RED);

        binding.metext8.setText(category.getLopens() + "");
        if (!(category.getLopens() == 0))
            binding.metext8.setTextColor(Color.GREEN);
        else
            binding.metext8.setTextColor(Color.RED);

        binding.metext9.setText(category.getGobcs() + "");
        if (!(category.getGobcs() == 0))
            binding.metext9.setTextColor(Color.GREEN);
        else
            binding.metext9.setTextColor(Color.RED);

        binding.metext10.setText(category.getLobcs() + "");
        if (!(category.getLobcs() == 0))
            binding.metext10.setTextColor(Color.GREEN);
        else
            binding.metext10.setTextColor(Color.RED);

        binding.metext11.setText(category.getGnt1s() + "");
        if (!(category.getGnt1s() == 0))
            binding.metext11.setTextColor(Color.GREEN);
        else
            binding.metext11.setTextColor(Color.RED);

        binding.metext16.setText(category.getLnt1s() + "");
        if (!(category.getLnt1s() == 0))
            binding.metext16.setTextColor(Color.GREEN);
        else
            binding.metext16.setTextColor(Color.RED);

        binding.metext17.setText(category.getGnt2s() + "");
        if (!(category.getGnt2s() == 0))
            binding.metext17.setTextColor(Color.GREEN);
        else
            binding.metext17.setTextColor(Color.RED);

        binding.metext18.setText(category.getLnt2s() + "");
        if (!(category.getLnt2s() == 0))
            binding.metext18.setTextColor(Color.GREEN);
        else
            binding.metext18.setTextColor(Color.RED);

        binding.metext23.setText(category.getGnt3s() + "");
        if (!(category.getGnt3s() == 0))
            binding.metext23.setTextColor(Color.GREEN);
        else
            binding.metext23.setTextColor(Color.RED);

        binding.metext24.setText(category.getLnt3s() + "");
        if (!(category.getLnt3s() == 0))
            binding.metext24.setTextColor(Color.GREEN);
        else
            binding.metext24.setTextColor(Color.RED);

        binding.metext25.setText(category.getGvjs() + "");
        if (!(category.getGvjs() == 0))
            binding.metext25.setTextColor(Color.GREEN);
        else
            binding.metext25.setTextColor(Color.RED);

        binding.metext26.setText(category.getLvjs() + "");
        if (!(category.getLvjs() == 0))
            binding.metext26.setTextColor(Color.GREEN);
        else
            binding.metext26.setTextColor(Color.RED);

        binding.metext31.setText(category.getGsts() + "");
        if (!(category.getGsts() == 0))
            binding.metext31.setTextColor(Color.GREEN);
        else
            binding.metext31.setTextColor(Color.RED);

        binding.metext32.setText(category.getLsts() + "");
        if (!(category.getLsts() == 0))
            binding.metext32.setTextColor(Color.GREEN);
        else
            binding.metext32.setTextColor(Color.RED);

        binding.metext33.setText(category.getGscs() + "");
        if (!(category.getGscs() == 0))
            binding.metext33.setTextColor(Color.GREEN);
        else
            binding.metext33.setTextColor(Color.RED);

        binding.metext29.setText(category.getLscs() + "");
        if (!(category.getLscs() == 0))
            binding.metext29.setTextColor(Color.GREEN);
        else
            binding.metext29.setTextColor(Color.RED);

        //en
        category = en;
        binding.entext3.setText(category.getGopens() + "");
        if (!(category.getGopens() == 0))
            binding.entext3.setTextColor(Color.GREEN);
        else
            binding.entext3.setTextColor(Color.RED);

        binding.entext8.setText(category.getLopens() + "");
        if (!(category.getLopens() == 0))
            binding.entext8.setTextColor(Color.GREEN);
        else
            binding.entext8.setTextColor(Color.RED);

        binding.entext9.setText(category.getGobcs() + "");
        if (!(category.getGobcs() == 0))
            binding.entext9.setTextColor(Color.GREEN);
        else
            binding.entext9.setTextColor(Color.RED);

        binding.entext10.setText(category.getLobcs() + "");
        if (!(category.getLobcs() == 0))
            binding.entext10.setTextColor(Color.GREEN);
        else
            binding.entext10.setTextColor(Color.RED);

        binding.entext11.setText(category.getGnt1s() + "");
        if (!(category.getGnt1s() == 0))
            binding.entext11.setTextColor(Color.GREEN);
        else
            binding.entext11.setTextColor(Color.RED);

        binding.entext16.setText(category.getLnt1s() + "");
        if (!(category.getLnt1s() == 0))
            binding.entext16.setTextColor(Color.GREEN);
        else
            binding.entext16.setTextColor(Color.RED);

        binding.entext17.setText(category.getGnt2s() + "");
        if (!(category.getGnt2s() == 0))
            binding.entext17.setTextColor(Color.GREEN);
        else
            binding.entext17.setTextColor(Color.RED);

        binding.entext18.setText(category.getLnt2s() + "");
        if (!(category.getLnt2s() == 0))
            binding.entext18.setTextColor(Color.GREEN);
        else
            binding.entext18.setTextColor(Color.RED);

        binding.entext23.setText(category.getGnt3s() + "");
        if (!(category.getGnt3s() == 0))
            binding.entext23.setTextColor(Color.GREEN);
        else
            binding.entext23.setTextColor(Color.RED);

        binding.entext24.setText(category.getLnt3s() + "");
        if (!(category.getLnt3s() == 0))
            binding.entext24.setTextColor(Color.GREEN);
        else
            binding.entext24.setTextColor(Color.RED);

        binding.entext25.setText(category.getGvjs() + "");
        if (!(category.getGvjs() == 0))
            binding.entext25.setTextColor(Color.GREEN);
        else
            binding.entext25.setTextColor(Color.RED);

        binding.entext26.setText(category.getLvjs() + "");
        if (!(category.getLvjs() == 0))
            binding.entext26.setTextColor(Color.GREEN);
        else
            binding.entext26.setTextColor(Color.RED);

        binding.entext31.setText(category.getGsts() + "");
        if (!(category.getGsts() == 0))
            binding.entext31.setTextColor(Color.GREEN);
        else
            binding.entext31.setTextColor(Color.RED);

        binding.entext32.setText(category.getLsts() + "");
        if (!(category.getLsts() == 0))
            binding.entext32.setTextColor(Color.GREEN);
        else
            binding.entext32.setTextColor(Color.RED);

        binding.entext33.setText(category.getGscs() + "");
        if (!(category.getGscs() == 0))
            binding.entext33.setTextColor(Color.GREEN);
        else
            binding.entext33.setTextColor(Color.RED);

        binding.entext29.setText(category.getLscs() + "");
        if (!(category.getLscs() == 0))
            binding.entext29.setTextColor(Color.GREEN);
        else
            binding.entext29.setTextColor(Color.RED);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressDialog.dismiss();
        finish();
    }
}