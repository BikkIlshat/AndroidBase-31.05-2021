package ru.geekbrains.notesjournal.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.geekbrains.notesjournal.NoteData;
import ru.geekbrains.notesjournal.R;

public class NotesJournalAdapter extends RecyclerView.Adapter<NotesJournalAdapter.ViewHolder> {

    private String[] dataSource;

    private OnItemClickListener itemClickListener;// Слушатель будетустанавливаться извне


    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД
    public NotesJournalAdapter(String[] dataSource) {
        this.dataSource = dataSource;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public NotesJournalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    // Создаём новый элемент пользовательского интерфейса
    // Через Inflater
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
    // Здесь можно установить всякие параметры
        return new ViewHolder(v); // возвращает новый ViewHolder с параметрами (v) которые только-что создали выше
    }




    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    // Получить элемент из источника данных (БД, интернет...)
    // Вынести на экран, используя ViewHolder
        //viewHolder.getTextView().setText(dataSource[i]);
        viewHolder.getTitleTextView().setText(dataSource[position]);
        viewHolder.getDateTextView().setText(dataSource[position]);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
//        viewHolder.getDateTextView().setText(formatter.format(dataSource[position].getCreationDate().getTime()));

    }


    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    // Сеттер слушателя нажатий
    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
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
       // private TextView textView;
        private CardView cardView;
        private LinearLayout itemLayout;
        private TextView titleTextView;
        private TextView dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // textView = (TextView) itemView;
            cardView = (CardView) itemView;
            itemLayout = itemView.findViewById(R.id.element_of_recycler_view);
            titleTextView = itemView.findViewById(R.id.first_tv_of_item);
            dateTextView = itemView.findViewById(R.id.second_tv_of_item);
    // Обработчик нажатий на этом ViewHolder
            itemLayout.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }


//        public TextView getTextView() {
//            return textView;
//        }


        public LinearLayout getItemLayout() {
            return itemLayout;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getDateTextView() {
            return dateTextView;
        }
    }



}
