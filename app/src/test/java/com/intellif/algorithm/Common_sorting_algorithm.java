package com.intellif.algorithm;

import com.intellif.test.A;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 通用算法: https://www.runoob.com/w3cnote/sort-algorithm-summary.html
 */
public class Common_sorting_algorithm {


    /**
     * 时间复杂度 n2
     */
    @Test
    public void xuanze() {
        int[] arr = {6, 5, 7, 18};
        int temp;//临时变量
        for (int i = 0; i < arr.length - 1; i++) {   //表示趟数，一共arr.length-1次。
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    /**
     * 基本思想：两个数比较大小，较大的数下沉，较小的数冒起来。
     * 过程：
     * 比较相邻的两个数据，如果第二个数小，就交换位置。
     * 从后向前两两比较，一直到比较最前两个数据。最终最小数被交换到起始的位置，这样第一个最小数的位置就排好了。
     * 继续重复上述过程，依次将第2.3...n-1个最小数排好位置。
     * 时间复杂度 n2
     */
    @Test
    public void maoPao() {
        int[] arr = {0,1,8};
        int temp;
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {

                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 在要排序的一组数中，假定前n-1个数已经排好序，现在将第n个数插到前面的有序数列中，使得这n个数也是排好顺序的。如此反复循环，直到全部排好顺序
     * <p>
     * 时间复杂度: o(n2)
     */
    @Test
    public void chaRu() {
        int array[] = {5, 3, 2};
        int lenth = array.length;
        int temp;

        for (int i = 0; i < lenth - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                } else {         //不需要交换
                    break;
                }
            }
        }
    }

    /**
     * 数据序列1： 13-17-20-42-28 利用插入排序，13-17-20-28-42. Number of swap:1;
     * 数据序列2： 13-17-20-42-14 利用插入排序，13-14-17-20-42. Number of swap:3;
     * 如果数据序列基本有序，使用插入排序会更加高效。
     * <p>
     * 基本思想：
     * 在要排序的一组数中，根据某一增量分为若干子序列，并对子序列分别进行插入排序。
     * 然后逐渐将增量减小,并重复上述过程。直至增量为1,此时数据序列基本有序,最后进行插入排序
     * 时间复杂度 : O(n^1.5)
     */
    @Test
    public void xiEr() {//这个算法参看菜鸟教程中示意图配合理解
        int[] array = {6, 3, 9, 8};
        int lenth = array.length;

        int temp = 0;
        int incre = lenth;

        while (true) {
            incre = incre / 2;

            for (int k = 0; k < incre; k++) {//  K值在增量内+1递增
                for (int i = k + incre; i < lenth; i += incre) {//i在数组长度内+增量递增  这样就能遍历到尾部
                    for (int j = i; j > k; j -= incre) { //进行增量跳跃后数组的插入排序

                        if (array[j] < array[j - incre]) {
                            temp = array[j - incre];
                            array[j - incre] = array[j];
                            array[j] = temp;
                        } else {
                            break;
                        }
                    }
                }
            }

            if (incre == 1) {
                break;
            }
        }
        System.out.println(Arrays.toString(array));

    }

    /**
     * 基本思想：（分治）
     *
     * 先从数列中取出一个数作为key值；
     * 将比这个数小的数全部放在它的左边，大于或等于它的数全部放在它的右边；
     * 对左右两个小数列重复第二步，直至各区间只有1个数
     *
     * 平均时间复杂度：O(N*logN)
     */
    @Test
    public void kuaiSu() {

    }


}
