package ru.mascom;

import java.util.Random;

/**
 * App utils
 *
 * @author vsushko
 */
public final class AppUtils {

    /**
     * Port
     */
    public static final int PORT = 1113;

    /**
     * Host name
     */
    public static final String HOST_NAME = "localhost";

    /**
     * Use only allowed methods
     */
    private AppUtils() {
        super();
    }

    /**
     * Returns random 6-digit number
     *
     * @return number
     */
    public static int generateRandomNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}
