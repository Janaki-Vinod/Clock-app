
package com.example.clockapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView clockText, stopwatchText;
    private Button stopwatchButton, timerButton;
    private Handler stopwatchHandler = new Handler();
    private Runnable stopwatchRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockText = findViewById(R.id.clockText);
        stopwatchText = findViewById(R.id.stopwatchText);
        stopwatchButton = findViewById(R.id.stopwatchButton);
        timerButton = findViewById(R.id.timerButton);

        // Update the clock every second
        Runnable clockRunnable = new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                int seconds = (int) (currentTime / 1000) % 60;
                int minutes = (int) ((currentTime / (1000 * 60)) % 60);
                int hours = (int) ((currentTime / (1000 * 60 * 60)) % 24);
                clockText.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                stopwatchHandler.postDelayed(this, 1000);
            }
        };
        stopwatchHandler.post(clockRunnable);

        // Stopwatch button functionality - Navigate to StopwatchActivity
        stopwatchButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StopwatchActivity.class);
            startActivity(intent);  // Start the new activity
        });

        // Timer button functionality - Navigate to TimerActivity
        timerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TimerActivity.class);
            startActivity(intent);  // Start the new Timer activity
        });
    }
}
