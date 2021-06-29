package com.example.gb_notes.data;

import android.os.Parcel;
import android.os.Parcelable;


public class Note implements Parcelable {
    private String heading;
    private String noteText;
    private String date;
    private int noteIndex;

    public Note(String heading, String noteText, String date, int index) {
        this.heading = heading;
        this.noteText = noteText;
        this.date = date;
        this.noteIndex = index;

    }

    protected Note(Parcel in) {
        heading = in.readString();
        noteText = in.readString();
        date = in.readString();
        noteIndex = in.readInt();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getHeading() {
        return heading;
    }

    public String getNoteText() {
        return noteText;
    }

    public String getDate() {
        return date;
    }

    public int getNoteIndex() {
        return noteIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(heading);
        dest.writeString(noteText);
        dest.writeString(date);
        dest.writeInt(noteIndex);
    }
}
