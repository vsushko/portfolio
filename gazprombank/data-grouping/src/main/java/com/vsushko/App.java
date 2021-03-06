package com.vsushko;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.toMap;

/**
 * @author vsushko
 */
public class App {

    public static void main(String[] args) {
        String operationsFileName;
        String sumsByDatesFileName;
        String sumsByOfficesFileName;

        if (args.length == 3) {
            if (isArgumentsExist(args)) {
                System.err.println("Some argument is missing (pass: operations.txt sums-by-dates.txt sums-by-offices.txt");
                return;
            }
            operationsFileName = args[0];
            sumsByDatesFileName = args[1];
            sumsByOfficesFileName = args[2];

            List<Operation> operations = FileHelper.getOperations(operationsFileName);
            Map<String, BigDecimal> operationsCountByDay = new TreeMap(new OperationDateComparator());
            Map<BigDecimal, BigDecimal> salesCountByOffice = new HashMap<>();

            if (operations.isEmpty()) {
                return;
            }
            for (Operation operation : operations) {
                String operationSum = operation.getOperationSum();
                String operationDate = operation.getOperationDate().toString();

                if (operationsCountByDay.containsKey(operationDate)) {
                    BigDecimal sumByDate = operationsCountByDay.get(operationDate);
                    operationsCountByDay.put(operationDate, sumByDate.add(new BigDecimal(operationSum)));
                } else {
                    operationsCountByDay.put(operationDate, new BigDecimal(operationSum));
                }

                BigDecimal officeNumber = new BigDecimal(operation.getOfficeNumber());

                if (salesCountByOffice.containsKey(officeNumber)) {
                    BigDecimal sumByDate = salesCountByOffice.get(officeNumber);
                    salesCountByOffice.put(officeNumber, sumByDate.add(new BigDecimal(operationSum)));
                } else {
                    salesCountByOffice.put(officeNumber, new BigDecimal(operationSum));
                }
            }

            salesCountByOffice = salesCountByOffice.entrySet().stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

            FileHelper.saveStatsCountByDay(sumsByDatesFileName, operationsCountByDay);
            FileHelper.saveStatsCountByOffice(sumsByOfficesFileName, salesCountByOffice);
        }
    }

    private static boolean isArgumentsExist(String[] args) {
        for (String s : args) {
            if (s == null) {
                return true;
            }
        }
        return false;
    }
}
