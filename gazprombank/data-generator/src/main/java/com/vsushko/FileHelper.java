package com.vsushko;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        Reader fileReader;
        try {
            fileReader = new FileReader(fileName);
            int data = fileReader.read();

            while (data != -1) {
                String officeNumber = Character.toString((char) data);
                if (!officeNumber.equals("\n")) {
                    offices.add(officeNumber);
                }
                data = fileReader.read();
            }
            fileReader.close();
            return offices;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
