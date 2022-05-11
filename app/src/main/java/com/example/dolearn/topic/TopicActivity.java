package com.example.dolearn.topic;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.dolearn.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicActivity extends AppCompatActivity {
    List<String> topicList;
    List<String> topicItemList;
    Map<String, List<String>> topicCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar();
        setContentView(R.layout.activity_topic);

        topicList = new ArrayList<>();
        createTopicList();
        createTopicCollection();
        expandableListView = findViewById(R.id.listView_topic);
        expandableListAdapter = new TopicAdapter(this, topicList, topicCollection);
        expandableListView.setAdapter(expandableListAdapter);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
//        expandableListView.setIndicatorBounds(width - 150, width);
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableListView.setIndicatorBounds(width - 250, width);
        } else {
            expandableListView.setIndicatorBoundsRelative(width - 250, width);
        }

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intentItem = new Intent(TopicActivity.this, ItemActivity.class);
                String sang = null;
                switch (i){
                    case 0:sang = getResources().getStringArray(R.array.tunhien_eng_list)[i1] ;
                        break;
                    case 1:sang = getResources().getStringArray(R.array.connguoi_eng_list)[i1] ;
                        break;
                    case 2:sang = getResources().getStringArray(R.array.cacmoiquanhe_eng_list)[i1] ;
                        break;
                    case 3:sang = getResources().getStringArray(R.array.suvatxungquanh_eng_list)[i1] ;
                        break;
                    case 4:sang = getResources().getStringArray(R.array.cuocsongthuongngay_eng_list)[i1] ;
                        break;
                    case 5:sang = getResources().getStringArray(R.array.congviec_eng_list)[i1] ;
                        break;
                    case 6:sang = getResources().getStringArray(R.array.nghethuat_eng_list)[i1] ;
                        break;
                    case 7:sang = getResources().getStringArray(R.array.truyenthong_eng_list)[i1] ;
                        break;
                    case 8:sang = getResources().getStringArray(R.array.lienlactintuc_eng_list)[i1] ;
                        break;
                    case 9:sang = getResources().getStringArray(R.array.cactuchitrangthaimucdo_eng_list)[i1] ;
                        break;
                    default:break;
                }
                intentItem.putExtra("filename", sang);
                startActivity(intentItem);
                return true;
            }
        });
    }

    private void createTopicCollection() {
        String[] natural = getResources().getStringArray(R.array.tunhien_list);
        String[] people = getResources().getStringArray(R.array.connguoi_list);
        String[] relationships = getResources().getStringArray(R.array.cacmoiquanhe_list);
        String[] thingsAround = getResources().getStringArray(R.array.suvatxungquanh_list);
        String[] life = getResources().getStringArray(R.array.cuocsongthuongngay_list);
        String[] work = getResources().getStringArray(R.array.congviec_list);
        String[] art = getResources().getStringArray(R.array.nghethuat_list);
        String[] communication = getResources().getStringArray(R.array.truyenthong_list);
        String[] telephoneLetter = getResources().getStringArray(R.array.lienlactintuc_list);
        String[] adverb = getResources().getStringArray(R.array.cactuchitrangthaimucdo_list);

        topicCollection = new HashMap<String, List<String>>();
        for (String topic : topicList) {
            if (topic.equals("Tự nhiên")) {
                loadTopicItem(natural);
            } else if (topic.equals("Con người")) {
                loadTopicItem(people);
            } else if (topic.equals("Các mối quan hệ")) {
                loadTopicItem(relationships);
            } else if (topic.equals("Sự vật xung quanh")) {
                loadTopicItem(thingsAround);
            } else if (topic.equals("Cuộc sống thường ngày")) {
                loadTopicItem(life);
            } else if (topic.equals("Công việc")) {
                loadTopicItem(work);
            } else if (topic.equals("Nghệ thuật")) {
                loadTopicItem(art);
            } else if (topic.equals("Truyền thông")) {
                loadTopicItem(communication);
            } else if (topic.equals("Liên lạc, tin tức")) {
                loadTopicItem(telephoneLetter);
            } else if (topic.equals("Các từ chỉ trạng thái, mức độ")) {
                loadTopicItem(adverb);
            }
            topicCollection.put(topic, topicItemList);
        }
    }

    private void loadTopicItem(String[] itemTopicList) {
        topicItemList = new ArrayList<>();
        for (String itemTopic : itemTopicList) {
            topicItemList.add(itemTopic);
        }
    }

    private void createTopicList() {
        topicList = new ArrayList<>();
        String[] myResArray = getResources().getStringArray(R.array.topic_list);
        topicList = Arrays.asList(myResArray);
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