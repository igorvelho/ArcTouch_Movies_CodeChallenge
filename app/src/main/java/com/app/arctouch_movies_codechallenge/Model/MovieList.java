package com.app.arctouch_movies_codechallenge.Model;

import java.util.List;

/**
 * Created by igorv on 12/12/2017.
 */

public class MovieList {
    private List<MovieDetail> results;

    public List<MovieDetail> getResults() {
        return results;
    }

    public void setResults(List<MovieDetail> results) {
        this.results = results;
    }

    public void appendResults(List<MovieDetail> results) {
        this.results.addAll(results);
    }
}