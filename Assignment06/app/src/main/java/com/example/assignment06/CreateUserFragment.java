package com.example.assignment06;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assignment06.databinding.FragmentCreateUserBinding;
import com.example.assignment06.databinding.FragmentMainBinding;

public class CreateUserFragment extends Fragment
{
    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";
    private User mUser;
    private User user;

    FragmentCreateUserBinding binding;
    CreateUserFragmentListener mListener;



    public CreateUserFragment() {}

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

        binding.nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(user != null)
                {
                    mUser = user;
                }

                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String role;

                if(binding.roleRadioGroup.getCheckedRadioButtonId() == R.id.studentRadioButton)
                {
                    role = "Student";
                }
                else if(binding.roleRadioGroup.getCheckedRadioButtonId() == R.id.employeeRadioButton)
                {
                    role = "Employee";
                }
                else if(binding.roleRadioGroup.getCheckedRadioButtonId() == R.id.otherRadioButton)
                {
                    role = "Other";
                }
                else
                {
                    role = "";
                }
                if(name.isEmpty())
                {
                    Toast.makeText(view.getContext(), "Please Enter your name", Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty())
                {
                    Toast.makeText(view.getContext(), "Please Enter your email", Toast.LENGTH_SHORT).show();
                }
                else if(role.isEmpty())
                {
                    Toast.makeText(view.getContext(), "Please Select a Role", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    user = new User(name, email, role);
                    mListener.gotoProfileWithUser(user);
                }
            }
        });
    }

    public interface CreateUserFragmentListener
    {
        void gotoProfileWithUser(User user);
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mListener = (CreateUserFragmentListener) context;
    }
}