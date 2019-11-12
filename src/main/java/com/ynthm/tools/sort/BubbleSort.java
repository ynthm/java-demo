package com.ynthm.tools.sort;

import java.util.Arrays;

/**
 * Author : Ynthm
 */
public class BubbleSort {



    public static void sort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        int length = array.length;
        //外层：需要length-1次循环比较
        for (int i = 0; i < length - 1; i++) {
            //内层：每次循环需要两两比较的次数，每次比较后，都会将当前最大的数放到最后位置，所以每次比较次数递减一次
            for (int j = 0; j < length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    //交换数组array的j和j+1位置的数据
                    swap(array, j, j + 1);
                }
            }
        }
    }

    /**
     * 交换数组array的i和j位置的数据
     *
     * @param array 数组
     * @param i     下标i
     * @param j     下标j
     */
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     *  快速排序（挖坑法递归）
     * @param arr   待排序数组
     * @param low   左边界
     * @param high  右边界
     */
    public static void sort(int arr[], int low, int high) {
        if (arr == null || arr.length <= 0) {
            return;
        }
        if (low >= high) {
            return;
        }

        int left = low;
        int right = high;
        int temp = arr[left]; //挖坑1：保存基准的值

        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }
            arr[left] = arr[right]; //坑2：从后向前找到比基准小的元素，插入到基准位置坑1中
            while (left < right && arr[left] <= temp) {
                left ++;
            }
            arr[right] = arr[left]; //坑3：从前往后找到比基准大的元素，放到刚才挖的坑2中
        }
        arr[left] = temp; //基准值填补到坑3中，准备分治递归快排
        System.out.println("Sorting: " + Arrays.toString(arr));
        sort(arr, low, left-1);
        sort(arr, left + 1, high);
    }
}
