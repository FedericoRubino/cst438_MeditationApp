package com.example.cst438_meditationapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class Meditation1Screens extends AppCompatActivity {

    public static final String EXTRA = "ONBOARD EXTRA";

    private ViewPager mViewPager;
    private Button doneBtn;
    private Timer timer;
    private int page = 0;

    private Meditation1Adapter meditationAdapter;
    private MediaPlayer meditation1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation1_screens);


        ConstraintLayout constraintLayout = findViewById(R.id.layout);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        meditation1 = MediaPlayer.create(this, R.raw.meditation1);


        mViewPager = findViewById(R.id.slideViewPager);
        doneBtn = findViewById(R.id.doneBtn);

        meditationAdapter = new Meditation1Adapter(this);
        mViewPager.setAdapter(meditationAdapter);

        mViewPager.addOnPageChangeListener(viewListener);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (page == 14) {
                    timer.cancel();
                    musicStop();
                }
                if (page == 0) {
                    musicStart();
                }
                mViewPager.setCurrentItem(page++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 7000, 7000);


    }

    public void musicStart(){
        meditation1.start();
    }

    public void musicStop()
    {
        meditation1.stop();
        meditation1 = MediaPlayer.create(this, R.raw.meditation1);
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

    public void goToHomePage(View view){
        Intent intent = Home.getIntent(this, "");
        startActivity(intent);
    }


    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, Meditation1Screens.class);
        intent.putExtra(EXTRA, val);
        return intent;
    }
}