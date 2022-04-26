package com.example.dolearn.topic;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.dolearn.HandleClass;
import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;

import java.sql.SQLOutput;
import java.util.ArrayList;
public class DetailedItem extends AppCompatActivity {
    TextView textView_engName,textView_vieName,textView_pronounce,textView_exampleEn,textView_exampleVi;
    CheckBox checkBox_star,checkBox_speaker;
    int itemNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_item);
        anhxa();
        Intent getI = getIntent();
        itemNumber = getI.getIntExtra("ItemNumber",0);
        ArrayList<Item> arrayList = (ArrayList<Item>) Dictionary.listItem.clone();
        textView_engName.setText(arrayList.get(itemNumber).getEngName());
        textView_vieName.setText(arrayList.get(itemNumber).getVieName());
        textView_pronounce.setText(arrayList.get(itemNumber).getPronoun());
        textView_exampleEn.setText(arrayList.get(itemNumber).getExampleEn());
        textView_exampleVi.setText(arrayList.get(itemNumber).getExampleVi());

        //Load note
        for (int index = 0; index < NoteActivity.listNote.size(); index++) {
            Item itemNote = NoteActivity.listNote.get(index);
            if (itemNote.getEngName().equals(Dictionary.listItem.get(itemNumber).getEngName())) {
                checkBox_star.setChecked(true);
            }
        }
    }

    private void anhxa() {
        textView_engName = findViewById(R.id.textView_engName);
        textView_vieName = findViewById(R.id.textView_vieName);
        textView_pronounce = findViewById(R.id.textView_pronounce);
        textView_exampleEn = findViewById(R.id.textView_exampleEn);
        textView_exampleVi = findViewById(R.id.textView_exampleVi);
        checkBox_speaker = findViewById(R.id.checkBox_speaker);
        checkBox_star = findViewById(R.id.checkBox_star);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox_star:
                if (checked) {
                    NoteActivity.listNote.add(Dictionary.listItem.get(itemNumber));
                } else {
                    for (int i = 0; i < NoteActivity.listNote.size(); i++) {
                        Item itemNote = NoteActivity.listNote.get(i);
                        if (itemNote.getEngName().equals(Dictionary.listItem.get(itemNumber).getEngName())) {
                            NoteActivity.listNote.remove(itemNote);
                            break;
                        }
                    }
                }
                //overwrite listNote to file
                HandleClass.loadDataToFile(DetailedItem.this);
                // Remove the meat
                break;
            case R.id.checkBox_speaker:
                if (checked) {
                    HandleClass.textToSpeech(DetailedItem.this, textView_engName, checkBox_speaker);
                    checkBox_speaker.setChecked(false);
                }
                break;
            // TODO: Veggie sandwich
        }
    }
}