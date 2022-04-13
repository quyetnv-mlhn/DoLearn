package com.example.dolearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ItemActivity extends AppCompatActivity {
    ListView listView_item;
    Dictionary dictionary = new Dictionary();
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        handle();
        anhxa();
        adapter = new ItemAdapter(this,R.layout.item, dictionary.natural);
        listView_item.setAdapter(adapter);
    }

    private void anhxa(){
        listView_item = findViewById(R.id.listView_item);
    }

    public void handle() {
        try {
            InputStream is = getAssets().open("Animals.txt");
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
                dictionary.natural.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}