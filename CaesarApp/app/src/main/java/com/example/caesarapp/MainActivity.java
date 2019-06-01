package com.example.caesarapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

private TextView encryptDecryptOutputText;
private EditText inputTextField;
private TextView shiftValueText;
private SeekBar shiftAmountBar;
private Button encryptDecryptButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        encryptDecryptOutputText=findViewById(R.id.encrypt_decrypt_output);
        inputTextField=findViewById(R.id.input_value);
        shiftValueText=findViewById(R.id.shift_value);
        shiftAmountBar=findViewById(R.id.seekBar);
        encryptDecryptButton=findViewById(R.id.encrypt_decrypt);
        shiftValueText.setText(String.valueOf(shiftAmountBar.getProgress()));
        inputTextField.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        shiftAmountBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shiftValueText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        encryptDecryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CaesarCiher cipher=new CaesarCiher(shiftAmountBar.getProgress());
                String inputText=inputTextField.getText().toString();
                encryptDecryptOutputText.setText(cipher.translate(inputText));

            }
        });
    }

}
