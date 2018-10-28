package com.vsushko;

import java.io.File;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {

    public static void main(String[] args) {
        String officesFileName = null;
        Integer amountOfOperations = 0;
        String outputFileName = null;

        if (args.length == 3) {
            officesFileName = args[0];
            try {
                amountOfOperations = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number");
            }
            outputFileName = args[2];
        }

        FileHelper.generateOfficesFile(amountOfOperations);

        if (officesFileName != null && !officesFileName.isEmpty()) {
            File file = new File(officesFileName);

            if (file.exists()) {
                int precedingYear = Year.now().getValue() - 1;

                List<String> offices = FileHelper.getOffices(officesFileName);
                List<String> outputData = new ArrayList<>();

                for (int i = 0; i < amountOfOperations; i++) {
                    int randomMonth = DateUtils.getRandomMonth();
                    int randomDay = DateUtils.getRandomDay(randomMonth, precedingYear);

                    LocalDate operationDate = LocalDate.of(precedingYear, randomMonth, randomDay);
                    String officeNumber = String.valueOf(getRandomOffice(offices));
                    String operationNumber = String.valueOf(i);
                    String operationSum = String.valueOf(DateUtils.getRandomPeriod(10_000, 100_000));
                    outputData.add(String.join(" ", operationDate.toString(), officeNumber, operationNumber,
                            operationSum, "\n"));
                }
                FileHelper.createOutputFile(outputFileName, outputData);
            } else {
                System.err.println("File doesn't exist");
            }
        }
    }

    private static String getRandomOffice(List<String> offices) {
        return offices.get(new Random().nextInt(offices.size()));
    }
}
