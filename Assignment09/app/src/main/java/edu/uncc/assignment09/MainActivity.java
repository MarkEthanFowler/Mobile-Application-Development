package edu.uncc.assignment09;

//Assignment 09
//MainActivity.java
//Ethan Fowler and Raziuddin Syed Khaja

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.uncc.assignment09.R;

public class MainActivity extends AppCompatActivity implements GenresFragment.GenresListener, BooksFragment.BooksListener, BookDetailsFragment.BooksDetailsListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.main, new GenresFragment()).addToBackStack(null).commit();
    }

    @Override
    public void closeBooks()
    {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoBookDetails(Book book)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main, BookDetailsFragment.newInstance(book)).addToBackStack(null).commit();
    }

    @Override
    public void closeBookDetails()
    {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoBooksForGenre(String genre)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.main, BooksFragment.newInstance(genre)).addToBackStack(null).commit();
    }
}