package com.example.dolearn.topic;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dolearn.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopicActivity extends AppCompatActivity {
    ListView listView_topic;
    List<String> topicList;
    TopicAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar();
        setContentView(R.layout.activity_topic);
        String[] myResArray = getResources().getStringArray(R.array.topic_list);
        List<String> topicList = Arrays.asList(myResArray);
        anhxa();
        adapter = new TopicAdapter(this,R.layout.topic,topicList);
        listView_topic.setAdapter(adapter);
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

    public void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Topic");
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