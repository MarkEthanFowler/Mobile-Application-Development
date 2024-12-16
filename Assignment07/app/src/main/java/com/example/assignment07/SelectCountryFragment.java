package com.example.assignment07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.assignment07.databinding.FragmentSelectCountryBinding;


public class SelectCountryFragment extends Fragment
{
    private static final String ARG_PARAM_COUNTRY = "ARG_PARAM_COUNTRY";
    private String mCountry;

    FragmentSelectCountryBinding binding;
    SelectCountryFragmentListener mListener;

    RadioButton radioButton;

    public SelectCountryFragment()
    {
        // Required empty public constructor
    }

    public static SelectCountryFragment newInstance(String country)
    {
        SelectCountryFragment fragment = new SelectCountryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_COUNTRY, country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mCountry = getArguments().getString(ARG_PARAM_COUNTRY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentSelectCountryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        binding.countryLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < Data.countries.length; i++)
        {
            radioButton = new RadioButton(view.getContext());
            radioButton.setId(i);
            radioButton.setText(Data.countries[i]);
            binding.countryRadioGroup.addView(radioButton);
        }

        binding.submitCountryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(binding.countryRadioGroup.getCheckedRadioButtonId() != -1)
                {
                    mCountry = Data.countries[binding.countryRadioGroup.getCheckedRadioButtonId()];
                }
                else
                {
                    mCountry = "";
                }

                if(mCountry.isEmpty())
                {
                    Toast.makeText(view.getContext(), "Please Enter your Country", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    binding.countryRadioGroup.removeAllViews();
                    mListener.gotoCreateUserWithCountrySubmit(mCountry);
                }

            }
        });

        binding.cancelCountryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.gotoCreateUserWithCountryCancel();
            }
        });

    }

    public interface SelectCountryFragmentListener
    {
        void gotoCreateUserWithCountryCancel();
        void gotoCreateUserWithCountrySubmit(String country);
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mListener = (SelectCountryFragmentListener) context;
    }
}