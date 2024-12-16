package com.example.assignment08;

//Assignment 08
//MainActivity.java
//Ethan Fowler and Raziuddin Syed Khaja

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, CreateUserFragment.CreateUserFragmentListener, SelectDoBFragment.SelectDoBFragmentListener, SelectMaritalStatusFragment.SelectMaritalStatusFragmentListener, SelectEduLevelFragment.SelectEduLevelFragmentListener, SelectStateFragment.SelectStateFragmentListener
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
    public void gotoSelectState()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main, new SelectStateFragment()).addToBackStack(null).commit();
    }

    @Override
    public void gotoSelectMaritalStatus()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main, new SelectMaritalStatusFragment()).addToBackStack(null).commit();
    }

    @Override
    public void gotoSelectEduLevel()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main, new SelectEduLevelFragment()).addToBackStack(null).commit();
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
    public void gotoCreateUserWithEduLevelCancel()
    {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoCreateUserWithEduLevelSubmit(String EduLevel)
    {
        CreateUserFragment createUserFragment = (CreateUserFragment) getSupportFragmentManager().findFragmentByTag("create-user-fragment");
        if(createUserFragment != null)
        {
            createUserFragment.setSelectedEduLevel(EduLevel);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void gotoCreateUserWithMaritalStatusCancel()
    {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoCreateUserWithMaritalStatusSubmit(String MaritalStatus)
    {
        CreateUserFragment createUserFragment = (CreateUserFragment) getSupportFragmentManager().findFragmentByTag("create-user-fragment");
        if(createUserFragment != null)
        {
            createUserFragment.setSelectedMaritalStatus(MaritalStatus);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void gotoCreateUserWithStateCancel()
    {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoCreateUserWithStateSubmit(String state)
    {
        CreateUserFragment createUserFragment = (CreateUserFragment) getSupportFragmentManager().findFragmentByTag("create-user-fragment");
        if(createUserFragment != null)
        {
            createUserFragment.setSelectedState(state);
            getSupportFragmentManager().popBackStack();
        }
    }
}