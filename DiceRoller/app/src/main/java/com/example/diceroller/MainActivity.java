package com.example.diceroller;

//importing all the required libraries
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//MainActivity class starts here. Implementing the SensorEventListener interface.
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // initialize the variables
    private int NumOfDice=0;

    //declare UI controls
    private Button button;
    private ImageView plus;
    private ImageView minus;
    private TextView Emessage;
    private TextView inputdice;

    //declare Sensor variables
    private static final float GRAVITY_THRESHOLD = 1.5F;
    private SensorManager sensorM;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize Sensor variables
        sensorM = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //initialize UI controls
        button=(Button)  findViewById(R.id.button);
        plus=(ImageView) findViewById(R.id.imageView);
        minus=(ImageView) findViewById(R.id.imageView2);
        Emessage=(TextView) findViewById(R.id.textView2);
        inputdice=(TextView) findViewById(R.id.textView3);

        // This event is triggered when "Click/Shake to Roll" is clicked
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiceRoller();
            }
        });

        // This event is triggered when "+" is clicked. This method increments the number of dice count by 1 on each click
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumOfDice++;
                inputdice.setText(NumOfDice+"");

            }
        });

        //This event is triggered when "-" is clicked. This method decrements the number of dice count by 1 on each click
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumOfDice--;
                inputdice.setText(NumOfDice+"");

            }
        });
    }


    //To check the number of dice and send the information to next screen via Intent
    public void openDiceRoller(){

        Intent intent=new Intent(this,dice_roller.class);
        try {
            int num = NumOfDice;

            // if the number of dice is not between 1 and 6, error message is thrown to the user
            if(num<1 || num>6)
            {
                Emessage.setVisibility(View.VISIBLE);
                return;
            }
            Emessage.setVisibility(View.INVISIBLE);

            // to send the number of dice count to next screen
            intent.putExtra("count",num);
            startActivity(intent);

        }catch (Exception e)
        {
            //to display error message if any exception is thrown
            Emessage.setVisibility(View.VISIBLE);
            return;
        }

    }


    // This method is triggered whenever the device is shaken
    @Override
    public void onSensorChanged(SensorEvent Sevent) {
        double dim1, dim2, dim3;

        if (Sevent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){

            dim1 = Sevent.values[0];
            dim2 = Sevent.values[1];
            dim3 = Sevent.values[2];

            double new_dim1 = dim1 / SensorManager.GRAVITY_EARTH;
            double new_dim2 = dim2 / SensorManager.GRAVITY_EARTH;
            double new_dim3 = dim3 / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement.
            float speed = (float)Math.sqrt(new_dim1 * new_dim1 + new_dim2 * new_dim2 + new_dim3 * new_dim3);
            System.out.println(speed);

            if (speed > GRAVITY_THRESHOLD) {
                // onUserAction();
                openDiceRoller();
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // This method is to unregister the SensorEventListener
    @Override
    protected void onResume() {
        super.onResume();
        if (sensor != null) {
            sensorM.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    // This method is to register the SensorEventListener
    @Override
    protected void onPause() {
        super.onPause();
        sensorM.unregisterListener(this);
    }


}
