package ru.geekbrains.notesjournal.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteData implements Parcelable {

    private String title; // заголовок
    private String description; // описание
    private Date date; // дата


    public NoteData(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    protected NoteData(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = new Date(in.readLong()); // изменил
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(date.getTime()); // изменил

    }


    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<NoteData> CREATOR = new Creator<NoteData>() {
        @Override
        public NoteData createFromParcel(Parcel in) {
            return new NoteData(in);
        }

        @Override
        public NoteData[] newArray(int size) {
            return new NoteData[size];
        }
    };


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Date getDate() {
        return date;
    }
}
