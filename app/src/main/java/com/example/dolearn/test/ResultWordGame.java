package com.example.dolearn.test;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dolearn.MainActivity;
import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultWordGame extends AppCompatActivity {
    TextView textViewResultPoint;
    Button buttonResultBackHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_word_game);
        textViewResultPoint = findViewById(R.id.textViewResultPoint);
        buttonResultBackHome=findViewById(R.id.buttonResultBackHome);
        Intent intent = getIntent();
        int point = intent.getIntExtra("Point",0);
        textViewResultPoint.setText(point+"/"+ NoteActivity.listNote.size());
        buttonResultBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBackHome = new Intent(ResultWordGame.this, MainActivity.class);
                startActivity(intentBackHome);
                }
        });

    }
}