package com.example.dolearn;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.example.dolearn.note.NoteActivity;
import com.example.dolearn.topic.Dictionary;
import com.example.dolearn.topic.Item;
import com.example.dolearn.translate.TranslateActivity;

import com.example.dolearn.topic.TopicActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {
    CardView cardView_topic, cardView_translate, cardView_note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        loadDataFromFile();
        Item item = new Item("Example (Noun)", "Ví dụ", "/ig´za:mp(ə)l/", "We study some examples.", "Chúng tôi nghiên cứu một số ví dụ.");
        if (NoteActivity.listNote.isEmpty()) {
            NoteActivity.listNote.add(item);
        }

        cardView_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TranslateActivity.class);
                startActivity(intent);
                System.out.println(Dictionary.listItem.size());

            }
        });

        cardView_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TopicActivity.class);
                startActivity(intent);
            }
        });

        cardView_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhxa() {
        cardView_translate = findViewById(R.id.cardView_translate);
        cardView_topic = findViewById(R.id.cardView_topic);
        cardView_note = findViewById(R.id.cardView_note);
    }

    public void loadDataFromFile() {
        NoteActivity.listNote.clear();
        try {
            FileInputStream fis = openFileInput("fileNote.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            String data[];
            while ((line = bufferedReader.readLine()) != null) {
                data = line.split("\t");
                NoteActivity.listNote.add(new Item(data[0], data[1], data[2], data[3], data[4]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}