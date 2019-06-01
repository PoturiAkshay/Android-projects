package com.example.diceroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText input;
    private TextView Emessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Intent intent=new Intent(this, dice_roller.class);
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
}
