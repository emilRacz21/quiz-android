package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Result extends AppCompatActivity {

    TextView result;
    Button restart;
    TextView rank;
    String[] clickedOptions;
    List<Pytania> pytanias;
    ListView resultList;
    int[] allQuestions;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultList = findViewById(R.id.resultList);
        pytanias= (List<Pytania>) getIntent().getSerializableExtra("pytanias");
        Intent getArray = getIntent();
        allQuestions= getArray.getIntArrayExtra("allQuestions");
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int points = bundle.getInt("points");
        int length = bundle.getInt("length");
        clickedOptions = bundle.getStringArray("answers");

        result = findViewById(R.id.result);
        restart = findViewById(R.id.restart);
        rank = findViewById(R.id.rank);

        // Oblicz procentową punktację
        int percentage = (int) (((double) points / length) * 100);

        result.setText("Odgadłeś " + points + " z " + length + " pytań!");

        setRanking(percentage);
        CustomAdapter customAdapter = new CustomAdapter(pytanias, allQuestions, clickedOptions,this);
        resultList.setAdapter(customAdapter);
        //restart po skonczonej grze
        restart.setOnClickListener(view -> {
            Intent intent = new Intent(Result.this, MainActivity.class);
            startActivity(intent);
        });
    }
    // Ustaw ranking w zależności od procentowej punktacji
    void setRanking(int percentage){
        if (percentage >= 90) {
            rank.setText(R.string.ocena5);
            rank.setTextColor(ContextCompat.getColor(this, R.color.positive));
        } else if (percentage >= 70) {
            rank.setText(R.string.ocena4);
            rank.setTextColor(ContextCompat.getColor(this, R.color.positive));
        } else if (percentage >= 50) {
            rank.setText(R.string.ocena3);
            rank.setTextColor(ContextCompat.getColor(this, R.color.average));
        } else {
            rank.setText(R.string.ocena2);
            rank.setTextColor(ContextCompat.getColor(this, R.color.negative));
        }
    }
}
