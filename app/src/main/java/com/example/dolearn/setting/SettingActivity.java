package com.example.dolearn.setting;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.dolearn.HandleClass;
import com.example.dolearn.MainActivity;
import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;
import com.example.dolearn.note.NoteDetailItem;
import com.example.dolearn.topic.Item;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {

    private int minute = 1;
    private int checked = 1;
    Switch swNotify;
    RadioGroup radioGroup, speedRate;
    RadioButton speedRate1, speedRate2, speedRate3, radioButton1, radioButton5, radioButton10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actionBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        radioGroup = findViewById(R.id.radioGroup);
        speedRate = findViewById(R.id.speedRate);
        swNotify = findViewById(R.id.swNotify);
        radioButton1 = findViewById(R.id.radio_1);
        radioButton5 = findViewById(R.id.radio_5);
        radioButton10 = findViewById(R.id.radio_10);
        speedRate1 = findViewById(R.id.speedRate1);
        speedRate2 = findViewById(R.id.speedRate2);
        speedRate3 = findViewById(R.id.speedRate3);

        if (HandleClass.speedRate == 0.5) {
            speedRate1.setChecked(true);
        } else if (HandleClass.speedRate == 1) {
            speedRate2.setChecked(true);
        } else if (HandleClass.speedRate == 2) {
            speedRate3.setChecked(true);
        }

        speedRate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.speedRate1:
                        HandleClass.speedRate = 0.5f;
                        break;
                    case R.id.speedRate2:
                        HandleClass.speedRate = 1.0f;
                        break;
                    case R.id.speedRate3:
                        HandleClass.speedRate = 2.0f;
                        break;
                }
            }
        });

        if (HandleClass.onOffSwitch != null && HandleClass.onOffSwitch) {
            swNotify.setChecked(true);
        }
        swNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    checked = 1;
                    HandleClass.onOffSwitch = true;
                } else {
                    checked = 0;
                    HandleClass.onOffSwitch = false;
                    setCalendar();
                }
            }
        });

        switch (HandleClass.minute) {
            case R.id.radio_1:
                minute = 1;
                radioButton1.setChecked(true);
                break;
            case R.id.radio_5:
                minute = 5;
                radioButton5.setChecked(true);
                break;
            case R.id.radio_10:
                minute = 10;
                radioButton10.setChecked(true);
                break;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_1:
                        minute = 1;
                        break;
                    case R.id.radio_5:
                        minute = 5;
                        break;
                    case R.id.radio_10:
                        minute = 10;
                        break;
                }
                HandleClass.minute = checkedId;
                if (HandleClass.onOffSwitch != null && HandleClass.onOffSwitch) {
                    setCalendar();
                }
            }
        });
    }

    private void setCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                0
        );
        setAlarm(calendar.getTimeInMillis());
    }

    private void setAlarm(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                0
        );

        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis,
                1000 * 60 * minute, alarmIntent);

        if (alarmMgr!= null && checked == 0) {
            alarmMgr.cancel(alarmIntent);
            Toast.makeText(this, "Alarm turned off", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Handle click backIcon
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}