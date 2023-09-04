package homewrok2;

import java.util.Random;

public class Task2 {
    public static Integer[] intArray;

    public static void main(String[] args) {
        intArrayFillIn();
        try {
            int d = 0;
            double catchedRes1 = intArray[8] / d;
            System.out.println("catchedRes1 = " + catchedRes1);
        } catch (ArithmeticException | IndexOutOfBoundsException e) {
            System.out.println("Catching exception: " + e);
        }
    }

    public static void intArrayFillIn() {
        Random random = new Random();
        intArray = new Integer[random.nextInt(1, random.nextInt(10, 20))];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = random.nextInt(0, 20);
        }
    }
}
