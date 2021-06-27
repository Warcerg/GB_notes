package com.example.gb_notes.ui;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gb_notes.data.CardsSource;
import com.example.gb_notes.data.Note;
import com.example.gb_notes.R;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private CardsSource dataSource;
    private OnItemClickListener itemClickListener;

    public NotesAdapter(CardsSource dataSource){
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
    holder.setData(dataSource.getNoteData(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.getSize();
    }


    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView index;
        private TextView heading;
        private TextView date;
        private TextView notePreview;
        private final int textPreviewLength = 14;
        private final String textPreviewEnd = "...";


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            index = itemView.findViewById(R.id.cardNoteIndex);
            heading = itemView.findViewById(R.id.cardNoteHeading);
            date = itemView.findViewById(R.id.cardNoteDate);
            notePreview = itemView.findViewById(R.id.cardNotePreview);
            initOnClickListeners();

        }

        private void initOnClickListeners() {
            viewHolderSetOnClickListener(index);
            viewHolderSetOnClickListener(heading);
            viewHolderSetOnClickListener(date);
            viewHolderSetOnClickListener(notePreview);
        }

        private void viewHolderSetOnClickListener(TextView textView) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

        public void setData(Note note){
            index.setText(String.valueOf(note.getNoteIndex()));
            heading.setText(note.getHeading());
            date.setText(note.getDate());
            notePreviewSet(note);
        }

        private void notePreviewSet(Note note) {
            StringBuilder sb = new StringBuilder();
            if(textPreviewLength > note.getNoteText().length() ){
                sb.append(note.getNoteText().substring(0, note.getNoteText().length()));
            } else {
                sb.append(note.getNoteText().substring(0, textPreviewLength));
            }
            sb.append(textPreviewEnd);
            notePreview.setText(sb.toString());
        }
    }
}
