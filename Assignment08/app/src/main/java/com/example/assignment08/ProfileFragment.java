package com.example.assignment08;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment08.databinding.FragmentProfileBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM_PROFILE = "ARG_PARAM_PROFILE";

    private User mUser;

    FragmentProfileBinding binding;

    ViewPager2 viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout tabLayout;

    public ProfileFragment() {}


    public static ProfileFragment newInstance(User user)
    {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_PROFILE, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mUser = (User) getArguments().getSerializable(ARG_PARAM_PROFILE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        viewPager = binding.profileViewPager;
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = binding.profileTabLayout;
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy()
        {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position)
            {
                if (position == 0)
                {
                    tab.setText("Contact Info");

                }
                else
                {
                    tab.setText("Demographics");
                }
            }
        }).attach();

    }

    public class ViewPagerAdapter extends FragmentStateAdapter
    {

        public ViewPagerAdapter(@NonNull Fragment fragment)
        {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position)
        {
            if(position == 0)
            {
                return ContactInfoFragment.newInstance(mUser);
            }
            else
            {
                return DemographicsFragment.newInstance(mUser);
            }

        }

        @Override
        public int getItemCount()
        {
            return 2;
        }
    }
}