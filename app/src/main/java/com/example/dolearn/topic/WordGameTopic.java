package com.example.dolearn.topic;

import static com.example.dolearn.R.drawable.custom_wordgame;
import static com.example.dolearn.R.drawable.custom_wordgame_transparent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.dolearn.HandleClass;
import com.example.dolearn.R;
import com.example.dolearn.test.ResultWordGame;

import java.util.ArrayList;
import java.util.Random;

public class WordGameTopic extends AppCompatActivity {
    int presCounter = 0;
    private int maxPresCounter;
    private String[] keys = new String[50];
    private String textAnswer;
    TextView textViewWordGameTitle,textViewWordGameVie;
    TextView textViewWordGame;
    ImageButton buttonWordGameReset,buttonWordGameDelete;
    Animation scale,rotate;
    GridLayout gridLayoutWordGame;
    ProgressBar progressBarWordGame;
    int i;
    int point;
    ArrayList<Integer> orderClick = new ArrayList<>();
    static ArrayList<Item> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar();
        setContentView(R.layout.activity_word_game_topic);
        mapping();
        list = (ArrayList<Item>) Dictionary.listItem.clone();
        progressBarWordGame.setMax(list.size());
        i = getIntent().getIntExtra("i",i);
        point = getIntent().getIntExtra("Point",point);
        progressBarWordGame.setProgress(i+1);
        scale = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);
        rotate = AnimationUtils.loadAnimation(this,R.anim.rotate);
        textViewWordGameVie.setText(list.get(i).getVieName());

        textAnswer = list.get(i).getEngName().substring(0, list.get(i).getEngName().indexOf(' '));
        maxPresCounter = textAnswer.length();
        for (int j = 0; j < list.get(i).getEngName().length(); j++) {
            keys[j] = String.valueOf(list.get(i).getEngName().charAt(j));
        }
        keys = shuffleArray(keys,maxPresCounter);
        for (int j =0;j<maxPresCounter;j++) {
            addView((findViewById(R.id.gridLayoutWordGame)),keys[j], (findViewById(R.id.textViewWordGame)),j,false);
        }
        buttonWordGameReset.setOnClickListener(view -> {
            presCounter = 0;
            buttonWordGameReset.startAnimation(rotate);
            textViewWordGame.setText("");
            gridLayoutWordGame.removeAllViews();
            orderClick.clear();
            for (int j =0;j<maxPresCounter;j++) {
                addView((findViewById(R.id.gridLayoutWordGame)),keys[j], (findViewById(R.id.textViewWordGame)),j,false);
            }

        });
        buttonWordGameDelete.setOnClickListener(view -> {
            if(presCounter>0){
                String stringTextView = textViewWordGame.getText().toString();
                stringTextView = stringTextView.substring(0,stringTextView.length()-1);
                textViewWordGame.setText(stringTextView);
                presCounter--;
                gridLayoutWordGame.removeAllViews();
                orderClick.remove(orderClick.size()-1);
                for (int j =0;j<maxPresCounter;j++) {
                    if(orderClick.contains(j)){
                        addView(( findViewById(R.id.gridLayoutWordGame)),"", ( findViewById(R.id.textViewWordGame)),j,true);
                    }else{
                        addView((findViewById(R.id.gridLayoutWordGame)),keys[j], ( findViewById(R.id.textViewWordGame)),j,false);
                    }
                }
            }
        });
    }
    private void mapping() {
        textViewWordGameTitle = findViewById(R.id.textViewWordGameTitle);
        textViewWordGameVie = findViewById(R.id.textViewWordGameVie);
        textViewWordGame = findViewById(R.id.textViewWordGame);
        gridLayoutWordGame = findViewById(R.id.gridLayoutWordGame);
        buttonWordGameReset = findViewById(R.id.buttonWordGameReset);
        progressBarWordGame = findViewById(R.id.progressBarWordGame);
        buttonWordGameDelete= findViewById(R.id.buttonWordGameDelete);
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
    private void addView(GridLayout viewParent, final String text, final TextView textViewWordGame,int j,boolean check) {
        GridLayout.LayoutParams gridLayoutParams = new GridLayout.LayoutParams(
        );
        gridLayoutParams.rightMargin = 20;
        gridLayoutParams.topMargin = 20;

        final TextView textView = new TextView(this);
        textView.setLayoutParams(gridLayoutParams);
        textView.setText(text);
        textView.setTextSize(30);
        if(!check) {
            textView.setGravity(Gravity.CENTER);
            textView.setClickable(true);
            textView.setBackground(this.getResources().getDrawable(custom_wordgame));
            textView.setTextColor(this.getResources().getColor(R.color.white));
        }else{
            textView.setClickable(false);
            textView.setBackground(this.getResources().getDrawable(custom_wordgame_transparent));
            textView.setTextColor(this.getResources().getColor(R.color.transparent));
        }


        textView.setOnClickListener(v -> {
            orderClick.add(j);
            if(presCounter < maxPresCounter) {
                if (presCounter == 0)
                    textViewWordGame.setText("");
                textViewWordGame.setText(textViewWordGame.getText().toString() + text);
                textView.setClickable(false);
                textView.startAnimation(scale);
                textView.animate().alpha(0).setDuration(300);
                presCounter++;
                if (presCounter == maxPresCounter) {
                    HandleClass.textToSpeechString(WordGameTopic.this, textAnswer);
                    doValidate();
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
            textViewWordGame.setText(""+list.get(i).getEngName());
            textViewWordGame.setTextColor(getResources().getColor(R.color.green));
            point++;
        } else {
            textViewWordGame.setText(""+list.get(i).getEngName());
            textViewWordGame.setTextColor(getResources().getColor(R.color.red));
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.incorrect);
            mp.start();
            Dictionary.wrongWordGame.add(list.get(i));
        }
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                i += 1;
                if(i<list.size()) {
                    getIntent().putExtra("Point", point);
                    getIntent().putExtra("i", i);
                    finish();
                    startActivity(getIntent());

                }else if(i==list.size()){
                    Intent intentResult = new Intent(WordGameTopic.this, ResultWordGame.class);
                    intentResult.putExtra("Point",point);
                    intentResult.putExtra("MaxPoint",list.size());
                    intentResult.putExtra("check",true);
                    startActivity(intentResult);
                }
            }
        }, 2000);
    }

    public void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Game");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
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