package com.example.myapplication20;

import com.example.myapplication20.ContactDatabaseHelper;
import com.example.myapplication20.Contact;
import java.util.ArrayList;
import java.util.List;

public class SearchUtil {
    private ContactDatabaseHelper dbHelper;

    public SearchUtil(ContactDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<Contact> searchByName(String keyword) {
        List<Contact> allContacts = dbHelper.getAllContacts();
        List<Contact> result = new ArrayList<>();
        for (Contact contact : allContacts) {
            if (contact.getName().contains(keyword) || contact.getPhone().contains(keyword)) {
                result.add(contact);
            }
        }
        return result;
    }
}
