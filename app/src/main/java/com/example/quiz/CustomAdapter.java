package com.example.quiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

public class CustomAdapter  extends BaseAdapter {
    List<Questions> questions;
    int[] allQuestions;
    String[] clickedOptions;
    LayoutInflater inflater;
    Context context;
    int adapterLength;
    CustomAdapter(int adapterLength, List<Questions> questions, int[] allQuestions, String[] clickedOptions, Context context){
        this.questions = questions;
        this.allQuestions = allQuestions;
        this.clickedOptions = clickedOptions;
        this.context = context;
        this.adapterLength = adapterLength;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return adapterLength;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override

    //Ustawiam layout dla mojego custom adaptera.
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.customadapter, parent, false);

        Questions myQuest = questions.get(allQuestions[position+1]);
        TextView titleQuest = convertView.findViewById(R.id.titleQuest);
        titleQuest.setText((position+1)+". "+ myQuest.getContentQuestion());

        TextView yourAnswer = convertView.findViewById(R.id.yourChoice);
        yourAnswer.setText(clickedOptions[position]);

        TextView goodChoice = convertView.findViewById(R.id.goodChoice);
        goodChoice.setText(myQuest.getCorrect());

        setPositiveOrNegativeColor(yourAnswer, myQuest, position);

        ImageView imageResult = convertView.findViewById(R.id.imageResult);
        imageResult.setBackgroundResource(myQuest.getImg());

        return convertView;
    }

    //Ustaw kolor wybranej przez ciebie odpowiedzi.
    void setPositiveOrNegativeColor(TextView yourAnswer, Questions questions, int position){
        yourAnswer.setTextColor(questions.getCorrect().equals(clickedOptions[position]) ?
                ContextCompat.getColor(context, R.color.positive) :
                ContextCompat.getColor(context, R.color.negative));
    }
}
