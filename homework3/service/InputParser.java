package homework3.service;

import homework3.service.exceptions.EmptyInputException;
import homework3.service.exceptions.InputNotEnoughValuesException;
import homework3.service.exceptions.InputTooManyValuesException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InputParser {
    private static final String[] keys = {"lastname", "firstname", "patronymic", "birthdate", "phone", "gender"};
    private static final int[] nameLength = {3, 28};  // относится ко всем полям ФИО
    private static final int[] phoneLength = {7, 16};
    private static final int genderLength = 1;
    private static int lang;  // используемый в имени язык (0-en, 1-ru)
    private static final Map<String, String> parsedInputHashMap = new HashMap<>();


    public static @NotNull String getInput() throws EmptyInputException {
        Scanner scanner = new Scanner(System.in);
        printInfo();
        printPrompt();
        try {
            String inputString = scanner.nextLine().trim();
            if (inputString.isEmpty()) {
                throw new EmptyInputException();
            }
            return inputString;
        } catch (EmptyInputException e) {
            throw new EmptyInputException();
        }
    }


    public static Map<String, String> getParsedInputHashMap() {
        return parsedInputHashMap;
    }


    public static boolean parseInput(@NotNull String input) throws IOException {
        parsedInputHashMap.clear();
        int filled_keys = 0;  // счетчик заполнений пропарсенных значений
        String[] valuesArray;
        // Заменим длинные пробелы одинарными и разделим
        valuesArray = input.trim().replaceAll("\\s+", " ").split(" ");
        // Проверим ввод на количество строк
        if (valuesArray.length < 6) {
            throw new InputNotEnoughValuesException("Введено слишком мало значений.");
        }
        if (valuesArray.length > 6) {
            throw new InputTooManyValuesException();
        }
        // Проверим строки на соответствие
        for (String s : valuesArray) {

            // Проверка на пол (keys[5]="gender")
            if ((parsedInputHashMap.get(keys[5]) == null) && (checkForGenderAndSaveValueIfTrue(s))) {
                filled_keys++;
                continue;

            }

            // Проверка на день рождения (keys[3]="birthdate")
            if ((parsedInputHashMap.get(keys[3]) == null) && (checkForBirthdateAndSaveValueIfTrue(s))) {
                filled_keys++;
                continue;
            }

            // Проверка на номер телефона (keys[4]="phone")
            if ((parsedInputHashMap.get(keys[4]) == null) && (checkForPhoneAndSaveValueIfTrue(s))) {
                filled_keys++;
                continue;
            }

            // Проверка на ФИО
            // В данной реализации необходимо соблюдать условие очередности написания ФИО
            // Сначала фамилия, затем имя, затем отчество
            if (((parsedInputHashMap.get(keys[0]) == null || parsedInputHashMap.get(keys[1]) == null
                    || parsedInputHashMap.get(keys[2]) == null)) && (checkForNameAndSaveValueIfTrue(s))) {
                filled_keys++;
            }
        }
        return keys.length == filled_keys;
    }


    private static boolean checkForGenderAndSaveValueIfTrue(@NotNull String s) throws IOException {
        if (s.length() == genderLength && (s.matches("[f|m]"))) {
            parsedInputHashMap.put(keys[5], s);
            return true;
        } else if (s.length() == genderLength) {
            throw new IOException("Вы ввели '" + s + "'. " +
                    "Данные длиной в один символ могут относится только к полу: 'f' или 'm'.");
        }
        return false;
    }


    private static boolean checkForBirthdateAndSaveValueIfTrue(@NotNull String s) {
        if (s.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            parsedInputHashMap.put(keys[3], s);
            return true;
        }
        return false;
    }


    private static boolean checkForPhoneAndSaveValueIfTrue(@NotNull String s) throws IOException {
        try {
            long phone = Long.parseLong(s);
            if (phoneLength[0] <= String.valueOf(phone).length() && String.valueOf(phone).length() <= phoneLength[1]) {
                parsedInputHashMap.put(keys[4], String.valueOf(phone));
                return true;
            } else throw new IOException("Вы ввели '" + s + "' в качестве телефона. " +
                    "Цифровые данные не соответствуют длине от 7 до 12 символов.");
        } catch (NumberFormatException ignored) {
            return false;
        }
    }


    private static boolean checkForNameAndSaveValueIfTrue(String s) throws IOException {
        // Проверим соответствие условиям длины
        if (!(nameLength[0] <= whatLength(s) && whatLength(s) <= nameLength[1])) {
            throw new IOException("Параметры ФИО не удовлетворяют условиям: " +
                    "длина от " + nameLength[0] + " до " + nameLength[1] + " символов ");
        }
        // Определим язык и запишем в текущую языковую переменную
        int currentLang = whatLanguage(s);

        // Отфильтруем от знаков пунктуации и запишем с заглавной
        String sFiltered = stringFilterWithHyphensAndCapitalize(s);

        // Поочередно проверим пустые позиции с именами и запишем в них строки
        if (parsedInputHashMap.get(keys[0]) == null) {
            // запишем язык, использованный для фамилии, в глобальную переменную
            lang = currentLang;
            parsedInputHashMap.put(keys[0], sFiltered);
            return true;

        } else if (parsedInputHashMap.get(keys[1]) == null) {
            // Проверим соответствие языка фамилии с именем
            if (lang == currentLang) {
                parsedInputHashMap.put(keys[1], sFiltered);
                return true;
            } else throw new IOException("Введенный язык имени не совпадает с языком фамилии.");

        } else if (parsedInputHashMap.get(keys[2]) == null) {
            // Проверим соответствие языка фамилии с отчеством
            if (lang == currentLang) {
                parsedInputHashMap.put(keys[2], sFiltered);
                return true;
            } else throw new IOException("Введенный язык отчества не совпадает с языком фамилии.");

        } else {
            return false;
        }
    }


    private static int whatLength(@NotNull String s) {
        return s.replaceAll("\\p{P}", "").length();
    }


    private static int whatLanguage(@NotNull String s) throws IOException {
        if (isCyrillic(s.replaceAll("\\p{P}", ""))) {
            return 1;
        } else if (isLatin(s.replaceAll("\\p{P}", ""))) {
            return 0;
        } else throw new IOException("Введенный язык не распознан.");
    }


    private static String stringFilterWithHyphensAndCapitalize(@NotNull String s) throws IOException {
        // чтобы оставить двойные имена и фамилии, используем regexp с исключением кроме (&&) ->
        // -> заменим все знаки пунктуации [ \p{P} ] кроме слов с дефисом внутри [ \\w+(?:-\\w+)+ ]
        String sFilteredWithHyphens = s.replaceAll("\\p{P}&&\\w+(?:-\\w+)+", "");
        // Запишем строку с заглавной буквы
        // Проверим, возможно капитализируем имя, написанное через дефис
        // Если да -> то распарсим его, капитализируем каждое имя в отдельности и вновь склеим через дефис
        try {
            if (sFilteredWithHyphens.matches("(.+)(-)(.+)")) {
                String[] splitHyphenedName = sFilteredWithHyphens.split("-");
                return splitHyphenedName[0].substring(0, 1).toUpperCase()
                        + splitHyphenedName[0].substring(1).toLowerCase()
                        + "-"
                        + splitHyphenedName[1].substring(0, 1).toUpperCase()
                        + splitHyphenedName[1].substring(1).toLowerCase();
            } else {
                return sFilteredWithHyphens.substring(0, 1).toUpperCase()
                        + sFilteredWithHyphens.substring(1).toLowerCase();
            }
        } catch (IndexOutOfBoundsException e) {
            // Поймаем, если что-то пойдет не так с индексацией после сплита
            throw new IOException("Ошибка в обработке имени с дефисом.");
        }
    }


    private static boolean isCyrillic(@NotNull String s) {
        for (int i = 0; i < s.toCharArray().length; i++) {
            if (Character.UnicodeBlock.of(s.toCharArray()[i]) != Character.UnicodeBlock.CYRILLIC) {
                return false;
            }
        }
        return true;
    }


    private static boolean isLatin(@NotNull String s) {
        for (int i = 0; i < s.toCharArray().length; i++) {
            if (Character.UnicodeBlock.of(s.toCharArray()[i]) != Character.UnicodeBlock.BASIC_LATIN) {
                return false;
            }
        }
        return true;
    }


    private static void printInfo() {
        System.out.println("Введите данные в следующем формате: ");
        System.out.println("""
                фамилия, имя, отчество - строки
                дата_рождения - строка формата dd.mm.yyyy
                номер_телефона - целое беззнаковое число без форматирования
                пол - символ латиницей f или m.
                'q' для выхода""");
    }


    private static void printPrompt() {
        System.out.print("> ");
    }


}