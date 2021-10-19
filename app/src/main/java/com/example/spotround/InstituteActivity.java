package com.example.spotround;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotround.modle.StudentInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;
import com.tom_roush.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.util.*;

public class InstituteActivity extends AppCompatActivity {
    private Button update, candidate, result;
    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private DocumentReference reference;
    int SELECT_PDF = 200;
    Dialog dialog;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private static boolean read_permission_flag = false;

    public InstituteActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute);
        update = findViewById(R.id.updvacanbutton);
        candidate = findViewById(R.id.viewcandbutton);
        result = findViewById(R.id.viewresultbutton);

        /*update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstituteActivity.this, UpdVacancyActivity.class));
            }
        });*/
        result.setOnClickListener(new View.OnClickListener() {
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

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELECT_PDF) {
            Uri selectedPdfUri = data.getData();
            if (null != selectedPdfUri) {
                String fileName = data.getData().getPath();
                fileName = fileName.substring(fileName.lastIndexOf(":") + 1);
                fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName;
                dialog = new Dialog(InstituteActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setTitle("Uploading "+ fileName);
                dialog.setContentView(R.layout.progress_bar_dialog);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                update(fileName,1);
            }
            else {
                Log.w("InstituteActivity", "Pdf not selected");
            }
            dialog.dismiss();
        }
    }

    private void update(String fileName, int a) {
        PDFBoxResourceLoader.init(getApplicationContext());
        final ProgressBar text = dialog.findViewById(R.id.IdProgressBar);
        final TextView text2 = dialog.findViewById(R.id.IdProgressPercentage);
        int n = 1;
        PDDocument document;
        try {
            document = PDDocument.load(new File(fileName));
            document.getClass();
            if(!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper Tstripper = new PDFTextStripper();
                int count = document.getPages().getCount();

                for(int i=2; i<=2; i++) {
                    Tstripper.setStartPage(i);
                    Tstripper.setEndPage(i);
                    String str = Tstripper.getText(document);
                    main(str);
                    int progress = (int)(100.0 * (i) / n);
                    Log.d("InstituteActivity","Uploading: " + progress + "%");
                    text.setProgress(progress);
                    text2.setText(String.valueOf(progress));
                }
                dialog.dismiss();
            }
        }
        catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
    }

    /*private void update(String fileName) {
        final ProgressBar text = dialog.findViewById(R.id.IdProgressBar);
        final TextView text2 = dialog.findViewById(R.id.IdProgressPercentage);
        PdfReader reader = null;
        int n = 1;
        try {
            reader = new PdfReader(fileName);

            n = reader.getNumberOfPages();
        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("Errorrrrrrrrr", e.getMessage());
        }

        for (int i = 1; i < 2; i++) {
            //extractedText = extractedText + PdfTextExtractor.getTextFromPage(reader, i + 1) + "\n";
            // to extract the PDF content from the different pages
            String line = null;
            try {
                line = PdfTextExtractor.getTextFromPage(reader, i + 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Log.d("Line", line);
            main(line);
            int progress = (int)(100.0 * (i+1) / n);
            Log.d("InstituteActivity","Uploading: " + progress + "%");
            text.setProgress(progress);
            text2.setText(String.valueOf(progress));
        }
        dialog.dismiss();
        assert reader != null;
        reader.close();
    }*/


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
            if (line.contains(" $")) {
                 line = line.replace(" $", "");
            }
            if (line.contains("NT 1 (NT-B)")) {
                line = line.replace("NT 1 (NT-B)", "NT-B");
            }
            if (line.contains("NT 2 (NT-C)")) {
                line = line.replace("NT 2 (NT-C)", "NT-C");
            }
            if (line.contains("NT 3 (NT-D)")) {
                line = line.replace("NT 3 (NT-D)", "NT-D");
            }
            if (line.contains("DJ/VJ")) {
                line = line.replace("DJ/VJ", "VJNT");
            }
            if (line.contains(" Yes")) {
                line = line.replace(" Yes", "");
            }
            if (line.contains(" PWD")) {
                line = line.replace(" PWD", "");
            }
            if (line.contains(" DEF")) {
                line = line.replace(" DEF", "");
            }
            if (line.contains("State Common Entrance Test Cell, Government of Maharashtra")
                    || line.contains("Published on 07/07/2019")
                    || line.contains("First Year Under Graduate Technical Courses in Engineering and Technology Admissions 2019-20 -")
                    || line.contains("Final Merit List  Maharashtra State Candidates")
                    || line.contains("Merit No Application ID Candidate's Full Name") || line.contains("Percentile")
                    || line.contains("/Mark") || line.contains("Merit Exam Category Gender") || line.contains("PWD")
                    || line.contains("/ Def") || line.contains("EWS TFWS") || line.contains("CET PCM") || line.contains("CET Math")
                    || line.contains("CET Physics") || line.contains("CET Chemistry") || line.contains("HSC PCM") || line.contains(" %")
                    || line.contains("HSC Math") || line.contains("HSC") || line.contains("Physics") || line.contains("Total")
                    || line.contains("SSC") || line.contains("Math") || line.contains("Science") || line.contains("English") || line.contains("%")) {
                if (line.contains("2063/2072")) {
                    break;
                }
            } else {
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
                        Log.d("Rank : ", word1[0]);
                        Log.d("Application ID : ", word1[1]);
                        int g = 0;
                        StringBuilder name = new StringBuilder();
                        while (g == 0) {
                            if (check1(word1[c])) {
                                g = 1;
                            } else
                                name.append(word1[c]).append(" ");
                            c++;
                        }
                        StudentInfo studentInfo = new StudentInfo(word1[0], word1[1], name.toString(), word1[c + 1],
                                word1[c + 2], word1[c + 3], word1[c + 4], word1[c + 5], word1[c + 6], word1[c + 9], word1[c + 10]);
                        Log.d("Name : ", name.toString());
                        /*Log.d("Caste : ", word1[c + 1]);
                        Log.d("Gender : ", word1[c + 2]);
                        Log.d("PCM Percentile : ", word1[c + 3]);
                        Log.d("Math Percentile : ", word1[c + 4]);
                        Log.d("Physics Percentile : ", word1[c + 5]);
                        Log.d("Chemistry Percentile : ", word1[c + 6]);
                        Log.d("HSC : ", word1[c + 9]);
                        Log.d("SSC : ", word1[c + 10] + "\n");*/
                        reference  = fireStore.collection("StudentInfo").document(studentInfo.getRank());
                        reference.set(studentInfo);
                    }
                            //System.out.println("\n"+cover);
                } else {
                    Log.d("Rank : ", word[0]);
                    //Log.d("Application ID : ", word[1]);
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

                    StudentInfo studentInfo = new StudentInfo(word[0], word[1], name.toString(), word[c1 + 1], word[c1 + 2],
                            word[c1 + 3], word[c1 + 4], word[c1 + 5], word[c1 + 6], word[c1 + 10], word[c1 + 11]);
                    Log.d("Name : ", name.toString());
                    //Log.d("Caste : ", word[c1 + 1]);
                    //Log.d("Gender : ", word[c1 + 2]);
                    //Log.d("PCM Percentile : ", word[c1 + 3]);
                    //Log.d("Math Percentile : ", word[c1 + 4]);
                    //Log.d("Physics Percentile : ", word[c1 + 5]);
                    //Log.d("Chemistry Percentile : ", word[c1 + 6]);
                    //Log.d("HSC : ", word[c1 + 10]);
                    //Log.d("SSC : ", word[c1 + 11] + "\n");
                    //System.out.println("\n"+line);

                    reference  = fireStore.collection("StudentInfo").document(studentInfo.getRank());
                    reference.set(studentInfo);
                }
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
}
