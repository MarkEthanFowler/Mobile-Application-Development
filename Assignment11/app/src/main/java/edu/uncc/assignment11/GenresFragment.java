package edu.uncc.assignment11;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import edu.uncc.assignment11.databinding.FragmentGenresBinding;
import edu.uncc.assignment11.databinding.GenreRowItemBinding;

public class GenresFragment extends Fragment {
    public GenresFragment() {
        // Required empty public constructor
    }

    FragmentGenresBinding binding;
    LinearLayoutManager genreLayoutManager;
    GenresAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGenresBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<String> mGenres = Data.getAllGenres();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Genres");

        binding.genresRecyclerView.setHasFixedSize(true);
        genreLayoutManager = new LinearLayoutManager(view.getContext());
        binding.genresRecyclerView.setLayoutManager(genreLayoutManager);
        adapter = new GenresAdapter(mGenres);
        binding.genresRecyclerView.setAdapter(adapter);


    }

    GenresListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GenresListener) {
            mListener = (GenresListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GenresListener");
        }
    }

    interface GenresListener {
        void gotoBooksForGenre(String genre);
    }

    class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresViewHolder>
    {
        ArrayList<String> genre;

        public GenresAdapter(ArrayList<String> genre)
        {
            this.genre = genre;
        }

        @NonNull
        @Override
        public GenresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            GenreRowItemBinding itemBinding = GenreRowItemBinding.inflate(getLayoutInflater(), parent, false);
            GenresViewHolder holder = new GenresViewHolder(itemBinding);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull GenresViewHolder holder, int position)
        {
            holder.CurrentGenre = genre.get(position);
            holder.setup();
        }

        @Override
        public int getItemCount() {
            return mGenres.size();
        }

        class GenresViewHolder extends RecyclerView.ViewHolder
        {
            GenreRowItemBinding itemBinding;
            String CurrentGenre;
            public GenresViewHolder(GenreRowItemBinding binding)
            {
                super(binding.getRoot());
                itemBinding = binding;
            }

            public void setup()
            {
                itemBinding.textViewGenreName.setText(CurrentGenre);

                itemBinding.getRoot().setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mListener.gotoBooksForGenre(CurrentGenre);
                    }
                });
            }
        }
    }
}