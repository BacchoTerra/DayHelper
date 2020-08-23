package com.bacchoterra.dayhelper.localdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bacchoterra.dayhelper.model.FeelingNote;

@Database(entities = FeelingNote.class,version = 1)
public abstract class FeelingNoteDatabase extends RoomDatabase {

    private static FeelingNoteDatabase instance;
    public abstract FeelingNoteDao feelingNoteDao();


    public static synchronized FeelingNoteDatabase getInstance(Context c){

        if (instance == null) {
            instance = Room.databaseBuilder(c.getApplicationContext(),FeelingNoteDatabase.class,"feeling_note_databse").fallbackToDestructiveMigration().build();
        }

        return instance;

    }

}
