package com.movietrailer.core.services;

import com.movietrailer.core.models.MovieData;

import java.util.List;

public interface MovieService {

    /**
     * Method to get top movies from IMDb.
     *
     * @return
     */
    List<MovieData> getTopMovies();

    /**
     * Method to search movies based on movie name.
     *
     * @param movieName
     *
     * @return
     */
    List<MovieData> searchMovies(final String movieName);

}
