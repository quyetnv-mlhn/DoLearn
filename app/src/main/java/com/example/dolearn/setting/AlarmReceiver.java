package com.example.dolearn.setting;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;
import com.example.dolearn.note.NoteDetailItem;
import com.example.dolearn.topic.Item;
import com.example.dolearn.topic.TopicActivity;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        int random = (int) (Math.random() * NoteActivity.listNote.size());
        Item item = NoteActivity.listNote.get(random);

        Intent resultIntent = new Intent(context, NoteDetailItem.class);
        resultIntent.putExtra("NoteItemNumber", random);
        resultIntent.setAction("NotificationBackground");
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, random, resultIntent, PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(item.getEngName() + " " + item.getPronoun())
                .setContentText(item.getVieName())
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

}
