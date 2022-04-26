package com.example.dolearn.topic;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dolearn.MainActivity;
import com.example.dolearn.R;

import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity {
    ListView listView_topic;
    ArrayList<String> topicList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        anhxa();
        ArrayAdapter<CharSequence> topicAdapter = ArrayAdapter.createFromResource(this,R.array.topic_list, android.R.layout.simple_list_item_1);
        listView_topic.setAdapter(topicAdapter);
        listView_topic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentTopicItem = new Intent(TopicActivity.this,TopicItemActivity.class);
                intentTopicItem.putExtra("NumberTopic",i);
                startActivity(intentTopicItem);
            }
        });
    }

    private void anhxa() {
        listView_topic = findViewById(R.id.listView_topic);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(TopicActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}