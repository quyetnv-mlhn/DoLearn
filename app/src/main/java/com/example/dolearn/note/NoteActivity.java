package com.example.dolearn.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.dolearn.MainActivity;
import com.example.dolearn.R;
import com.example.dolearn.test.WordGame;
import com.example.dolearn.topic.DetailedItem;
import com.example.dolearn.topic.Dictionary;
import com.example.dolearn.topic.Item;
import com.example.dolearn.topic.ItemActivity;
import com.example.dolearn.topic.ItemAdapter;
import com.example.dolearn.topic.TopicActivity;
import com.example.dolearn.topic.TopicItemActivity;


import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {
    public static ArrayList<Item> listNote = new ArrayList<Item>();
    ListView listView_item;
    NoteAdapter adapter;
    Button buttonWordGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        listView_item = findViewById(R.id.listView_item);
        buttonWordGame = findViewById(R.id.buttonWordGame);
        adapter = new NoteAdapter(this, R.layout.item, listNote);
        listView_item.setAdapter(adapter);
        listView_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent_detailedItem = new Intent(NoteActivity.this, NoteDetailItem.class);
                intent_detailedItem.putExtra("NoteItemNumber", i);
                startActivity(intent_detailedItem);
            }
        });
        buttonWordGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentWordGame = new Intent(NoteActivity.this, WordGame.class);
                startActivity(intentWordGame);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(NoteActivity.this, MainActivity.class);
            intent.putExtra("flags",1);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}