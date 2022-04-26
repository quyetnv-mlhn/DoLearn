package com.example.dolearn;

import android.content.Context;

import com.example.dolearn.note.NoteActivity;
import com.example.dolearn.topic.Item;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class HandleClass {
    public static void loadDataToFile(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("fileNote.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            for (Item noteItem: NoteActivity.listNote) {
                osw.write(noteItem.getEngName() + "\t" + noteItem.getVieName() + "\t"
                        + noteItem.getPronoun() + "\t" + noteItem.getExampleEn() + "\t"
                        + noteItem.getExampleVi() + "\n");
            }
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
