package com.example.diceroller;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.app.Service;

public class DeviceShake extends Service implements SensorEventListener {

    float dim1, dim2, dim3;
    float prevdim1, prevdim2, prevdim3;

    boolean initial=true;
    boolean deviceshaked=false;
    float threshold=12.5f;

    Sensor s;
    SensorManager sm;

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        sm=(SensorManager) getSystemService(SENSOR_SERVICE);
        s=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, s,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        updateDimensionsParameters(event.values[0],event.values[1],event.values[2]);
        if((!deviceshaked) && isDimensionChanged()){
            deviceshaked=true;
        }
        else if ((deviceshaked) && isDimensionChanged()){
           System.out.println("shake identified");
            shakeAction();

        }
        else if ((deviceshaked) && (!isDimensionChanged()))
        {
            deviceshaked=false; 
        }

    }

    private void shakeAction() {

        Intent i=new Intent(this,dice_roller.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private boolean isDimensionChanged() {

        float change_in_dim1=Math.abs(prevdim1-dim1);
        float change_in_dim2=Math.abs(prevdim2-dim2);
        float change_in_dim3=Math.abs(prevdim3-dim3);

        return (change_in_dim1> threshold && change_in_dim2>threshold)||
                (change_in_dim1>threshold && change_in_dim3>threshold) ||
                (change_in_dim2>threshold && change_in_dim3>threshold);
    }

    private void updateDimensionsParameters(float value1, float value2, float value3) {

        if(initial)
        {
            prevdim1=value1;
            prevdim2=value2;
            prevdim3=value3;
            initial=false;

        }
        else
        {
            prevdim1=dim1;
            prevdim2=dim2;
            prevdim3=dim3;
        }

        dim1=value1;
        dim2=value2;
        dim3=value3;
    }
}
