package edu.uncc.posts;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.uncc.posts.models.AuthResponse;

//Assignment 16
//MainActivity.java
//Ethan Fowler Raziuddin Syed Khaja

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, SignUpFragment.SignUpListener, PostsFragment.PostsListener, CreatePostFragment.CreatePostListener
{
    public SharedPreferences loginCredentials;

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

        loginCredentials = getSharedPreferences("token", MODE_PRIVATE);


        String token = loginCredentials.getString("token", "");
        if(token.equals(""))
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new LoginFragment())
                    .commit();
        }
        else
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new PostsFragment()).commit();
        }

    }

    @Override
    public void createNewAccount()
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SignUpFragment())
                .commit();
    }

    @Override
    public void authCompleted(AuthResponse authResponse)
    {
        SharedPreferences.Editor editor = loginCredentials.edit();

        editor.putString("token", authResponse.getToken());
        editor.putString("userName", authResponse.getUser_fullname());
        editor.putString("userId", authResponse.getUser_id());
        editor.apply();

        getSupportFragmentManager().beginTransaction().replace(R.id.main, new PostsFragment()).commit();
    }

    @Override
    public void login()
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void logout()
    {
        SharedPreferences.Editor editor = loginCredentials.edit();

        editor.putString("token", "");
        editor.apply();

        getSupportFragmentManager().beginTransaction().replace(R.id.main, new LoginFragment()).commit();
    }

    @Override
    public void createPost()
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreatePostFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goBackToPosts()
    {
        getSupportFragmentManager().popBackStack();
    }
}