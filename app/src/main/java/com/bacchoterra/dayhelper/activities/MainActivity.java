package com.bacchoterra.dayhelper.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bacchoterra.dayhelper.R;
import com.bacchoterra.dayhelper.adapter.DrawerNoteAdapter;
import com.bacchoterra.dayhelper.model.FeelingNote;
import com.bacchoterra.dayhelper.viewmodel.FeelingViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    //Layout
    private ViewGroup rootLayout;
    private Toolbar toolbar;
    private AdvanceDrawerLayout drawerLayout;
    private View headerView;
    private CustomAnalogClock analogClock;
    private TextView txtMoodDesc;
    private EditText editFeeling;
    private RecyclerView recyclerView;

    //Colors/Feelings
    private int happyColor;
    private int angerColor;
    private int sadColor;
    private int loveColor;
    private int fearColor;
    private int currentColor;

    //ViewModel
    private FeelingViewModel mViewModel;

    //Adapter
    private DrawerNoteAdapter feelingNotesAdapter;

    //Model
    FeelingNote note = new FeelingNote();
    int noteFeeling = 450;

    //Dialog
    AlertDialog alertDialog;
    View dialogLayout;
    TextInputEditText editTitle;
    TextInputLayout dialogInputLayout;


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
        initViewModel();
        initRecyclerView();

    }

    private void initViews() {
        rootLayout = findViewById(R.id.activity_main_rootLayout);
        toolbar = findViewById(R.id.activity_main_toolbar);
        drawerLayout = findViewById(R.id.activity_main_drawerLayout);
        headerView = findViewById(R.id.activity_main_header_view);
        txtMoodDesc = findViewById(R.id.activity_main_txtMoodDesc);
        analogClock = findViewById(R.id.activity_main_analogClock);
        editFeeling = findViewById(R.id.activity_main_editFeeling);
        recyclerView = findViewById(R.id.activity_main_recyclerView);
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
        drawerLayout.useCustomBehavior(GravityCompat.START);
    }

    private void initColors() {

        happyColor = getResources().getColor(R.color.happy);
        sadColor = getResources().getColor(R.color.sad);
        fearColor = getResources().getColor(R.color.fear);
        loveColor = getResources().getColor(R.color.love);
        angerColor = getResources().getColor(R.color.anger);

        Drawable background = rootLayout.getBackground();
        if (background instanceof ColorDrawable)
            currentColor = ((ColorDrawable) background).getColor();

    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(FeelingViewModel.class);
        mViewModel.getAllNotes().observe(this, new Observer<List<FeelingNote>>() {
            @Override
            public void onChanged(List<FeelingNote> feelingNotes) {
                feelingNotesAdapter.submitList(feelingNotes);
            }
        });
    }

    private void initRecyclerView() {

        feelingNotesAdapter = new DrawerNoteAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(feelingNotesAdapter);

        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

                int dragFlag = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlag = ItemTouchHelper.END;

                return makeMovementFlags(dragFlag,swipeFlag);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

                final FeelingNote minusNote = feelingNotesAdapter.getNoteAt(viewHolder.getAdapterPosition());


                AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(MainActivity.this);
                deleteBuilder.setTitle(R.string.delete_note);
                deleteBuilder.setMessage(R.string.are_you_sure_you_want_to_delete_this_note);
                deleteBuilder.setCancelable(true);
                deleteBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mViewModel.delete(minusNote);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        feelingNotesAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                });
                deleteBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        feelingNotesAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                });
                AlertDialog deleteDialog = deleteBuilder.create();
                deleteDialog.show();
                Button pButton = deleteDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button nButton = deleteDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                pButton.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                nButton.setTextColor(currentColor);


            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(getResources().getColor(R.color.red))
                        .addActionIcon(R.drawable.ic_delete_forever_white_24dp)
                        .create()
                        .decorate();


            }
        };
        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);

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
                changeBackgroundColor(happyColor);
                headerView.setBackgroundColor(happyColor);
                txtMoodDesc.setText(R.string.we_are_glad_you_are_happy);
                editFeeling.setHint(R.string.happiness_is_always_contagious_save_your_best_moments);
                noteFeeling = FeelingNote.HAPPY;
                break;
            case R.id.activity_main_fabSad:
                changeBackgroundColor(sadColor);
                headerView.setBackgroundColor(sadColor);
                txtMoodDesc.setText(R.string.it_is_ok_to_be_sad);
                editFeeling.setHint(R.string.sadness_is_sometimes_a_great_friend_we_can_learn_a_lot_from_it_why_blue_today);
                noteFeeling = FeelingNote.SAD;
                break;
            case R.id.activity_main_fabFear:
                changeBackgroundColor(fearColor);
                headerView.setBackgroundColor(fearColor);
                txtMoodDesc.setText(R.string.fear_is_only_natural);
                editFeeling.setHint(R.string.fear_gives_us_the_possibility_to_remember_that_we_are_courageous_what_s_going_on);
                noteFeeling = FeelingNote.FEAR;
                break;
            case R.id.activity_main_fabLove:
                changeBackgroundColor(loveColor);
                headerView.setBackgroundColor(loveColor);
                txtMoodDesc.setText(R.string.love_is_what_we_all_need);
                editFeeling.setHint(R.string.never_forget_value_current_things_they_are_the_best_what_do_you_value_today);
                noteFeeling = FeelingNote.LOVE;
                break;
            case R.id.activity_main_fabAnger:
                changeBackgroundColor(angerColor);
                headerView.setBackgroundColor(angerColor);
                txtMoodDesc.setText(R.string.dont_let_anger_make_your_decisions);
                editFeeling.setHint(R.string.anger_is_momentary_i_guarantee_you_it_is_never_worth_it_what_is_bothering_you);
                noteFeeling = FeelingNote.ANGER;
                break;

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_activity_menu_simple_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_simple_save_save) {

            if (!editFeeling.getText().toString().isEmpty()) {
                if (noteFeeling != 450) {

                    dialogLayout = getLayoutInflater().inflate(R.layout.dialog_note_title, null);
                    editTitle = dialogLayout.findViewById(R.id.dialog_editTitle);
                    dialogInputLayout = dialogLayout.findViewById(R.id.dialog_textInputLayout);
                    dialogInputLayout.setBoxStrokeColor(currentColor);
                    dialogInputLayout.setHintTextColor(ColorStateList.valueOf(currentColor));

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.add_a_title_to_your_note);
                    builder.setView(dialogLayout);
                    builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (!editTitle.getText().toString().isEmpty()) {
                                note.setTitle(editTitle.getText().toString());
                                note.setNote(editFeeling.getText().toString());
                                note.setFeeling(noteFeeling);
                                note.setPublished(false);
                                mViewModel.insert(note);
                                editFeeling.setText(null);

                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.add_a_title_to_your_note), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                    Button pBtn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    pBtn.setTextColor(currentColor);

                } else {
                    Snackbar.make(rootLayout, R.string.choose_a_feeling_for_the_note, Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(rootLayout, R.string.create_a_note, Snackbar.LENGTH_LONG).show();
            }

        }

        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}
