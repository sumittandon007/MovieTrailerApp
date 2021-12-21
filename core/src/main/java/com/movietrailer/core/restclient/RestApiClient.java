package com.movietrailer.core.restclient;

import com.movietrailer.core.models.ImdbDataModel;
import com.movietrailer.core.models.MovieData;
import feign.Param;
import feign.RequestLine;

public interface RestApiClient {

    @RequestLine("GET /Top250Movies/{apiKey}")
    ImdbDataModel getTopMovies(@Param("apiKey") String apiKey);

    @RequestLine("GET /SearchMovie/{apiKey}/{movieName}")
    ImdbDataModel searchMovies(@Param("apiKey") String apiKey, @Param("movieName") String movieName);

    @RequestLine("GET /YouTubeTrailer/{apiKey}/{id}")
    MovieData getYoutubeTrailer(@Param("apiKey") String apiKey, @Param("id") String id);

}


