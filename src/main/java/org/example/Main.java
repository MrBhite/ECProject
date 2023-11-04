package org.example;

import org.example.Uitls.DataInit;
public class Main {


    public static void main(String[] args) {
        // 初始化数据处理类
        DataInit dataInit = new DataInit(
                "res/user_tag_query.10W.TRAIN",
                new String[]{"好吃","好喝","好玩","开心","春天","大地","太阳","头发","奔跑","柴火","闪电","祝福","山川","心脏","月亮"},
                new String[]{"的","吗","什么","有","一个","是"},
                "res/output.txt"
        );

        //进行数据处理
        try {
            dataInit.doInit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}