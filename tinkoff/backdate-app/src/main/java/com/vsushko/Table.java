package com.vsushko;

import java.time.LocalDate;

/**
 * @author vsushko
 */
public class Table {

    static class Entry {
        LocalDate date;
        boolean isBackDate;
        Entry next;

        Entry(LocalDate date) {
            this.date = date;
        }
    }

    private Entry head;
    private LocalDate minDate;

    public Table() {
        this.head = null;
    }

    public void insert(LocalDate date) {
        Entry newEntry = new Entry(date);

        if (head == null) {
            head = newEntry;
            minDate = date;
            return;
        }
        Entry current = head;

        while (current.next != null) {
            current = current.next;
        }

        if (date.isBefore(getMax(current.date))) {
            current.next = newEntry;
            newEntry.isBackDate = true;
        }
        current.next = newEntry;
    }

    public Entry getAllEntries() {
        return head;
    }

    public Entry getBackDatedEntries() {
        if (head == null) {
            return null;
        }
        Entry result = null;
        Entry current = head;
        while (current.next != null) {
            if (current.isBackDate) {
                if (result == null) {
                    result = current;
                } else {
                    result.next = current;
                }
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
