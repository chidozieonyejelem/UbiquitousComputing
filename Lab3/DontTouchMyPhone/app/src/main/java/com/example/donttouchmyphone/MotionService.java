package com.example.donttouchmyphone;

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
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MotionService extends Service implements SensorEventListener {

    public static final String NOTIFICATION_CHANNEL_ID = "10003";

    private SensorManager sensorManager;
    private Sensor mSensor;
    private HandlerThread sensorThread;

    private float lastX = 0;
    private float lastY = 0;
    private float lastZ = 0;
    private boolean firstReading = true;
    private long lastUpdate = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (sensorThread == null) {
            sensorThread = new HandlerThread("SensorThread");
            sensorThread.start();
            Handler handler = new Handler(sensorThread.getLooper());

            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mSensor != null) {
                sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL, handler);
            }
        }

        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if (firstReading) {
            lastX = x;
            lastY = y;
            lastZ = z;
            firstReading = false;
            return;
        }

        float change = Math.abs(x - lastX) + Math.abs(y - lastY) + Math.abs(z - lastZ);

        lastX = x;
        lastY = y;
        lastZ = z;

        long actualTime = System.currentTimeMillis();

        if (change > 3) {
            if (actualTime - lastUpdate < 5000) {
                return;
            }
            lastUpdate = actualTime;
            sendNotification(x, y, z, change);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void sendNotification(float x, float y, float z, float change) {
        String time = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault()).format(new Date());

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("time", time);
        intent.putExtra("x", x);
        intent.putExtra("y", y);
        intent.putExtra("z", z);
        intent.putExtra("change", change);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Motion Alerts", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Alerts when the phone is moved");
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Motion detected")
                .setContentText("Your phone was moved at " + time)
                .setContentIntent(pIntent)
                .setAutoCancel(true);

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
        if (sensorThread != null) {
            sensorThread.quitSafely();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}