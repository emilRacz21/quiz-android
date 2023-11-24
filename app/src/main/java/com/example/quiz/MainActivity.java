package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    List<Questions> logoQuestion = new ArrayList<>();
    TextView currentQuestionText;
    ImageView questionImage;
    Button[] answerButtons = new Button[4];
    TextView questionContentText;
    TextView feedbackText;
    LinearLayout hideLayout;
    LinearLayout questAnimation;
    ArrayAdapter<Integer> numOfQuestAdapter;
    int points = 0;
    QuizManager quizManager;
    int[] allQuestions = new int[13];
    Set<Integer> usedIndexes = new HashSet<>();
    String[] selectedOptions = new String[allQuestions.length];
    int currentIndex = 0;
    boolean isClickable;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        currentQuestionText = findViewById(R.id.question);
        questionImage = findViewById(R.id.photo);
        feedbackText = findViewById(R.id.isPositive);
        questionContentText = findViewById(R.id.questNumber);
        hideLayout = findViewById(R.id.hide);
        questAnimation = findViewById(R.id.questAnimation);

        int[] id = {R.id.button1, R.id.button2, R.id.button3, R.id.button4};
        for (int i = 0; i < answerButtons.length ; i++)  answerButtons[i] = findViewById(id[i]);

        //Klasa create.
        quizManager = new QuizManager(logoQuestion, allQuestions, questionContentText, currentQuestionText, answerButtons, questionImage);

        setQuizQuestions();
        makeRandom();
        displayStartDialog();
        }

        //Losowe indexowanie pytań.
        void makeRandom(){
            for (int i = 0; i < logoQuestion.size(); i++) {
                int random;
                do {
                    random = (int) Math.round(Math.random() * (logoQuestion.size() - 1));
                } while (usedIndexes.contains(random));
                usedIndexes.add(random);
                allQuestions[i] = random;
            }
        }
        //Utwórz początkowy dialog do wybrania ilosci pytań.
        void displayStartDialog() {
            quizManager.createNums();
            hideLayout.setVisibility(View.GONE);
            Dialog dialog = new Dialog(this);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.start);
            LinearLayout animation = dialog.findViewById(R.id.animateStartDialog);
            animate(Techniques.Bounce, 1000, 1,animation);
            Spinner setNums = dialog.findViewById(R.id.spinner);
            makeAdapter(setNums);
            Button begin = dialog.findViewById(R.id.startGame);
            begin.setOnClickListener(view -> {
                startGame();
                dialog.dismiss();
            });
            dialog.show();
        }
        //Utwórz adapter z dostępną liczbą pytań.
        void makeAdapter(Spinner setNums) {
            numOfQuestAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quizManager.getAvailableQuestionCounts());
            numOfQuestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            setNums.setAdapter(numOfQuestAdapter);
            setNums.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    numOfQuestAdapter.notifyDataSetChanged();
                    quizManager.setEndGame(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        }
        //Rozpocznij grę.
        void startGame() {
            hideLayout.setVisibility(View.VISIBLE);
            animate(Techniques.Bounce, 1000, 1,questAnimation);
            quizManager.createQuestions();
            isClickable = true;
            setStartColors();
            Handler next  = new Handler();
            View.OnClickListener clickQuest = v -> {
                if(isClickable) clickedButton(v, next);
                isClickable = false;
            };
            for (Button button : answerButtons) button.setOnClickListener(clickQuest);
        }
        //Ustaw kolory początkowe gdy juz klikne odpowiedź.
        void setStartColors(){
            for (Button button : answerButtons) button.setBackgroundColor(ContextCompat.getColor(this, R.color.button));
            feedbackText.setText(R.string.empty);
        }

        //Operacja po kliknieciu przycisku.
        void clickedButton(View v, Handler next){
            Button clicked = findViewById(v.getId());
            selectedOptions[currentIndex] = String.valueOf(clicked.getText());
            if (clicked.getText().equals(quizManager.getCurrentQuestion().getCorrect())) {
                setPositiveOrNegativeColor(R.color.positive, R.string.poprawna_odpowiedz, clicked);
                points += 1;
            } else {
                setPositiveOrNegativeColor(R.color.negative, R.string.zla_odpowiedz, clicked);
            }
            if (quizManager.getNext() > quizManager.getEndGame()) {
                yourResult();
            } else {
                clicked.setClickable(false);
                next.postDelayed(() -> {
                    currentIndex += 1;
                    startGame();
                }, 1000);
            }
        }
        //Ustaw kolory w zależności jaka jest odpowiedź.
        void setPositiveOrNegativeColor(int color, int string, Button clicked){
            clicked.setBackgroundColor(ContextCompat.getColor(this, color));
            feedbackText.setText(string);
            feedbackText.setTextColor(ContextCompat.getColor(this, color));
        }
        //Przeslij do nowego activity swoje wyniki gry!.
        void yourResult() {
            Intent sendIntent = new Intent(MainActivity.this, Result.class);
            sendIntent.putExtra("length", quizManager.getEndGame()+1);
            sendIntent.putExtra("points",points);
            sendIntent.putExtra("answers", selectedOptions);
            sendIntent.putExtra("logoQuestion", (Serializable) logoQuestion);
            sendIntent.putExtra("allQuestions", allQuestions);
            startActivity(sendIntent);
        }
        //Ustaw pytania do quizu.
        void setQuizQuestions() {
            logoQuestion.clear();
            logoQuestion.add(new Questions("Co to za logo?",
                    "Fiat", "Mercedes-Benz", "Bucik", "Opel",
                    "Mercedes-Benz",R.drawable.mercedes_logo));

            logoQuestion.add(new Questions("Co to za marka?",
                    "Puma", "Adidas", "Nike", "Reebok",
                    "Nike",R.drawable.nike_logo));

            logoQuestion.add(new Questions("Co to za logo?",
                    "Google", "Microsoft", "Apple", "Samsung",
                    "Apple",R.drawable.apple_logo));

            logoQuestion.add(new Questions("Co to za logo?",
                    "Chevrolet", "Honda", "Ford", "Toyota",
                    "Toyota",R.drawable.toyota_logo));

            logoQuestion.add(new Questions("Co to za logo?",
                    "Sprite", "Pepsi", "Coca-Cola", "Fanta",
                    "Coca-Cola",R.drawable.coca_cola_logo));
            logoQuestion.add(new Questions("Co to za logo?",
                    "McDonald's", "Burger King", "KFC", "Subway",
                    "McDonald's", R.drawable.mcdonalds_logo));
            logoQuestion.add(new Questions("Co to za logo?",
                    "Amazon", "eBay", "Alibaba", "Walmart",
                    "Amazon", R.drawable.amazon_logo));
            logoQuestion.add(new Questions("Co to za logo?",
                    "Google", "Microsoft", "Apple", "Facebook",
                    "Google", R.drawable.google_logo));

            logoQuestion.add(new Questions("Co to za logo?",
                    "IBM", "Intel", "AMD", "Microsoft",
                    "Microsoft", R.drawable.microsoft_logo));

            logoQuestion.add(new Questions("Co to za logo?",
                    "Nike", "Under Armour", "Puma", "Adidas",
                    "Adidas", R.drawable.adidas_logo));

            logoQuestion.add(new Questions("Co to za logo?",
                    "Reebok", "Asics", "Under Armour", "Fila",
                    "Under Armour", R.drawable.underarmour_logo));
            logoQuestion.add(new Questions("Co to za logo?",
                    "BMW", "Mercedes-Benz", "Audi", "Lexus",
                    "Audi", R.drawable.audi_logo));
        }
        //Zrób animacje.
        void animate(Techniques techniques, int delay, int repeat, View view){
            YoYo.with(techniques).delay(delay).repeat(repeat).playOn(view);
        }
    }