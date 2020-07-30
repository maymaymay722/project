import java.util.Arrays;

/*
选择排序
 */
public class SelectionSort {
    public static int[] selectsort(int[] array){
        if(array.length==0){
            return array;
        }

        for (int i = 0; i < array.length; i++) {
            int minIndex=i;
            for (int j = i; j < array.length; j++) {
                // 找到最小数的索引
                if(array[j]<array[minIndex]){
                    // 将最小数的索引保存
                    minIndex=j;
                }
                int tmp=array[minIndex];
                array[minIndex]=array[i];
                array[i]=tmp;
            }
        }
        return array;
    }
}
