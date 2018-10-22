package com.vsushko;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TableTest {

    @Test
    public void testInsertEntries() {
        Table table = new Table();
        table.insert(LocalDate.of(2016, 9, 11));
        table.insert(LocalDate.of(2016, 9, 12));
        table.insert(LocalDate.of(2016, 9, 13));
        table.insert(LocalDate.of(2016, 9, 14));
        table.insert(LocalDate.of(2016, 9, 15));
        assertEquals(5, table.size());

        Table.Entry entry = table.getAllEntries();
        System.out.println(entry.date);
        while (entry.next != null) {
            entry = entry.next;
            System.out.println(entry.date + " ");
        }
    }

    @Test
    public void testGetBackDateEntriesWithEmptyTable() {
        Table table = new Table();
        assertEquals(0, table.size());
        Table.Entry entry = table.getBackDatedEntries();
        assertNull(entry);
    }

    @Test
    public void testGetBackDateEntries() {
        Table table = new Table();
        table.insert(LocalDate.of(2016, 9, 11));
        table.insert(LocalDate.of(2016, 9, 12));
        table.insert(LocalDate.of(2016, 9, 13));
        table.insert(LocalDate.of(2016, 9, 14));
        table.insert(LocalDate.of(2016, 9, 9));
        table.insert(LocalDate.of(2016, 9, 8));
        table.insert(LocalDate.of(2016, 9, 15));
        assertEquals(7, table.size());
        Table.Entry entry = table.getBackDatedEntries();

        while (entry.next != null) {
            assertTrue(entry.isBackDate);
            entry = entry.next;
        }
    }

    @Test
    public void testGetBackDateEntriesWhenTheyNotExist() {
        Table table = new Table();
        table.insert(LocalDate.of(2016, 9, 11));
        table.insert(LocalDate.of(2016, 9, 12));
        table.insert(LocalDate.of(2016, 9, 13));
        table.insert(LocalDate.of(2016, 9, 14));
        table.insert(LocalDate.of(2016, 9, 15));
        assertEquals(5, table.size());
        Table.Entry entry = table.getBackDatedEntries();
        assertNull(entry);
    }
}