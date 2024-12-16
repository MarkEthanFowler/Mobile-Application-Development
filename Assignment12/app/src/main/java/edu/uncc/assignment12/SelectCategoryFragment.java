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

import edu.uncc.assignment12.databinding.CategoryRowItemBinding;
import edu.uncc.assignment12.databinding.FragmentSelectCategoryBinding;


public class SelectCategoryFragment extends Fragment {

    String[] mCategories = {"Housing", "Transportation", "Food", "Health", "Other"};

    public SelectCategoryFragment() {
        // Required empty public constructor
    }

    FragmentSelectCategoryBinding binding;
    LinearLayoutManager categoryLayoutManager;
    CategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setHasFixedSize(true);
        categoryLayoutManager = new LinearLayoutManager(view.getContext());
        binding.recyclerView.setLayoutManager(categoryLayoutManager);
        adapter = new CategoryAdapter(mCategories);
        binding.recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.onCancelSelectCategory();
            }
        });


    }

    SelectCategoryListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectCategoryListener) {
            mListener = (SelectCategoryListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectCategoryListener");
        }
    }

    interface SelectCategoryListener {
        void selectCategory(String category);
        void onCancelSelectCategory();
    }

    class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>
    {
        String[] categories;

        public CategoryAdapter(String[] categories)
        {
            this.categories = categories;
        }

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            CategoryRowItemBinding itemBinding = CategoryRowItemBinding.inflate(getLayoutInflater(), parent, false);
            CategoryViewHolder holder = new CategoryViewHolder(itemBinding);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position)
        {
            holder.SelectedCategory = categories[position];
            holder.setup();
        }

        @Override
        public int getItemCount()
        {
            return categories.length;
        }

        class CategoryViewHolder extends RecyclerView.ViewHolder
        {
            CategoryRowItemBinding itemBinding;
            String SelectedCategory;

            public CategoryViewHolder(CategoryRowItemBinding binding)
            {
                super(binding.getRoot());
                itemBinding = binding;
            }

            public void setup()
            {
                itemBinding.textViewCategoryItems.setText(SelectedCategory);
                itemBinding.getRoot().setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mListener.selectCategory(SelectedCategory);
                    }
                });
            }
        }
    }

}