package org.example;

import org.example.Uitls.DataInit;

// 按两次 Shift 打开“随处搜索”对话框并输入 `show whitespaces`，
// 然后按 Enter 键。现在，您可以在代码中看到空格字符。
public class Main {
    public static void main(String[] args) {
//        初始化数据处理类
        DataInit dataInit = new DataInit(
                "res/user_tag_query.10W.TRAIN",
                new String[]{"好吃","好喝","好玩","开心","春天","大地","太阳","头发","奔跑","柴火","闪电","祝福","山川","心脏","月亮"},
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