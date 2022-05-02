package com.example.dolearn.topic;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dolearn.R;

import java.util.Arrays;
import java.util.List;

public class TopicItemActivity extends AppCompatActivity {
    ListView listView_topic_item;
    String topicName;
    List<String> topicItemList;
    TopicAdapter adapter ;
    String[] myResArray;
    String sang = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_item);
        Intent intent = getIntent();
        int numberTopic = intent.getIntExtra("NumberTopic",10);
        anhxa();
        ArrayAdapter<CharSequence> topicItemAdapter = null;
        switch (numberTopic){
            case 0:myResArray = getResources().getStringArray(R.array.tunhien_list);
                topicName = "Nature";
                break;
            case 1:myResArray = getResources().getStringArray(R.array.connguoi_list);
                topicName = "People";
                break;
            case 2:myResArray = getResources().getStringArray(R.array.cacmoiquanhe_list);
                topicName = "Relationships";
                break;
            case 3:myResArray = getResources().getStringArray(R.array.suvatxungquanh_list);
                topicName = "Things around";
                break;
            case 4:myResArray = getResources().getStringArray(R.array.cuocsongthuongngay_list);
                topicName = "Life";
                break;
            case 5:myResArray = getResources().getStringArray(R.array.congviec_list);
                topicName = "Work";
                break;
            case 6:
                myResArray = getResources().getStringArray(R.array.nghethuat_list);
                topicName = "Art";
                break;
            case 7:myResArray = getResources().getStringArray(R.array.truyenthong_list);
                topicName = "Communication";
                break;
            case 8:myResArray = getResources().getStringArray(R.array.lienlactintuc_list);
                topicName = "Telephone & letter";
                break;
            case 9:myResArray = getResources().getStringArray(R.array.cactuchitrangthaimucdo_list);
                topicName = "Words indicating status, level";
                break;
            default:break;
        }
        List<String> topicList = Arrays.asList(myResArray);
        adapter = new TopicAdapter(this,R.layout.topic,topicList);
        listView_topic_item.setAdapter(adapter);
        listView_topic_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentItem = new Intent(TopicItemActivity.this,ItemActivity.class);
                switch (numberTopic){
                    case 0:sang = getResources().getStringArray(R.array.tunhien_eng_list)[i] ;
                    break;
                    case 1:sang = getResources().getStringArray(R.array.connguoi_eng_list)[i] ;
                    break;
                    case 2:sang = getResources().getStringArray(R.array.cacmoiquanhe_eng_list)[i] ;
                    break;
                    case 3:sang = getResources().getStringArray(R.array.suvatxungquanh_eng_list)[i] ;
                    break;
                    case 4:sang = getResources().getStringArray(R.array.cuocsongthuongngay_eng_list)[i] ;
                    break;
                    case 5:sang = getResources().getStringArray(R.array.congviec_eng_list)[i] ;
                    break;
                    case 6:sang = getResources().getStringArray(R.array.nghethuat_eng_list)[i] ;
                    break;
                    case 7:sang = getResources().getStringArray(R.array.truyenthong_eng_list)[i] ;
                    break;
                    case 8:sang = getResources().getStringArray(R.array.lienlactintuc_eng_list)[i] ;
                    break;
                    case 9:sang = getResources().getStringArray(R.array.cactuchitrangthaimucdo_eng_list)[i] ;
                    break;
                    default:break;
                }
                intentItem.putExtra("filename",sang);
                startActivity(intentItem);
            }
        });
        actionBar();
    }

    private void anhxa() {
        listView_topic_item = findViewById(R.id.listView_topic_item);
    }

    public void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(topicName);
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