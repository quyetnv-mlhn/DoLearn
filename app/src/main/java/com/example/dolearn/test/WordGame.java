package com.example.dolearn.test;
import static com.example.dolearn.R.drawable.custom_wordgame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.dolearn.HandleClass;
import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;
import java.util.Random;
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

                    if (presCounter == maxPresCounter) {
                        HandleClass.textToSpeechString(WordGame.this, NoteActivity.listNoteClone.get(i).getEngName());
                        doValidate();
                    }
                }
            }
        });

        viewParent.addView(textView);
    }


    @SuppressLint("ResourceAsColor")
    private void doValidate() {
        presCounter = 0;
        if (editTextWordGame.getText().toString().equals(textAnswer)) {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);
            mp.start();
            editTextWordGame.setText(""+NoteActivity.listNoteClone.get(i).getEngName());
            editTextWordGame.setTextColor(R.color.green);
            point++;
        } else {
            editTextWordGame.setTextColor(R.color.red);
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.incorrect);
            mp.start();
        }
        i += 1;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
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
        }, 2000);
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