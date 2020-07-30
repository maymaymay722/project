
/*
快速排序
 */
public class QuickSort {
    public static void quickSort(int[] array){
        if(array.length==0){
            return;
        }
        quickSortInter(array,0,array.length-1);
    }

    // 待排序空间 [left,right]
    private static void quickSortInter(int[] array, int left, int right) {
        if(left>=right){
            return;
        }
        // 1.选择基准值 array[left]
        // 2.做 partition
        int pivotIndex=partition(array,left,right);
        // 左边小区间[left,pivotIndex-1]
        // 右边小区间[pivotIndex+1,right]
        // 3.分别对左右小区间按同样方式处理
        quickSortInter(array,left,pivotIndex-1);
        quickSortInter(array,pivotIndex+1,right);
    }

    private static int partition(int[] array, int left, int right) {
        int begin=left;
        int end=right;
        int pivot=array[left];

        // [left,begin)  <= pivot
        // (end,right]   >= pivot
        while(begin<end){
            while(begin<end && array[end]>=pivot){
                end--;
            }
            while(begin<end && array[begin]<= pivot){
                begin++;
            }
            swap(array,begin,end);
        }
        swap(array,left,begin);
        return begin;
    }

    private static void swap(int[] array,int i,int j){
        int tmp=array[i];
        array[i]=array[j];
        array[j]=tmp;
    }
}
