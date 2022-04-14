package com.example.dolearn.topic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dolearn.R;

public class TopicItemActivity extends AppCompatActivity {
            ListView listView_topic_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_item);
        Intent intent = getIntent();
        int numberTopic = intent.getIntExtra("NumberTopic",10);
        anhxa();
        ArrayAdapter<CharSequence> topicItemAdapter = null;
            if(numberTopic==0) {
                topicItemAdapter = ArrayAdapter.createFromResource(this, R.array.tunhien_list, android.R.layout.simple_list_item_1);
            }else if(numberTopic == 1){
                 topicItemAdapter = ArrayAdapter.createFromResource(this,R.array.connguoi_list, android.R.layout.simple_list_item_1);
            }else if(numberTopic == 2){
                topicItemAdapter = ArrayAdapter.createFromResource(this, R.array.cacmoiquanhe_list, android.R.layout.simple_list_item_1);
            }
            else if(numberTopic == 3){
                topicItemAdapter = ArrayAdapter.createFromResource(this, R.array.suvatxungquanh_list, android.R.layout.simple_list_item_1);
            }
            else if(numberTopic == 4){
                topicItemAdapter = ArrayAdapter.createFromResource(this, R.array.cuocsongthuongngay_list, android.R.layout.simple_list_item_1);
            }
            else if(numberTopic == 5){
                topicItemAdapter = ArrayAdapter.createFromResource(this, R.array.congviec_list, android.R.layout.simple_list_item_1);
            }
            else if(numberTopic == 6){
                topicItemAdapter = ArrayAdapter.createFromResource(this, R.array.nghethuat_list, android.R.layout.simple_list_item_1);
            }
            else if(numberTopic == 7){
                topicItemAdapter = ArrayAdapter.createFromResource(this, R.array.truyenthong_list, android.R.layout.simple_list_item_1);
            }
            else if(numberTopic == 8){
                topicItemAdapter = ArrayAdapter.createFromResource(this, R.array.dienthoaivathutin_list, android.R.layout.simple_list_item_1);
            }
            else if(numberTopic == 9){
                topicItemAdapter = ArrayAdapter.createFromResource(this, R.array.cactuchitrangthaimucdo_list, android.R.layout.simple_list_item_1);
            }
        listView_topic_item.setAdapter(topicItemAdapter);
        listView_topic_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentItem = new Intent(TopicItemActivity.this,ItemActivity.class);
                startActivity(intentItem);
            }
        });
    }
    private void anhxa() {
        listView_topic_item = findViewById(R.id.listView_topic_item);
    }
}