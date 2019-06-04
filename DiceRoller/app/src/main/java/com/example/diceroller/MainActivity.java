package com.example.diceroller;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Button button;
    private EditText input;
    private TextView Emessage;

    private static final float GRAVITY_THRESHOLD = 1.5F;
    private SensorManager sensorM;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorM = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        button=(Button)  findViewById(R.id.button);
        input=(EditText) findViewById(R.id.editText);
        Emessage=(TextView) findViewById(R.id.textView2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiceRoller();
            }
        });
    }


    public void openDiceRoller(){
        Intent intent=new Intent(this,dice_roller.class);
        try {
            int num = Integer.parseInt(input.getText().toString());
            if(num<1 || num>6)
            {
                System.out.println("please enter number between 1 and 6");
                Emessage.setVisibility(View.VISIBLE);
                return;
            }
            Emessage.setVisibility(View.INVISIBLE);
            intent.putExtra("count",num);
            startActivity(intent);
        }catch (Exception e)
        {
            Emessage.setVisibility(View.VISIBLE);
            return;
        }


    }


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

    @Override
    protected void onResume() {
        super.onResume();
        if (sensor != null) {
            sensorM.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorM.unregisterListener(this);
    }


}
