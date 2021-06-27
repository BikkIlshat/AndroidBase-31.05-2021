package ru.geekbrains.notesjournal;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NoteData implements Parcelable {

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


    private String title;
    private String content;
    private Calendar creationDate;
    private Date date;

    public NoteData(String title, String content, Calendar creationDate, Date date) {
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public Date getDate() {
        return date;
    }

    protected NoteData(Parcel in) {
        title = in.readString();
        content = in.readString();
        creationDate = (Calendar) in.readSerializable();
        date = stringToDate(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeSerializable(creationDate);

    }


    private Date stringToDate(String text) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
        Date date;

        try {
            date = formatter.parse(text);
        } catch (ParseException e) {
            date = new Date();
        }

        return date;
    }
}
