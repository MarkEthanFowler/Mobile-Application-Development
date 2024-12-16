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

import java.util.ArrayList;

import edu.uncc.assignment11.databinding.BookRowItemBinding;
import edu.uncc.assignment11.databinding.FragmentBooksBinding;

public class BooksFragment extends Fragment {
    private static final String ARG_PARAM_GENRE = "ARG_PARAM_GENRE";
    private String mGenre;

    public static BooksFragment newInstance(String genre) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_GENRE, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGenre = getArguments().getString(ARG_PARAM_GENRE);
        }
    }

    ArrayList<Book> mBooks = new ArrayList<>();

    public BooksFragment() {
        // Required empty public constructor
    }

    FragmentBooksBinding binding;
    LinearLayoutManager booksLayoutManager;
    BooksAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Books");
        mBooks.clear();
        mBooks.addAll(Data.getBooksByGenre(mGenre));

        binding.textViewGenreTitle.setText(mGenre);

        binding.booksRecyclerView.setHasFixedSize(true);
        booksLayoutManager = new LinearLayoutManager(view.getContext());
        binding.booksRecyclerView.setLayoutManager(booksLayoutManager);
        adapter = new BooksAdapter(mBooks);
        binding.booksRecyclerView.setAdapter(adapter);

        binding.buttonBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.closeBooks();
            }
        });
    }

    BooksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BooksListener) {
            mListener = (BooksListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BooksListener");
        }
    }

    interface BooksListener{
        void closeBooks();
        void gotoBookDetails(Book book);
    }

    class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder>
    {
        ArrayList<Book> books;

        public BooksAdapter(ArrayList<Book> books)
        {
            this.books = books;
        }

        @NonNull
        @Override
        public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            BookRowItemBinding itemBinding = BookRowItemBinding.inflate(getLayoutInflater(), parent, false);
            BooksViewHolder holder  = new BooksViewHolder(itemBinding);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BooksViewHolder holder, int position)
        {
            holder.SelectedBook = books.get(position);
            holder.setup();
        }

        @Override
        public int getItemCount()
        {
            return mBooks.size();
        }

        class BooksViewHolder extends RecyclerView.ViewHolder
        {
            BookRowItemBinding itemBinding;
            Book SelectedBook;

            public BooksViewHolder(BookRowItemBinding binding)
            {
                super(binding.getRoot());
                itemBinding = binding;
            }

            public void setup()
            {
                itemBinding.textViewTitle.setText(SelectedBook.getTitle());
                itemBinding.textViewAuthor.setText(SelectedBook.getAuthor());
                itemBinding.textViewGenres.setText(SelectedBook.getGenre());
                itemBinding.textViewBookYear.setText(String.valueOf(SelectedBook.getYear()));

                itemBinding.getRoot().setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mListener.gotoBookDetails(SelectedBook);
                    }
                });
            }
        }
    }
}