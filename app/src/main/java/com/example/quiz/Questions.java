package com.example.quiz;

import java.io.Serializable;

public class Questions implements Serializable {
    private final String contentQuestion;
    private final String answerOne;
    private final String answerTwo;
    private final String answerThree;
    private final String answerFour;
    private final String correct;
    private final int img;

    public Questions(String contentQuestion, String answerOne, String answerTwo, String answerThree, String answerFour, String correct, int img) {
        this.contentQuestion = contentQuestion;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerFour = answerFour;
        this.correct = correct;
        this.img = img;
    }

    public String getContentQuestion() {
        return contentQuestion;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public String getCorrect() {
        return correct;
    }

    public int getImg() {
        return img;
    }
}
