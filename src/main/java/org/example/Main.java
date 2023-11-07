package org.example;

import org.example.Uitls.DataInit;

import java.io.File;
import java.util.*;

public class Main {


    public static void main(String[] args) throws Exception{



        String[] keys = {"好吃","好喝","好玩","开心","春天","大地","太阳","头发","奔跑","柴火","闪电","祝福","山川","心脏","月亮"};
        // 初始化数据处理类
        DataInit dataInit = new DataInit(
                "res/user_tag_query.10W.TRAIN",
                new String[]{"好吃","好喝","好玩","开心","春天","大地","太阳","头发","奔跑","柴火","闪电","祝福","山川","心脏","月亮"},
                new String[]{"的","吗","什么","有","一个","是"},
                "res/output.txt"
                keys,
                "res/output.txt",
                true
        );

        String[] keys = {"好吃","好喝","好玩","开心","春天","大地","太阳","头发","奔跑","柴火","闪电","祝福","山川","心脏","月亮"};
        // 初始化数据处理类
        DataInit dataInit = new DataInit(
                "res/user_tag_query.10W.TRAIN",
                keys,
                "res/output.txt",
                true
        );

        //进行数据处理
        int[] lines = new int[15]; // lines[n]表示第n个关键词出现的次数(s)
        try {
            dataInit.doInit(lines);
        }catch (Exception e){
            e.printStackTrace();
        }

        // 测试第n个关键词出现次数的统计
//      for(int line : lines){
//          System.out.println(line);
//      }

        CompKeyUtil compKeyUtil = new CompKeyUtil(dataInit);
        //遍历种子关键词
        for(String key : keys){
            Map<String, Double> compareMap = new HashMap<>();

            List<Map.Entry<String, Integer>> middleKeys = compKeyUtil.findMiddle(key); // 得到中间关键词及其在output中出现次数(sa)
            middleKeys.remove(0);  // 将自身排除
            /*
            * for (Map.Entry<String, Integer> middleKey : middleKeys) {
            *    * middleKey.getKey()   //   中间关键词
            *    * middleKey.getValue() //   权重(sa)
            * }
            * */

            int[] counts = new int[CAPACITY];        // 储存第n个中间关键词在整体出现的次数(a)
            String[] middles = new String[CAPACITY]; // 储存第n个关键词本身

            //遍历中间关键词得到竞争关键词
            for (int i = 0; i < CAPACITY; i++) {
                Map.Entry<String, Integer> entry = middleKeys.get(i);
                String string = entry.getKey();
                middles[i] = string;
            }

            /*
            // 测试中间关键词查找是否正确
            for(String middle : middles){
                System.out.println(middle);
            }
            */

            // 查找各中间关键词在整体出现的次数(a)
            compKeyUtil.countMiddle(middles,counts);

            /*
            // 测试中间关键词查找是否正确
            for(int count : counts){
                System.out.println(count);
            }
            */

            /*
            for (Map.Entry<String, Integer> middleKey : middleKeys) {
                System.out.println(middleKey.getKey()+":"+middleKey.getValue()); //     竞争关键词
            }
            */

            int count = 0; // 中间关键词序号计数器
            for(String middle : middles){// 遍历中间关键词
                List<Map.Entry<String, Integer>> compareKeys = compKeyUtil.findCompare(middle); // 得到竞争关键词及其在原文件中出现次数(ka)

                compKeyUtil.calculateCompare(compareMap, middleKeys.get(count).getValue(), compareKeys, counts[count], lines);
                count++;
            }

            // 将LinkedHashMap转换为List
            List<Map.Entry<String, Double>> compareList = new ArrayList<>(compareMap.entrySet());
            // 使用Comparator和stream进行值的降序排序
            compareList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            // 排除本身
            compareList.remove(0);

//            // 按竞争度从大到小显示当前种子关键词的竞争关键词，（调试用）
//            count = 0;
//            for(Map.Entry<String, Double> map : compareList){
//                System.out.println(map.getKey() + ": "+ map.getValue());
//                count ++;
//                if(count == CAPACITY) break;
//            }

            /*
            * 如上，此程序块的上一级程序块为一个for循环，而循环的内容是遍历种子关键词数组
            * 即当前程序块是针对种子关键词数组中的某一个种子关键词
            * 对于竞争关键词，已经使用compareList进行封装
            * 该封装类型是一个List类，其中泛式为包含<String, Double>键值对的Map.Entry
            * 其中String为竞争关键词本身，Double为竞争该竞争关键词对于当前种子关键词的竞争度
            * 详细使用见上方调试代码
            * 在对compareList的遍历中，map.getKey()获得竞争关键词，map.getValue()获得竞争度，数据类型如上所述
            * compareList不仅只含有10个数据，其数据非常多，但由于简化计算，故对其循环次数默认定为静态量CAPACITY(此量在下方代码有定义)
            * */

        }
    }

    private static int CAPACITY = 10;
}