package com.example.runtracker_s00187127_ronan_finnegan_duffy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RunSummary extends AppCompatActivity {

    TextView conSteps, conCalories, conMeters, conSeconds;
    double calorieCount=0, meterCount=0;
    int second=0, counter=0;
    TextView conSetDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_summary);

        counter = getIntent().getIntExtra("Steps", -1);
        second = getIntent().getIntExtra("Seconds", -1);

        conSteps = findViewById(R.id.tvSteps);
        conCalories = findViewById(R.id.tvCalories);
        conMeters = findViewById(R.id.tvMeters);
        conSeconds = findViewById(R.id.tvSeconds);
        conSetDate = findViewById(R.id.tvDate);

        calorieCount = counter * 0.04;
        meterCount = counter * 0.8;

        conSeconds.setText(String.valueOf(second) + "  seconds covered");
        conSteps.setText(String.valueOf(counter) + " steps travelled");
        conMeters.setText(String.valueOf(meterCount) + " meters travelled");
        conCalories.setText(String.valueOf(calorieCount) + " calories burned");


        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        conSetDate.setText(date);

    }

    public void goBack(View view){
        finish();
    }
}