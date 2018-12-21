package com.thetehnocafe.gurleensethi.workmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import androidx.work.Data;
import androidx.work.Worker;

public class MyWorker extends Worker {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_TEXT = "text";
    public static final String EXTRA_OUTPUT_MESSAGE = "output_message";

    @NonNull
    @Override
    public Result doWork() {
        //Executed on different thread

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String title = getInputData().getString(EXTRA_TITLE, "Default Title");
        String text = getInputData().getString(EXTRA_TEXT, "Default Text");

        //sendNotification("Simple Work Manager", "I have been send by WorkManager!");
        sendNotification(title, text+"  "+ id());

        Data output = new Data.Builder()
                .putString(EXTRA_OUTPUT_MESSAGE, "I have come from MyWorker!")
                .build();

        setOutputData(output);

        return Result.SUCCESS;
    }

    public void sendNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(id(), notification.build());
    }

    int id() {
        double randomDouble = Math.random();
        randomDouble = randomDouble * 50 + 1;
        int randomInt = (int) randomDouble;
        System.out.println(randomInt);
        return randomInt;
    }
}
