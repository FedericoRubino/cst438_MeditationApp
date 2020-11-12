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
            R.drawable.mediations_icon,
            R.drawable.photos_icon,
            R.drawable.connect_icon,
    };

    public String[] slide_headings = {
            "GUIDED MEDITATIONS",
            "SHARE PHOTOS",
            "CONNECT"
    };

    public String[] slide_descriptions = {
            "Use guided meditations to re-center and refocus. Tune out the noise of the outside world for a couple of minutes a day and re-center yourself. Find a momement of calm. Work on developing a restful mind instead of a restless one.",
            "Take photos of things that bring you joy and calm. Share these photos with others so that they can share in your relaxation and mindfulness. Comment on others photos and create meaningful conversations about wellness.",
            "Connect with like minded individuals and find others who share the same interest in practicing self-care and mindfulness to reduce stress and anxiety."
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

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout) object);
    }


}
