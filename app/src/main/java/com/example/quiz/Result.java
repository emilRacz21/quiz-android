package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    TextView result;
    Button restart;
    TextView rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();

        int points = bundle.getInt("points");
        int length = bundle.getInt("length");

        result = findViewById(R.id.result);
        restart = findViewById(R.id.restart);
        rank = findViewById(R.id.rank);

        // Oblicz procentową punktację
        int percentage = (int) (((double) points / length) * 100);

        result.setText("Odgadłeś " + points + " z " + length + " pytań!");

        // Ustaw ranking w zależności od procentowej punktacji
        if (percentage >= 90) {
            rank.setText("Ocena: 5");
            rank.setTextColor(ContextCompat.getColor(this, R.color.positive));
        } else if (percentage >= 70) {
            rank.setText("Ocena: 4");
            rank.setTextColor(ContextCompat.getColor(this, R.color.positive));
        } else if (percentage >= 50) {
            rank.setText("Ocena: 3");
            rank.setTextColor(ContextCompat.getColor(this, R.color.average));
        } else {
            rank.setText("Ocena: 2");
            rank.setTextColor(ContextCompat.getColor(this, R.color.negative));
        }

        restart.setOnClickListener(view -> {
            Intent intent = new Intent(Result.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
