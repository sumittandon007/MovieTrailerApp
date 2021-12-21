package com.movietrailer.core.services.impl;

import com.movietrailer.core.models.Contact;
import com.movietrailer.core.services.ContactService;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;

@Component(service = ContactService.class, immediate = true)
public class ContactServiceImpl implements ContactService {

    private List<Contact> contactForm = new ArrayList<>();

    @Override

    public List<Contact> getForms() {
        return contactForm;
    }

    @Override
    public void saveForm(final Contact contact) {
        contactForm.add(contact);
    }
}
