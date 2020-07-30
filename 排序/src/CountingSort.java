import java.lang.reflect.Array;
import java.util.Arrays;

/*
计数排序
 */
public class CountingSort {
    public static int[] CountingSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        int bias;
        int min = array[0];
        int max = array[0];

        // 找出待排序的数组中最大和最小的元素
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }

        // 统计每个值出现的次数，存在数组bucket中
        bias = 0 - min;
        int[] bucket = new int[max - min + 1];  // 数组的长度
        // 将指定的布尔值分配给数组的每个元素
        Arrays.fill(bucket, 0);
        for (int i = 0; i < array.length; i++) {
            bucket[array[i] + bias]++;
        }

        int index = 0, i = 0;
        while (index < array.length) {
            if (bucket[i] != 0) {
                array[index] = i - bias;
                bucket[i]--;
                index++;
            } else {
                i++;
            }
        }
        return array;
    }
}
