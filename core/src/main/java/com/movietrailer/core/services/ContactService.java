package com.movietrailer.core.services;

import com.movietrailer.core.models.Contact;

import java.util.List;

public interface ContactService {

    /**
     * Method to get all stored forms.
     *
     * @return
     */
    List<Contact> getForms();

    /**
     * Method to save forms in memory.
     *
     * @param contact
     */
    void saveForm(Contact contact);
}
