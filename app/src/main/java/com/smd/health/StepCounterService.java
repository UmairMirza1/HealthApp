package com.smd.health;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.smd.health.data.DataStorage;
import com.smd.health.data.IStepCountDao;


public class StepCounterService extends Service implements SensorEventListener {
    static final String CHANNEL_ID = "STEPS";
    static final int NOTIFICATION_ID = 1;

    //Firebase stuff
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private IStepCountDao stepCountDao;

    //local vars
    private SensorManager sensorManager;
    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder notificationBuilder;
    private boolean started = false;
    private long currentSystemStepCount = 0;
    private long initialStepCount = -1;


    @Override
    public void onCreate() {
        Log.i("STEP", "Service Created");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor == null) {
            Log.e("STEP", "Step sensor not found");
            return;
        }

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onDestroy() {
        Log.i("STEP", "Service Destroyed");

        sensorManager.unregisterListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags,
                              int startId) {

        Log.i("STEP", "Service Start command");

        notificationManager = NotificationManagerCompat.from(this);

        if (!started) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Steps", NotificationManager.IMPORTANCE_LOW);
                channel.setDescription("Number of Steps Taken");

                notificationManager.createNotificationChannel(channel);
            }

            Notification notification = createNotification();

            startForeground(NOTIFICATION_ID, notification);
        }

        //check if there is an authenticated user
        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();
            stepCountDao = DataStorage.getInstance();

            //check if service was started due to boot event
             if (intent != null) {
                 //we need to set value of initial step count
                 IStepCountDao.IStepCountListener listener = new IStepCountDao.IStepCountListener() {
                     @Override
                     public void onStepCountUpdated(long count) {
                         initialStepCount = count;
                         if (notificationManager != null) {
                             notificationManager.notify(NOTIFICATION_ID, createNotification());
                         }
                         stepCountDao.removeStepCountListener(this);
                     }
                 };
                 stepCountDao.attachStepCountListener(listener);
             }

            started = true;
            return Service.START_STICKY;
        }
        else {
            stopForeground(true);
            return Service.START_NOT_STICKY;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long stepCount = Float.valueOf(sensorEvent.values[0]).longValue();
        currentSystemStepCount = stepCount;
        Log.i("STEP", "Steps count " + stepCount);
        Notification updatedNotification = createNotification();

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, updatedNotification);
        }

        if (initialStepCount != -1) {
            stepCountDao.saveStepCount(getTotalStepCount());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public long getTotalStepCount() {
        return  initialStepCount + currentSystemStepCount;
    }

    private Notification createNotification() {
        if (notificationBuilder == null) {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

            notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setOnlyAlertOnce(true)
                    .setPriority(Notification.PRIORITY_LOW);
        }
        return notificationBuilder
                .setContentTitle("Steps Taken: " + getTotalStepCount())
                .setContentText("Step Goal: 10 Steps")
                .build();
    }
}