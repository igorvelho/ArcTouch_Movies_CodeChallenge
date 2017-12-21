package com.app.arctouch_movies_codechallenge.Helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.arctouch_movies_codechallenge.Model.MovieDetail;
import com.app.arctouch_movies_codechallenge.Model.MovieList;
import com.app.arctouch_movies_codechallenge.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by igorv on 14/12/2017.
 */

public class MovieAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private MovieList movieList;
    private SharedPrefsHelper sharedPrefs;
    private Context context;


    public MovieAdapter(Activity activity, MovieList list) {
        movieList = list;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sharedPrefs = new SharedPrefsHelper(activity);
        context = activity;
    }

    @Override
    public int getCount() {
        return movieList.getResults().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        MovieDetail movie = movieList.getResults().get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.movierow, null);
            holder = new ViewHolder();
            holder.genre = convertView.findViewById(R.id.txtMovieGenre);
            holder.name = convertView.findViewById(R.id.txtMovieName);
            holder.releasedate = convertView.findViewById(R.id.txtReleaseDate);
            holder.image = convertView.findViewById(R.id.imgPoster);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        StringBuilder genreSb = new StringBuilder();
        if(movie.getGenreIds() != null) {
            for (int genreId : movie.getGenreIds()) {
                if (genreSb.length() > 0)
                    genreSb.append(" | ");
                genreSb.append(sharedPrefs.GetGenre(genreId));
            }
        }
        movie.setGenre(genreSb.toString());

        holder.genre.setText(movie.getGenre());
        holder.name.setText(movie.getOriginalTitle());
        holder.releasedate.setText(movie.getReleaseDate());
        if(movie.getPosterPath() != null) {
            Picasso.with(holder.image.getContext())
                    .load(MyApp.getAppContext().getString(R.string.imageURLBase) + movie.getPosterPath())
                    .into(holder.image);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView genre;
        TextView name;
        TextView releasedate;
        ImageView image;
    }
}
