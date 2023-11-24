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
    List<Pytania> questions;
    int[] allQuestions;
    String[] clickedOptions;
    LayoutInflater inflater;
    Context context;
    CustomAdapter(List<Pytania> questions, int[] allQuestions, String[] clickedOptions, Context context){
        this.questions = questions;
        this.allQuestions = allQuestions;
        this.clickedOptions = clickedOptions;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return clickedOptions.length;
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.customadapter, parent, false);
        }

        Pytania pytania = questions.get(allQuestions[position]);
        TextView titleQuest = convertView.findViewById(R.id.titleQuest);
        titleQuest.setText((position+1)+". "+pytania.getTresc());

        TextView yourAnswer = convertView.findViewById(R.id.yourChoice);
        yourAnswer.setText(clickedOptions[position]);
        yourAnswer.setTextColor(ContextCompat.getColor(context,R.color.button));

        TextView goodChoice = convertView.findViewById(R.id.goodChoice);
        goodChoice.setText(pytania.getPoprawna());
        goodChoice.setTextColor(ContextCompat.getColor(context,R.color.positive));

        ImageView imageResult = convertView.findViewById(R.id.imageResult);
        imageResult.setBackgroundResource(pytania.getImg());

        return convertView;
    }

}
