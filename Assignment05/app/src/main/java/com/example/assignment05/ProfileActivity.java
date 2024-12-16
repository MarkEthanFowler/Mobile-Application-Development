package com.example.assignment05;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity
{

    TextView textViewName;
    TextView textViewEmail;
    TextView textViewAge;
    TextView profileTextViewCountry;
    TextView textViewDoB;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewAge = findViewById(R.id.textViewAge);
        profileTextViewCountry = findViewById(R.id.profileTextViewCountry);
        textViewDoB = findViewById(R.id.textViewDoB);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(CreateUserActivity.CREATE_USER_KEY))
        {
            user = (User) getIntent().getSerializableExtra(CreateUserActivity.CREATE_USER_KEY);

            if(user != null)
            {
                textViewName.setText(user.getName());
                textViewEmail.setText(user.getEmail());
                textViewAge.setText(String.valueOf(user.getAge()));
                profileTextViewCountry.setText(user.getCountry());
                textViewDoB.setText(user.getDob());
            }
        }
    }
}