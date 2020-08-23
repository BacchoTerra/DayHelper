package com.bacchoterra.dayhelper;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock;

public class MainActivity extends AppCompatActivity {

    //Layout
    private ViewGroup rootLayout;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private CustomAnalogClock analogClock;

    //Colors/Feelings
    private int happy;
    private int anger;
    private int sad;
    private int love;
    private int fear;

    private int currentColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        initViews();
        initDrawerLayout();
        initAnalogClock();
        initColors();

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    private void initViews() {
        rootLayout = findViewById(R.id.activity_main_rootLayout);
        toolbar = findViewById(R.id.activity_main_toolbar);
        drawerLayout = findViewById(R.id.activity_main_drawerLayout);
        analogClock = findViewById(R.id.activity_main_analogClock);
    }

    private void initAnalogClock() {

        analogClock.setScale(0.7f);
        analogClock.setAutoUpdate(true);

    }

    private void initDrawerLayout() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initColors() {

        happy = getResources().getColor(R.color.happy);
        sad = getResources().getColor(R.color.sad);
        fear = getResources().getColor(R.color.fear);
        love = getResources().getColor(R.color.love);
        anger = getResources().getColor(R.color.anger);

        Drawable background = rootLayout.getBackground();
        if (background instanceof ColorDrawable)
            currentColor = ((ColorDrawable) background).getColor();

    }

    private void changeBackgroundColor(final int colorTo) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), currentColor, colorTo);
        colorAnimation.setDuration(600); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                rootLayout.setBackgroundColor((int) animator.getAnimatedValue());
                toolbar.setBackgroundColor((int) animator.getAnimatedValue());
                currentColor = colorTo;


            }

        });
        colorAnimation.start();
    }

    public void fabChoice(View view) {

        switch (view.getId()) {

            case R.id.activity_main_fabHappy:
                changeBackgroundColor(happy);
                break;
            case R.id.activity_main_fabSad:
                changeBackgroundColor(sad);
                break;
            case R.id.activity_main_fabFear:
                changeBackgroundColor(fear);
                break;
            case R.id.activity_main_fabLove:
                changeBackgroundColor(love);
                break;
            case R.id.activity_main_fabAnger:
                changeBackgroundColor(anger);
                break;

        }


    }
}
