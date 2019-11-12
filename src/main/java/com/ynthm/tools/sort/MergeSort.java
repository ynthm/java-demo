package com.ynthm.tools.sort;

/**
 * 归并排序
 * Author : Ynthm
 */
public class MergeSort {
    private static void mergeSort(int[] array,int[] tmp,int left,int right){
        if(left<right){
            int center = ( left + right ) / 2;//取数组的中点
            mergeSort(array,tmp,left,center);//归并排序数组的前半部分
            mergeSort(array,tmp,center+1,right);//归并排序数组的后半部分
            merge(array,tmp,left,center+1,right);//将数组的前后半部分合并
        }
    }
    /*
     * 超简单的合并函数
     */
    private static void merge(int[] array, int[] tmp, int leftPos, int rightPos, int rightEnd) {
        // TODO Auto-generated method stub
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;
        while(leftPos <= leftEnd && rightPos <= rightEnd){
            if(array[leftPos]<=array[rightPos]){
                tmp[tmpPos++] = array[leftPos++];
            }else{
                tmp[tmpPos++] = array[rightPos++];
            }
        }
        while(leftPos <= leftEnd){
            tmp[tmpPos++] = array[leftPos++];
        }
        while(rightPos <= rightEnd){
            tmp[tmpPos++] = array[rightPos++];
        }
        for(int i=0;i<numElements;i++,rightEnd--){
            array[rightEnd] = tmp[rightEnd];
        }
    }
    public static void mergeSort(int[] array){
        int[] tmp = new int[array.length];//声明一个用来合并的数组
        mergeSort(array,tmp,0,array.length-1);//调用排序函数，传入数字的起点和终点
    }
}
