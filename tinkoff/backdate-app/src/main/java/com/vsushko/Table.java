package com.vsushko;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author vsushko
 */
public class Table {

    static class Entry {
        int id;
        LocalDate date;
        boolean isBackDate;
        Entry next;

        Entry(LocalDate date) {
            this.date = date;
        }
    }

    private Entry head;
    private LocalDate minDate;
    private int size = 1;

    public Table() {
        this.head = null;
    }

    public void insert(LocalDate date) {
        Entry newEntry = new Entry(date);

        if (head == null) {
            head = newEntry;
            minDate = date;
            head.id = size;
            size += 1;
            return;
        }
        Entry current = head;

        while (current.next != null) {
            current = current.next;
        }

        if (date.isBefore(getMax(current.date))) {
            current.next = newEntry;
            newEntry.isBackDate = true;
            newEntry.id = size;
        }
        current.next = newEntry;
        size += 1;
    }

    public Entry getAllEntries() {
        return head;
    }

    public Collection<Integer> getBackDatedEntriesIds() {
        List<Integer> result = new ArrayList<>();
        if (head == null) {
            return null;
        }
        Entry current = head;
        while (current.next != null) {
            if (current.isBackDate) {
                result.add(current.id);
            }
            current = current.next;
        }
        return result;
    }

    private LocalDate getMax(LocalDate date) {
        if (date.isAfter(minDate)) {
            return date;
        }
        return minDate;
    }

    public int size() {
        if (head == null) {
            return 0;
        }

        int count = 1;
        Entry current = head;

        while (current.next != null) {
            current = current.next;
            count++;
        }
        return count;
    }
}
