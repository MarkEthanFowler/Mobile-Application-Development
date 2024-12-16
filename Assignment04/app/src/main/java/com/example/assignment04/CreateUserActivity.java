package com.example.assignment04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//Assignment 04
//CreateUserActivity.java
//Ethan Fowler

public class CreateUserActivity extends AppCompatActivity
{
    EditText editTextName;
    EditText editTextEmail;
    RadioGroup roleRadioGroup;
    static public final String USER_KEY = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);


        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String role = "";

                if(roleRadioGroup.getCheckedRadioButtonId() == R.id.studentRadioButton)
                {
                    role = "Student";
                }
                else if(roleRadioGroup.getCheckedRadioButtonId() == R.id.employeeRadioButton)
                {
                    role = "Employee";
                }
                else if(roleRadioGroup.getCheckedRadioButtonId() == R.id.otherRadioButton)
                {
                    role = "Other";
                }
                else
                {
                    role = "";
                }


                if(name.isEmpty())
                {
                    Toast.makeText(CreateUserActivity.this, "Please Enter your name", Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty())
                {
                    Toast.makeText(CreateUserActivity.this, "Please Enter your email", Toast.LENGTH_SHORT).show();
                }
                else if(role.isEmpty())
                {
                    Toast.makeText(CreateUserActivity.this, "Please Select a Role", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(CreateUserActivity.this, ProfileActivity.class);
                    User user = new User(name, email, role);
                    intent.putExtra(USER_KEY, user);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }
}