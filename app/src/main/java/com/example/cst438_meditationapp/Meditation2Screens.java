package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class Meditation2Screens extends AppCompatActivity {
    public static final String EXTRA = "ONBOARD EXTRA";

    private ViewPager mViewPager;
    private Button doneBtn;
    Timer timer;
    private int page = 0;

    private MeditationAdapter mediationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation2_screens);

        mViewPager = findViewById(R.id.slideViewPager);
        doneBtn = findViewById(R.id.doneBtn);

        mediationAdapter = new MeditationAdapter(this);

        mViewPager.setAdapter(mediationAdapter);

        mViewPager.addOnPageChangeListener(viewListener);

        pageSwitcher(4);

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position != 13){
                doneBtn.setEnabled(false);
                doneBtn.setVisibility(View.INVISIBLE);
            } else {
                doneBtn.setEnabled(true);
                doneBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 4000);
        // delay
        // in
        // milliseconds
    }

    class RemindTask extends TimerTask {
        @Override
        public void run() {
            if (page == 14) { // number of pages 14
                timer.cancel();
            } else {
                mViewPager.setCurrentItem(page++);
            }

        }
    }


    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, Meditation2Screens.class);
        intent.putExtra(EXTRA, val);
        return intent;
    }
}