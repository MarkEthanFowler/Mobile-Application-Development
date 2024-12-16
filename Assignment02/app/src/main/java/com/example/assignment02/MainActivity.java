package com.example.assignment02;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;
// Assignment02
// MainActivity.java
// Ethan Fowler and Raziuddin Syed Khaja

public class MainActivity extends AppCompatActivity
{
    final String TAG = "assignment02";
    SeekBar redSeekBar;
    SeekBar greenSeekBar;
    SeekBar blueSeekBar;
    TextView redTextValue;
    TextView greenTextValue;
    TextView blueTextValue;
    TextView colorHexValue;
    TextView colorRGBValue;
    View colorView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        redSeekBar = findViewById(R.id.redSeekBar);
        greenSeekBar = findViewById(R.id.greenSeekBar);
        blueSeekBar = findViewById(R.id.blueSeekBar);
        redTextValue = findViewById(R.id.redTextValue);
        greenTextValue = findViewById(R.id.greenTextValue);
        blueTextValue = findViewById(R.id.blueTextValue);
        colorHexValue = findViewById(R.id.colorHexValue);
        colorRGBValue = findViewById(R.id.colorRGBValue);
        colorView = findViewById(R.id.colorView);


        colorView.setBackgroundColor(Color.rgb(redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress()));
        colorHexValue.setText(String.format("#%02X%02X%02X", redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress()));
        colorRGBValue.setText(String.format("( " + redSeekBar.getProgress() + ", " +  greenSeekBar.getProgress() +  ", " + blueSeekBar.getProgress() + " )" ));

        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                redTextValue.setText(String.valueOf(progress));
                colorView.setBackgroundColor(Color.rgb(progress, greenSeekBar.getProgress(), blueSeekBar.getProgress()));
                colorHexValue.setText(String.format("#%02X%02X%02X", redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress()));
                colorRGBValue.setText(String.format("( " + redSeekBar.getProgress() + ", " +  greenSeekBar.getProgress() +  ", " + blueSeekBar.getProgress() + " )" ));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                greenTextValue.setText(String.valueOf(progress));
                colorView.setBackgroundColor(Color.rgb(redSeekBar.getProgress(), progress, blueSeekBar.getProgress()));
                colorHexValue.setText(String.format("#%02X%02X%02X", redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress()));
                colorRGBValue.setText(String.format("( " + redSeekBar.getProgress() + ", " +  greenSeekBar.getProgress() +  ", " + blueSeekBar.getProgress() + " )" ));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                blueTextValue.setText(String.valueOf(progress));
                colorView.setBackgroundColor(Color.rgb(redSeekBar.getProgress(), greenSeekBar.getProgress(), progress));
                colorHexValue.setText(String.format("#%02X%02X%02X", redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress()));
                colorRGBValue.setText(String.format("( " + redSeekBar.getProgress() + ", " +  greenSeekBar.getProgress() +  ", " + blueSeekBar.getProgress() + " )" ));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        findViewById(R.id.whiteColorButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                redTextValue.setText(String.valueOf(255));
                greenTextValue.setText(String.valueOf(255));
                blueTextValue.setText(String.valueOf(255));
                redSeekBar.setProgress(255);
                greenSeekBar.setProgress(255);
                blueSeekBar.setProgress(255);
                colorView.setBackgroundColor(Color.rgb(255, 255, 255));
                colorHexValue.setText(String.format("#%02X%02X%02X", redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress()));
                colorRGBValue.setText(String.format("( " + redSeekBar.getProgress() + ", " +  greenSeekBar.getProgress() +  ", " + blueSeekBar.getProgress() + " )" ));
            }
        });

        findViewById(R.id.blackColorButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                redTextValue.setText(String.valueOf(0));
                greenTextValue.setText(String.valueOf(0));
                blueTextValue.setText(String.valueOf(0));
                redSeekBar.setProgress(0);
                greenSeekBar.setProgress(0);
                blueSeekBar.setProgress(0);
                colorView.setBackgroundColor(Color.rgb(0, 0, 0));
                colorHexValue.setText(String.format("#%02X%02X%02X", redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress()));
                colorRGBValue.setText(String.format("( " + redSeekBar.getProgress() + ", " +  greenSeekBar.getProgress() +  ", " + blueSeekBar.getProgress() + " )" ));
            }
        });

        findViewById(R.id.blueColorButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                redTextValue.setText(String.valueOf(0));
                greenTextValue.setText(String.valueOf(0));
                blueTextValue.setText(String.valueOf(255));
                redSeekBar.setProgress(0);
                greenSeekBar.setProgress(0);
                blueSeekBar.setProgress(255);
                colorView.setBackgroundColor(Color.rgb(0, 0, 255));
                colorHexValue.setText(String.format("#%02X%02X%02X", redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress()));
                colorRGBValue.setText(String.format("( " + redSeekBar.getProgress() + ", " +  greenSeekBar.getProgress() +  ", " + blueSeekBar.getProgress() + " )" ));
            }
        });

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                redTextValue.setText(String.valueOf(64));
                greenTextValue.setText(String.valueOf(128));
                blueTextValue.setText(String.valueOf(0));
                redSeekBar.setProgress(64);
                greenSeekBar.setProgress(128);
                blueSeekBar.setProgress(0);
                colorView.setBackgroundColor(Color.rgb(64, 128, 0));
                colorHexValue.setText(String.format("#%02X%02X%02X", redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress()));
                colorRGBValue.setText(String.format("( " + redSeekBar.getProgress() + ", " +  greenSeekBar.getProgress() +  ", " + blueSeekBar.getProgress() + " )" ));
            }
        });


    }

}