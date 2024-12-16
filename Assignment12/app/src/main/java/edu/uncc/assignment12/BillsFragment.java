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
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.uncc.assignment12.databinding.BillRowItemsBinding;
import edu.uncc.assignment12.databinding.FragmentBillsBinding;

public class BillsFragment extends Fragment {
    public BillsFragment() {
        // Required empty public constructor
    }

    FragmentBillsBinding binding;

    LinearLayoutManager billsLayoutManager;
    BillsAdapter adapter;

    private ArrayList<Bill> mBills = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBills.clear();
        mBills.addAll(mListener.getAllBills());

        binding.recyclerView.setHasFixedSize(true);
        billsLayoutManager = new LinearLayoutManager(view.getContext());
        binding.recyclerView.setLayoutManager(billsLayoutManager);
        adapter = new BillsAdapter(mBills);
        binding.recyclerView.setAdapter(adapter);

        binding.buttonClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.clearAllBills();
                mBills.clear();
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonNew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.gotoCreateBill();
            }
        });
    }

    BillsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BillsListener) {
            mListener = (BillsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BillsListener");
        }
    }

    interface BillsListener {
        void goToBillSummary(Bill bill);
        void goToEditBill(Bill bill);
        ArrayList<Bill> getAllBills();
        void removeBill(Bill bill);
        void gotoCreateBill();
        void clearAllBills();
    }

    class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.BillsViewHolder>
    {
        ArrayList<Bill> bills;

        public BillsAdapter(ArrayList<Bill> bills)
        {
            this.bills = bills;
        }

        @NonNull
        @Override
        public BillsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            BillRowItemsBinding itemsBinding = BillRowItemsBinding.inflate(getLayoutInflater(), parent, false);
            BillsViewHolder holder = new BillsViewHolder(itemsBinding);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BillsViewHolder holder, int position)
        {
            holder.SelectedBill = bills.get(position);
            holder.setup();
        }

        @Override
        public int getItemCount()
        {
            return bills.size();
        }

        class BillsViewHolder extends RecyclerView.ViewHolder
        {
            BillRowItemsBinding itemsBinding;
            Bill SelectedBill;

            public BillsViewHolder(BillRowItemsBinding binding)
            {
                super(binding.getRoot());
                itemsBinding = binding;
            }

            public void setup()
            {
                itemsBinding.textViewName.setText(SelectedBill.getName());
                itemsBinding.textViewAmount.setText(String.valueOf(SelectedBill.getAmount()));
                itemsBinding.textViewDiscounts.setText(String.valueOf(SelectedBill.getDiscount()) + " (" + (SelectedBill.getAmount() * (SelectedBill.getDiscount() / 100)) + ")");
                itemsBinding.textViewTotal.setText(String.valueOf(SelectedBill.getAmount() - (SelectedBill.getAmount() * (SelectedBill.getDiscount() / 100))));
                itemsBinding.textViewDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(SelectedBill.getBillDate()));
                itemsBinding.textViewCategories.setText(SelectedBill.getCategory());

                itemsBinding.getRoot().setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mListener.goToBillSummary(SelectedBill);
                    }
                });

                itemsBinding.imageViewEdit.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mListener.goToEditBill(SelectedBill);
                    }
                });

                itemsBinding.imageViewDelete.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mListener.removeBill(SelectedBill);
                        mBills.remove(SelectedBill);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }
}