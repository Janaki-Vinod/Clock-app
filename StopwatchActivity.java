package com.example.clockapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StopwatchActivity extends AppCompatActivity {

    private TextView stopwatchText;
    private Button stopwatchButton, stopwatchResetButton;
    private boolean isStopwatchRunning = false;
    private long elapsedTime = 0; // in milliseconds for stopwatch
    private Handler stopwatchHandler = new Handler();
    private Runnable stopwatchRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        stopwatchText = findViewById(R.id.stopwatchText);
        stopwatchButton = findViewById(R.id.stopwatchButton);
        stopwatchResetButton = findViewById(R.id.stopwatchResetButton);

        // Stopwatch button functionality
        stopwatchButton.setOnClickListener(v -> {
            if (isStopwatchRunning) {
                // Stop stopwatch
                stopwatchHandler.removeCallbacks(stopwatchRunnable);
                stopwatchButton.setText("Start Stopwatch");
                stopwatchResetButton.setVisibility(View.VISIBLE); // Show reset button
            } else {
                // Start stopwatch
                stopwatchRunnable = new Runnable() {
                    @Override
                    public void run() {
                        elapsedTime += 1000;
                        int seconds = (int) (elapsedTime / 1000) % 60;
                        int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
                        int hours = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
                        stopwatchText.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                        stopwatchHandler.postDelayed(this, 1000);
                    }
                };
                stopwatchHandler.post(stopwatchRunnable);
                stopwatchButton.setText("Stop Stopwatch");
                stopwatchResetButton.setVisibility(View.INVISIBLE); // Hide reset button while running
            }
            isStopwatchRunning = !isStopwatchRunning;
        });

        // Stopwatch reset button functionality
        stopwatchResetButton.setOnClickListener(v -> {
            elapsedTime = 0;
            stopwatchText.setText("00:00:00");
            stopwatchButton.setText("Start Stopwatch");
            stopwatchResetButton.setVisibility(View.INVISIBLE); // Hide reset button
        });
    }
}
