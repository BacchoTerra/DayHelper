package com.bacchoterra.dayhelper.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bacchoterra.dayhelper.R;
import com.bacchoterra.dayhelper.model.FeelingNote;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class NoteActivity extends AppCompatActivity {

    //Layout components
    private Toolbar toolbar;
    private TextView txtNote;
    private BottomNavigationViewEx bottomNavigationViewEx;

    //Model
    FeelingNote note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        init();
    }

    private void init() {
        initViews();
        populateLayout();
        initToolbar();
        initBottomNavView();
    }

    private void initViews() {

        toolbar = findViewById(R.id.activity_note_toolbar);
        txtNote = findViewById(R.id.activity_note_txtNote);
        txtNote.setMovementMethod(new ScrollingMovementMethod());
        bottomNavigationViewEx = findViewById(R.id.activity_note_bottomNavView);


    }

    private void initToolbar() {

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(note.getTitle());
        }

    }

    private void initBottomNavView() {
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);
        bottomNavigationViewEx.setItemBackground(2, android.R.color.holo_orange_light);


        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.btm_nav_note_edit:
                        Toast.makeText(NoteActivity.this, "edit", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.btm_nav_note_share:
                        Toast.makeText(NoteActivity.this, "share", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.btm_nav_note_exclude:
                        createAlertDialog();
                        return true;
                }
                ;


                return false;
            }
        });

    }

    private void populateLayout() {


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            note = (FeelingNote) bundle.get(MainActivity.GLOBAL_NOTE_KEY);
            txtNote.setText(note.getNote());

            switch (note.getFeeling()) {

                case FeelingNote.HAPPY:
                    bottomNavigationViewEx.setItemBackground(0, R.color.happy);
                    bottomNavigationViewEx.setItemBackground(1, R.color.happy);
                    break;

                case FeelingNote.SAD:
                    bottomNavigationViewEx.setItemBackground(0, R.color.sad);
                    bottomNavigationViewEx.setItemBackground(1, R.color.sad);
                    break;

                case FeelingNote.FEAR:
                    bottomNavigationViewEx.setItemBackground(0, R.color.fear);
                    bottomNavigationViewEx.setItemBackground(1, R.color.fear);
                    break;

                case FeelingNote.LOVE:
                    bottomNavigationViewEx.setItemBackground(0, R.color.love);
                    bottomNavigationViewEx.setItemBackground(1, R.color.love);
                    break;

                case FeelingNote.ANGER:
                    bottomNavigationViewEx.setItemBackground(0, R.color.anger);
                    bottomNavigationViewEx.setItemBackground(1, R.color.anger);
                    break;


            }
        }


    }

    private void createAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_note);
        builder.setMessage("Are you sure you want to delete this note? It can't be undone");
        builder.setCancelable(true);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent();
                intent.putExtra(MainActivity.GLOBAL_NOTE_KEY, note);
                setResult(RESULT_OK, intent);
                finish();


            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        Button pBtn = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pBtn.setTextColor(getResources().getColor(R.color.red));
        Button nBtn = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nBtn.setTextColor(getResources().getColor(R.color.transparentBlack));

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
