package com.ynthm.tools.sort;

/**
 * 选择排序
 * Author : Ynthm
 */
public class SelectSort {
    public static void selectSort(int[] array){
        for(int i = 0;i<array.length-1;i++){
            int min = array[i];
            int minindex = i;
            for(int j = i;j<array.length;j++){
                if(array[j]<min){  //选择当前最小的数
                    min = array[j];
                    minindex = j;
                }
            }
            if(i != minindex){ //若i不是当前元素最小的，则和找到的那个元素交换
                array[minindex] = array[i];
                array[i] = min;
            }
        }
    }
}
