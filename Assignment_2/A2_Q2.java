import java.util.*;
import java.math.BigInteger;

public class A2_Q2 {
    public static BigInteger num_swaps(int[] numbers){
        //this problem is basically a variation of merge sort 
        return mergeSort(numbers, 0, numbers.length-1);        
    }

    public static BigInteger mergeSort(int[] numbers, int left, int right){
        BigInteger swaps = BigInteger.ZERO;
        if (left < right){
            int middle = (left + right) / 2;
            //count the number of swaps in our left subarray
            swaps = swaps.add(mergeSort(numbers,left, middle));
            //count the number of swaps in our right subarray
            swaps = swaps.add(mergeSort(numbers,middle+1, right));
            //now merge the two subarrays and count the swaps while we do it
            swaps = swaps.add(merge(numbers, left, middle, right));
        }

        return swaps;
    }

    public static BigInteger merge(int[] numbers, int left, int middle, int right){
        BigInteger swaps = BigInteger.ZERO;

        //create our left and right arrays
        int leftSize = middle - left + 1;
        int rightSize = right - middle;

        int[] left_array = new int[leftSize];
        int[] right_array = new int[rightSize];

        for (int i = 0; i < leftSize; i++){
            left_array[i] = numbers[left + i];
        }

        for (int i = 0; i < rightSize; i++){
            right_array[i] = numbers[middle + 1 + i];
        }

        
        //now we do the merging
        int i = 0, j = 0, k = left;
        while (i < leftSize && j < rightSize){
            if (left_array[i] <= right_array[j]){
                //if our left subarray's element is less than the right 
                //subarray's element, no swap occurs so we just copy the 
                //number to our array, and continue to iterate
                numbers[k] = left_array[i];
                i++;
            } else {
                //however if our right subarray's element is less than the
                //left subarray's element, a swap DOES occur (as it was ahead of the left element originally)
                //so we update our number of swaps ny leftSize - i (how many we jumped over)
                numbers[k] = right_array[j];
                j++;
                swaps = swaps.add(BigInteger.valueOf(leftSize - i));
            }
            k++;
        }

        //copy our remaining elements in both arrays 
        while (i < leftSize){
            numbers[k] = left_array[i];
            i++;
            k++;
        }

        while (j < rightSize){
            numbers[k] = right_array[j];
            j++;
            k++;
        }

        return swaps;
    }
}
