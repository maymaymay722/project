import java.util.ArrayList;

/*
桶排序
 */
public class BucketSort {
    public static ArrayList<Integer> bucketSort(ArrayList<Integer> array,int bucketSize){
        if(array==null || array.size()<2){
            return array;
        }

        int max=array.get(0);
        int min=array.get(0);
        // 找到最大最小值
        for (int i = 1; i < array.size(); i++) {
            if(array.get(i)>max){
                max=array.get(i);
            }
            if(array.get(i)<min){
                min=array.get(i);
            }
        }

        int bucketCount=(max-min)/bucketSize+1;
        ArrayList<ArrayList<Integer>> bucketArr=new ArrayList<>(bucketCount);
        ArrayList<Integer> resultArr=new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            bucketArr.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < array.size(); i++) {
            bucketArr.get((array.get(i)-min)/bucketSize).add(array.get(i));
        }
        for (int i = 0; i < bucketCount; i++) {
            if(bucketCount==1){
                bucketSize--;
            }
            ArrayList<Integer> tmp=bucketSort(bucketArr.get(i),bucketSize);
            for (int j = 0; j < tmp.size(); j++) {
                resultArr.add(tmp.get(j));
            }
        }
        return resultArr;
    }
}
