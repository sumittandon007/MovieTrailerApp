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
import com.movietrailer.core.models.Contact;
import com.movietrailer.core.services.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component(service = Servlet.class, immediate = true, property = {"sling.servlet.paths=/bin/contact",
        "sling.servlet.methods=POST"})

@Slf4j
@ServiceDescription("Movie Servlet")
public class ContactServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    private static final String FIRSTNAME = "firstName";

    private static final String LASTNAME = "lastName";

    private static final String MOBILE = "mobile";

    private static final String MOVIE_TRAILER_NAME = "movieTrailerName";

    @Reference
    private transient ContactService contactService;

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(contactService.getForms()));
    }

    @Override
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            String firstName = request.getParameter(FIRSTNAME);
            String lastName = request.getParameter(LASTNAME);
            String mobile = request.getParameter(MOBILE);
            String movieTrailer = request.getParameter(MOVIE_TRAILER_NAME);

            if (isNotBlank(firstName) && isNotBlank(lastName) && isNotBlank(mobile) && isNotBlank(movieTrailer)) {
                Contact contact = Contact.builder()
                                         .firstName(firstName)
                                         .lastName(lastName)
                                         .mobile(mobile)
                                         .movieTrailerName(movieTrailer)
                                         .build();
                contactService.saveForm(contact);
                response.getWriter().write("Application Saved !");
                response.sendRedirect("/bin/contact");
            }
        } catch (Exception e) {
            log.error("Form Exception :", e);
        }
    }
}
