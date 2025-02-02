package com.example.assignment08;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment08.databinding.FragmentContactInfoBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactInfoFragment extends Fragment
{

    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";
    private User mUser;

    FragmentContactInfoBinding binding;

    public ContactInfoFragment()
    {

    }

    public static ContactInfoFragment newInstance(User user)
    {
        ContactInfoFragment fragment = new ContactInfoFragment();
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
        binding = FragmentContactInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewName.setText(mUser.getName());
        binding.textViewEmail.setText(mUser.getEmail());
        binding.textViewPhone.setText(mUser.getPhone());
        binding.profileTextViewState.setText(mUser.getState());
    }
}