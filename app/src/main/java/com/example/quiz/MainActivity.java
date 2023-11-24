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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    List<Pytania> pytanias = new ArrayList<>();
    TextView question;
    ImageView photo;
    Button[] buttons = new Button[4];
    TextView questNumber;
    TextView postiveText;
    LinearLayout hide;
    ArrayAdapter<Integer> adapter;
    int points = 0;
    Create create;
    int[] allQuestions = new int[12];
    Set<Integer> usedIndexes = new HashSet<>();
    String[] clickedOptions = new String[allQuestions.length];
    int index = 0;
    boolean isClicable;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        question = findViewById(R.id.question);
        photo = findViewById(R.id.photo);
        postiveText = findViewById(R.id.isPositive);
        questNumber = findViewById(R.id.questNumber);
        hide = findViewById(R.id.hide);

        int[] id = {R.id.button1, R.id.button2, R.id.button3, R.id.button4};
        for (int i = 0 ; i < buttons.length ; i++)  buttons[i] = findViewById(id[i]);

        //Klasa create.
        create= new Create(pytanias, allQuestions, questNumber, question, buttons, photo);

        setPytaniaLogo();
        makeRandom();
        displayDialog();
        }

        //Losowe indexowanie pytań.
        void makeRandom(){
            for (int i = 0; i < pytanias.size(); i++) {
                int random;
                do {
                    random = (int) Math.round(Math.random() * (pytanias.size() - 1));
                } while (usedIndexes.contains(random));
                usedIndexes.add(random);
                allQuestions[i] = random;
            }
        }
        //Utwórz początkowy dialog do wybrania ilosci pytań.
        void displayDialog() {
            create.createNums();
            hide.setVisibility(View.GONE);
            Dialog dialog = new Dialog(this);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.start);
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
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, create.quests);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            setNums.setAdapter(adapter);
            setNums.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    adapter.notifyDataSetChanged();
                    create.endGame = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        }
        //Rozpocznij grę.
        void startGame() {
            hide.setVisibility(View.VISIBLE);
            create.createPytania();
            isClicable= true;
            setStartColors();
            Handler next  = new Handler();
            View.OnClickListener clickQuest = v -> {
                if(isClicable) clickedButton(v, next);
                isClicable = false;
            };
            for (Button button : buttons) button.setOnClickListener(clickQuest);
        }
        //Ustaw kolory początkowe gdy juz klikne odpowiedź.
        void setStartColors(){
            for (Button button : buttons) button.setBackgroundColor(ContextCompat.getColor(this, R.color.button));
            postiveText.setText(R.string.empty);
        }

        //Operacja po kliknieciu przycisku.
        void clickedButton(View v, Handler next){
            Button clicked = findViewById(v.getId());
            clickedOptions[index] = String.valueOf(clicked.getText());
            if (clicked.getText().equals(create.questions.getPoprawna())) {
                setPositiveOrNegativeColor(R.color.positive, R.string.poprawna_odpowiedz, clicked);
                points += 1;
            } else {
                setPositiveOrNegativeColor(R.color.negative, R.string.zla_odpowiedz, clicked);
            }
            if (create.next > create.endGame) {
                yourResult();
            } else {
                clicked.setClickable(false);
                next.postDelayed(() -> {
                    index += 1;
                    startGame();
                }, 1000);
            }
        }
        //Ustaw kolory w zależności jaka jest odpowiedź.
        void setPositiveOrNegativeColor(int color, int string, Button clicked){
            clicked.setBackgroundColor(ContextCompat.getColor(this, color));
            postiveText.setText(string);
            postiveText.setTextColor(ContextCompat.getColor(this, color));
        }
        //Przeslij do nowego activity swoje wyniki gry!.
        void yourResult() {
            Intent sendIntent = new Intent(MainActivity.this, Result.class);
            sendIntent.putExtra("length", create.endGame+1);
            sendIntent.putExtra("points",points);
            sendIntent.putExtra("answers",clickedOptions);
            sendIntent.putExtra("pytanias", (Serializable) pytanias);
            sendIntent.putExtra("allQuestions", allQuestions);
            startActivity(sendIntent);
        }
        //Ustaw pytania do quizu.
        void setPytaniaLogo() {
            pytanias.clear();
            pytanias.add(new Pytania("Co to za logo?",
                    "Fiat", "Mercedes-Benz", "Bucik", "Opel",
                    "Mercedes-Benz",R.drawable.mercedes_logo));

            pytanias.add(new Pytania("Co to za marka?",
                    "Puma", "Adidas", "Nike", "Reebok",
                    "Nike",R.drawable.nike_logo));

            pytanias.add(new Pytania("Co to za logo?",
                    "Google", "Microsoft", "Apple", "Samsung",
                    "Apple",R.drawable.apple_logo));

            pytanias.add(new Pytania("Co to za logo?",
                    "Chevrolet", "Honda", "Ford", "Toyota",
                    "Toyota",R.drawable.toyota_logo));

            pytanias.add(new Pytania("Co to za logo?",
                    "Sprite", "Pepsi", "Coca-Cola", "Fanta",
                    "Coca-Cola",R.drawable.coca_cola_logo));
            pytanias.add(new Pytania("Co to za logo?",
                    "McDonald's", "Burger King", "KFC", "Subway",
                    "McDonald's", R.drawable.mcdonalds_logo));
            pytanias.add(new Pytania("Co to za logo?",
                    "Amazon", "eBay", "Alibaba", "Walmart",
                    "Amazon", R.drawable.amazon_logo));
            pytanias.add(new Pytania("Co to za logo?",
                    "Google", "Microsoft", "Apple", "Facebook",
                    "Google", R.drawable.google_logo));

            pytanias.add(new Pytania("Co to za logo?",
                    "IBM", "Intel", "AMD", "Microsoft",
                    "Microsoft", R.drawable.microsoft_logo));

            pytanias.add(new Pytania("Co to za logo?",
                    "Nike", "Under Armour", "Puma", "Adidas",
                    "Adidas", R.drawable.adidas_logo));

            pytanias.add(new Pytania("Co to za logo?",
                    "Reebok", "Asics", "Under Armour", "Fila",
                    "Under Armour", R.drawable.underarmour_logo));
        }
    }