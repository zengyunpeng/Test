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
        int[] arr = {0, 1, 8};
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
     * <p>
     * 先从数列中取出一个数作为key值；
     * 将比这个数小的数全部放在它的左边，大于或等于它的数全部放在它的右边；
     * 对左右两个小数列重复第二步，直至各区间只有1个数
     * <p>
     * 平均时间复杂度：O(N*logN)
     */
    @Test
    public void kuaiSu() {
        int[] arr = {3, 5, 8, 1, 2, 9, 4, 7, 6};
        System.out.println("排序前：" + Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println("快速排序：" + Arrays.toString(arr));
    }


    /**
     * 快速排序：对冒泡排序的一种改进
     *
     * @param arr        待排序数组
     * @param startIndex 待排序序列的起点
     * @param endIndex   待排序序列的终点
     */
    public static void quickSort(int[] arr, int startIndex, int endIndex) {
        if (startIndex >= endIndex) return;  // 不满足排序条件，直接返回

        int left = startIndex;  // 初始化，左指针指向起始位置
        int right = endIndex;  // 初始化，右指针指向最大索引位置
        int pivot = arr[endIndex];  // 初始化，基准值选取最大索引位置的值

        // 左右指针相遇时结束
        while (left != right) {
            // 在不越界的前提下，移动左指针，找到大于 pivot 的数
            while (left < right && arr[left] <= pivot) {
                left++;  // 没找到，将左指针后移，继续找
            }
            // 在不越界的前提下，移动右指针，找到小于 pivot 的数
            while (left < right && arr[right] >= pivot) {
                right--;  // 没找到，将右指针前移，继续找
            }
            // 两个 while 循环都停止时，交换 left 与 right 位置的值
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }
        // 当左右指针相遇时，一轮排序结束，需要先交换 pivot 与 相遇位置的值，保证 pivot 左边都比自己小，右边都比自己大
        arr[endIndex] = arr[left];  // pivot 的位置为原来的 endIndex 处    走到这一步代码， arr[left]>pivot left=right
        arr[left] = pivot;
        // 对 pivot 左右两边的数据再次进行上面的处理，递归
        quickSort(arr, startIndex, left - 1);
        quickSort(arr, right + 1, endIndex);
        //
    }

    /**
     * 代码简化
     *
     * @param a
     * @param l
     * @param r
     */
    public static void quickSort2(int a[], int l, int r) {
        if (l >= r)
            return;

        int i = l;
        int j = r;
        int key = a[l];//选择第一个数为key

        while (i < j) {

            while (i < j && a[j] >= key)//从右向左找第一个小于key的值
                j--;
            if (i < j) {
                a[i] = a[j];
                i++;
            }

            while (i < j && a[i] < key)//从左向右找第一个大于key的值
                i++;

            if (i < j) {
                a[j] = a[i];
                j--;
            }
        }
        //i == j
        a[i] = key;
        quickSort2(a, l, i - 1);//递归调用
        quickSort2(a, i + 1, r);//递归调用
    }

    /**
     * 归并排序
     * 时间复杂度 O(N*logN)
     */
    public static void merge_sort(int a[],int first,int last,int temp[]){

        if(first < last){
            int middle = (first + last)/2;
            merge_sort(a,first,middle,temp);//左半部分排好序
            merge_sort(a,middle+1,last,temp);//右半部分排好序
            mergeArray(a,first,middle,last,temp); //合并左右部分
        }
    }

    //合并 ：将两个有序的序列a[first-middle],a[middle+1-end]合并
    public static void mergeArray(int a[],int first,int middle,int end,int temp[]){
        int i = first;
        int m = middle;
        int j = middle+1;
        int n = end;
        int k = 0;
        while(i<=m && j<=n){
            if(a[i] <= a[j]){
                temp[k] = a[i];
                k++;
                i++;
            }else{
                temp[k] = a[j];
                k++;
                j++;
            }
        }
        while(i<=m){
            temp[k] = a[i];
            k++;
            i++;
        }
        while(j<=n){
            temp[k] = a[j];
            k++;
            j++;
        }

        for(int ii=0;ii<k;ii++){
            a[first + ii] = temp[ii];
        }
    }
    //------------------------------------------------------------------------------------------
    //构建最小堆
    public static void MakeMinHeap(int a[], int n){
        for(int i=(n-1)/2 ; i>=0 ; i--){
            MinHeapFixdown(a,i,n);
        }
    }
    //从i节点开始调整,n为节点总数 从0开始计算 i节点的子节点为 2*i+1, 2*i+2
    public static void MinHeapFixdown(int a[],int i,int n){

        int j = 2*i+1; //子节点
        int temp = 0;

        while(j<n){
            //在左右子节点中寻找最小的
            if(j+1<n && a[j+1]<a[j]){
                j++;
            }

            if(a[i] <= a[j])
                break;

            //较大节点下移
            temp = a[i];
            a[i] = a[j];
            a[j] = temp;

            i = j;
            j = 2*i+1;
        }
    }

    public static void MinHeap_Sort(int a[],int n){
        int temp = 0;
        MakeMinHeap(a,n);

        for(int i=n-1;i>0;i--){
            temp = a[0];
            a[0] = a[i];
            a[i] = temp;
            MinHeapFixdown(a,0,i);
        }
    }


}
