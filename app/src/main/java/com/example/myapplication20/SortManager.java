package com.example.myapplication20;

import com.example.myapplication20.Contact;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortManager {
    public static final int SORT_NAME_ASC = 0;
    public static final int SORT_NAME_DESC = 1;
    public static final int SORT_PHONE_ASC = 2;
    public static final int SORT_PHONE_DESC = 3;

    public void sort(List<Contact> list, int sortType) {
        Comparator<Contact> comparator;
        switch (sortType) {
            case SORT_NAME_ASC:
                comparator = Comparator.comparing(Contact::getName);
                break;
            case SORT_NAME_DESC:
                comparator = Comparator.comparing(Contact::getName).reversed();
                break;
            case SORT_PHONE_ASC:
                comparator = Comparator.comparing(Contact::getPhone);
                break;
            case SORT_PHONE_DESC:
                comparator = Comparator.comparing(Contact::getPhone).reversed();
                break;
            default:
                return;
        }
        Collections.sort(list, comparator);
    }
}
