package com.example.cst438_meditationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class MeditationAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public MeditationAdapter(Context context){
        this.context = context;

    }

    public String[] slide_descriptions = {
            "@string/begin_a_two_minute_breathing_exercise_to_recenter_and_refocus",
            "Find a comfortable position....",
            "Lengthen your spine....",
            "Put your hands on your belly....",
            "Begin to slow your breathing....",
            "Breath in for a count of four through your nose....",
            "Slowly breath out for a count of four through your mouth....",
            "Breath in for a count of four through your nose....",
            "Slowly breath out for a count of four through your mouth....",
            "Breath in for a count of four through your nose....",
            "Slowly breath out for a count of four through your mouth....",
            "Breath in for a count of four through your nose....",
            "Slowly breath out for a count of four through your mouth....",
            "Great job!\n Repeat this mediation thoughout the day as you begin to feel overwhelmed or stressed."
    };


    @Override
    public int getCount() {
        return slide_descriptions.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView =(ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading =(TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription =(TextView) view.findViewById(R.id.slide_description);

        slideDescription.setText(slide_descriptions[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout) object);
    }


}
