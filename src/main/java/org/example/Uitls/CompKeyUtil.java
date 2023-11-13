package org.example.Uitls;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.*;
import java.util.*;

@Data
@AllArgsConstructor
public class CompKeyUtil {

//    public static void main(String[] args) {
//        String[] keys = {"耳机","好喝","好玩","开心","春天","大地","太阳","头发","奔跑","柴火","闪电","祝福","山川","心脏","月亮"};
//        String[] no_use_args = {"的", "吗", "什么", "有", "一个", "是"};
//        // 初始化数据处理类
//        DataInit dataInit = new DataInit(
//                "res/user_tag_query.10W.TRAIN",
//                keys,
//                no_use_args,
//                "res/output.txt",
//                true
//        );
//
//    }

    public List<Map.Entry<String, Integer>> findMiddle(String key) throws Exception{
        // 打开并读取文件
        InputStreamReader inStream = new InputStreamReader(new FileInputStream(new File(dataInit.getOutPath())), "GBK");
        BufferedReader br = new BufferedReader(inStream);
        String valueString = null;
        String[] tokens = null;
        Map<String, Integer> wordCountMap= new LinkedHashMap<>();

        // 读取中间关键词并置入map中
        while((valueString = br.readLine()) != null){
            if(!valueString.contains(key)) continue; // 如果该搜索记录含有所需关键字

            tokens = valueString.split(" ");
            for(String token : tokens){
                wordCountMap.put(token, wordCountMap.getOrDefault(token, 0) + 1);
            }
        }

        // 将LinkedHashMap转换为List
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(wordCountMap.entrySet());
        // 使用Comparator和stream进行值的降序排序
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        /*
        // 测试输出排序后的结果
        System.out.println("source key:" + key);
        int count = 0;
        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            count++;
            if(count==CAPACITY) break;
        }
         */
        return entryList;
    }

    public void countMiddle(String[] middles, int[] counts) throws Exception{
        InputStreamReader inStream = new InputStreamReader(new FileInputStream(new File(dataInit.getInPath())), "GBK");
        BufferedReader br = new BufferedReader(inStream);

        String valueString = null;
        String[] tokens = null;
        while((valueString = br.readLine()) != null){
            tokens = valueString.split("\t");
            for(String token : tokens){
                for(int i = 0; i < CAPACITY ; i++){
                    if(token.contains(middles[i])){
                        counts[i]++;
                    }
                }
            }
        }

    }

    public List<Map.Entry<String, Integer>> findCompare(String key) throws Exception{
        // 打开并读取文件
        InputStreamReader inStream = new InputStreamReader(new FileInputStream(new File(dataInit.getInPath())), "GBK");
        BufferedReader br = new BufferedReader(inStream);
        String valueString = null;
        String[] tokens1 = null;
        String[] tokens2 = null;
        Map<String, Integer> wordCountMap= new LinkedHashMap<>();

        int sum = 0;
        // 读取关键词并置入map中
        while((valueString = br.readLine()) != null){
            tokens1 = valueString.split("\t");
            for(String token : tokens1){                     // 对于每一个用户的搜索
                sum++;
                if (token.contains(key)){
                    String temp = IKUtil.splitKeyWord(token).toString();
                    tokens2 = temp.split(" ");
                    for(String tok : tokens2){
                        if(tok.equals(key)) continue;
                        wordCountMap.put(tok, wordCountMap.getOrDefault(tok, 0) + 1);
//                        System.out.println(wordCountMap.getOrDefault(tok, 0));
                    }
                }
            }

        }
//        System.out.println("DEBUG..." + sum);

        // 将LinkedHashMap转换为List
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(wordCountMap.entrySet());
        // 使用Comparator和stream进行值的降序排序
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));


        /*
        // 测试输出排序后的结果
        System.out.println("middle key:" + key);
        int count = 0;
        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            count++;
            if(count==CAPACITY) break;
        }
         */

        return entryList;
    }

    public void calculateCompare(
            Map<String, Double> compareMap,
            int middleKeyCount,                            //                         (sa)
            List<Map.Entry<String, Integer>> compareKeys,  // compareKey.getValue()   (ka)
            int counts,                                    //                         (a)
            int[] lines                                    //                         (s)
    ){
        double degree = 0;
        String word = "";
        for(int i = 0; i < CAPACITY; i++){
//            System.out.println("DEBUGING2...");
//            System.out.println("sa: "+ middleKeys.get(i).getValue().doubleValue());
//            System.out.println("ka: "+ compareKeys.get(i).getValue().doubleValue());
//            System.out.println("a: "+ counts[i]);
//            System.out.println("s: "+ lines[i]);
            word = compareKeys.get(i).getKey();
            degree = (middleKeyCount / lines[i]) * compareKeys.get(i).getValue().doubleValue() / (counts - middleKeyCount);
            compareMap.put(word, compareMap.getOrDefault(word, 0.0) + degree);
        }
    }

    private DataInit dataInit;
    private static int CAPACITY = 10;
}
