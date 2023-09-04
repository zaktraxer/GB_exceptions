package homewrok2;

import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) {
        Float num = getFloatNumberFromUser();
        System.out.println("Вы ввели: " + num);
    }

    public static Float getFloatNumberFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите дробное число.");
        System.out.println("В качестве разделителя используйте символ точки.");
        System.out.print("> ");
        while (true) {
            try {
                return Float.parseFloat(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод, повторите:");
                System.out.print("> ");
            }
        }
    }
}