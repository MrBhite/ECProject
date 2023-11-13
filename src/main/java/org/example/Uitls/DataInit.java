package org.example.Uitls;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;

@Data
@AllArgsConstructor
public class DataInit {

    public static void main(String[] args) throws Exception {
        String[] keys = {"耳机","好喝","好玩","开心","春天","大地","太阳","头发","奔跑","柴火","闪电","祝福","山川","心脏","月亮"};
        String[] no_use_args = {"的", "吗", "什么", "有", "一个", "是"};
        // 初始化数据处理类
        DataInit dataInit = new DataInit(
                "res/user_tag_query.10W.TRAIN",
                keys,
                no_use_args,
                "res/output.txt",
                true
        );

        //进行数据处理
        int[] lines = new int[15]; // lines[n]表示第n个关键词出现的次数(s)
        try {
            dataInit.doInit(lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doInit(int[] lines) throws Exception {
        InputStreamReader inStream = new InputStreamReader(new FileInputStream(new File(inPath)), "GBK");
        OutputStreamWriter outStream = new OutputStreamWriter(new FileOutputStream(new File(outPath)), "GBK");
        BufferedReader br = new BufferedReader(inStream);
        BufferedWriter bw = new BufferedWriter(outStream);
        bw.flush(); // 将缓存区写入

        int lineCount = 0;
        while ((br.readLine()) != null) {
            lineCount++;
            if (lineCount == 2) {
                isEmpty = false;
                break;
            }
        }

        String valueString = null;
        String[] tokens = null;

        while ((valueString = br.readLine()) != null) {
            tokens = valueString.split("\t");
            for (int count = 0; count < 15; count++) {
                for (String token : tokens) {
                    if (token.contains(keys[count])) {
                        String temp = IKUtil.splitKeyWord(token).toString();
                        for (String no_use : no_use_args) {
                            if (temp.contains(no_use)) {
                                temp = temp.replace(no_use, "");
                            }
                        }
                        bw.append(temp);
                        bw.newLine();
                        lines[count]++;
                    }
                }
            }
        }
        bw.close();
    }

    private String inPath; // 输入文件路径
    private String[] keys; // 关键字数组
    private String[] no_use_args;//停用词数组
    private String outPath; // 输出文件路径
    private boolean isEmpty;
}
