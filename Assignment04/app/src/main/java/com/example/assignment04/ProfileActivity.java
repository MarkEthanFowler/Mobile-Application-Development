package com.example.assignment04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

//Assignment 04
//ProfileActivity.java
//Ethan Fowler

public class ProfileActivity extends AppCompatActivity
{
    TextView textViewName;
    TextView textViewEmail;
    TextView textViewRole;
    User user;
    User updateUser;
    static public final String PROFILE_USER_KEY = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ActivityResultLauncher<Intent> startEditActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>()
        {
            @Override
            public void onActivityResult(ActivityResult o)
            {
                if(o.getResultCode() == RESULT_OK && o.getData() != null && o.getData().getSerializableExtra(EditUserActivity.EDIT_USER_KEY) != null)
                {
                    updateUser = (User) o.getData().getSerializableExtra(EditUserActivity.EDIT_USER_KEY);
                    if(updateUser != null)
                    {
                        textViewName.setText(updateUser.getName());
                        textViewEmail.setText(updateUser.getEmail());
                        textViewRole.setText(updateUser.getRole());
                        user = updateUser;
                    }
                    else
                    {
                        textViewName.setText("N/A");
                        textViewEmail.setText("N/A");
                        textViewRole.setText("N/A");
                    }

                }
            }
        });

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
        textViewRole = findViewById(R.id.textViewRole);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(CreateUserActivity.USER_KEY))
        {
            user = (User) getIntent().getSerializableExtra(CreateUserActivity.USER_KEY);
            if(user != null)
            {
                textViewName.setText(user.getName());
                textViewEmail.setText(user.getEmail());
                textViewRole.setText(user.getRole());
            }
            else
            {
                textViewName.setText("N/A");
                textViewEmail.setText("N/A");
                textViewRole.setText("N/A");
            }

        }

        findViewById(R.id.updateButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this, EditUserActivity.class);
                intent.putExtra(PROFILE_USER_KEY, user);
                startEditActivityForResult.launch(intent);
            }
        });
    }
}