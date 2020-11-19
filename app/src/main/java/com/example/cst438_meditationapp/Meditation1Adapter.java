package com.example.cst438_meditationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class Meditation1Adapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public Meditation1Adapter(Context context){
        this.context = context;

    }

    public String[] meditation_descriptions = {
            "Find a comfortable position....",
            "Either sitting or laying down.....",
            "Close your eyes....",
            "Begin to slow your breathing.....",
            "Outside anxieties might show up but let them float away like a boat on smooth water....",
            "Envision yourself at a lake in the forest....",
            "No one else is around....",
            "It is quiet except for the chirping of birds and the whisper of wind in the trees....",
            "You walk to the shore....",
            "The shore is sandy and you massage your feet in the sand…",
            "There is a wooded path by the shore….",
            "You meander up the path slowly noticing details like the moss on the trees....",
            "As the path widens you come to a hammock by your cabin....",
            "You lay in the hammock which rocks slowly back and forth in the breeze…",
            "You are cocooned looking at the tree canopy above you....",
            "You realize your anxieties have faded into the background....",
            "You are relaxed and the rocking has released the tension in your muscles....",
            "Slowly you get out of the hammock and continue with your day....",
            "Repeat this mediation when you need a break from the hectic pace of your world."

};


    @Override
    public int getCount() {
        return meditation_descriptions.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.meditation_screen_layout, container, false);

        TextView slideDescription =(TextView) view.findViewById(R.id.meditation_description);

        slideDescription.setText(meditation_descriptions[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout) object);
    }


}