package com.vsushko;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * @author vsushko
 */
public class OfficeNumberComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return new BigDecimal(String.valueOf(o1)).compareTo(new BigDecimal(String.valueOf(o2)));
    }
}
