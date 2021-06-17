package ru.geekbrains.notesjournal;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteData implements Parcelable {
    private final String name;
    private final String describe;
    private final Date date;


    public NoteData(String name, String describe, Date date) {
        this.name = name;
        this.describe = describe;
        this.date = date;
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

    public String getName() {
        return name;
    }

    public String getDescribe() {
        return describe;
    }

    public Date getDate() {
        return date;
    }

    protected NoteData(Parcel in) {
        name = in.readString();
        describe = in.readString();
        date = stringToDate(in.readString());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(describe);
    }
}
