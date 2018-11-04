package com.vsushko;

import java.util.List;

/*
    Данные об операциях находятся в файле, который сгенерирован в предыдущей задаче.
    Программа должна подсчитать сумму всех операций за каждый день и суммы всех операций в каждой точке продаж.
    Программе в качестве параметров передаются имя файла с операциями, имя файла со статистикой по датам,
    имя файла со статистикой по точкам продаж.
    Статистика по датам должна быть отсортирована по возрастанию дат.
    Статистика по точкам продаж должна быть отсортирована по убыванию суммы.
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

            if (!operations.isEmpty()) {

            }
        }
    }

    private static boolean isArgumentsExist(String[] args) {
        for (String s : args) {
            if (s == null) {
                return false;
            }
        }
        return true;
    }
}
