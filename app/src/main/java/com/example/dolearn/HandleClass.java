package com.example.dolearn;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import com.example.dolearn.note.NoteActivity;
import com.example.dolearn.topic.Item;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class HandleClass {
    public static TextToSpeech tts;
    public static Boolean onOffSwitch;
    public static int minute;
    public static float speedRate = 1;

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
                    tts.setSpeechRate(speedRate);
                    tts.speak(engName.getText().toString().split("\\(")[0], TextToSpeech.QUEUE_FLUSH, null, "");
                }
            }
        });
    }
    public static void textToSpeechString(Context context, String engName) {
        tts = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                    tts.setSpeechRate(speedRate);
                    tts.speak(engName.split("\\(")[0], TextToSpeech.QUEUE_FLUSH, null, "");
                }
            }
        });
    }

    //Viet hoa chu cai dau tien
    public static String upperCaseFirst(String string) {
        String firstLetter = string.substring(0, 1);
        String remainingLetters = string.substring(1, string.length());
        firstLetter = firstLetter.toUpperCase();
        return firstLetter + remainingLetters;
    }

    //Kiem tra item co tom tai trong listNote hay khong
    public static Boolean checkExistInNote(Item item) {
        return NoteActivity.listEngName.contains(item.getEngName());
    }
}
