package com.example.quiz;

import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
public class QuizManager {
    private final List<Questions> logoQuestion;
    private final int[] allQuestionIndices;
    private int next;
    private final TextView numOfQuestion;
    private final TextView yourQuestion;
    private Questions currentQuestion;
    private final ImageView questionImage;
    private Integer[] availableQuestionCounts;
    private final Button[] answerButtons;
    private int endGame;

    QuizManager(List<Questions> logoQuestion, int[] allQuestionIndices, TextView numOfQuestion, TextView yourQuestion, Button[] answerButtons, ImageView questionImage) {
        this.logoQuestion = logoQuestion;
        this.allQuestionIndices = allQuestionIndices;
        this.yourQuestion = yourQuestion;
        this.numOfQuestion = numOfQuestion;
        this.answerButtons = answerButtons;
        this.questionImage = questionImage;
    }
    public void setEndGame(int endGame) {
        this.endGame = endGame;
    }

    public int getNext() {
        return next;
    }

    public Questions getCurrentQuestion() {
        return currentQuestion;
    }

    public Integer[] getAvailableQuestionCounts() {
        return availableQuestionCounts;
    }

    public int getEndGame() {
        return endGame;
    }

    @SuppressLint("SetTextI18n")
    void createQuestions() {
        next += 1;
        currentQuestion = logoQuestion.get(allQuestionIndices[next]);
        numOfQuestion.setText("Pytanie " + next + " z " + (endGame + 1));
        yourQuestion.setText(currentQuestion.getContentQuestion());
        questionImage.setBackgroundResource(currentQuestion.getImg());
        answerButtons[0].setText(currentQuestion.getAnswerOne());
        answerButtons[1].setText(currentQuestion.getAnswerTwo());
        answerButtons[2].setText(currentQuestion.getAnswerThree());
        answerButtons[3].setText(currentQuestion.getAnswerFour());
    }

    void createNums() {
        availableQuestionCounts = new Integer[logoQuestion.size()];
        for (int i = 0; i < logoQuestion.size(); i++) {
            availableQuestionCounts[i] = i + 1;
        }
    }
}
