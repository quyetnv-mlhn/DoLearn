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
import android.widget.GridLayout;
import android.widget.ImageButton;
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
    TextView textViewWordGame;
    ImageButton buttonWordGameDelete;
    Animation scale,rotate;
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
        scale = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);
        rotate = AnimationUtils.loadAnimation(this,R.anim.rotate);
        textViewWordGameVie.setText(NoteActivity.listNoteClone.get(i).getVieName());

        textAnswer = NoteActivity.listNoteClone.get(i).getEngName().substring(0, NoteActivity.listNoteClone.get(i).getEngName().indexOf(' '));
        maxPresCounter = textAnswer.length();
        for (int j = 0; j < NoteActivity.listNoteClone.get(i).getEngName().length(); j++) {
            keys[j] = String.valueOf(NoteActivity.listNoteClone.get(i).getEngName().charAt(j));
        }
        keys = shuffleArray(keys,maxPresCounter);
        for (int j =0;j<maxPresCounter;j++) {
            addView(((GridLayout) findViewById(R.id.gridLayoutWordGame)),keys[j], ((TextView) findViewById(R.id.textViewWordGame)));
        }
        buttonWordGameDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.refresh);
                mp.start();
                presCounter = 0;
                buttonWordGameDelete.startAnimation(rotate);
                textViewWordGame.setText("");
                gridLayoutWordGame.removeAllViews();
                for (int j =0;j<maxPresCounter;j++) {
                    addView(((GridLayout) findViewById(R.id.gridLayoutWordGame)),keys[j], ((TextView) findViewById(R.id.textViewWordGame)));
                }

            }
        });
    }
    private void anhxa() {
        textViewWordGameTitle = findViewById(R.id.textViewWordGameTitle);
        textViewWordGameVie = findViewById(R.id.textViewWordGameVie);
        textViewWordGame = findViewById(R.id.textViewWordGame);
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
    private void addView(GridLayout viewParent, final String text, final TextView textViewWordGame) {
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
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mp.start();
                if(presCounter < maxPresCounter) {
                    if (presCounter == 0)
                        textViewWordGame.setText("");

                    textViewWordGame.setText(textViewWordGame.getText().toString() + text);
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
        if (textViewWordGame.getText().toString().equals(textAnswer)) {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);
            mp.start();
            textViewWordGame.setText(""+NoteActivity.listNoteClone.get(i).getEngName());
            textViewWordGame.setTextColor(getResources().getColor(R.color.green));
            point++;
        } else {
            textViewWordGame.setTextColor(getResources().getColor(R.color.red));
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