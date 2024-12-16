package com.example.assignment06;

//Assignment 06
//MainActivity.java
//Ethan Fowler and Raziuddin Syed Khaja

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, CreateUserFragment.CreateUserFragmentListener, ProfileFragment.ProfileFragmentListener, EditUserFragment.EditUserFragmentListener
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
        getSupportFragmentManager().beginTransaction().replace(R.id.main, new CreateUserFragment()).addToBackStack(null).commit();
    }

    @Override
    public void gotoProfileWithUser(User user)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main, ProfileFragment.newInstance(user), "profile-fragment").addToBackStack(null).commit();
    }

    @Override
    public void gotoEditUserWithUser(User user)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main, EditUserFragment.newInstance(user)).addToBackStack(null).commit();
    }

    @Override
    public void goBackToProfileWithChanges(User user)
    {
        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("profile-fragment");
        if(profileFragment != null)
        {
            profileFragment.setUpdatedUser(user);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void goBackToProfileWithNoChanges()
    {
        getSupportFragmentManager().popBackStack();
    }
}