package com.example.assignment1a;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Context app = this;
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText editTextNumberDecimal = findViewById(R.id.editTextNumberDecimal);
        TextView conversionValue = findViewById(R.id.conversionValue);
        findViewById(R.id.cToFButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    String stringFahrenheit = editTextNumberDecimal.getText().toString();
                    double fahrenheit = Double.parseDouble(stringFahrenheit);
                    double celsius = (fahrenheit * (9.0 / 5.0)) + 32;
                    conversionValue.setText(String.format("%.2f", celsius));
                }
                catch(NumberFormatException e) {
                    Toast.makeText(app, "Temperature text box is empty", Toast.LENGTH_LONG).show();
                }

            }
        });

        findViewById(R.id.fToCButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    String stringCelsius = editTextNumberDecimal.getText().toString();
                    double celsius = Double.parseDouble(stringCelsius);
                    double fahrenheit = (5.0/9.0) * (celsius - 32);
                    conversionValue.setText(String.format("%.2f", fahrenheit));
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(app, "Temperature text box is empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editTextNumberDecimal.setText("");
                conversionValue.setText("NA");
            }
        });
    }
}