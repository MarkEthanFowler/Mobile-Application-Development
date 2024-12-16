package edu.uncc.assignment12;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.util.ArrayList;

import edu.uncc.assignment12.databinding.DiscountRowItemBinding;
import edu.uncc.assignment12.databinding.FragmentSelectDiscountBinding;


public class SelectDiscountFragment extends Fragment {
    public SelectDiscountFragment() {
        // Required empty public constructor
    }

    FragmentSelectDiscountBinding binding;
    ArrayList<String> discounts = new ArrayList<>();

    LinearLayoutManager discountLayoutManager;
    DiscountAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectDiscountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        discounts.add("10%");
        discounts.add("15%");
        discounts.add("18%");
        discounts.add("Custom");

        binding.seekBar.setMax(50);
        binding.seekBar.setProgress(25);

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textViewSeekBarProgress.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.recyclerView.setHasFixedSize(true);
        discountLayoutManager = new LinearLayoutManager(view.getContext());
        binding.recyclerView.setLayoutManager(discountLayoutManager);
        adapter = new DiscountAdapter(discounts);
        binding.recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelectDiscount();
            }
        });
    }

    SelectDiscountListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectDiscountListener) {
            mListener = (SelectDiscountListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectDiscountListener");
        }
    }

    interface SelectDiscountListener {
        void onDiscountSelected(double discount);
        void onCancelSelectDiscount();
    }

    class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder>
    {
        ArrayList<String> discounts;

        public DiscountAdapter(ArrayList<String> discounts)
        {
            this.discounts = discounts;
        }

        @NonNull
        @Override
        public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            DiscountRowItemBinding itemBinding = DiscountRowItemBinding.inflate(getLayoutInflater(), parent, false);
            DiscountViewHolder holder = new DiscountViewHolder(itemBinding);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull DiscountViewHolder holder, int position)
        {
            holder.SelectedDiscount = discounts.get(position);
            holder.setup();
        }

        @Override
        public int getItemCount()
        {
            return discounts.size();
        }

        class DiscountViewHolder extends RecyclerView.ViewHolder
        {
            DiscountRowItemBinding itemBinding;
            String SelectedDiscount;

            public DiscountViewHolder(DiscountRowItemBinding binding)
            {
                super(binding.getRoot());
                itemBinding = binding;
            }

            public void setup()
            {
                itemBinding.textViewDiscountAmount.setText(SelectedDiscount);

                itemBinding.getRoot().setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(SelectedDiscount.equals("10%"))
                        {
                            mListener.onDiscountSelected(10);
                        }
                        else if(SelectedDiscount.equals("15%"))
                        {
                            mListener.onDiscountSelected(15);
                        }
                        else if(SelectedDiscount.equals("18%"))
                        {
                            mListener.onDiscountSelected(18);
                        }
                        else if(SelectedDiscount.equals("Custom"))
                        {
                            mListener.onDiscountSelected(binding.seekBar.getProgress());
                        }
                        else {}
                    }
                });

            }
        }
    }
}