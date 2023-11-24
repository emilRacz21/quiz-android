package com.example.quiz;
import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Create {
    List<Pytania> pytanias;
    int[] tab;
    int next;
    TextView questNumber;
    TextView question;
    Pytania questions;
    ImageView photo;
    Integer[] quests;
    Button[] buttons;
    int endGame;
    Create( List<Pytania> pytanias,  int[] tab,TextView questNumber,TextView question,Button[] buttons ,ImageView photo){
        this.pytanias = pytanias;
        this.tab = tab;
        this.question = question;
        this.questNumber = questNumber;
        this.buttons = buttons;
        this.photo= photo;
    }
        //Utwórz pytanie.
        @SuppressLint("SetTextI18n")
        void createPytania(){
            next+=1;
            questions= pytanias.get(tab[next]);
            questNumber.setText("Pytanie "+ next+ " z "+ (endGame+1));
            question.setText(questions.getTresc());
            photo.setBackgroundResource(questions.getImg());
            buttons[0].setText(questions.getOdpowiedz1());
            buttons[1].setText(questions.getOdpowiedz2());
            buttons[2].setText(questions.getOdpowiedz3());
            buttons[3].setText(questions.getOdpowiedz4());
    }

    //Utwórz liczby indexy z pytaniami dostępnymi w grze.
    void createNums(){
        quests= new Integer[pytanias.size()];
        for (int i = 0; i < pytanias.size(); i++) {
            quests[i] = i + 1;
        }
    }
}


