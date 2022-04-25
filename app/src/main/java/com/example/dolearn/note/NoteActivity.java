package com.example.dolearn.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dolearn.R;
import com.example.dolearn.topic.DetailedItem;
import com.example.dolearn.topic.Dictionary;
import com.example.dolearn.topic.Item;
import com.example.dolearn.topic.ItemActivity;
import com.example.dolearn.topic.ItemAdapter;


import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {
    public static ArrayList<Item> listNote = new ArrayList<Item>();
    ListView listView_item;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        listView_item = findViewById(R.id.listView_item);
        adapter = new NoteAdapter(this, R.layout.item, listNote);
        listView_item.setAdapter(adapter);
        listView_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent_detailedItem = new Intent(NoteActivity.this, DetailedItem.class);
                intent_detailedItem.putExtra("NoteItemNumber", i);
                startActivity(intent_detailedItem);
            }
        });
    }
}