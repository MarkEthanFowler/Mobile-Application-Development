package com.example.assignment07;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assignment07.databinding.FragmentCreateUserBinding;
import com.example.assignment07.databinding.FragmentMainBinding;

public class CreateUserFragment extends Fragment
{
    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";
    private User mUser;
    private String selectedDoB;
    private String selectedCountry;
    Context context;

    FragmentCreateUserBinding binding;
    CreateUserFragmentListener mListener;

    public void setSelectedDoB(String selectedDoB)
    {
        this.selectedDoB = selectedDoB;
    }

    public void setSelectedCountry(String selectedCountry)
    {
        this.selectedCountry = selectedCountry;
    }

    public CreateUserFragment() {
        // Required empty public constructor
    }

    public static CreateUserFragment newInstance(User user)
    {
        CreateUserFragment fragment = new CreateUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mUser = (User) getArguments().getSerializable(ARG_PARAM_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentCreateUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if(selectedDoB == null)
        {
            binding.textViewDob.setText("N/A");
        }
        else
        {
            binding.textViewDob.setText(selectedDoB);
        }

        if(selectedCountry == null)
        {
            binding.textViewCountry.setText("N/A");
        }
        else
        {
            binding.textViewCountry.setText(selectedCountry);
        }

        binding.selectDobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.gotoSelectDoB();
            }
        });

        binding.selectCountryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.gotoSelectCountry();
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String age = binding.editTextAge.getText().toString();
                String country = binding.textViewCountry.getText().toString();
                String DoB = binding.textViewDob.getText().toString();

                if(name.isEmpty() || name.equals("N/A"))
                {
                    Toast.makeText(view.getContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty() || email.equals("N/A"))
                {
                    Toast.makeText(view.getContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                }
                else if(age.isEmpty() || age.equals("N/A"))
                {
                    Toast.makeText(view.getContext(), "Please enter your age", Toast.LENGTH_SHORT).show();
                }
                else if(country.isEmpty() || country.equals("N/A"))
                {
                    Toast.makeText(view.getContext(), "Please enter your country of origin", Toast.LENGTH_SHORT).show();
                }
                else if(DoB.isEmpty() || DoB.equals("N/A"))
                {
                    Toast.makeText(view.getContext(), "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    User user = new User(name, email, Integer.parseInt(age), country, DoB);
                    mListener.gotoProfileWithUser(user);
                }
            }
        });
    }

    public interface CreateUserFragmentListener
    {
        void gotoProfileWithUser(User user);
        void gotoSelectDoB();
        void gotoSelectCountry();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mListener = (CreateUserFragmentListener) context;
    }
}