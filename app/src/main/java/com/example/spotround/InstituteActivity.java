package com.example.spotround;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.spotround.modle.StudentInfo;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.util.*;
import java.io.File;
import java.io.IOException;

public class InstituteActivity extends AppCompatActivity {
    private Button update, candidate, result;
    FirebaseFirestore store;

    public InstituteActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute);
        update = findViewById(R.id.updvacanbutton);
        candidate = findViewById(R.id.viewcandbutton);
        result = findViewById(R.id.viewresultbutton);
        store = FirebaseFirestore.getInstance();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstituteActivity.this, UpdVacancyActivity.class));
            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

    }

    private void update() {
        try {

            File file = new File("pdf.txt");
            PdfReader reader = new PdfReader(String.valueOf(file));

            int n = reader.getNumberOfPages();

            for (int i = 0; i < 1; i++) {
                //extractedText = extractedText + PdfTextExtractor.getTextFromPage(reader, i + 1) + "\n";
                // to extract the PDF content from the different pages
                StudentInfo studentInfo = main(PdfTextExtractor.getTextFromPage(reader, i + 1));
                if(studentInfo != null) {

                }
            }

            reader.close();
        } catch (Exception e) {
            // for handling error while extracting the text file.
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

    StudentInfo main(String line) {
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
                        Log.d("Caste : ", word1[c + 1]);
                        Log.d("Gender : ", word1[c + 2]);
                        Log.d("PCM Percentile : ", word1[c + 3]);
                        Log.d("Math Percentile : ", word1[c + 4]);
                        Log.d("Physics Percentile : ", word1[c + 5]);
                        Log.d("Chemistry Percentile : ", word1[c + 6]);
                        Log.d("HSC : ", word1[c + 9]);
                        Log.d("SSC : ", word1[c + 10] + "\n");
                        return studentInfo;
                    }
                            //System.out.println("\n"+cover);
                } else {
                    Log.d("Rank : ", word[0]);
                    Log.d("Application ID : ", word[1]);
                    int c1 = 2;
                    int g1 = 0;
                    String name = "";
                    while (g1 == 0) {
                        if (check1(word[c1])) {
                            g1 = 1;
                        } else
                            name = name + word[c1] + " ";
                        c1++;
                    }

                    StudentInfo studentInfo = new StudentInfo(word[0], word[1], name, word[c1 + 1], word[c1 + 2],
                            word[c1 + 3], word[c1 + 4], word[c1 + 5], word[c1 + 6], word[c1 + 10], word[c1 + 11]);
                    Log.d("Name : ", name);
                    Log.d("Caste : ", word[c1 + 1]);
                    Log.d("Gender : ", word[c1 + 2]);
                    Log.d("PCM Percentile : ", word[c1 + 3]);
                    Log.d("Math Percentile : ", word[c1 + 4]);
                    Log.d("Physics Percentile : ", word[c1 + 5]);
                    Log.d("Chemistry Percentile : ", word[c1 + 6]);
                    Log.d("HSC : ", word[c1 + 10]);
                    Log.d("SSC : ", word[c1 + 11] + "\n");
                    //System.out.println("\n"+line);
                    return studentInfo;
                }
            }
        }
        return null;
    }
}
