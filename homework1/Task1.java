package homework1;

public class Task1 {
    /* Реализуйте метод, принимающий в качестве аргументов двумерный массив.
     * Метод должен проверить что длина строк и столбцов с одинаковым индексом одинакова,
     * детализировать какие строки со столбцами не требуется. */

    public static void main(String[] args) {

        int[][] ints1 = new int[5][];
        ints1[0] = new int[]{1, 2, 3, 4, 5};
        ints1[1] = new int[]{1, 2, 3, 4};
        ints1[2] = new int[]{1, 2, 3};
        ints1[3] = new int[]{1, 2, 3, 4};
        ints1[4] = new int[]{1, 2};

        int[][] ints2 = new int[5][];
        ints2[0] = new int[]{1, 2, 3, 4, 5};
        ints2[1] = new int[]{1, 2, 3, 4, 5};
        ints2[2] = new int[]{1, 2, 3, 4, 5};
        ints2[3] = new int[]{1, 2, 3, 4, 5};
        ints2[4] = new int[]{1, 2, 3, 4, 5};

        int[][] ints3 = null;

        System.out.println(isMatrixSquared(ints1));
        System.out.println(isMatrixSquared(ints2));
        System.out.println(isMatrixSquared(ints3));
    }

    private static boolean isMatrixSquared(int[][] matrix) {
        try {
            if (matrix == null) {
                throw new NullPointerException();
            }
            for (int[] ints : matrix) {
                if (ints.length != matrix.length) {
                    throw new RuntimeException();
                }
            }
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

}