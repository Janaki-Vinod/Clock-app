
package com.example.clockapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    private EditText timeInput;  // To input custom time (in seconds)
    private TextView timerText;
    private Button startButton, pauseButton, resetButton;
    private CountDownTimer countDownTimer;
    private long timeRemaining = 0; // in milliseconds
    private boolean isTimerRunning = false;
    private boolean isTimerPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timeInput = findViewById(R.id.timeInput);
        timerText = findViewById(R.id.timerText);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(v -> startTimer());
        pauseButton.setOnClickListener(v -> pauseTimer());
        resetButton.setOnClickListener(v -> resetTimer());
    }

    private void startTimer() {
        if (timeInput.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please set a time first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert input time to milliseconds
        long inputTime = Long.parseLong(timeInput.getText().toString()) * 1000; // in seconds

        if (isTimerRunning && !isTimerPaused) {
            // Start a new countdown
            timeRemaining = inputTime;
            countDownTimer = new CountDownTimer(timeRemaining, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeRemaining = millisUntilFinished;
                    updateTimerDisplay(timeRemaining);
                }

                @Override
                public void onFinish() {
                    timerText.setText("00:00");
                    Toast.makeText(TimerActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                    resetButton.setVisibility(View.VISIBLE);
                    startButton.setText("Start Timer");
                }
            };
            countDownTimer.start();
            startButton.setText("Pause Timer");
            pauseButton.setVisibility(View.VISIBLE);
        } else {
            // Pause the timer
            countDownTimer.cancel();
            startButton.setText("Resume Timer");
            pauseButton.setVisibility(View.INVISIBLE);
        }

        isTimerRunning = !isTimerRunning;
        isTimerPaused = false;
    }

    private void pauseTimer() {
        // Pause the timer
        countDownTimer.cancel();
        startButton.setText("Resume Timer");
        pauseButton.setVisibility(View.INVISIBLE);
        isTimerPaused = true;
    }

    private void resetTimer() {
        countDownTimer.cancel();
        timeRemaining = 0;
        updateTimerDisplay(timeRemaining);
        startButton.setText("Start Timer");
        pauseButton.setVisibility(View.INVISIBLE);
    }

    private void updateTimerDisplay(long timeInMillis) {
        int seconds = (int) (timeInMillis / 1000) % 60;
        int minutes = (int) ((timeInMillis / (1000 * 60)) % 60);
        int hours = (int) ((timeInMillis / (1000 * 60 * 60)) % 24);
        timerText.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }
}
