/*
 *  Copyright 2015 Adobe Systems Incorporated
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movietrailer.core.services.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
@Component(service = Servlet.class, immediate = true, property = {"sling.servlet.paths=/bin/movies",
        "sling.servlet.methods=GET"})

@ServiceDescription("Movie Servlet")
public class MovieServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    private static final String MOVIE_NAME = "movieName";

    @Reference
    private transient MovieService movieService;

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            final String type = request.getParameter("type");
            final String movieName = request.getParameterMap().containsKey(MOVIE_NAME) ? request.getParameter(MOVIE_NAME) : EMPTY;
            final ObjectMapper mapper = new ObjectMapper();
            String trailerData = StringUtils.EMPTY;
            if (equalsIgnoreCase(type, "top")) {
                trailerData = mapper.writeValueAsString(movieService.getTopMovies());

            } else if (equalsIgnoreCase(type, "search") && isNotBlank(movieName)) {
                trailerData = mapper.writeValueAsString(movieService.searchMovies(movieName));
            }

            response.setContentType("application/json");
            response.getWriter().write(trailerData);
        } catch (Exception e) {
            log.error("Exception : ", e);
        }
    }
}
