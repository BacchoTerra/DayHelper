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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bacchoterra.dayhelper.R;
import com.bacchoterra.dayhelper.model.FeelingNote;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class NoteActivity extends AppCompatActivity {

    //Layout components
    private Toolbar toolbar;
    private TextView txtNote;
    private EditText editTitle;
    private EditText editNote;
    private FloatingActionButton fabSaveEdit;
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
        initClickListeners();
    }

    private void initViews() {

        toolbar = findViewById(R.id.activity_note_toolbar);
        txtNote = findViewById(R.id.activity_note_txtNote);
        txtNote.setMovementMethod(new ScrollingMovementMethod());
        editNote = findViewById(R.id.activity_note_editNote);
        editTitle = findViewById(R.id.activity_note_editTitle);
        fabSaveEdit = findViewById(R.id.activity_note_fabSaveEdit);
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

                        txtNote.setVisibility(txtNote.getVisibility() == View.VISIBLE? View.GONE:View.VISIBLE);
                        toolbar.setVisibility(toolbar.getVisibility() == View.VISIBLE? View.INVISIBLE:View.VISIBLE);
                        editNote.setVisibility(editNote.getVisibility() == View.VISIBLE? View.GONE:View.VISIBLE);
                        fabSaveEdit.setVisibility(editNote.getVisibility() == View.VISIBLE?View.VISIBLE:View.GONE);
                        editTitle.setVisibility(editNote.getVisibility() == View.VISIBLE?View.VISIBLE:View.GONE);

                        return true;
                    case R.id.btm_nav_note_share:
                        Toast.makeText(NoteActivity.this, "share", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.btm_nav_note_exclude:
                        createDeleteAlertDialog();
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
            editTitle.setText(note.getTitle());
            editNote.setText(note.getNote());

            switch (note.getFeeling()) {

                case FeelingNote.HAPPY:
                    bottomNavigationViewEx.setItemBackground(0, R.color.happy);
                    bottomNavigationViewEx.setItemBackground(1, R.color.happy);
                    fabSaveEdit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.happy)));
                    break;

                case FeelingNote.SAD:
                    bottomNavigationViewEx.setItemBackground(0, R.color.sad);
                    bottomNavigationViewEx.setItemBackground(1, R.color.sad);
                    fabSaveEdit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.sad)));

                    break;

                case FeelingNote.FEAR:
                    bottomNavigationViewEx.setItemBackground(0, R.color.fear);
                    bottomNavigationViewEx.setItemBackground(1, R.color.fear);
                    fabSaveEdit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.fear)));
                    break;

                case FeelingNote.LOVE:
                    bottomNavigationViewEx.setItemBackground(0, R.color.love);
                    bottomNavigationViewEx.setItemBackground(1, R.color.love);
                    fabSaveEdit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.love)));
                    break;

                case FeelingNote.ANGER:
                    bottomNavigationViewEx.setItemBackground(0, R.color.anger);
                    bottomNavigationViewEx.setItemBackground(1, R.color.anger);
                    fabSaveEdit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.anger)));
                    break;


            }


        }


    }

    private void createDeleteAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_note);
        builder.setMessage("Are you sure you want to delete this note? It can't be undone");
        builder.setCancelable(true);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent();
                intent.putExtra(MainActivity.GLOBAL_NOTE_KEY, note);
                MainActivity.ACTION_MADE = MainActivity.ACTION_DELETE;
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

    private void initClickListeners(){

        fabSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editNote.getText().toString().equals(note.getNote()) || !editTitle.getText().toString().equals(note.getTitle())){

                    note.setTitle(editTitle.getText().toString());
                    note.setNote(editNote.getText().toString());

                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.GLOBAL_NOTE_KEY,note);
                    MainActivity.ACTION_MADE = MainActivity.ACTION_EDIT;
                    setResult(RESULT_OK,intent);
                    Snackbar.make(toolbar,R.string.note_updated,Snackbar.LENGTH_LONG).show();
                    editTitle.setVisibility(View.GONE);
                    editNote.setVisibility(View.GONE);
                    toolbar.setVisibility(View.VISIBLE);
                    txtNote.setVisibility(View.VISIBLE);
                    fabSaveEdit.setVisibility(View.GONE);

                    txtNote.setText(note.getNote());
                    toolbar.setTitle(note.getTitle());

                }else {
                    Toast.makeText(NoteActivity.this, R.string.no_differences_detected, Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
