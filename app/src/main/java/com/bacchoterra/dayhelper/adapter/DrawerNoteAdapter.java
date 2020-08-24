package com.bacchoterra.dayhelper.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bacchoterra.dayhelper.R;
import com.bacchoterra.dayhelper.model.FeelingNote;

public class DrawerNoteAdapter extends ListAdapter<FeelingNote, DrawerNoteAdapter.MyViewHolder> {

    Activity activity;

    private static final DiffUtil.ItemCallback<FeelingNote> DIFF_CALLBACK = new DiffUtil.ItemCallback<FeelingNote>() {
        @Override
        public boolean areItemsTheSame(@NonNull FeelingNote oldItem, @NonNull FeelingNote newItem) {
            return oldItem.getRoomId() == newItem.getRoomId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull FeelingNote oldItem, @NonNull FeelingNote newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getNote().equals(newItem.getNote()) && oldItem.getPublished().equals(newItem.getPublished()) && oldItem.getFeeling() == newItem.getFeeling();
        }
    };


    public DrawerNoteAdapter(Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_drawer_item_row, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FeelingNote note = getItem(position);

        holder.txtTitle.setText(note.getTitle());

        switch (note.getFeeling()){

            case FeelingNote.HAPPY:
                holder.rootLayout.setBackgroundColor(activity.getResources().getColor(R.color.happy));
                break;
            case FeelingNote.SAD:
                holder.rootLayout.setBackgroundColor(activity.getResources().getColor(R.color.sad));
                break;
            case FeelingNote.FEAR:
                holder.rootLayout.setBackgroundColor(activity.getResources().getColor(R.color.fear));
                break;
            case FeelingNote.LOVE:
                holder.rootLayout.setBackgroundColor(activity.getResources().getColor(R.color.love));
                break;
            case FeelingNote.ANGER:
                holder.rootLayout.setBackgroundColor(activity.getResources().getColor(R.color.anger));

        }



    }

    public FeelingNote getNoteAt(int pos) {
        return getItem(pos);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        ViewGroup rootLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.note_drawer_item_row_txtTitulo);
            rootLayout = itemView.findViewById(R.id.note_drawer_item_row_rootLayout);

        }
    }


}


