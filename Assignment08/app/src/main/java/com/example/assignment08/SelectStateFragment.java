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

import com.example.assignment08.databinding.FragmentSelectStateBinding;

public class SelectStateFragment extends Fragment
{

    private static final String ARG_PARAM_STATE = "ARG_PARAM_STATE";
    private String mState;

    FragmentSelectStateBinding binding;
    SelectStateFragmentListener mListener;

    RadioButton radioButton;

    public SelectStateFragment() {}

    public static SelectStateFragment newInstance(String state)
    {
        SelectStateFragment fragment = new SelectStateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_STATE, state);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mState = getArguments().getString(ARG_PARAM_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentSelectStateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.stateLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < Data.states.length; i++)
        {
            radioButton = new RadioButton(view.getContext());
            radioButton.setId(i);
            radioButton.setText(Data.states[i]);
            binding.stateRadioGroup.addView(radioButton);
        }

        binding.submitStateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(binding.stateRadioGroup.getCheckedRadioButtonId() != -1)
                {
                    mState = Data.states[binding.stateRadioGroup.getCheckedRadioButtonId()];
                }
                else
                {
                    mState = "";
                }

                if(mState.isEmpty())
                {
                    Toast.makeText(view.getContext(), "Please Enter your State", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    binding.stateRadioGroup.removeAllViews();
                    mListener.gotoCreateUserWithStateSubmit(mState);
                }

            }
        });

        binding.cancelStateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.gotoCreateUserWithStateCancel();
            }
        });
    }

    public interface SelectStateFragmentListener
    {
        void gotoCreateUserWithStateCancel();
        void gotoCreateUserWithStateSubmit(String state);
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mListener = (SelectStateFragmentListener) context;
    }
}