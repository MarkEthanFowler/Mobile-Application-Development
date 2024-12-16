package edu.uncc.assignment09;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.assignment09.databinding.FragmentBooksBinding;

public class BooksFragment extends Fragment
{
    private static final String ARG_PARAM_GENRE = "ARG_PARAM_GENRE";
    private String mGenre;
    BooksAdapter adapter;

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
        binding.genreNameTextView.setText(mGenre);
        adapter = new BooksAdapter(view.getContext(), R.layout.books_row_item, mBooks);
        binding.bookListView.setAdapter(adapter);
        binding.bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mListener.gotoBookDetails(mBooks.get(position));
            }
        });
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


    public class BooksAdapter extends ArrayAdapter<Book>
    {

        public BooksAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects)
        {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            if(convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.books_row_item, parent, false);
            }
            Book book = getItem(position);
            TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
            TextView textViewAuthor = convertView.findViewById(R.id.textViewAuthor);
            TextView textViewGenres = convertView.findViewById(R.id.textViewGenres);
            TextView textViewYears = convertView.findViewById(R.id.textViewYears);
            if(book != null)
            {
                textViewTitle.setText(book.getTitle());
                textViewAuthor.setText(book.getAuthor());
                textViewGenres.setText(book.getGenre());
                textViewYears.setText(String.valueOf(book.getYear()));
            }
            return convertView;
        }
    }
}



