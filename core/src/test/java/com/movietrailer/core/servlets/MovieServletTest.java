/*
 *  Copyright 2018 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.movietrailer.core.servlets;

import com.movietrailer.core.models.Contact;
import com.movietrailer.core.models.MovieData;
import com.movietrailer.core.services.ContactService;
import com.movietrailer.core.services.EHCacheService;
import com.movietrailer.core.services.MovieService;
import com.movietrailer.core.services.impl.ContactServiceImpl;
import com.movietrailer.core.services.impl.EHCacheServiceImpl;
import com.movietrailer.core.services.impl.MovieServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MovieServletTest {

    @Mock
    public MovieService movieService;

    @InjectMocks
    MovieServlet movieServlet = new MovieServlet();

    private List<MovieData> movieList = new ArrayList();

    private static final String RESPONSE_DATA = "[{\"id\":\"123\",\"rank\":\"1\",\"year\":\"1992\",\"image\":\"someimage.png\",\"videoUrl\":\"somevideourl\"}]";

    @BeforeEach
    void init() {
        movieList.add(getMovieData());
    }

    @Test
    @DisplayName("Test - Top Movies")
    void testTopMovies(AemContext context) throws ServletException, IOException {
        context.request().addRequestParameter("type", "top");

        when(movieService.getTopMovies()).thenReturn(movieList);
        movieServlet.doGet(context.request(), context.response());
        assertEquals(RESPONSE_DATA, context.response().getOutputAsString());
    }

    @Test
    @DisplayName("Test - Search by Movie Name")
    void testSearchByMovieName(AemContext context) throws ServletException, IOException {
        context.request().addRequestParameter("type", "search");
        context.request().addRequestParameter("movieName", "IronMan");

        when(movieService.searchMovies(anyString())).thenReturn(movieList);
        movieServlet.doGet(context.request(), context.response());
        assertEquals(RESPONSE_DATA, context.response().getOutputAsString());
    }

    private MovieData getMovieData() {
        MovieData movieData = new MovieData();
        movieData.setId("123");
        movieData.setImage("someimage.png");
        movieData.setRank("1");
        movieData.setYear("1992");
        movieData.setVideoUrl("somevideourl");
        return movieData;
    }
}
