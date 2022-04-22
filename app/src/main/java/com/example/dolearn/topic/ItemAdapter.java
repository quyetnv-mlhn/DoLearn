package com.example.dolearn.topic;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.dolearn.R;

import java.util.List;
import java.util.Locale;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Item> itemList;
    private TextToSpeech textToSpeech;

    public ItemAdapter(Context context, int layout, List<Item> itemList) {
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

        // Text to Speech
        textToSpeech();
        speak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                textToSpeech.speak(engName.getText().toString().split(" ")[0], TextToSpeech.QUEUE_FLUSH, null);
                speak.setChecked(false);
            }
        });

        return view;
    }

    public void textToSpeech() {
        textToSpeech = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
    }
}
