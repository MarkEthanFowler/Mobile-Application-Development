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

import com.example.assignment08.databinding.FragmentSelectEduLevelBinding;
import com.example.assignment08.databinding.FragmentSelectStateBinding;


public class SelectEduLevelFragment extends Fragment
{

    private static final String ARG_PARAM_EDU_LEVEL = "ARG_PARAM_EDU_LEVEL";
    private String mEduLevel;

    FragmentSelectEduLevelBinding binding;
    SelectEduLevelFragmentListener mListener;


    RadioButton radioButton;

    public SelectEduLevelFragment() {}

    public static SelectEduLevelFragment newInstance(String eduLevel)
    {
        SelectEduLevelFragment fragment = new SelectEduLevelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_EDU_LEVEL, eduLevel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mEduLevel = getArguments().getString(ARG_PARAM_EDU_LEVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentSelectEduLevelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.eduLevelLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < Data.educationLevels.length; i++)
        {
            radioButton = new RadioButton(view.getContext());
            radioButton.setId(i);
            radioButton.setText(Data.educationLevels[i]);
            binding.eduLevelRadioGroup.addView(radioButton);
        }

        binding.submitEduLevelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(binding.eduLevelRadioGroup.getCheckedRadioButtonId() != -1)
                {
                    mEduLevel = Data.educationLevels[binding.eduLevelRadioGroup.getCheckedRadioButtonId()];
                }
                else
                {
                    mEduLevel = "";
                }

                if(mEduLevel.isEmpty())
                {
                    Toast.makeText(view.getContext(), "Please Enter your Edu Level", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    binding.eduLevelRadioGroup.removeAllViews();
                    mListener.gotoCreateUserWithEduLevelSubmit(mEduLevel);
                }

            }
        });

        binding.cancelEduLevelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.gotoCreateUserWithEduLevelCancel();
            }
        });
    }

    public interface SelectEduLevelFragmentListener
    {
        void gotoCreateUserWithEduLevelCancel();
        void gotoCreateUserWithEduLevelSubmit(String EduLevel);
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mListener = (SelectEduLevelFragmentListener) context;
    }
}