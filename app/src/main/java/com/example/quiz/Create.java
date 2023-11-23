package com.example.quiz;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Create {
    List<Pytania> pytanias;
    Integer[] tab;
    int next;
    TextView questNumber;
    TextView question;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Pytania questions;
    ImageView photo;
    Integer[] quests;
    int endGame;
    Create( List<Pytania> pytanias,  Integer[] tab,TextView questNumber,TextView question, Button button1, Button button2, Button button3, Button button4 ,ImageView photo){
        this.pytanias = pytanias;
        this.tab = tab;
        this.question = question;
        this.questNumber = questNumber;
        this.button1 = button1;
        this.button2 = button2;
        this.button3 = button3;
        this.button4 = button4;
        this.photo= photo;
    }
        void createPytania(){
        questions= pytanias.get(tab[next]);
        next+=1;
        questNumber.setText("Pytanie "+ next+ " z "+ (endGame+1));
        question.setText(questions.getTresc());
        button1.setText(questions.getOdpowiedz1());
        button2.setText(questions.getOdpowiedz2());
        button3.setText(questions.getOdpowiedz3());
        button4.setText(questions.getOdpowiedz4());
        photo.setBackgroundResource(questions.getImg());
    }
    void createNums(){
        quests= new Integer[pytanias.size()];
        for (int i = 0; i < pytanias.size(); i++) {
            quests[i] = i + 1;
        }
    }
}


