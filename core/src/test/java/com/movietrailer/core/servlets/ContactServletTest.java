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
import com.movietrailer.core.services.ContactService;
import com.movietrailer.core.services.impl.ContactServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
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
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ContactServletTest {

    @Mock
    public ContactService contactService;

    @InjectMocks
    ContactServlet contactServlet = new ContactServlet();

    @Test
    @DisplayName("Test - Get contact details")
    void testGetForm(AemContext context) throws ServletException, IOException {

        List<Contact> contactList = new ArrayList<>();
        contactList.add(getContact());

        when(contactService.getForms()).thenReturn(contactList);
        contactServlet.doGet(context.request(), context.response());

        assertEquals("[{\"firstName\":\"John\",\"lastName\":\"Doe\",\"mobile\":\"+31123456789\",\"movieTrailerName\":\"IronMan\"}]", context.response().getOutputAsString());
    }

    @Test
    @DisplayName("Test - Save contact details")
    void testSaveForm(AemContext context) {
        ContactService contactService = context.registerService(new ContactServiceImpl());
        contactService.saveForm(getContact());
        assertEquals("John", contactService.getForms().get(0).getFirstName());
    }

    @Test
    @DisplayName("Test - Save From with Servlet")
    void testSaveFormWithServlet(AemContext context) throws ServletException, IOException {
        ContactService contactService = context.registerService(new ContactServiceImpl());

        Map<String, Object> map = new HashMap<>();
        map.put("firstName", "Richard");
        map.put("lastName", "Jansen");
        map.put("mobile", "+31123456789");
        map.put("movieTrailerName", "Blue Hawk");

        context.request().setParameterMap(map);
        contactServlet.doPost(context.request(), context.response());
        assertEquals(302, context.response().getStatus());
    }

    private Contact getContact() {
        final Contact contact = Contact.builder()
                                       .firstName("John")
                                       .lastName("Doe")
                                       .mobile("+31123456789")
                                       .movieTrailerName("IronMan")
                                       .build();
        return contact;
    }

}
