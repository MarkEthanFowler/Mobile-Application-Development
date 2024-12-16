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

import com.example.assignment06.databinding.FragmentEditUserBinding;
import com.example.assignment06.databinding.FragmentMainBinding;

public class EditUserFragment extends Fragment
{
    private static final String ARG_PARAM_EDIT_USER = "ARG_PARAM_EDIT_USER";

    private User mUser;

    FragmentEditUserBinding binding;
    EditUserFragmentListener mListener;

    public EditUserFragment()
    {
        // Required empty public constructor
    }

    public static EditUserFragment newInstance(User user)
    {
        EditUserFragment fragment = new EditUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_EDIT_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mUser = (User) getArguments().getSerializable(ARG_PARAM_EDIT_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentEditUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        binding.editTextNameEdit.setText(mUser.getName());
        binding.editTextEmailEdit.setText(mUser.getEmail());
        if(mUser.getRole().equals("Student"))
        {
            binding.editStudentRadioButton.toggle();
        }
        else if(mUser.getRole().equals("Employee"))
        {
            binding.editEmployeeRadioButton.toggle();
        }
        else if(mUser.getRole().equals("Other"))
        {
            binding.editOtherRadioButton.toggle();
        }
        else {}

        binding.cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.goBackToProfileWithNoChanges();
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = binding.editTextNameEdit.getText().toString();
                String email = binding.editTextEmailEdit.getText().toString();
                String role = "";

                if(binding.editRoleRadioGroup.getCheckedRadioButtonId() == R.id.editStudentRadioButton)
                {
                    role = "Student";
                }
                else if(binding.editRoleRadioGroup.getCheckedRadioButtonId() == R.id.editEmployeeRadioButton)
                {
                    role = "Employee";
                }
                else if(binding.editRoleRadioGroup.getCheckedRadioButtonId() == R.id.editOtherRadioButton)
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
                    mUser = new User(name, email, role);
                    mListener.goBackToProfileWithChanges(mUser);
                }
            }
        });
    }

    public interface EditUserFragmentListener
    {
        void goBackToProfileWithChanges(User user);
        void goBackToProfileWithNoChanges();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mListener = (EditUserFragmentListener) context;
    }
}