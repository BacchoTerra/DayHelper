package com.bacchoterra.dayhelper;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock;

public class MainActivity extends AppCompatActivity {

    //Layout
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private CustomAnalogClock analogClock;

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

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    private void initViews(){
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
}
