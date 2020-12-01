package com.example.runtracker_s00187127_ronan_finnegan_duffy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.*;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // All global variables go here
    private final double HI_STEP = 11.0;
    private final double LO_STEP = 8.0;
    boolean highLimit = false;

    // Counters and textviews
    CountUpTimer timer;
    TextView counter;
    TextView tvSteps;
    int second, steps = 0;

    // Sensors
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get instances of textviews into the java
        counter = findViewById(R.id.tvRunLength);
        tvSteps = findViewById(R.id.tvStepCount);

        // get instances of sensor manager and determine the type of sensor that needs to be used
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        timer = new CountUpTimer(30000000) {  // should be high for the run (ms)
            public void onTick(int second) {
                counter.setText(String.valueOf(second));
            }
        };
    }

    public void doStart(View view) {
            if(steps > 0 || second > 0){
                tvSteps.setText("0");
                counter.setText("0");
                steps = 0;
                timer.start();
                Toast.makeText(this, "Tracking Started", Toast.LENGTH_LONG).show();
                super.onResume();
                // turn on the sensor
                mSensorManager.registerListener((SensorEventListener) this, mSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }
            else{
                timer.start();
                Toast.makeText(this, "Tracking Started", Toast.LENGTH_LONG).show();
                super.onResume();
                // turn on the sensor
                mSensorManager.registerListener((SensorEventListener) this, mSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }


    }

    public void doStop(View view) {
        timer.cancel();
        Toast.makeText(this, "Run stopped...", Toast.LENGTH_LONG).show();
        mSensorManager.unregisterListener(this);
    }

    public void doReset(View view){
        counter.setText("0");
        tvSteps.setText("0");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];


        // get a magnitude number using Pythagoras's Theorem
        double mag = round(sqrt((x*x) + (y*y) + (z*z)), 2);


        if ((mag > HI_STEP) && (highLimit == false)) {
            highLimit = true;
        }
        if ((mag < LO_STEP) && (highLimit == true)) {
            // we have a step
            steps++;
            tvSteps.setText(String.valueOf(steps));
            highLimit = false;
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void summaryView(View view) {
        // Create a context of the summary activity
        Intent summaryAct = new Intent(view.getContext(), RunSummary.class);

        // Pass the two variables over to the new activity
        summaryAct.putExtra("Steps", steps);
        summaryAct.putExtra("Seconds", second);

        // Start the activity
        startActivity(summaryAct);
    }
}