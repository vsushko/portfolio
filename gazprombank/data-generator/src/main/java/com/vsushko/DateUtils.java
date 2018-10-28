package com.vsushko;

import java.util.Calendar;

/**
 * @author vsushko
 */
public final class DateUtils {

    private static final int FIRST_MONTH = 1;
    private static final int LAST_MONTH = 12;

    /**
     * User only allowed methods
     */
    private DateUtils() {
        super();
    }

    /**
     * Returns random month
     *
     * @return month
     */
    public static int getRandomMonth() {
        return getRandomPeriod(FIRST_MONTH, LAST_MONTH);
    }

    /**
     * Returns random period
     *
     * @return month
     */
    public static int getRandomPeriod(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static int getRandomDay(int month, int year) {
        int result;

        switch (month) {
            case 2:
                if (isLeapYear(year)) {
                    result = getRandomPeriod(1, 29);
                } else {
                    result = getRandomPeriod(1, 28);
                }
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                result = getRandomPeriod(1, 31);
                break;
            default:
                result = getRandomPeriod(1, 30);
                break;
        }
        return result;
    }

    private static boolean isLeapYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        int numberOfDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);

        return numberOfDays > 365;
    }
}
