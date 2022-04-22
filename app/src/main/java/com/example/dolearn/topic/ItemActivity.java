package com.example.dolearn.topic;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dolearn.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class ItemActivity extends AppCompatActivity {
    ListView listView_item;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        handle();
        listView_item = findViewById(R.id.listView_item);
        adapter = new ItemAdapter(this,R.layout.item, Dictionary.listItem);
        listView_item.setAdapter(adapter);
        listView_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent_detailedItem = new Intent(ItemActivity.this, DetailedItem.class);
                intent_detailedItem.putExtra("ItemNumber", i);
                startActivity(intent_detailedItem);
            }
        });

        }
    public void handle() {
        ArrayList<Item> arrayList = new ArrayList<Item>();
        try {
            Intent intent = getIntent();
            String filename = intent.getStringExtra("filename");
            InputStream is = getAssets().open(filename+".txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer);
            Scanner sc = new Scanner(text);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split("\t");
                Item item = new Item(data[0], data[1], data[2], data[3], data[4]);
                arrayList.add(item);
            }
            Dictionary.listItem = (ArrayList<Item>) arrayList.clone();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}