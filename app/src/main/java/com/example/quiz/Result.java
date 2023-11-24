package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

public class Result extends AppCompatActivity {

    TextView resultTextView;
    Button restartButton;
    TextView rankTextView;
    String[] userAnswers;
    List<Questions> questionsList;
    ListView resultListView;
    int[] correctAnswers;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultListView = findViewById(R.id.resultList);
        questionsList = (List<Questions>) getIntent().getSerializableExtra("logoQuestion");
        Intent getIntent = getIntent();
        correctAnswers = getIntent.getIntArrayExtra("allQuestions");
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        int userPoints = extras.getInt("points");
        int totalQuestions = extras.getInt("length");
        userAnswers = extras.getStringArray("answers");

        resultTextView = findViewById(R.id.result);
        restartButton = findViewById(R.id.restart);
        rankTextView = findViewById(R.id.rank);

        // Calculate the percentage score
        int percentage = (int) (((double) userPoints / totalQuestions) * 100);

        resultTextView.setText("Odgadłeś " + userPoints + " z " + totalQuestions + " pytań!");

        setRanking(percentage);
        System.out.println(questionsList.get(0).getCorrect());
        CustomAdapter customAdapter = new CustomAdapter(totalQuestions, questionsList, correctAnswers, userAnswers, this);
        resultListView.setAdapter(customAdapter);

        // Restart the game
        restartButton.setOnClickListener(view -> {
            Intent intent = new Intent(Result.this, MainActivity.class);
            startActivity(intent);
        });
    }

    // Set the rank based on the percentage score
    void setRanking(int percentage) {
        if (percentage >= 90) {
            rankTextView.setText(R.string.grade5);
            rankTextView.setTextColor(ContextCompat.getColor(this, R.color.positive));
        } else if (percentage >= 70) {
            rankTextView.setText(R.string.grade4);
            rankTextView.setTextColor(ContextCompat.getColor(this, R.color.positive));
        } else if (percentage >= 50) {
            rankTextView.setText(R.string.grade3);
            rankTextView.setTextColor(ContextCompat.getColor(this, R.color.average));
        } else {
            rankTextView.setText(R.string.grade2);
            rankTextView.setTextColor(ContextCompat.getColor(this, R.color.negative));
        }
    }
}
