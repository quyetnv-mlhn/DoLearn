package com.example.dolearn.note;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.dolearn.R;
import com.example.dolearn.topic.Item;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Item> itemList;
    private TextToSpeech textToSpeech;

    public NoteAdapter(Context context, int layout, List<Item> itemList) {
        this.context = context;
        this.layout = layout;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);

        // ánh xạ View
        TextView engName = view.findViewById(R.id.textviewEnglish);
        TextView vieName = view.findViewById(R.id.textviewVietnam);
        CheckBox note = view.findViewById(R.id.checkboxStar);
        CheckBox speak = view.findViewById(R.id.checkboxSpeaker);

        // Gán giá trị
        Item item = itemList.get(i);
        engName.setText(item.getEngName());
        vieName.setText(item.getVieName());
        note.setFocusable(false);
        note.setFocusableInTouchMode(false);
        speak.setFocusable(false);
        speak.setFocusableInTouchMode(false);

        //Load note
        for (int index = 0; index < NoteActivity.listNote.size(); index++) {
            Item itemNote = NoteActivity.listNote.get(index);
            if (itemNote.getEngName().equals(item.getEngName())) {
                note.setChecked(true);
            }
        }

        // Text to Speech
        textToSpeech();
        speak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                textToSpeech.speak(engName.getText().toString().split(" ")[0], TextToSpeech.QUEUE_FLUSH, null);
                speak.setChecked(false);
            }
        });

        //Add and remove note
        note.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    NoteActivity.listNote.add(item);
                } else {
                    for (int i = 0; i < NoteActivity.listNote.size(); i++) {
                        Item itemNote = NoteActivity.listNote.get(i);
                        if (itemNote.getEngName().equals(item.getEngName())) {
                            NoteActivity.listNote.remove(itemNote);
                            break;
                        }
                    }
                }

                //overwrite listNote to file
                try {
                    FileOutputStream fos = context.openFileOutput("fileNote.txt", Context.MODE_PRIVATE);
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    for (Item noteItem:NoteActivity.listNote) {
                        osw.write(noteItem.getEngName() + "\t" + noteItem.getVieName() + "\t"
                                + noteItem.getPronoun() + "\t" + noteItem.getExampleEn() + "\t"
                                + noteItem.getExampleVi() + "\n");
                    }
                    osw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public void textToSpeech() {
        textToSpeech = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
    }
}