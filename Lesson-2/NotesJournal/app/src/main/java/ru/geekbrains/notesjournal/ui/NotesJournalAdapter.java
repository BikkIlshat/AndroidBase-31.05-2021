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
    private final NoteSource dataSource;
    private final Fragment fragment;
    private OnItemClickListener itemClickListener;// Слушатель будетустанавливаться извне
    private int menuPosition;


    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД


    public NotesJournalAdapter(NoteSource dataSource, Fragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создаём новый элемент пользовательского интерфейса Через Inflater
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        // Здесь можно установить всякие параметры
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(v);// возвращает новый ViewHolder с параметрами (v) которые только-что создали выше
    }


    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull NotesJournalAdapter.ViewHolder holder,
                                 int position) {
        holder.setData(dataSource.getNoteData(position));
        Log.d(TAG, String.format("onBindViewHolder - %d", position));
    }

    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return dataSource.getSize();
    }

    public int getMenuPosition(){
        return menuPosition;
    }


    // Сеттер слушателя нажатий
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    /// Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на
    // один пункт списка

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
