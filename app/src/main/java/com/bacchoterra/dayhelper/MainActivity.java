package com.bacchoterra.dayhelper;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bacchoterra.dayhelper.model.FeelingNote;
import com.bacchoterra.dayhelper.viewmodel.FeelingViewModel;
import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Layout
    private ViewGroup rootLayout;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private CustomAnalogClock analogClock;
    private TextView txtMoodDesc;
    private EditText editFeeling;

    //Colors/Feelings
    private int happy;
    private int anger;
    private int sad;
    private int love;
    private int fear;
    private int currentColor;

    //ViewModel
    FeelingViewModel mViewModel;


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
        txtMoodDesc = findViewById(R.id.activity_main_txtMoodDesc);
        analogClock = findViewById(R.id.activity_main_analogClock);
        editFeeling = findViewById(R.id.activity_main_editFeeling);
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
                txtMoodDesc.setText(R.string.we_are_glad_you_are_happy);
                editFeeling.setHint(R.string.happiness_is_always_contagious_save_your_best_moments);
                break;
            case R.id.activity_main_fabSad:
                changeBackgroundColor(sad);
                txtMoodDesc.setText(R.string.it_is_ok_to_be_sad);
                editFeeling.setHint(R.string.sadness_is_sometimes_a_great_friend_we_can_learn_a_lot_from_it_why_blue_today);
                break;
            case R.id.activity_main_fabFear:
                changeBackgroundColor(fear);
                txtMoodDesc.setText(R.string.fear_is_only_natural);
                editFeeling.setHint(R.string.fear_gives_us_the_possibility_to_remember_that_we_are_courageous_what_s_going_on);
                break;
            case R.id.activity_main_fabLove:
                changeBackgroundColor(love);
                txtMoodDesc.setText(R.string.love_is_what_we_all_need);
                editFeeling.setHint(R.string.never_forget_value_current_things_they_are_the_best_what_do_you_value_today);
                break;
            case R.id.activity_main_fabAnger:
                changeBackgroundColor(anger);
                txtMoodDesc.setText(R.string.dont_let_anger_make_your_decisions);
                editFeeling.setHint(R.string.anger_is_momentary_i_guarantee_you_it_is_never_worth_it_what_is_bothering_you);
                break;

        }


    }

    private void initViewModel(){
        mViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(FeelingViewModel.class);
        mViewModel.getAllNotes().observe(this, new Observer<List<FeelingNote>>() {
            @Override
            public void onChanged(List<FeelingNote> feelingNotes) {
                //Update recyclerView;
            }
        });
    }

}
