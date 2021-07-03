package ru.geekbrains.notesjournal.ui;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import ru.geekbrains.notesjournal.R;
import ru.geekbrains.notesjournal.data.NoteData;
import ru.geekbrains.notesjournal.data.NoteSource;

public class NotesJournalAdapter extends RecyclerView.Adapter<NotesJournalAdapter.ViewHolder> {

    private final static String TAG = "NoteData";
    private NoteSource dataSource;
    private final Fragment fragment;
    private OnItemClickListener itemClickListener;
    private int menuPosition;


    public NotesJournalAdapter(Fragment fragment) {

        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull NotesJournalAdapter.ViewHolder holder,
                                 int position) {
        holder.setData(dataSource.getNoteData(position));
        Log.d(TAG, String.format("onBindViewHolder - %d", position));
    }


    @Override
    public int getItemCount() {
        return dataSource.getSize();
    }

    public int getMenuPosition(){
        return menuPosition;
    }


    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void setDataSource(NoteSource dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private LinearLayout itemLayout;
        private TextView titleTextView;
        private TextView dateTextView;
        private TextView date;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            itemLayout = itemView.findViewById(R.id.element_of_recycler_view);
            titleTextView = itemView.findViewById(R.id.first_tv_of_item);
            dateTextView = itemView.findViewById(R.id.second_tv_of_item);
            date = itemView.findViewById(R.id.date);

            registerContextMenu(itemView);


            itemLayout.setOnLongClickListener(v -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    menuPosition = ViewHolder.this.getLayoutPosition();
                    itemView.showContextMenu(10, 10);
                }
                return true;
            });

            itemLayout.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, ViewHolder.this.getAdapterPosition());
                }
            });
        }

        private void registerContextMenu(View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(v -> {
                    menuPosition = ViewHolder.this.getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        public void setData(NoteData data){
            titleTextView.setText(data.getTitle());
            dateTextView.setText(data.getDescription());
           date.setText(new SimpleDateFormat("dd-MM-yy").format(data.getDate()));
        }
    }
}
