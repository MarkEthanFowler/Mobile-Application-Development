package com.example.assignment08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.assignment08.databinding.FragmentSelectMaritalStatusBinding;

public class SelectMaritalStatusFragment extends Fragment
{

    private static final String ARG_PARAM_MARITAL_STATUS = "ARG_PARAM_MARITAL_STATUS";
    private String mMaritalStatus;

    FragmentSelectMaritalStatusBinding binding;
    SelectMaritalStatusFragmentListener mListener;

    RadioButton radioButton;

    public SelectMaritalStatusFragment() {}


    public static SelectMaritalStatusFragment newInstance(String maritalStatus)
    {
        SelectMaritalStatusFragment fragment = new SelectMaritalStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_MARITAL_STATUS, maritalStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mMaritalStatus = getArguments().getString(ARG_PARAM_MARITAL_STATUS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentSelectMaritalStatusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.maritalStatusLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < Data.maritalStatus.length; i++)
        {
            radioButton = new RadioButton(view.getContext());
            radioButton.setId(i);
            radioButton.setText(Data.maritalStatus[i]);
            binding.maritalStatusRadioGroup.addView(radioButton);
        }

        binding.submitMaritalStatusButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(binding.maritalStatusRadioGroup.getCheckedRadioButtonId() != -1)
                {
                    mMaritalStatus = Data.maritalStatus[binding.maritalStatusRadioGroup.getCheckedRadioButtonId()];
                }
                else
                {
                    mMaritalStatus = "";
                }

                if(mMaritalStatus.isEmpty())
                {
                    Toast.makeText(view.getContext(), "Please Enter your Marital Status", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    binding.maritalStatusRadioGroup.removeAllViews();
                    mListener.gotoCreateUserWithMaritalStatusSubmit(mMaritalStatus);
                }

            }
        });

        binding.cancelMaritalStatusButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.gotoCreateUserWithMaritalStatusCancel();
            }
        });
    }

    public interface SelectMaritalStatusFragmentListener
    {
        void gotoCreateUserWithMaritalStatusCancel();
        void gotoCreateUserWithMaritalStatusSubmit(String MaritalStatus);
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mListener = (SelectMaritalStatusFragmentListener) context;
    }
}