package com.example.assignment07;

//Assignment 07
//MainActivity.java
//Ethan Fowler and Raziuddin Syed Khaja

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, CreateUserFragment.CreateUserFragmentListener, SelectDoBFragment.SelectDoBFragmentListener, SelectCountryFragment.SelectCountryFragmentListener
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
        getSupportFragmentManager().beginTransaction().add(R.id.main, new MainFragment()).commit();
    }

    @Override
    public void gotoCreateUser()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main, new CreateUserFragment(), "create-user-fragment").addToBackStack(null).commit();
    }

    @Override
    public void gotoProfileWithUser(User user)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main, ProfileFragment.newInstance(user)).addToBackStack(null).commit();
    }

    @Override
    public void gotoSelectDoB()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main, new SelectDoBFragment()).addToBackStack(null).commit();
    }

    @Override
    public void gotoSelectCountry()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main, new SelectCountryFragment()).addToBackStack(null).commit();
    }

    @Override
    public void gotoCreateUserWithDoBCancel()
    {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoCreateUserWithDoBSubmit(String DoB)
    {
        CreateUserFragment createUserFragment = (CreateUserFragment) getSupportFragmentManager().findFragmentByTag("create-user-fragment");
        if(createUserFragment != null)
        {
            createUserFragment.setSelectedDoB(DoB);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void gotoCreateUserWithCountryCancel()
    {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoCreateUserWithCountrySubmit(String country)
    {
        CreateUserFragment createUserFragment = (CreateUserFragment) getSupportFragmentManager().findFragmentByTag("create-user-fragment");
        if(createUserFragment != null)
        {
            createUserFragment.setSelectedCountry(country);
            getSupportFragmentManager().popBackStack();
        }
    }
}