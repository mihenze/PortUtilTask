package org.example.portTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Утилитный класс для работы с последовательностью чисел произвольной длины,
 * полученной из объекта Port в виде массива строк. Каждая строка представляет из себя
 * последовательность чисел, перечисленных через дефис и(или) через запятую,
 * пример: "1-5,7,9-11"
 * @author Mikhail Yakimenko
 * @version 1.0
 * */
public final class PortUtil {

    /** Паттерн для проверки валидности полученного массива строк
     * Последовательность должна начинаться и заканчиваться числом и содержать в себе цифры, запятую и дефис
     * */
    private static final String patternSequence = "^[0-9]$|^[0-9]+[0-9,-]*[0-9]$";

    /** Данный метод преобразует массив строк из объекта Port в массив последовательности чисел
     * @param indexes - массив строк
     * @return возвращает последовательность чисел
     * */
    public static int[][] getSequencesNumber(String[] indexes){
        // если пришел null
        if (indexes == null) {
            // вернем пустой массив
            return new int[][]{{}};
        }

        int [][] resultArray = new int[indexes.length][];
        int iteratorResultArray = 0;

        for (String index : indexes){
            // проверка если пришла не пустая, не верная последовательность
            if (!index.matches(patternSequence) && !index.isBlank()){
                //выбрасываем исключение
                throw new IllegalArgumentException("Invalid sequence!");
            }

            List<Integer> arrayList = new ArrayList<>();
            // паттерн для проверки строки на число
            String digit = "[0-9]+";

            // добавим пробелы перед знаками, чтобы разделить полученную последовательность на части
            String[] arrayX = index.replace(",", " , ").replace("-", " - ").split(" ");
            int iterator = 0;
            while(iterator < arrayX.length) {
                if(arrayX[iterator].matches(digit)) { // если число то добавляем
                    arrayList.add(Integer.parseInt(arrayX[iterator]));
                } else {
                    if (arrayX[iterator].equals("-")) { // если дефис
                        // определяем начало диапазона
                        int start = Integer.parseInt(arrayX[iterator-1]);
                        iterator++;
                        // добавляем все элементы, пока не дойдем до конца диапазона
                        for (int j = ++start; j <= Integer.parseInt(arrayX[iterator]); j++) {
                            arrayList.add(j); // добавляем
                        }
                    }
                }
                iterator++;
            }
            // преобразуем в массив
            int[] temp = arrayList.stream().mapToInt(e -> e).toArray();
            resultArray[iteratorResultArray++] = temp;
        }

        return resultArray;
    }

    /** Данный метод возвращает все возможные упорядоченные пары элементов, полученных массивов чисел,
     * которые можно получить используя метод {@link #getSequencesNumber(String[])}
     * @param inputArrays - массив последовательности чисел
     * метод основан на Декартовом произведении множеств.
     * @return возвращает упорядоченные пары элементов
     * */
    public static int[][] getPairElements(int[][] inputArrays) {
        // если пришел null
        if (inputArrays == null) {
            // вернем пустой массив
            return new int[][]{{}};
        }

        // промежуточный результат
        int[][] result = new int[][]{{}};

        // обходим входные массивы
        for (int i = 0; i < inputArrays.length; i++) {
            // текущий массив
            int[] currentArray = inputArrays[i];
            // если массив не равен null и не пустой
            if (!(currentArray == null || currentArray.length == 0)) {
                // промежуточный результат для следующей итерации
                int[][] nextResult = new int[result.length * currentArray.length][];

                // строки текущего промежуточного результата
                for (int j = 0; j < result.length; j++) {
                    // текущая строка
                    int[] row = result[j];
                    // элементы текущего массива
                    for (int k = 0; k < currentArray.length; k++) {
                        // текущий элемент
                        int element = currentArray[k];
                        // копируем текущую строку в новый массив [длина + 1]
                        int[] array = Arrays.copyOf(row, row.length + 1);
                        array[row.length] = element;
                        nextResult[j * currentArray.length + k] = array;
                    }
                }
                // передаём на следующую итерацию
                result = nextResult;
            }
        }
        // возвращаем итоговый результат
        return result;
    }
}
