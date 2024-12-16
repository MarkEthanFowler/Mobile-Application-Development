package com.example.assignment08;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment08.databinding.FragmentContactInfoBinding;
import com.example.assignment08.databinding.FragmentDemographicsBinding;

public class DemographicsFragment extends Fragment
{

    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";
    private User mUser;

    FragmentDemographicsBinding binding;

    public DemographicsFragment()
    {
        // Required empty public constructor
    }

    public static DemographicsFragment newInstance(User user)
    {
        DemographicsFragment fragment = new DemographicsFragment();
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
        binding = FragmentDemographicsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewDoB.setText(mUser.getDoB());
        binding.profileTextViewMaritalStatus.setText(mUser.getMaritalStatus());
        binding.profileTextViewEduLevel.setText(mUser.getEduLevel());
    }
}