package com.example.assignment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//FilterActivity class in which the input route number is taken from the user
public class FilterActivity extends AppCompatActivity {

    EditText input;
    Button sub_btn;

    //declaring the input value as null initially
    String bus_num="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        input=findViewById(R.id.editText);
        sub_btn=findViewById(R.id.button);


        //on click of submit button
        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetching the input value provided by the user in edit text
                bus_num=input.getText().toString().trim();

                //sending the route number to MapsActivity class
                Intent intent=new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("bus",bus_num);
                startActivity(intent);
            }
        });

    }
}
