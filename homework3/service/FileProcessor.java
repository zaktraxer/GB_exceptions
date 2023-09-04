package homework3.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileProcessor {
    private static String logFile;
    private static final HashMap<String, String> log = new HashMap<>();


    public static void setLogFile(String logFile) {
        FileProcessor.logFile = logFile;
    }


    public static HashMap<String, String> getLog() {
        return log;
    }

    public static void processFiles(String path, String inFile) throws IOException {
        try {
            checkForInputFile(path, inFile);
        } catch (IOException e) {
            throw new IOException();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path + inFile))) {
            readLog(path);
            String line;
            while ((line = br.readLine()) != null) {
                if (InputParser.parseInput(line)) {
                    Entry entry = parseEntryFromMap(InputParser.getParsedInputHashMap());
                    checkAndCreateFile(path, entry.lastname);
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter((path + entry.lastname), true))) {
                        bw.append(String.valueOf(entry));
                        bw.newLine();
                    } catch (IOException e) {
                        throw new IOException(e);
                    }
                }
            }
            writeLog(path);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    private static void writeLog(String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + logFile))) {
            checkAndCreateFile(path, logFile);
            for (String key : log.keySet()) {
                bw.write(key + "->" + log.get(key));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Ошибка при записи в лог файл.");
        }
    }

    private static void readLog(String path) throws IOException {
        File file = new File(path + logFile);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path + logFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                var parts = line.trim().split("->");
                log.putIfAbsent(parts[0], parts[1]);
            }
        } catch (IOException e) {
            throw new IOException("Ошибка при чтении лог файла.");
        }
    }


    private static void checkForInputFile(String path, String inFile) throws IOException {
        File file = new File(path + inFile);
        if (!file.exists()) {
            throw new FileNotFoundException("Файла для чтения не существует.");
        }
    }


    private static void checkAndCreateFile(String path, String outFile) throws IOException {
        Date date = new Date();
        try {
            if (createFile(path, outFile)) {
                log.putIfAbsent(outFile, new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(date));
                System.out.println("Файл успешно создан: " + path + outFile);
            }
        } catch (IOException e) {
            throw new IOException("Проблема при создании файла для записи");
        }
    }


    private static boolean createFile(String path, String fileToCreate) throws IOException {
        File file = new File(path + fileToCreate);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            throw new IOException("Проблема при создании файла для записи");
        }
    }


    public static void printFile(String path, String file) throws IOException {
        try {
            checkForInputFile(path, file);
        } catch (IOException e) {
            throw new IOException();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path + file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    private static class Entry {
        private String lastname, firstname, patronymic, birthdate, phone, gender;

        @Override
        // <Фамилия><Имя><Отчество><дата_рождения><номер_телефона><пол>
        public String toString() {
            return "<" + lastname + "><" + firstname + "><" + patronymic + "><" + birthdate + "><"
                    + phone + "><" + gender + ">";
        }
    }


    private static Entry parseEntryFromMap(Map<String, String> map) {
        Entry entry = new Entry();
        entry.lastname = map.get("lastname");
        entry.firstname = map.get("firstname");
        entry.patronymic = map.get("patronymic");
        entry.birthdate = map.get("birthdate");
        entry.phone = map.get("phone");
        entry.gender = map.get("gender");
        return entry;
    }


}