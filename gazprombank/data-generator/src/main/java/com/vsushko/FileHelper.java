package com.vsushko;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author vsushko
 */
public final class FileHelper {

    /**
     * Use only allowed methods
     */
    private FileHelper() {
        super();
    }

    public static void generateOfficesFile(Integer amountOfOperations) {
        Writer fileWriter;
        try {
            fileWriter = new FileWriter("offices.txt");
            for (int i = 0; i < amountOfOperations; i++) {
                fileWriter.write(DateUtils.getRandomPeriod(0, amountOfOperations) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createOutputFile(String fileName, List<String> outputData) {
        Writer fileWriter;
        try {
            fileWriter = new FileWriter(fileName);
            for (String data : outputData) {
                fileWriter.write(data);
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getOffices(String fileName) {
        List<String> offices = new ArrayList<>();
        Scanner reader = null;
        try {
            reader = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (reader.hasNext()) {
            offices.add(String.valueOf(reader.nextInt()));
        }
        reader.close();
        return offices;
    }
}
