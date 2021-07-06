package com.example.gb_notes.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {

    public static class Fields {
        public final static String HEADING = "heading";
        public final static String NOTE_TEXT = "note text";
        public final static String DATE = "date";
        public final static String NOTE_INDEX = "index";
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.HEADING, note.getHeading());
        answer.put(Fields.NOTE_TEXT, note.getNoteText());
        answer.put(Fields.DATE, note.getDate());
        answer.put(Fields.NOTE_INDEX, note.getNoteIndex());
        return answer;
    }

    public static Note toNoteData(String id, Map<String, Object> doc) {
        long noteIndex = (long) doc.get(Fields.NOTE_INDEX);
        Timestamp timeStamp = (Timestamp)doc.get(Fields.DATE);
        Note answer = new Note((String) doc.get(Fields.HEADING)
                , (String) doc.get(Fields.NOTE_TEXT)
                , timeStamp.toDate()
                , ((int) noteIndex));
        answer.setId(id);
        return answer;
    }
}
