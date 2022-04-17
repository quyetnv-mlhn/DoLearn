package com.example.dolearn;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import com.example.dolearn.translate.TranslateActivity;

import com.example.dolearn.topic.TopicActivity;


public class MainActivity extends AppCompatActivity {
    CardView cardView_topic, cardView_translate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();

        cardView_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TranslateActivity.class);
                startActivity(intent);
            }
        });

        cardView_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTopic = new Intent(MainActivity.this, TopicActivity.class);
                startActivity(intentTopic);
            }
        });
    }

    private void anhxa() {
        cardView_translate = findViewById(R.id.cardView_translate);
        cardView_topic = findViewById(R.id.cardView_topic);
    }
}