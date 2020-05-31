package src;

import java.util.Arrays;
import java.util.Date;

public class DealData {
    //存放整个所有页的数组
    private int[] datas = {};

    private int num = 0;

    private Date date;

    public void getFormatData(int[] in){
        boolean flag = false;
        if(in[in.length-1] == 0) flag = true;
        int len = this.datas.length;
        this.datas = Arrays.copyOf(this.datas, len + in.length);
        for(int i = 0; i < in.length; i++) {
            if(in[i] == 0){
                this.datas = Arrays.copyOfRange(this.datas, 0, len + i);
                break;
            }
            this.datas[len + i] = in[i];
        }
        if(flag){
            for(int i = 0; i < this.datas.length-1; i+=2) this.datas[i/2] = this.datas[i];
            this.datas = Arrays.copyOfRange(this.datas, 0, this.datas.length/2);
            this.num = this.datas.length;
            this.date = new Date();
            for(int data:datas) System.out.printf("%x ",data);
            this.datas = new int[0];
        }
    }

}
