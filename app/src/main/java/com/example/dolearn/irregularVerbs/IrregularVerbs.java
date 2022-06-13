package com.example.dolearn.irregularVerbs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dolearn.MainActivity;
import com.example.dolearn.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class IrregularVerbs extends AppCompatActivity {
    ArrayList<IrregularVerbsItem> irregularVerbsItems;
    ArrayList<String> infinitiveList = new ArrayList<>();
    TextView infinitiveTV, pastTV, pastParticipleTV, vieNameTV;
    TableLayout tableLayout;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irregular_verbs);
        actionBar();
        irregularVerbsItems = new ArrayList<>();
        handle();
        tableLayout = findViewById(R.id.tableVerbs);

        for (int i = 0; i < irregularVerbsItems.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.table_row, null);

            infinitiveTV = view.findViewById(R.id.infinitive);
            pastTV = view.findViewById(R.id.past);
            pastParticipleTV = view.findViewById(R.id.pastParticiple);
            vieNameTV = view.findViewById(R.id.vieName);

            IrregularVerbsItem irregularVerbsItem = irregularVerbsItems.get(i);
            infinitiveTV.setText(irregularVerbsItem.getInfinitive());
            pastTV.setText(irregularVerbsItem.getPast());
            pastParticipleTV.setText(irregularVerbsItem.getPastParticiple());
            vieNameTV.setText(irregularVerbsItem.getVieName());
            if (i % 2 == 0) {
                view.setBackgroundColor(this.getResources().getColor(R.color.sub));
                infinitiveTV.setTextColor(this.getResources().getColor(R.color.navy));
                pastTV.setTextColor(this.getResources().getColor(R.color.navy));
                pastParticipleTV.setTextColor(this.getResources().getColor(R.color.navy));
                vieNameTV.setTextColor(this.getResources().getColor(R.color.navy));
            }
            tableLayout.addView(view);
            infinitiveList.add(irregularVerbsItems.get(i).getInfinitive());
        }

        listView = findViewById(R.id.listIrregularVerbs);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, infinitiveList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view2 = inflater.inflate(R.layout.table_row, null);
                tableLayout.removeAllViews();

                infinitiveTV = view2.findViewById(R.id.infinitive);
                pastTV = view2.findViewById(R.id.past);
                pastParticipleTV = view2.findViewById(R.id.pastParticiple);
                vieNameTV = view2.findViewById(R.id.vieName);

                for (int j = 0; j < infinitiveList.size(); j++) {
                    if (infinitiveList.get(j).equals(listView.getItemAtPosition(i))) {
                        IrregularVerbsItem irregularVerbsItem = irregularVerbsItems.get(j);
                        infinitiveTV.setText(irregularVerbsItem.getInfinitive());
                        pastTV.setText(irregularVerbsItem.getPast());
                        pastParticipleTV.setText(irregularVerbsItem.getPastParticiple());
                        vieNameTV.setText(irregularVerbsItem.getVieName());
                        break;
                    }
                }
                listView.setVisibility(View.GONE);
                tableLayout.setVisibility(View.VISIBLE);
                tableLayout.addView(view2);
            }
        });
    }

    public void handle() {
        try {
            InputStream is = getAssets().open("Verbs.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer);
            Scanner sc = new Scanner(text);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split("\t");
                IrregularVerbsItem irregularVerbsItem = new IrregularVerbsItem(data[0], data[1], data[2], data[3]);
                irregularVerbsItems.add(irregularVerbsItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(IrregularVerbs.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("360 Irregular Verbs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Handle click backIcon
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(IrregularVerbs.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                listView.setVisibility(View.GONE);
                tableLayout.setVisibility(View.VISIBLE);
                tableLayout.removeAllViews();
                for (int i = 0; i < infinitiveList.size(); i++) {
                    try {
                        if (infinitiveList.get(i).substring(0, s.length()).contains(s)) {
                            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = inflater.inflate(R.layout.table_row, null);

                            infinitiveTV = view.findViewById(R.id.infinitive);
                            pastTV = view.findViewById(R.id.past);
                            pastParticipleTV = view.findViewById(R.id.pastParticiple);
                            vieNameTV = view.findViewById(R.id.vieName);

                            IrregularVerbsItem irregularVerbsItem = irregularVerbsItems.get(i);
                            infinitiveTV.setText(irregularVerbsItem.getInfinitive());
                            pastTV.setText(irregularVerbsItem.getPast());
                            pastParticipleTV.setText(irregularVerbsItem.getPastParticiple());
                            vieNameTV.setText(irregularVerbsItem.getVieName());

//                            DisplayMetrics displayMetrics = new DisplayMetrics();
//                            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                            int width = displayMetrics.widthPixels / 4;
//                            infinitiveTV.setWidth(width);
//                            pastTV.setWidth(width);
//                            pastParticipleTV.setWidth(width);
//                            vieNameTV.setWidth(width);

                            if (i % 2 == 0) {
                                view.setBackgroundColor(IrregularVerbs.this.getResources().getColor(R.color.sub));
                                infinitiveTV.setTextColor(IrregularVerbs.this.getResources().getColor(R.color.navy));
                                pastTV.setTextColor(IrregularVerbs.this.getResources().getColor(R.color.navy));
                                pastParticipleTV.setTextColor(IrregularVerbs.this.getResources().getColor(R.color.navy));
                                vieNameTV.setTextColor(IrregularVerbs.this.getResources().getColor(R.color.navy));
                            }
                            tableLayout.addView(view);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listView.setVisibility(View.VISIBLE);
                tableLayout.setVisibility(View.GONE);
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recreate();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}