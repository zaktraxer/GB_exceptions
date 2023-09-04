package homework1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class RandomExceptionsClass {
    public static void main(String[] args) throws IOException {
        try {
            callMethod6("");
            ArrayList<String> stringArraysList = (ArrayList<String>) callMethod1();
            int a = 10, b = 0;
            callMethod6("");
            Integer div = callMethod2(a, b);
            System.out.println(div);
            callMethod6("");
            callMethod3(stringArraysList);
        } catch (ClassCastException e) {
            String name = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("Found error in " + name + " -> ClassCastException");
        } catch (NullPointerException e) {
            String name = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("Found error in " + name + " -> NullPointerException");
        } catch (RuntimeException e) {
            e.printStackTrace();  // TODO: сделать более мощный логгер
        }
    }

    private static void callMethod6(String s) {
        System.out.println("Hello World, it's me!");
    }

    private static void callMethod3(ArrayList<String> stringArraysList) {
        callMethod6("");
        callMethod3(stringArraysList);
    }

    private static Collection<String> callMethod1() throws IOException {
        callMethod2(100000000, 10 - 10);
        return new LinkedList<>();
    }

    public static Integer callMethod2(int a, int b) throws IOException {
        callMethod6("");
        try (FileInputStream fis = new FileInputStream("1.txt")) {
            fis.read();  //  TODO: данные не выводятся и не сохраняются
            if (fis.available() > 0) throw new RuntimeException();
        } catch (FileNotFoundException e) {
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            System.out.println("Found error in " + name + " -> FileNotFoundException");
        } catch (RuntimeException e) {
            String name = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("Found error in " + name + " -> RuntimeException");
            e.printStackTrace();  // TODO: сделать более мощный логгер
        }
        try {
            Integer num = callMethod4("124O");
        } catch (NullPointerException e) {
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            System.out.println("Found error in " + name + " -> NullPointerException");
        }
        try {
            return a / b;
        } catch (ArithmeticException e) {
            String name = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("Found error in " + name + " -> ArithmeticException");
        }
        return null;
    }

        private static Integer callMethod4 (String s){
            callMethod5(s);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                String name = new Object() {
                }.getClass().getEnclosingMethod().getName();
                System.out.println("Found error in " + name + " -> NumberFormatException");
                return null;
            }
        }

        private static void callMethod5 (String s){
            callMethod6("");
            String[] strings;  // TODO: нет обращений к массиву после инициализации
            try {
                strings = new String[5];
                for (int i = 1; i <= strings.length; i++) {
                    strings[i] = s;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                String name = new Object() {
                }.getClass().getEnclosingMethod().getName();
                System.out.println("Found error in " + name + " -> ArrayIndexOutOfBoundsException");
            }
        }
    }