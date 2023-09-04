package homework3;

import homework3.service.FileProcessor;
import homework3.service.InputParser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String path = "src/main/java/homework3/service/resources/";
        String inFile = "input.txt";
        String logFile = "log.bak";

        try {
            // Ручной ввод с клавиатуры
//            tryKeyboardInput();
//            System.out.println(InputParser.getParsedInputHashMap());

            // Обработка файла
            FileProcessor.setLogFile(logFile);
            System.out.println("\n----- Содержимое файла до обработки: -----\n");
            FileProcessor.printFile(path, inFile);
            System.out.println();
            FileProcessor.processFiles(path, inFile);
            for (String newFile : FileProcessor.getLog().keySet()) {
                System.out.println("\n----- Содержимое файла '" + newFile + "' после обработки: -----\n");
                FileProcessor.printFile(path, newFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void tryKeyboardInput() throws IOException {
        String rawInput;
        // Тестовые строки:
        /*
         иванова-белова анна-мария фёдоровна f 01.09.2010 79555667755
          f 78889995556677 02.09.2011 тюрина любовь купидоновна
           m   788899977744   сизый-лебедев   антуан  вальдемарович  03.09.2012
          788855678677 02.09.2013 тюрина ангелина леопольдовна f
         */
        try {
            rawInput = InputParser.getInput();
            InputParser.parseInput(rawInput);
        } catch (IOException e) {
            throw new IOException();
        }
    }


}