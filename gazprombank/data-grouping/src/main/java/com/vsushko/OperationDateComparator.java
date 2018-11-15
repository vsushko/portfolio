package com.vsushko;

import java.time.LocalDate;
import java.util.Comparator;

/**
 * @author vsushko
 */
public class OperationDateComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return LocalDate.parse(String.valueOf(o1)).compareTo(LocalDate.parse(String.valueOf(o2)));
    }
}
