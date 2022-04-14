package com.example.dolearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.dolearn.topic.TopicActivity;

public class MainActivity extends AppCompatActivity {
    CardView cardView_topic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        cardView_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTopic = new Intent(MainActivity.this, TopicActivity.class);
                startActivity(intentTopic);

            }
        });
    }

    private void anhxa() {
        cardView_topic = findViewById(R.id.cardView_topic);
    }
}