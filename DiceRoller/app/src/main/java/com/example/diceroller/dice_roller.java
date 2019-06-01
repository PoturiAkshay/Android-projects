package com.example.diceroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class dice_roller extends AppCompatActivity {

    public static final Random RANDOM=new Random();
    private Button button;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private int num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roller);
        button=(Button)  findViewById(R.id.button);
        num=getIntent().getIntExtra("count",0);
        imageView1=(ImageView) findViewById(R.id.imageView1);
        imageView2=(ImageView) findViewById(R.id.imageView2);
        imageView3=(ImageView) findViewById(R.id.imageView3);
        imageView4=(ImageView) findViewById(R.id.imageView4);
        imageView5=(ImageView) findViewById(R.id.imageView5);
        imageView6=(ImageView) findViewById(R.id.imageView6);

        if (num==1) {
            imageView2.setVisibility(View.VISIBLE);
        }
        else if(num==2)
        {
            imageView2.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);
        }
        else if(num==3)
        {
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);

        }
        else if(num==4)
        {
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);
        }
        else if(num==5)
        {
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);

        }
        else if(num==6)
        {
            imageView1.setVisibility(View.VISIBLE);
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);
            imageView4.setVisibility(View.VISIBLE);
            imageView5.setVisibility(View.VISIBLE);
            imageView6.setVisibility(View.VISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result1=getRandomValue();
                int result2=getRandomValue();
                int result3=getRandomValue();
                int result4=getRandomValue();
                int result5=getRandomValue();
                int result6=getRandomValue();


                if (num==1)
                {
                    imageView2.setImageResource(getResources().getIdentifier("dice"+result2,"drawable","com.example.diceroller"));
                }
                else if (num==2){

                    imageView2.setImageResource(getResources().getIdentifier("dice"+result2,"drawable","com.example.diceroller"));
                    imageView5.setImageResource(getResources().getIdentifier("dice"+result5,"drawable","com.example.diceroller"));
                }
                else if (num==3){
                    imageView1.setImageResource(getResources().getIdentifier("dice"+result1,"drawable","com.example.diceroller"));
                    imageView2.setImageResource(getResources().getIdentifier("dice"+result2,"drawable","com.example.diceroller"));
                    imageView3.setImageResource(getResources().getIdentifier("dice"+result3,"drawable","com.example.diceroller"));

                }
                else if (num==4){
                    imageView1.setImageResource(getResources().getIdentifier("dice"+result1,"drawable","com.example.diceroller"));
                    imageView2.setImageResource(getResources().getIdentifier("dice"+result2,"drawable","com.example.diceroller"));
                    imageView3.setImageResource(getResources().getIdentifier("dice"+result3,"drawable","com.example.diceroller"));
                    imageView5.setImageResource(getResources().getIdentifier("dice"+result5,"drawable","com.example.diceroller"));
                }
                else if (num==5){
                    imageView1.setImageResource(getResources().getIdentifier("dice"+result1,"drawable","com.example.diceroller"));
                    imageView2.setImageResource(getResources().getIdentifier("dice"+result2,"drawable","com.example.diceroller"));
                    imageView3.setImageResource(getResources().getIdentifier("dice"+result3,"drawable","com.example.diceroller"));
                    imageView4.setImageResource(getResources().getIdentifier("dice"+result4,"drawable","com.example.diceroller"));
                    imageView5.setImageResource(getResources().getIdentifier("dice"+result5,"drawable","com.example.diceroller"));

                }
                else if (num==6){
                    imageView1.setImageResource(getResources().getIdentifier("dice"+result1,"drawable","com.example.diceroller"));
                    imageView2.setImageResource(getResources().getIdentifier("dice"+result2,"drawable","com.example.diceroller"));
                    imageView3.setImageResource(getResources().getIdentifier("dice"+result3,"drawable","com.example.diceroller"));
                    imageView4.setImageResource(getResources().getIdentifier("dice"+result4,"drawable","com.example.diceroller"));
                    imageView5.setImageResource(getResources().getIdentifier("dice"+result5,"drawable","com.example.diceroller"));
                    imageView6.setImageResource(getResources().getIdentifier("dice"+result6,"drawable","com.example.diceroller"));

                }


            }
        });

    }

    public int getRandomValue(){

        return RANDOM.nextInt(6)+1;
    }
}
