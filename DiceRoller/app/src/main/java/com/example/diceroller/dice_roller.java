package com.example.diceroller;

//importing all the required libraries
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

//dice_roller class starts here
public class dice_roller extends AppCompatActivity implements SensorEventListener {

    //initial variables declaration
    public static final Random RANDOM = new Random();
    private int num;

    // UI controls declaration
    private Button button;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private TextView textView3;

    // Sensor manager controls declaration
    private static final float GRAVITY_THRESHOLD = 1.5F;
    private SensorManager sensorM;
    private Sensor sensor;

    // declare media player to generate sound effects
    private MediaPlayer media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roller);

        num = getIntent().getIntExtra("count", 0);


        // initialize UI controls
        button = (Button) findViewById(R.id.button);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        imageView6 = (ImageView) findViewById(R.id.imageView6);
        textView3 = (TextView) findViewById(R.id.textView3);

        // initialize sensor event variables
        sensorM = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // initialize media player variables
        media = MediaPlayer.create(this, R.raw.audio);
        media.start();

        // rolls the dice
        rollDice();

        // triggers when the button "Roll the Dice" is clicked
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });

    }

    // generates new sensor information such as timestamp etc.
    @Override
    public void onSensorChanged(SensorEvent Sevent) {
        double dim1, dim2, dim3;

        if (Sevent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            dim1 = Sevent.values[0];
            dim2 = Sevent.values[1];
            dim3 = Sevent.values[2];

            double new_dim1 = dim1 / SensorManager.GRAVITY_EARTH;
            double new_dim2 = dim2 / SensorManager.GRAVITY_EARTH;
            double new_dim3 = dim3 / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement.
            float speed = (float) Math.sqrt(new_dim1 * new_dim1 + new_dim2 * new_dim2 + new_dim3 * new_dim3);
            System.out.println(speed);

            if (speed > GRAVITY_THRESHOLD) {
                // onUserAction();
                rollDice();
            }
        }
    }


    // this method is called every time "Roll the Dice" is clicked
    private void rollDice() {
        int result1 = getRandomValue();
        int result2 = getRandomValue();
        int result3 = getRandomValue();
        int result4 = getRandomValue();
        int result5 = getRandomValue();
        int result6 = getRandomValue();

        // to generate sound effects of dice rolling whenever dice is rolled.
        media.start();

        // depending on the number of dice chosen by the user, corresponding dice images are generated randomly in their positions
        // User will also be displayed with the total score for each round.
        if (num == 1) {
            imageView2.setVisibility(View.VISIBLE);
            imageView2.setImageResource(getResources().getIdentifier("dice" + result2, "drawable", "com.example.diceroller"));
            textView3.setText("Total: " + result2);
        } else if (num == 2) {
            imageView2.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);

            imageView2.setImageResource(getResources().getIdentifier("dice" + result2, "drawable", "com.example.diceroller"));
            imageView5.setImageResource(getResources().getIdentifier("dice" + result5, "drawable", "com.example.diceroller"));
            int sum = result2 + result5;
            textView3.setText("Total: " + sum);
        } else if (num == 3) {
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);

            imageView1.setImageResource(getResources().getIdentifier("dice" + result1, "drawable", "com.example.diceroller"));
            imageView2.setImageResource(getResources().getIdentifier("dice" + result2, "drawable", "com.example.diceroller"));
            imageView3.setImageResource(getResources().getIdentifier("dice" + result3, "drawable", "com.example.diceroller"));

            int sum = result1 + result2 + result3;
            textView3.setText("Total: " + sum);
        } else if (num == 4) {
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);
            imageView1.setImageResource(getResources().getIdentifier("dice" + result1, "drawable", "com.example.diceroller"));
            imageView2.setImageResource(getResources().getIdentifier("dice" + result2, "drawable", "com.example.diceroller"));
            imageView3.setImageResource(getResources().getIdentifier("dice" + result3, "drawable", "com.example.diceroller"));
            imageView5.setImageResource(getResources().getIdentifier("dice" + result5, "drawable", "com.example.diceroller"));
            int sum = result1 + result2 + result3 + result5;
            textView3.setText("Total: " + sum);
        } else if (num == 5) {
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);

            imageView1.setImageResource(getResources().getIdentifier("dice" + result1, "drawable", "com.example.diceroller"));
            imageView2.setImageResource(getResources().getIdentifier("dice" + result2, "drawable", "com.example.diceroller"));
            imageView3.setImageResource(getResources().getIdentifier("dice" + result3, "drawable", "com.example.diceroller"));
            imageView4.setImageResource(getResources().getIdentifier("dice" + result4, "drawable", "com.example.diceroller"));
            imageView5.setImageResource(getResources().getIdentifier("dice" + result5, "drawable", "com.example.diceroller"));
            int sum = result1 + result2 + result3 + result5 + result4;
            textView3.setText("Total: " + sum);
        } else if (num == 6) {
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);
            imageView6.setVisibility(View.VISIBLE);
            imageView1.setImageResource(getResources().getIdentifier("dice" + result1, "drawable", "com.example.diceroller"));
            imageView2.setImageResource(getResources().getIdentifier("dice" + result2, "drawable", "com.example.diceroller"));
            imageView3.setImageResource(getResources().getIdentifier("dice" + result3, "drawable", "com.example.diceroller"));
            imageView4.setImageResource(getResources().getIdentifier("dice" + result4, "drawable", "com.example.diceroller"));
            imageView5.setImageResource(getResources().getIdentifier("dice" + result5, "drawable", "com.example.diceroller"));
            imageView6.setImageResource(getResources().getIdentifier("dice" + result6, "drawable", "com.example.diceroller"));
            int sum = result1 + result2 + result3 + result5 + result4 + result6;
            textView3.setText("Total: " + sum);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // This method unregisters the SensorEventListener
    @Override
    protected void onResume() {
        super.onResume();
        if (sensor != null) {
            sensorM.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    // This method registers the SensorEventListener
    @Override
    protected void onPause() {
        super.onPause();
        sensorM.unregisterListener(this);
    }

    // This method is to generate random value
    public int getRandomValue() {

        return RANDOM.nextInt(6) + 1;
    }

}
