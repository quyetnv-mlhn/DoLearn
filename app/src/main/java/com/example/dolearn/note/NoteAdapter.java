package com.example.dolearn.note;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.dolearn.HandleClass;
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
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
        }

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

        //Load note from file to App
        for (int index = 0; index < NoteActivity.listNote.size(); index++) {
            note.setChecked(true);
        }

        // Text to Speech
        speak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                HandleClass.textToSpeech(context, engName);
                speak.setChecked(false);
            }
        });

        //Add and remove note
        note.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    for (int i = 0; i < itemList.size(); i++) {
                        Item itemNote = itemList.get(i);
                        if (itemNote.getEngName().equals(item.getEngName())) {
                            itemList.remove(itemNote);
                            notifyDataSetChanged();
                            break;
                        }
                    }
                }

                //overwrite listNote to file
                HandleClass.loadDataToFile(context);
            }
        });
        return view;
    }
}