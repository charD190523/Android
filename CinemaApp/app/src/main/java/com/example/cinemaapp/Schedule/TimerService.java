package com.example.cinemaapp.Schedule;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimerService extends Service {

    private static final String TAG = "TimerService";
    private static final long TIME_LIMIT_MS = TimeUnit.MINUTES.toMillis(7);
    private static final long COUNTDOWN_INTERVAL = 1000;
    private long remainingTimeInMillis = TIME_LIMIT_MS;
    private CountDownTimer countDownTimer;
    private final IBinder binder = new LocalBinder();

    public static final String ACTION_TIMER_TICK = "com.example.bhd.ACTION_TIMER_TICK";
    public static final String EXTRA_TIME_REMAINING = "com.example.bhd.EXTRA_TIME_REMAINING";
    public static final String ACTION_TIMER_FINISHED = "com.example.bhd.ACTION_TIMER_FINISHED";

    public class LocalBinder extends Binder {
        TimerService getService() {
            return TimerService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        startTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(remainingTimeInMillis, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTimeInMillis = millisUntilFinished;
                Log.d(TAG, "Tick: " + formatTime(millisUntilFinished / 1000));
                sendBroadcast(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "Timer finished");
                sendFinishBroadcast();
                stopSelf(); // Dừng Service khi hết thời gian
            }
        }.start();
    }

    private void sendBroadcast(long millisUntilFinished) {
        Intent intent = new Intent(ACTION_TIMER_TICK);
        intent.putExtra(EXTRA_TIME_REMAINING, millisUntilFinished);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendFinishBroadcast() {
        Intent intent = new Intent(ACTION_TIMER_FINISHED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public String formatTime(long seconds) {
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
    }

    public long getRemainingTime() {
        return remainingTimeInMillis;
    }

    public void setRemainingTime(long timeInMillis) {
        remainingTimeInMillis = timeInMillis;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            startTimer(); // Restart timer with new time
        }
    }
}