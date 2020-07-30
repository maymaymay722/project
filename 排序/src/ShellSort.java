
/*
希尔排序
 */
public class ShellSort {
    public static int[] shellSort(int[] array) {
        if (array.length == 0) {
            return array;
        }

        int len = array.length;
        int tmp, gap = len / 2;

        while (gap > 0) {
            for (int i = gap; i < len; i++) {
                tmp = array[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && array[preIndex] > tmp) {
                    array[preIndex + gap] = array[preIndex];
                    preIndex -= gap;
                }
                array[preIndex + gap] = tmp;
            }
            gap /= 2;
        }
        return array;
    }
}
