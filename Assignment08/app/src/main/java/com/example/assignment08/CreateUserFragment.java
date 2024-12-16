package com.example.assignment08;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assignment08.databinding.FragmentCreateUserBinding;


public class CreateUserFragment extends Fragment
{
    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";
    private User mUser;
    private String selectedDoB;
    private String selectedState;
    private String selectedEduLevel;
    private String selectedMaritalStatus;
    Context context;

    FragmentCreateUserBinding binding;
    CreateUserFragmentListener mListener;

    public void setSelectedDoB(String selectedDoB)
    {
        this.selectedDoB = selectedDoB;
    }

    public void setSelectedState(String selectedState)
    {
        this.selectedState = selectedState;
    }

    public void setSelectedEduLevel(String selectedEduLevel)
    {
        this.selectedEduLevel = selectedEduLevel;
    }

    public void setSelectedMaritalStatus(String selectedMaritalStatus)
    {
        this.selectedMaritalStatus = selectedMaritalStatus;
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

        if(selectedState == null)
        {
            binding.textViewState.setText("N/A");
        }
        else
        {
            binding.textViewState.setText(selectedState);
        }

        if(selectedEduLevel == null)
        {
            binding.textViewEduLevel.setText("N/A");
        }
        else
        {
            binding.textViewEduLevel.setText(selectedEduLevel);
        }

        if(selectedMaritalStatus == null)
        {
            binding.textViewMaritialStatus.setText("N/A");
        }
        else
        {
            binding.textViewMaritialStatus.setText(selectedMaritalStatus);
        }

        binding.selectDobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.gotoSelectDoB();
            }
        });

        binding.selectStateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.gotoSelectState();
            }
        });

        binding.selectEduLevelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.gotoSelectEduLevel();
            }
        });

        binding.selectMaritialStatusButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.gotoSelectMaritalStatus();
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String phone = binding.editTextPhone.getText().toString();
                String state = binding.textViewState.getText().toString();
                String DoB = binding.textViewDob.getText().toString();
                String maritalStatus = binding.textViewMaritialStatus.getText().toString();
                String eduLevel = binding.textViewEduLevel.getText().toString();

                if(name.isEmpty() || name.equals("N/A"))
                {
                    Toast.makeText(view.getContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty() || email.equals("N/A"))
                {
                    Toast.makeText(view.getContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                }
                else if(phone.isEmpty() || phone.equals("N/A"))
                {
                    Toast.makeText(view.getContext(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
                }
                else if(state.isEmpty() || state.equals("N/A"))
                {
                    Toast.makeText(view.getContext(), "Please enter your state", Toast.LENGTH_SHORT).show();
                }
                else if(DoB.isEmpty() || DoB.equals("N/A"))
                {
                    Toast.makeText(view.getContext(), "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                }
                else if(maritalStatus.isEmpty() || maritalStatus.equals("N/A"))
                {
                    Toast.makeText(view.getContext(), "Please enter your Marital Status", Toast.LENGTH_SHORT).show();
                }
                else if(eduLevel.isEmpty() || eduLevel.equals("N/A"))
                {
                    Toast.makeText(view.getContext(), "Please enter your Edu Level", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    User user = new User(name, email, phone, state, DoB, maritalStatus, eduLevel);
                    mListener.gotoProfileWithUser(user);
                }
            }
        });
    }

    public interface CreateUserFragmentListener
    {
        void gotoProfileWithUser(User user);
        void gotoSelectDoB();
        void gotoSelectState();
        void gotoSelectMaritalStatus();
        void gotoSelectEduLevel();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mListener = (CreateUserFragmentListener) context;
    }
}