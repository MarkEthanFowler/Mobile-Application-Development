package edu.uncc.assignment10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import edu.uncc.assignment10.databinding.FragmentBillsBinding;

public class BillsFragment extends Fragment {
    public BillsFragment() {
        // Required empty public constructor
    }

    String sortAttribute = "Date", sortOrder = "ASC";
    BillsAdapter adapter;

    public void setSortItems(String sortAttribute, String sortOrder)
    {
        this.sortAttribute = sortAttribute;
        this.sortOrder = sortOrder;


    }

    FragmentBillsBinding binding;

    private ArrayList<Bill> mBills = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);



        mBills.clear();
        mBills.addAll(mListener.getAllBills());


        binding.textViewSortedBy.setText("Sorted By " + sortAttribute + " (" + sortOrder + ")");

        adapter = new BillsAdapter(view.getContext(), R.layout.bills_row_item, mBills);
        binding.listView.setAdapter(adapter);

        if(sortAttribute != null && sortOrder != null)
        {
            if(sortAttribute.equals("Date") && sortOrder.equals("ASC"))
            {
                Collections.sort(mBills, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill b1, Bill b2)
                    {
                        return b1.getBillDate().compareTo(b2.getBillDate());
                    }
                });
            }
            else if (sortAttribute.equals("Date") && sortOrder.equals("DESC"))
            {
                Collections.sort(mBills, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill b1, Bill b2)
                    {
                        return b2.getBillDate().compareTo(b1.getBillDate());
                    }
                });
            }
            else if(sortAttribute.equals("Category") && sortOrder.equals("ASC"))
            {
                Collections.sort(mBills, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill b1, Bill b2)
                    {
                        return b1.getCategory().compareTo(b2.getCategory());
                    }
                });
            }
            else if (sortAttribute.equals("Category") && sortOrder.equals("DESC"))
            {
                Collections.sort(mBills, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill b1, Bill b2)
                    {
                        return b2.getCategory().compareTo(b1.getCategory());
                    }
                });
            }
            else if(sortAttribute.equals("Discount") && sortOrder.equals("ASC"))
            {
                Collections.sort(mBills, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill b1, Bill b2)
                    {
                        return (int) (b1.getDiscount() - b2.getDiscount());
                    }
                });
            }
            else if (sortAttribute.equals("Discount") && sortOrder.equals("DESC"))
            {
                Collections.sort(mBills, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill b1, Bill b2)
                    {
                        return (int) (-1 * (b1.getDiscount() - b2.getDiscount()));
                    }
                });
            }
            else{}
        }
        mListener.getAllBills();
        adapter.notifyDataSetChanged();

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mListener.goToBillSummary(mBills.get(position));
            }
        });

        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAllBills();
                mBills.clear();
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoCreateBill();
            }
        });

        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSortSelection();
                adapter.notifyDataSetChanged();
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
        ArrayList<Bill> getAllBills();
        void gotoCreateBill();
        void gotoSortSelection();
        void clearAllBills();
    }


    public class BillsAdapter extends ArrayAdapter<Bill>
    {
        public BillsAdapter(@NonNull Context context, int resource, @NonNull List<Bill> objects)
        {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            if(convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.bills_row_item, parent, false);
            }
            Bill bill = getItem(position);
            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewBillsAmount = convertView.findViewById(R.id.textViewBillsAmount);
            TextView textViewDiscounts = convertView.findViewById(R.id.textViewDiscounts);
            TextView textViewTotalBill = convertView.findViewById(R.id.textViewTotalBill);
            TextView textViewDate = convertView.findViewById(R.id.textViewDate);
            TextView textViewCategories =  convertView.findViewById(R.id.textViewCategories);
            if(bill != null)
            {
                textViewName.setText(bill.getName());
                textViewBillsAmount.setText(String.valueOf("$" + bill.getAmount()));
                textViewDiscounts.setText(String.valueOf(bill.getDiscount()) + " ($" + (bill.getAmount() * (bill.getDiscount() / 100)) + ")");
                textViewTotalBill.setText("$" + String.valueOf((bill.getAmount() - (bill.getAmount() * (bill.getDiscount() / 100)))));
                textViewDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(bill.getBillDate()));
                textViewCategories.setText(String.valueOf(bill.getCategory()));
            }
            return convertView;
        }
    }

}