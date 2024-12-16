package edu.uncc.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.weatherapp.databinding.FragmentCitiesBinding;
import edu.uncc.weatherapp.models.City;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//Assignment15
//CitiesFragment.java
//Ethan Fowler and Raziuddin Syed Khaja

public class CitiesFragment extends Fragment
{
    FragmentCitiesBinding binding;
    OkHttpClient client = new OkHttpClient();
    ArrayList<City> mCities = new ArrayList<>();
    ArrayAdapter<City> adapter;
    CitiesListener mListener;


    public CitiesFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentCitiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getCities(view);

        adapter = new ArrayAdapter<City>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, mCities);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mListener.gotoWeatherForecast(mCities.get(position));
            }
        });

    }

    void getCities(View view)
    {
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/cities")
                .build();

        client.newCall(request).enqueue(new Callback() {
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
                        JSONArray citiesJsonArray = json.getJSONArray("cities");
                        mCities.clear();
                        for (int i = 0; i < citiesJsonArray.length(); i++)
                        {
                            JSONObject cityJsonObject = citiesJsonArray.getJSONObject(i);
                            City city = new City();
                            city.setName(cityJsonObject.getString("name"));
                            city.setState(cityJsonObject.getString("state"));
                            city.setLat(cityJsonObject.getDouble("lat"));
                            city.setLng(cityJsonObject.getDouble("lng"));

                            mCities.add(city);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                    catch(Exception e){}
                }
                else{}

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mListener = (CitiesListener) context;
    }

    public interface CitiesListener
    {
        void gotoWeatherForecast(City city);
    }
}