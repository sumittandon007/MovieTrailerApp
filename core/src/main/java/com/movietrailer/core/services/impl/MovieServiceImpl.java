package com.movietrailer.core.services.impl;

import com.movietrailer.core.models.ImdbDataModel;
import com.movietrailer.core.models.MovieData;
import com.movietrailer.core.restclient.RestApiClient;
import com.movietrailer.core.restclient.RestInterceptor;
import com.movietrailer.core.services.EHCacheService;
import com.movietrailer.core.services.MovieService;
import com.movietrailer.core.services.config.MovieConfig;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.ehcache.Cache;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.movietrailer.core.services.impl.EHCacheServiceImpl.DEFAULT_CACHE;

@Slf4j
@Component(service = MovieService.class, immediate = true)
public class MovieServiceImpl implements MovieService {

    @Reference
    private EHCacheService ehcache;

    private static final String TOP_MOVIES = "topMovies";

    private String imdbApiKey;

    private String imdbApiEndpoint;

    private int limit;

    @Activate
    protected void activate(final MovieConfig config) {
        this.imdbApiKey = config.imdbApiKey();
        this.imdbApiEndpoint = config.imdbApiEndpoint();
        this.limit = config.limit();
    }

    @Override
    public List<MovieData> getTopMovies() {
        final Cache<String, List<MovieData>> cache = ehcache.getCache(DEFAULT_CACHE);
        if (cache.get(TOP_MOVIES) != null) {
            return cache.get(TOP_MOVIES);
        }
        final List<MovieData> topTrailerList = new ArrayList<>();

        final ImdbDataModel topMovies = getClient().getTopMovies(this.imdbApiKey);
        if (CollectionUtils.isNotEmpty(topMovies.getItems())) {
            topMovies.getItems().stream()
                     .sorted(Comparator.comparing(item -> Integer.valueOf(item.getRank())))
                     .limit(limit)
                     .forEachOrdered(item -> topTrailerList.add(getYoutubeVideo(item, getClient())));
            cache.put(TOP_MOVIES, topTrailerList);
            return topTrailerList;
        }
        log.info("No Top Movies found in IMDb");
        return new ArrayList<>();
    }

    @Override
    public List<MovieData> searchMovies(final String movieName) {
        final Cache<String, List<MovieData>> cache = ehcache.getCache(DEFAULT_CACHE);
        if (cache.get(movieName) != null) {
            return cache.get(movieName);
        }
        List<MovieData> movieSearchList = new ArrayList<>();
        final ImdbDataModel movieSearchResult = getClient().searchMovies(this.imdbApiKey, movieName);
        if (movieSearchResult != null) {
            movieSearchResult.getResults()
                             .stream()
                             .limit(limit)
                             .forEachOrdered(item -> movieSearchList.add(getYoutubeVideo(item, getClient())));
            cache.put(movieName, movieSearchList);
            return movieSearchList;
        }
        log.info("No movie found with name : {}", movieName);
        return new ArrayList<>();
    }

    /**
     * Method to retrieve youtube url.
     *
     * @param item
     * @param client
     *
     * @return
     */
    private MovieData getYoutubeVideo(final MovieData item, final RestApiClient client) {
        final MovieData vts = client.getYoutubeTrailer(this.imdbApiKey, item.getId());
        item.setVideoUrl(vts.getVideoUrl());
        item.setYear(vts.getYear());
        return item;
    }

    /**
     * Feign Client
     *
     * @return
     */
    private RestApiClient getClient() {
        return Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .requestInterceptor(new RestInterceptor())
                    .target(RestApiClient.class, this.imdbApiEndpoint);
    }
}
