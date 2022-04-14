package com.example.dolearn.topic;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolearn.R;
import java.util.ArrayList;
public class DetailedItem extends AppCompatActivity {
    TextView textView_engName,textView_vieName,textView_pronounce,textView_exampleEn,textView_exampleVi;
    CheckBox checkBox_star,checkBox_speaker;
    Dictionary dictionary = new Dictionary();
    ArrayList<Item> itemDetailedList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_item);
        anhxa();
        Intent getI = getIntent();
        int itemNumber = getI.getIntExtra("ItemNumber",0);
        Bundle args = getI.getBundleExtra("BUNDLE");
        ArrayList<Item> arrayList = (ArrayList<Item>) args.getSerializable("ARRAYLIST");
        Toast.makeText(this, ""+itemNumber, Toast.LENGTH_SHORT).show();
        textView_engName.setText(arrayList.get(itemNumber).getEngName());
        textView_vieName.setText(arrayList.get(itemNumber).getVieName());
        textView_pronounce.setText(arrayList.get(itemNumber).getPronoun());
        textView_exampleEn.setText(arrayList.get(itemNumber).getExampleEn());
        textView_exampleVi.setText(arrayList.get(itemNumber).getExampleVi());

    }

    private void anhxa() {
        textView_engName = findViewById(R.id.textView_engName);
        textView_vieName = findViewById(R.id.textView_vieName);
        textView_pronounce = findViewById(R.id.textView_pronounce);
        textView_exampleEn = findViewById(R.id.textView_exampleEn);
        textView_exampleVi = findViewById(R.id.textView_exampleVi);
        checkBox_speaker = findViewById(R.id.checkboxSpeaker);
        checkBox_star = findViewById(R.id.checkboxStar);
    }
}