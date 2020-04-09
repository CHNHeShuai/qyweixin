package com.sinocontact.qyweixin.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jerry
 * @description
 * @since 2020/4/8 16:26
 */
public class LeetCode {
    public static int movingCount(int m, int n, int k) {
        if(n<1 || n>100 || m<1 || m>100){
            return 0;
        }
        if(k==0){
            return 1;
        }
        int count = 0;
        //向上移动
        tag:for (int i=0;i<=m-1;i++){
            //向右移动
            for(int j=0;j<=n-1;j++){
                int sum = 0;
                if(i>9){
                    sum += i/10 + i%10;
                }else {
                    sum += i;
                }
                if(j>9){
                    sum += j/10 + j%10;
                }else {
                    sum += j;
                }
                if(sum<=k){
                    count++;
                }else {
                    continue tag;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int i = movingCount(16, 8, 4);
        System.out.println(i);
    }
}
