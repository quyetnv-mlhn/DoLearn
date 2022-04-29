package com.example.dolearn;

import static android.speech.tts.TextToSpeech.Engine.ACTION_CHECK_TTS_DATA;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dolearn.note.NoteActivity;
import com.example.dolearn.topic.Item;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class HandleClass {
    public static TextToSpeech tts;

    //Overwrite data from App to fileNote
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

    //Text to Speech
    public static void textToSpeech(Context context, TextView engName) {
        tts = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                    tts.setSpeechRate(1.0f); //0.5, 1.0, 2.0
                    tts.speak(engName.getText().toString().split("\\(")[0], TextToSpeech.QUEUE_FLUSH, null, "");
                }
            }
        }, ACTION_CHECK_TTS_DATA);
    }
}
