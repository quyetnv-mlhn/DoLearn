package com.example.dolearn.test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dolearn.MainActivity;
import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultWordGame extends AppCompatActivity {
    TextView textViewResultPoint;
    Button buttonResultBackHome,buttonResultRedo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar();
        setContentView(R.layout.activity_result_word_game);
        textViewResultPoint = findViewById(R.id.textViewResultPoint);
        buttonResultBackHome=findViewById(R.id.buttonResultBackHome);
        Intent intent = getIntent();
        ArrayList<Integer> falseList = intent.getIntegerArrayListExtra("falseList");
        int point = intent.getIntExtra("Point",0);
        textViewResultPoint.setText(point+"/"+ NoteActivity.listNote.size());
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.victory);
        mp.start();
        buttonResultBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBackHome = new Intent(ResultWordGame.this, MainActivity.class);
                startActivity(intentBackHome);
            }
        });
    }

    public void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Handle click backIcon
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}