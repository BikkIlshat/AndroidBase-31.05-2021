package ru.geekbrains.notesjournal.ui;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import ru.geekbrains.notesjournal.R;
import ru.geekbrains.notesjournal.data.NoteData;
import ru.geekbrains.notesjournal.data.NoteSource;

public class NotesJournalAdapter extends RecyclerView.Adapter<NotesJournalAdapter.ViewHolder> {

    private final static String TAG = "SocialNetwork";
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        // Здесь можно установить всякие параметры
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(v);// возвращает новый ViewHolder с параметрами (v) которые только-что создали выше
    }


    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull NotesJournalAdapter.ViewHolder holder, int position) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран, используя ViewHolder

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

        private TextView title;
        private TextView description;
        private TextView date;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.note_content);
            date = itemView.findViewById(R.id.date);

            registerContextMenu(itemView);

    // Обработчик нажатий на этом ViewHolder
            title.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        menuPosition = ViewHolder.this.getLayoutPosition();
                        itemView.showContextMenu(10, 10);
                    }
                    return true;
                }
            });

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, ViewHolder.this.getAdapterPosition());
                    }
                }
            });
        }

        private void registerContextMenu(View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        menuPosition = ViewHolder.this.getLayoutPosition();
                        return false;
                    }
                });
                fragment.registerForContextMenu(itemView);
            }
        }
        public void setData(NoteData data){
            title.setText(data.getTitle());
            description.setText(data.getDescription());
            date.setText(new SimpleDateFormat("dd-MM-yy").format(data.getDate()));
        }
    }
}
