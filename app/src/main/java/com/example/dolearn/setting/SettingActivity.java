package com.example.dolearn.setting;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;
import com.example.dolearn.note.NoteDetailItem;
import com.example.dolearn.topic.Item;

public class SettingActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sendNotification();
    }

    public void sendNotification() {
        int random = (int) (Math.random() * NoteActivity.listNote.size());
        Item item = NoteActivity.listNote.get(random);

        Intent resultIntent = new Intent(this, NoteDetailItem.class);
        resultIntent.putExtra("NoteItemNumber", random);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_MUTABLE);

        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(item.getEngName() + " " + item.getPronoun())
                .setContentText(item.getVieName())
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}