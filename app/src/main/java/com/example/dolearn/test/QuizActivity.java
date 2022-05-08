package com.example.dolearn.test;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TimeUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dolearn.HandleClass;
import com.example.dolearn.MainActivity;
import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;
import com.example.dolearn.topic.Dictionary;
import com.example.dolearn.topic.Item;
import com.example.dolearn.topic.ItemActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    TextView total_question_tv, question_tv, pronounce_tv;
    Button ans_a_btn, ans_b_btn, ans_c_btn, ans_d_btn;
    ProgressBar progressBar;
    CheckBox checkBoxSpeak;

//    ArrayList<Item> noteList = getIntent().getStringExtra("sourceList").equals("fromNote") ?
//            (ArrayList<Item>) NoteActivity.listNote.clone() :
//            (ArrayList<Item>) Dictionary.listItem.clone();

//    ArrayList<Item> noteList = (ArrayList<Item>) NoteActivity.listNote.clone();
//    ArrayList<Boolean> addedChoice = new ArrayList<Boolean>(Arrays.asList(new Boolean[noteList.size()]));
//    ArrayList<String> words = new ArrayList<String>();
//    ArrayList<String> pronounceList = new ArrayList<String>();
//    ArrayList<String> choices = new ArrayList<String>();

    ArrayList<Item> noteList = new ArrayList<Item>();
    ArrayList<Boolean> addedChoice = new ArrayList<Boolean>();
    ArrayList<String> words = new ArrayList<String>();
    ArrayList<String> pronounceList = new ArrayList<String>();
    ArrayList<String> choices = new ArrayList<String>();

//    ArrayList<Item> noteList;
//    ArrayList<Boolean> addedChoice;
//    ArrayList<String> words, pronounceList, choices;

    int score = 0;

    int totalQuestion;
    int currentQuestionIndex = 0;
    boolean allowPress = true;
    String selectedAnswer = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar();
        setContentView(R.layout.activity_quiz);

        noteList = getIntent().getStringExtra("sourceList").equals("fromNote") ?
                (ArrayList<Item>) NoteActivity.listNote.clone() :
                (ArrayList<Item>) Dictionary.listItem.clone();
        totalQuestion = noteList.size();
//        addedChoice = (ArrayList<Boolean>)(Arrays.asList(new Boolean[noteList.size()]));
//        System.out.println(getIntent().getStringExtra("sourceList"));

//        total_question_tv = findViewById(R.id.total_question_tv);
        progressBar = findViewById(R.id.progressBar);
        question_tv = findViewById(R.id.question_tv);
        pronounce_tv = findViewById(R.id.TVPronounce);
        checkBoxSpeak = findViewById(R.id.checkboxSpeaker);
        ans_a_btn = findViewById(R.id.ans_a_btn);
        ans_b_btn = findViewById(R.id.ans_b_btn);
        ans_c_btn = findViewById(R.id.ans_c_btn);
        ans_d_btn = findViewById(R.id.ans_d_btn);

        ans_a_btn.setOnClickListener(this);
        ans_b_btn.setOnClickListener(this);
        ans_c_btn.setOnClickListener(this);
        ans_d_btn.setOnClickListener(this);

//        total_question_tv.setText("Tổng số câu hỏi: " + noteList.size());
        progressBar.setMax(noteList.size());

        Collections.shuffle(noteList);

        for(int i = 0; i < noteList.size(); i++){
            String tokens = noteList.get(i).getEngName().toString();
            words.add(tokens);
            pronounceList.add(noteList.get(i).getPronoun().toString());
            addedChoice.add(false);
        }


        loadNewQuestion();

//        System.out.println(noteList);
//        System.out.println(choices);

    }

    @Override
    public void onClick(View view) {
        if(allowPress) {

            Button clickedButton = (Button) view;

            String correctAnswer = noteList.get(currentQuestionIndex).getVieName();

            selectedAnswer = clickedButton.getText().toString();
            if (selectedAnswer.equals(correctAnswer)) {
                clickedButton.setBackground(this.getResources().getDrawable(R.drawable.mybutton_correct));
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);
                mp.start();
                score++;
            } else {
                clickedButton.setBackground(this.getResources().getDrawable(R.drawable.mybutton_wrong));
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.incorrect);
                mp.start();
                if (ans_a_btn.getText().toString().equals(correctAnswer)) {
                    ans_a_btn.setBackground(this.getResources().getDrawable(R.drawable.mybutton_correct));
                }
                if (ans_b_btn.getText().toString().equals(correctAnswer)) {
                    ans_b_btn.setBackground(this.getResources().getDrawable(R.drawable.mybutton_correct));
                }
                if (ans_c_btn.getText().toString().equals(correctAnswer)) {
                    ans_c_btn.setBackground(this.getResources().getDrawable(R.drawable.mybutton_correct));
                }
                if (ans_d_btn.getText().toString().equals(correctAnswer)) {
                    ans_d_btn.setBackground(this.getResources().getDrawable(R.drawable.mybutton_correct));
                }
            }

            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long l) {
                    allowPress = false;
                }
                @Override
                public void onFinish() {
                    allowPress = true;
                    currentQuestionIndex++;
                    loadNewQuestion();
                }
            }.start();
        }
    }

    void loadNewQuestion(){
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        progressBar.setProgress(currentQuestionIndex + 1);

        ans_a_btn.setBackground(this.getResources().getDrawable(R.drawable.mybutton));
        ans_b_btn.setBackground(this.getResources().getDrawable(R.drawable.mybutton));
        ans_c_btn.setBackground(this.getResources().getDrawable(R.drawable.mybutton));
        ans_d_btn.setBackground(this.getResources().getDrawable(R.drawable.mybutton));

        question_tv.setText(words.get(currentQuestionIndex));
        pronounce_tv.setText(pronounceList.get(currentQuestionIndex));

        randomChoice();
        ans_a_btn.setText(choices.get(0));
        ans_b_btn.setText(choices.get(1));
        ans_c_btn.setText(choices.get(2));
        ans_d_btn.setText(choices.get(3));

        checkBoxSpeak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                HandleClass.textToSpeech(getApplicationContext(), question_tv);
                checkBoxSpeak.setChecked(false);
            }
        });

    }

    void finishQuiz(){
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.victory);
        mp.start();
        String passStatus = "";

        if(score >= totalQuestion * 0.6){
            passStatus = "Đạt";
        }else{
            passStatus = "Không đạt";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Trả lời đúng " + score + " trong tổng số " + totalQuestion + " câu hỏi")
                .setPositiveButton("Bắt đầu lại", ((dialogInterface, i) -> restartQuiz()))
                .setNegativeButton("Trở về", (((dialogInterface, i) -> backToMenu())))
                .setCancelable(false)
                .show();
    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    void randomChoice(){
        choices.clear();
        choices.add(noteList.get(currentQuestionIndex).getVieName());

        Random random = new Random();
        int randomPos = random.nextInt(noteList.size());

        Collections.fill(addedChoice, Boolean.FALSE);
//        System.out.println(addedChoice);
        for(int i = 0; i < 3; i++){
            while (randomPos == currentQuestionIndex || addedChoice.get(randomPos))
                randomPos = random.nextInt(noteList.size());
            choices.add(noteList.get(randomPos).getVieName());
            addedChoice.set(randomPos, true);
        }
        Collections.shuffle(choices);
    }

    void backToMenu(){
        if(getIntent().getStringExtra("sourceList").equals("fromNote")){
            Intent intentBackNote = new Intent(QuizActivity.this, NoteActivity.class);
            startActivity(intentBackNote);
        }else{
            Intent intentBackItem = new Intent(QuizActivity.this, ItemActivity.class);
            startActivity(intentBackItem);
        }

    }

    public void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Quiz: Chọn nghĩa đúng của từ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Handle click backIcon
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
