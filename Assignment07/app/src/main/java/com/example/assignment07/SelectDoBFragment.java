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
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.assignment07.databinding.FragmentSelectDoBBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectDoBFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectDoBFragment extends Fragment
{

    private static final String ARG_PARAM_DOB = "ARG_PARAM_DOB";
    private String mDoB;

    FragmentSelectDoBBinding binding;
    SelectDoBFragmentListener mListener;

    public SelectDoBFragment()
    {
        // Required empty public constructor
    }

    public static SelectDoBFragment newInstance(String DoB)
    {
        SelectDoBFragment fragment = new SelectDoBFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_DOB, DoB);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mDoB = getArguments().getString(ARG_PARAM_DOB);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentSelectDoBBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        binding.DoBCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                mDoB = String.valueOf(month + 1) + "/" + dayOfMonth + "/" + year;
            }
        });

        binding.cancelDoBButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.gotoCreateUserWithDoBCancel();
            }
        });

        binding.submitDoBButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mDoB != null)
                {
                    if(mDoB.isEmpty())
                    {
                        Toast.makeText(view.getContext(), "Please Enter your DoB", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mListener.gotoCreateUserWithDoBSubmit(mDoB);
                    }
                }
                else
                {
                    Toast.makeText(view.getContext(), "Please Enter your DoB", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface SelectDoBFragmentListener
    {
        void gotoCreateUserWithDoBCancel();
        void gotoCreateUserWithDoBSubmit(String DoB);
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mListener = (SelectDoBFragmentListener) context;
    }
}