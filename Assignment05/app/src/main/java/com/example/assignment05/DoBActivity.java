package com.example.assignment05;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class DoBActivity extends AppCompatActivity
{
    static public final String DOB_USER_KEY = "user";
    CalendarView DoBCalendarView;
    String DoB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_do_bactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DoBCalendarView = findViewById(R.id.DoBCalendarView);

        DoBCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                DoB = month + "/" + dayOfMonth + "/" + year;
                //Log.d("TAG", "onSelectedDayChange: " + month + " " + dayOfMonth + " " + year);
            }
        });

        findViewById(R.id.cancelDoBButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DoB = "N/A";
                Intent intent = new Intent();
                intent.putExtra(DOB_USER_KEY, DoB);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.submitDoBButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.putExtra(DOB_USER_KEY, DoB);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}