package com.ynthm.tools.sort;

/**
 * 希尔排序
 * Author : Ynthm
 */
public class ShellSort {
    public static void shellSort(int[] array){
        int j;
        for(int gap = array.length/2; gap>0; gap /= 2){
            //定义一个增长序列，即分割数组的增量,d1=N/2   dk=(d(k-1))/2
            for(int i = gap; i<array.length;i++){
                int tmp = array[i];
                for( j =i; j>=gap&&tmp<array[j-gap]; j -= gap){
                    //将相距为Dk的元素进行排序
                    array[j] = array[j-gap];
                }
                array[j] = tmp;
            }
        }
    }
}
