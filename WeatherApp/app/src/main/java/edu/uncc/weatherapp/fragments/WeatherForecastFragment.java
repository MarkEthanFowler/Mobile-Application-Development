package edu.uncc.weatherapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.weatherapp.databinding.ForecastListItemBinding;
import edu.uncc.weatherapp.databinding.FragmentWeatherForecastBinding;
import edu.uncc.weatherapp.models.City;
import edu.uncc.weatherapp.models.Forecast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//Assignment15
//WeatherForecastFragment.java
//Ethan Fowler and Raziuddin Syed Khaja

public class WeatherForecastFragment extends Fragment
{

    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";

    private City mCity;
    FragmentWeatherForecastBinding binding;
    OkHttpClient client = new OkHttpClient();
    LinearLayoutManager linearLayout;
    WeatherForecastAdapter weatherAdapter;
    ArrayList<Forecast> mForecast = new ArrayList<>();




    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(City city)
    {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewCityName.setText(mCity.getName() + " " + mCity.getState());

        weatherAdapter = new WeatherForecastAdapter();
        linearLayout = new LinearLayoutManager(view.getContext());
        binding.recyclerView.setLayoutManager(linearLayout);
        binding.recyclerView.setAdapter(weatherAdapter);

        getWeatherForecastURL();

    }

    class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>
    {

        @NonNull
        @Override
        public WeatherForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            ForecastListItemBinding itemBinding = ForecastListItemBinding.inflate(getLayoutInflater(), parent, false);
            WeatherForecastViewHolder holder = new WeatherForecastViewHolder(itemBinding);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull WeatherForecastViewHolder holder, int position)
        {
            holder.CurrentForecast = mForecast.get(position);
            holder.setup();
        }

        @Override
        public int getItemCount()
        {

            return mForecast.size();
        }

        class WeatherForecastViewHolder extends RecyclerView.ViewHolder
        {
            ForecastListItemBinding itemBinding;
            Forecast CurrentForecast;

            public WeatherForecastViewHolder(ForecastListItemBinding binding)
            {
                super(binding.getRoot());
                itemBinding = binding;
            }

            public void setup()
            {
                itemBinding.textViewDateTime.setText(CurrentForecast.getStartTime());
                itemBinding.textViewForecast.setText(CurrentForecast.getWeatherForecast());
                itemBinding.textViewTemperature.setText(String.valueOf(CurrentForecast.getTemperature()) + " F");
                itemBinding.textViewWindSpeed.setText(CurrentForecast.getWindSpeed());
                itemBinding.textViewHumidity.setText(CurrentForecast.getPrecepitationChance() + "%");

                //Provided by Sadia
                Picasso.get().load(CurrentForecast.getIcon()).into(itemBinding.imageView);

            }
        }
    }

    void getWeatherForecastURL()
    {
        String lat = String.valueOf(mCity.getLat());
        String lng = String.valueOf(mCity.getLng());
        String coordinates = (lat + "," + lng);
        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https").host("api.weather.gov").addPathSegment("points").addPathSegment(coordinates).build();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e)
            {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
            {

                if(response.isSuccessful())
                {
                    String body = response.body().string();
                    try
                    {

                        JSONObject json = new JSONObject(body);
                        JSONObject properties = json.getJSONObject("properties");
                        String forecast = properties.getString("forecast");
                        getWeatherForecast(forecast);

                    }
                    catch(Exception e) {}
                }
            }
        });
    }

    void getWeatherForecast(String forecast)
    {
        Request request = new Request.Builder().url(forecast).build();

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e)
            {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
            {
                if(response.isSuccessful())
                {
                    String body = response.body().string();
                    try
                    {

                        JSONObject json = new JSONObject(body);
                        JSONObject properties = json.getJSONObject("properties");
                        JSONArray forecastJsonArray = properties.getJSONArray("periods");
                        mForecast.clear();

                        for (int i = 0; i < forecastJsonArray.length(); i++)
                        {
                            try
                            {

                                JSONObject forecastJsonObject = forecastJsonArray.getJSONObject(i);
                                JSONObject precipiationObject = forecastJsonObject.getJSONObject("probabilityOfPrecipitation");

                                Forecast newForecast = new Forecast();


                                String startTime = forecastJsonObject.getString("startTime");
                                String currentWeather = forecastJsonObject.getString("shortForecast");
                                String windSpeed = forecastJsonObject.getString("windSpeed");
                                String icon = forecastJsonObject.getString("icon");

                                //Sadia helped fix these variables
                                int temperature = forecastJsonObject.getInt("temperature");
                                String precipitationChance = precipiationObject.getString("value");

                                if(precipitationChance.equals("null"))
                                {
                                    precipitationChance = "0";
                                }

                                newForecast.setTemperature(temperature);
                                newForecast.setStartTime(startTime);
                                newForecast.setWeatherForecast(currentWeather);
                                newForecast.setWindSpeed(windSpeed);
                                newForecast.setIcon(icon);
                                newForecast.setPrecepitationChance(precipitationChance);

                                mForecast.add(newForecast);

                            }
                            catch(JSONException e)
                            {
                                throw new RuntimeException(e);
                            }

                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                weatherAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    catch(Exception e)
                    {

                    }
                }
            }
        });
    }
}