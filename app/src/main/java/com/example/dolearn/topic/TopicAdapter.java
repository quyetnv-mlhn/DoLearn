package com.example.dolearn.topic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.dolearn.HandleClass;
import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TopicAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<String>> topicCollection;
    private List<String> topicList;

    public TopicAdapter(Context context, List<String> topicList, Map<String, List<String>> topicCollection) {
        this.context = context;
        this.topicList = topicList;
        this.topicCollection = topicCollection;
    }

    @Override
    public int getGroupCount() {
        return topicCollection.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return topicCollection.get(topicList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return topicList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return topicCollection.get(topicList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String topicName = getGroup(i).toString();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.topic, null);
        }
        TextView topic = view.findViewById(R.id.textViewTopic);
        topic.setText(topicName);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String itemTopic = getChild(i, i1).toString();
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.topic_item, null);
        }
        TextView item = view.findViewById(R.id.textViewTopicItem);
        item.setText(itemTopic);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}

