package com.ynthm.tools.sort;

/**
 * Author : Ynthm
 */
public class Test {

    public static void main(String[] args) {
        int[] arr = {9,8,7,6,5,4,3,2,1};
        BubbleSort.sort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
