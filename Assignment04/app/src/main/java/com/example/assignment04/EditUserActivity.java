package com.example.assignment04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//Assignment 04
//EditUserActivity.java
//Ethan Fowler

public class EditUserActivity extends AppCompatActivity
{
    EditText editTextNameEdit;
    EditText editTextEmailEdit;
    RadioGroup editRoleRadioGroup;
    RadioButton editStudentRadioButton;
    RadioButton editEmployeeRadioButton;
    RadioButton editOtherRadioButton;
    static public final String EDIT_USER_KEY = "user";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNameEdit = findViewById(R.id.editTextNameEdit);
        editTextEmailEdit = findViewById(R.id.editTextEmailEdit);
        editRoleRadioGroup = findViewById(R.id.editRoleRadioGroup);
        editStudentRadioButton = findViewById(R.id.editStudentRadioButton);
        editEmployeeRadioButton = findViewById(R.id.editEmployeeRadioButton);
        editOtherRadioButton = findViewById(R.id.editOtherRadioButton);

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(ProfileActivity.PROFILE_USER_KEY))
        {
            user = (User) getIntent().getSerializableExtra(ProfileActivity.PROFILE_USER_KEY);
            if(user != null)
            {
                editTextNameEdit.setText(user.getName());
                editTextEmailEdit.setText(user.getEmail());
                if(user.getRole().equals("Student"))
                {
                    editStudentRadioButton.toggle();
                }
                else if(user.getRole().equals("Employee"))
                {
                    editEmployeeRadioButton.toggle();
                }
                else if(user.getRole().equals("Other"))
                {
                    editOtherRadioButton.toggle();
                }
                else
                {

                }

            }
            else
            {
                editTextNameEdit.setText("N/A");
                editTextEmailEdit.setText("N/A");
            }
        }

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = editTextNameEdit.getText().toString();
                String email = editTextEmailEdit.getText().toString();
                String role = "";

                if(editRoleRadioGroup.getCheckedRadioButtonId() == R.id.editStudentRadioButton)
                {
                    role = "Student";
                }
                else if(editRoleRadioGroup.getCheckedRadioButtonId() == R.id.editEmployeeRadioButton)
                {
                    role = "Employee";
                }
                else if(editRoleRadioGroup.getCheckedRadioButtonId() == R.id.editOtherRadioButton)
                {
                    role = "Other";
                }
                else
                {
                    role = "";
                }


                if(name.isEmpty())
                {
                    Toast.makeText(EditUserActivity.this, "Please Enter your name", Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty())
                {
                    Toast.makeText(EditUserActivity.this, "Please Enter your email", Toast.LENGTH_SHORT).show();
                }
                else if(role.isEmpty())
                {
                    Toast.makeText(EditUserActivity.this, "Please Select a Role", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    User updateUser = new User(name, email, role);
                    Intent intent = new Intent();
                    intent.putExtra(EDIT_USER_KEY, updateUser);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            }
        });

        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.putExtra(EDIT_USER_KEY, user);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}