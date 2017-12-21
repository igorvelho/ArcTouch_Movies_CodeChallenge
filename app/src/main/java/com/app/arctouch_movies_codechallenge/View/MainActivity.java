package com.app.arctouch_movies_codechallenge.View;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.app.arctouch_movies_codechallenge.Helper.DownloadImagesTask;
import com.app.arctouch_movies_codechallenge.Helper.MovieAdapter;
import com.app.arctouch_movies_codechallenge.Helper.SharedPrefsHelper;
import com.app.arctouch_movies_codechallenge.Model.GenreDetail;
import com.app.arctouch_movies_codechallenge.Model.GenreList;
import com.app.arctouch_movies_codechallenge.Model.MovieDetail;
import com.app.arctouch_movies_codechallenge.Model.MovieList;
import com.app.arctouch_movies_codechallenge.R;
import com.app.arctouch_movies_codechallenge.Helper.RetrieveAPIDataTask;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private SharedPrefsHelper sharedPrefs;
    private ListView upcomingMoviesList;
    private EditText editTextMovies;
    private ImageButton btnSearch;
    private int pageNumber = 1;
    private MovieList movies;
    private MovieAdapter moviesAdapter;
    private MovieList searchMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefs = new SharedPrefsHelper(this);

        upcomingMoviesList = findViewById(R.id.upcomingMoviesList);
        editTextMovies = findViewById(R.id.editTextMovies);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchMovies();
            }
        });

        upcomingMoviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                MovieDetailFragment dialog = new MovieDetailFragment();

                Bundle b = new Bundle();
                Gson gson = new Gson();
                b.putString("movieDetail", gson.toJson(movies.getResults().get(position)));

                dialog.setArguments(b);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                MovieDetailFragment tPrev =  ( MovieDetailFragment ) fragmentManager.findFragmentByTag("movieDetail");

                if(tPrev!=null)
                    fragmentTransaction.remove(tPrev);

                dialog.show(fragmentTransaction, "movieDetail");
            }
        });

        upcomingMoviesList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (upcomingMoviesList.getLastVisiblePosition() - upcomingMoviesList.getHeaderViewsCount() -
                        upcomingMoviesList.getFooterViewsCount()) >= (upcomingMoviesList.getCount() - 1)) {
                    if(editTextMovies.getText().length() == 0) {
                        pageNumber++;
                        LoadUpcomingMovies(true);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        LoadGenres();
        LoadUpcomingMovies(false);

    }

    private void LoadGenres()
    {
        String genreJson = "";
        try {
            RetrieveAPIDataTask taskGenre = new RetrieveAPIDataTask();
            genreJson = taskGenre.execute("genre/movie/list").get();

            Gson gson = new Gson();
            GenreList genreList = gson.fromJson(genreJson, GenreList.class);

            for (GenreDetail genre : genreList.getGenres())
            {
                sharedPrefs.WiteGenre(genre.getId(), genre.getName());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void LoadUpcomingMovies(boolean onlyNotify)
    {
        String movieJson = "";
        try {
            RetrieveAPIDataTask taskUpcoming = new RetrieveAPIDataTask();
            movieJson = taskUpcoming.execute("movie/upcoming", "page="+pageNumber).get();

            Gson gson = new Gson();

            if(onlyNotify == false) {
                movies = gson.fromJson(movieJson, MovieList.class);
                moviesAdapter = new MovieAdapter(this, movies);
                upcomingMoviesList.setAdapter(moviesAdapter);
            }
            else
            {
                MovieList pageMovies =  gson.fromJson(movieJson, MovieList.class);
                movies.appendResults(pageMovies.getResults());
                moviesAdapter.notifyDataSetChanged();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void SearchMovies()
    {
        String movieJson = "";
        try {
            if(editTextMovies.getText().length() == 0)
            {
                LoadUpcomingMovies(false);
            }
            else
            {
                RetrieveAPIDataTask taskUpcoming = new RetrieveAPIDataTask();
                movieJson = taskUpcoming.execute("search/movie", "query=" + editTextMovies.getText()).get();
                Gson gson = new Gson();
                movies =  gson.fromJson(movieJson, MovieList.class);
                moviesAdapter = new MovieAdapter(this, movies);
                upcomingMoviesList.setAdapter(moviesAdapter);
                moviesAdapter.notifyDataSetChanged();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

