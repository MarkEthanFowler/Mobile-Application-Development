package edu.uncc.weatherapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.uncc.weatherapp.fragments.CitiesFragment;
import edu.uncc.weatherapp.fragments.WeatherForecastFragment;
import edu.uncc.weatherapp.models.City;

//Assignment15
//MainActivity.java
//Ethan Fowler and Raziuddin Syed Khaja
public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesListener
{

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
        getSupportFragmentManager().beginTransaction().add(R.id.main, new CitiesFragment()).addToBackStack(null).commit();
    }


    @Override
    public void gotoWeatherForecast(City city)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main, WeatherForecastFragment.newInstance(city)).addToBackStack(null).commit();
    }
}