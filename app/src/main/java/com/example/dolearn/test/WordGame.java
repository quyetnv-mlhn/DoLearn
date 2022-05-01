package com.example.dolearn.test;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static com.example.dolearn.R.drawable.custom_wordgame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;
import com.example.dolearn.topic.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.jar.JarOutputStream;

public class WordGame extends AppCompatActivity {
    int presCounter = 0;
    private int maxPresCounter;
    private String[] keys = new String[50];
    private String textAnswer;
    TextView textViewWordGameTitle,textViewWordGameVie;
    EditText editTextWordGame;
    Button  buttonWordGameDelete;
    Animation scale;
    GridLayout gridLayoutWordGame;

    int i;
    int point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar();
        setContentView(R.layout.activity_word_game);
        anhxa();

        Intent intent = getIntent();
        int check = intent.getIntExtra("flags",0);
        if (check == 1){
            i = 0;
            point = 0;
        }else if(check == 0){
            i = getIntent().getIntExtra("i",i);
            point = getIntent().getIntExtra("Point",point);
        }
        buttonWordGameDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i < NoteActivity.listNoteClone.size()) {
                    getIntent().putExtra("i", i);
                    finish();
                    startActivity(getIntent());
                }else {

                }
            }
        });
        scale = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        textViewWordGameVie.setText(NoteActivity.listNoteClone.get(i).getVieName());

        textAnswer = NoteActivity.listNoteClone.get(i).getEngName().substring(0, NoteActivity.listNoteClone.get(i).getEngName().indexOf(' '));
        maxPresCounter = textAnswer.length();
        for (int j = 0; j < NoteActivity.listNoteClone.get(i).getEngName().length(); j++) {
            keys[j] = String.valueOf(NoteActivity.listNoteClone.get(i).getEngName().charAt(j));
        }
        keys = shuffleArray(keys,maxPresCounter);
        for (int j =0;j<maxPresCounter;j++) {
            addView(((GridLayout) findViewById(R.id.gridLayoutWordGame)),keys[j], ((EditText) findViewById(R.id.editTextWordGame)));
        }
    }

    private void anhxa() {
        textViewWordGameTitle = findViewById(R.id.textViewWordGameTitle);
        textViewWordGameVie = findViewById(R.id.textViewWordGameVie);
        editTextWordGame = findViewById(R.id.editTextWordGame);
        gridLayoutWordGame = findViewById(R.id.gridLayoutWordGame);
        buttonWordGameDelete = findViewById(R.id.buttonWordGameDelete);
    }
    private String[] shuffleArray(String[] ar,int maxPresCounter) {
        Random rnd = new Random();
        for (int i = maxPresCounter-1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }
    private void addView(GridLayout viewParent, final String text, final EditText editText) {
        GridLayout.LayoutParams gridLayoutParams = new GridLayout.LayoutParams(
        );

        gridLayoutParams.rightMargin = 20;

        final TextView textView = new TextView(this);

        textView.setLayoutParams(gridLayoutParams);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(30);
        textView.setBackground(this.getResources().getDrawable(custom_wordgame));
        textView.setTextColor(this.getResources().getColor(R.color.white));


        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(presCounter < maxPresCounter) {
                    if (presCounter == 0)
                        editText.setText("");

                    editText.setText(editText.getText().toString() + text);
                    textView.setClickable(false);
                    textView.startAnimation(scale);
                    textView.animate().alpha(0).setDuration(300);
                    presCounter++;

                    if (presCounter == maxPresCounter)
                        doValidate();
                }
            }
        });

        viewParent.addView(textView);
    }


    private void doValidate() {
        presCounter = 0;
        Log.d("edit",editTextWordGame.getText()+"");
        Log.d("textAnswer",textAnswer+"");
        if (editTextWordGame.getText().toString().equals(textAnswer)) {
            point ++;
            editTextWordGame.setText("");
            Toast.makeText(this, "Congratulations!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        i += 1;
        if(i<NoteActivity.listNoteClone.size()) {
            getIntent().putExtra("Point", point);
            getIntent().putExtra("i", i);
            finish();
            startActivity(getIntent());
        }else if(i==NoteActivity.listNoteClone.size()){
            Intent intentResult = new Intent(WordGame.this,ResultWordGame.class);
            intentResult.putExtra("Point",point);
            startActivity(intentResult);
        }
    }

    public void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Game");
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