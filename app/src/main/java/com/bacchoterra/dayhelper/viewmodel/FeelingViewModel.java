package com.bacchoterra.dayhelper.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bacchoterra.dayhelper.localdb.FeelingNoteRepository;
import com.bacchoterra.dayhelper.model.FeelingNote;

import java.util.List;

public class FeelingViewModel extends AndroidViewModel {

    private FeelingNoteRepository repo;
    private LiveData<List<FeelingNote>> allNotes;

    public FeelingViewModel(@NonNull Application application) {
        super(application);
        repo = new FeelingNoteRepository(application);
        allNotes = repo.getAllNotes();
    }

    public void insert (FeelingNote feelingNote){
     repo.insert(feelingNote);
    }
    public void update (FeelingNote feelingNote){
        repo.update(feelingNote);
    }
    public void delete (FeelingNote feelingNote){
        repo.delete(feelingNote);
    }
    public LiveData<List<FeelingNote>> getAllNotes(){
        return allNotes;
    }
}
