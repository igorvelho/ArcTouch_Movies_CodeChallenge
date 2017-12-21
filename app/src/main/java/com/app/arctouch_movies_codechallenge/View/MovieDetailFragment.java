package com.app.arctouch_movies_codechallenge.View;


import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.arctouch_movies_codechallenge.Helper.MyApp;
import com.app.arctouch_movies_codechallenge.Model.MovieDetail;
import com.app.arctouch_movies_codechallenge.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


public class MovieDetailFragment extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_movie_detail, null);

        Bundle b = getArguments();

        String movieJson = b.getString("movieDetail");
        Gson gson = new Gson();
        MovieDetail movie = gson.fromJson(movieJson, MovieDetail.class);
        TextView genre = view.findViewById(R.id.txtMovieGenre);
        TextView name = view.findViewById(R.id.txtMovieName);
        TextView releasedate = view.findViewById(R.id.txtReleaseDate);
        TextView overview = view.findViewById(R.id.txtOverview);
        ImageView image = view.findViewById(R.id.imgPoster);

        genre.setText(movie.getGenre());
        name.setText(movie.getOriginalTitle());
        overview.setText(movie.getOverview());
        releasedate.setText(movie.getReleaseDate());
        if(movie.getBackdropPath() != null) {
            Picasso.with(image.getContext())
                    .load(MyApp.getAppContext().getString(R.string.imageURLBase) + movie.getBackdropPath())
                    .into(image);
        }

        return view;
    }
}
