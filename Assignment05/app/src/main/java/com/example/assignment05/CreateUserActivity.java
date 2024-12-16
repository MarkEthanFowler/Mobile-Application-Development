package com.example.assignment05;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class CreateUserActivity extends AppCompatActivity
{
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextAge;
    TextView textViewCountry;
    TextView textViewDob;

    int checked = -1;
    User user;
    String country;
    String DoB;

    static public final String CREATE_USER_KEY = "user";

    ActivityResultLauncher<Intent> startDobActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>()
    {
        @Override
        public void onActivityResult(ActivityResult o)
        {
            if(o.getResultCode() == RESULT_OK && o.getData() != null && o.getData().getSerializableExtra(DoBActivity.DOB_USER_KEY) != null)
            {
                DoB = (String) o.getData().getSerializableExtra(DoBActivity.DOB_USER_KEY);
                if(DoB != null)
                {
                    textViewDob.setText(DoB);
                }
                else
                {
                    textViewDob.setText("N/A");
                }
            }
        }
    });

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
        editTextAge = findViewById(R.id.editTextAge);
        textViewCountry = findViewById(R.id.textViewCountry);
        textViewDob = findViewById(R.id.textViewDob);

        findViewById(R.id.selectDobButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CreateUserActivity.this, DoBActivity.class);
                startDobActivityForResult.launch(intent);
            }
        });

        findViewById(R.id.selectCountryButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserActivity.this);

                builder.setTitle("Choose a Country")
                        .setSingleChoiceItems(Data.countries, checked, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                country = Data.countries[which];
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                textViewCountry.setText(country);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                country = "N/A";
                                textViewCountry.setText(country);
                            }
                        });


                        builder.create().show();
            }
        });

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String age = editTextAge.getText().toString();
                String country = textViewCountry.getText().toString();
                String DoB = textViewDob.getText().toString();

                if(name.isEmpty() || name.equals("N/A"))
                {
                    Toast.makeText(CreateUserActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty() || email.equals("N/A"))
                {
                    Toast.makeText(CreateUserActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }
                else if(age.isEmpty() || age.equals("N/A"))
                {
                    Toast.makeText(CreateUserActivity.this, "Please enter your age", Toast.LENGTH_SHORT).show();
                }
                else if(country.isEmpty() || country.equals("N/A"))
                {
                    Toast.makeText(CreateUserActivity.this, "Please enter your country of origin", Toast.LENGTH_SHORT).show();
                }
                else if(DoB.isEmpty() || DoB.equals("N/A"))
                {
                    Toast.makeText(CreateUserActivity.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(CreateUserActivity.this, ProfileActivity.class);
                    user = new User(name, email, Integer.parseInt(editTextAge.getText().toString()), country, DoB);
                    intent.putExtra(CREATE_USER_KEY, user);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}