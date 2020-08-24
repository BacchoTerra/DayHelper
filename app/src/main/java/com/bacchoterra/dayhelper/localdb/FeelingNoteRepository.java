package com.bacchoterra.dayhelper.localdb;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bacchoterra.dayhelper.model.FeelingNote;

import java.util.List;

public class FeelingNoteRepository {

    private FeelingNoteDao mDao;
    private LiveData<List<FeelingNote>> allNotes;

    public FeelingNoteRepository(Application application) {
        FeelingNoteDatabase database = FeelingNoteDatabase.getInstance(application);
        mDao = database.feelingNoteDao();
        allNotes = mDao.getAllFeelingNotes();
    }

    public void insert(FeelingNote feelingNote){
        new InsertNoteAsyncTask(mDao).execute(feelingNote);
    }

    public void update(FeelingNote feelingNote){
        new UpdateNoteAsyncTask(mDao).execute(feelingNote);
    }

    public void delete(FeelingNote feelingNote){
        new DeleteNoteAsyncTask(mDao).execute(feelingNote);
    }

    public LiveData<List<FeelingNote>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<FeelingNote, Void, Void> {

        private FeelingNoteDao myDao;

        public InsertNoteAsyncTask(FeelingNoteDao dao) {
            this.myDao = dao;
        }

        @Override
        protected Void doInBackground(FeelingNote... feelingNotes) {

            myDao.insert(feelingNotes[0]);

            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<FeelingNote, Void, Void> {

        private FeelingNoteDao myDao;

        public UpdateNoteAsyncTask(FeelingNoteDao dao) {
            this.myDao = dao;
        }

        @Override
        protected Void doInBackground(FeelingNote... feelingNotes) {

            myDao.insert(feelingNotes[0]);

            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<FeelingNote, Void, Void> {

        private FeelingNoteDao myDao;

        public DeleteNoteAsyncTask(FeelingNoteDao dao) {
            this.myDao = dao;
        }

        @Override
        protected Void doInBackground(FeelingNote... feelingNotes) {

            myDao.delete(feelingNotes[0]);

            return null;
        }
    }


}
