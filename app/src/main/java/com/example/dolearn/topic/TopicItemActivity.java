package com.example.dolearn.topic;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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
                topicItemAdapter = ArrayAdapter.createFromResource(this, R.array.lienlactintuc_list, android.R.layout.simple_list_item_1);
            }
            else if(numberTopic == 9){
                topicItemAdapter = ArrayAdapter.createFromResource(this, R.array.cactuchitrangthaimucdo_list, android.R.layout.simple_list_item_1);
            }
        listView_topic_item.setAdapter(topicItemAdapter);
        listView_topic_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentItem = new Intent(TopicItemActivity.this,ItemActivity.class);
                String sang = new String();
               if(numberTopic== 0){
                  sang = getResources().getStringArray(R.array.tunhien_eng_list)[i] ;
               }else if(numberTopic == 1){
                   sang = getResources().getStringArray(R.array.connguoi_eng_list)[i] ;
               }else if(numberTopic == 2){
                   sang = getResources().getStringArray(R.array.cacmoiquanhe_eng_list)[i] ;
               }else if(numberTopic == 3){
                   sang = getResources().getStringArray(R.array.suvatxungquanh_eng_list)[i] ;
               }else if(numberTopic == 4){
                   sang = getResources().getStringArray(R.array.cuocsongthuongngay_eng_list)[i] ;
               }else if(numberTopic == 5){
                   sang = getResources().getStringArray(R.array.congviec_eng_list)[i] ;
               }else if(numberTopic == 6){
                   sang = getResources().getStringArray(R.array.nghethuat_eng_list)[i] ;
               }else if(numberTopic == 7){
                   sang = getResources().getStringArray(R.array.truyenthong_eng_list)[i] ;
               }else if(numberTopic == 8){
                   sang = getResources().getStringArray(R.array.lienlactintuc_eng_list)[i] ;
               }
               else if(numberTopic == 9){
                   sang = getResources().getStringArray(R.array.cactuchitrangthaimucdo_eng_list)[i] ;
               }
               intentItem.putExtra("filename",sang);
                startActivity(intentItem);
            }
        });
    }

    private void anhxa() {
        listView_topic_item = findViewById(R.id.listView_topic_item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(TopicItemActivity.this, TopicActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}