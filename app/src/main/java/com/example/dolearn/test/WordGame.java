package com.example.dolearn.test;
import static com.example.dolearn.R.drawable.custom_wordgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;

import java.util.Random;

public class WordGame extends AppCompatActivity {
    int presCounter = 0;
    private int maxPresCounter = 4;
    private String[] keys = {"R", "I", "B", "D", "X","Q","A","B"};
    private String textAnswer = "BIRD" ;
    TextView textViewWordGameTitle,textViewWordGameVie;
    EditText editTextWordGame;
    Button buttonWordGameNext;
    Animation smallbigforth;
    LinearLayout linearLayoutWordGame;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_game);
        anhxa();
        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);
        Intent intent = getIntent();
        int check = intent.getIntExtra("flags",0);
        if (check == 1){
            i = 0;
        }else if(check == 0){
            i = getIntent().getIntExtra("i",i);
        }
        textViewWordGameVie.setText(NoteActivity.listNote.get(i).getVieName().toString());
        // textAnswer = NoteActivity.listNote.get(i).getEngName();
        for (String key : keys) {
            addView(((LinearLayout) findViewById(R.id.linearLayoutWordGame)), key, ((EditText) findViewById(R.id.editTextWordGame)));
        }
        buttonWordGameNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i < NoteActivity.listNote.size()-1) {
                    i += 1;
                    getIntent().putExtra("i", i);
                    finish();
                    startActivity(getIntent());
                }else {

                }
            }
        });
    }

    private void anhxa() {
        textViewWordGameTitle = findViewById(R.id.textViewWordGameTitle);
        textViewWordGameVie = findViewById(R.id.textViewWordGameVie);
        editTextWordGame = findViewById(R.id.editTextWordGame);
        buttonWordGameNext = findViewById(R.id.buttonWordGameNext);
        linearLayoutWordGame = findViewById(R.id.linearLayoutWordGame);
    }
    private String[] shuffleArray(String[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }
    private void addView(LinearLayout viewParent, final String text, final EditText editText) {
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayoutParams.rightMargin = 20;

        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutParams);
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
                    textView.startAnimation(smallbigforth);
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


        if(editTextWordGame.getText().toString().equals(textAnswer)) {

            editTextWordGame.setText("");
            Toast.makeText(this, "Congratulations!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }

        keys = shuffleArray(keys);
        linearLayoutWordGame.removeAllViews();
        for (String key : keys) {
            addView(linearLayoutWordGame, key, editTextWordGame);
        }

    }

}