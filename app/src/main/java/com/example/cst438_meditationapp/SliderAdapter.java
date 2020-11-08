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

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;

    }

    // Arrays
    public int[] slide_images = {
            R.drawable.eat_icon,
            R.drawable.sleep_icon,
            R.drawable.code_icon,
    };

    public String[] slide_headings = {
            "EAT",
            "SLEEP",
            "CODE"
    };

    public String[] slide_descriptions = {
            "Es gibt viele Variationen der Passages des Lorem Ipsum, aber der Hauptteil erlitt Änderungen in irgendeiner Form, durch Humor oder zufällige Wörter welche nicht einmal ansatzweise glaubwürdig aussehen. Wenn du eine Passage des Lorem Ipsum nutzt, solltest du aufpassen dass in der Mitte des Textes keine ungewollten Wörter stehen. Viele der Generatoren im Internet neigen dazu, vorgefertigte Stücke zu wiederholen",
            "Es gibt viele Variationen der Passages des Lorem Ipsum, aber der Hauptteil erlitt Änderungen in irgendeiner Form, durch Humor oder zufällige Wörter welche nicht einmal ansatzweise glaubwürdig aussehen. Wenn du eine Passage des Lorem Ipsum nutzt, solltest du aufpassen dass in der Mitte des Textes keine ungewollten Wörter stehen. Viele der Generatoren im Internet neigen dazu, vorgefertigte Stücke zu wiederholen",
            "Es gibt viele Variationen der Passages des Lorem Ipsum, aber der Hauptteil erlitt Änderungen in irgendeiner Form, durch Humor oder zufällige Wörter welche nicht einmal ansatzweise glaubwürdig aussehen. Wenn du eine Passage des Lorem Ipsum nutzt, solltest du aufpassen dass in der Mitte des Textes keine ungewollten Wörter stehen. Viele der Generatoren im Internet neigen dazu, vorgefertigte Stücke zu wiederholen"
    };


    @Override
    public int getCount() {
        return slide_headings.length;
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

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descriptions[position]);

        container.addView(view);

        return view;

    }



}
