package com.example.dolearn.note;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dolearn.MainActivity;
import com.example.dolearn.R;
import com.example.dolearn.test.QuizActivity;
import com.example.dolearn.test.WordGame;
import com.example.dolearn.topic.Item;


import java.util.ArrayList;
import java.util.Collections;

public class NoteActivity extends AppCompatActivity {
    public static ArrayList<Item> listNote = new ArrayList<Item>();
    public static ArrayList<Item> listNoteClone;
    ListView listView_item;
    NoteAdapter adapter;
    Button buttonWordGame;
    Button buttonQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar();
        setContentView(R.layout.activity_note);
        //listNote clone shuffle
        listNoteClone = (ArrayList<Item>) listNote.clone();
        Collections.shuffle(listNoteClone);

        listView_item = findViewById(R.id.listView_item);
        buttonWordGame = findViewById(R.id.buttonWordGame);
        buttonQuiz = findViewById(R.id.buttonPractice);
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

        buttonQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listNote.size() > 3){
                    Intent intentQuiz = new Intent(NoteActivity.this, QuizActivity.class);
                    intentQuiz.putExtra("sourceList", "fromNote");
                    startActivity(intentQuiz);
                }else{
                    Toast.makeText(NoteActivity.this, "Cần ít nhất 4 từ để luyện tập!", Toast.LENGTH_SHORT).show();
                }
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
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Handle click backIcon
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(NoteActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}