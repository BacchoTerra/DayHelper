package com.bacchoterra.dayhelper.localdb;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bacchoterra.dayhelper.model.FeelingNote;

import java.util.List;

public interface FeelingNoteDao {

    @Insert
    void insert(FeelingNote feelingNote);

    @Update
    void update(FeelingNote feelingNote);

    @Delete
    void delete(FeelingNote feelingNote);

    @Query("SELECT * FROM feeling_note_table")
    LiveData<List<FeelingNote>> getAllFeelingNotes();


}
