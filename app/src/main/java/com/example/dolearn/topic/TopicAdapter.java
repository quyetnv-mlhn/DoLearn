package com.example.dolearn.topic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.dolearn.HandleClass;
import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends BaseAdapter{
        private Context context;
        private int layout;
        private List arrayList;

        public TopicAdapter(Context context, int layout, List<String> arrayList) {
            this.context = context;
            this.layout = layout;
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            TextView textViewTopic = view.findViewById(R.id.textViewTopic);
            textViewTopic.setText(arrayList.get(i).toString());
            return view;
        }
    }

