package com.vsushko;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author vsushko
 */
public final class FileHelper {

    private FileHelper() {
        super();
    }

    public static List<Operation> getOperations(String fileName) {
        List<Operation> operations = new ArrayList<>();
        Scanner reader = null;
        try {
            reader = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (reader.hasNext()) {
            String line = reader.nextLine();
            String[] lineItems = line.split(" ");
            operations.add(new Operation(LocalDate.parse(lineItems[0]), lineItems[1], lineItems[2], lineItems[3]));
        }
        reader.close();
        return operations;
    }
}
