package org.example.Uitls;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;

@Data
@AllArgsConstructor
public class DataInit {

    public void doInit(int[] lines) throws Exception{
        InputStreamReader inStream = new InputStreamReader(new FileInputStream(new File(inPath)), "GBK");
        OutputStreamWriter outStream = new OutputStreamWriter(new FileOutputStream(new File(outPath)), "GBK");
        BufferedReader br = new BufferedReader(inStream);
        BufferedWriter bw = new BufferedWriter(outStream);
        bw.flush(); // 将缓存区写入

        int lineCount = 0;
        while ((br.readLine()) != null) {
            lineCount++;
            if(lineCount == 2){
                isEmpty = false;
                break;
            }
        }

        String valueString = null;
        String[] tokens = null;

        while((valueString = br.readLine()) != null){
            tokens = valueString.split("\t");
            for(int count = 0; count < 15; count++){
                for(String token : tokens){
                    if (token.contains(keys[count])){
                        String temp = IKUtil.splitKeyWord(token).toString();
                        if(true){
                            bw.append(temp);
                            bw.newLine();
                        }
                        lines[count]++;
                    }
                }
            }
        }



        for(String key:keys){

        }
        bw.close();
    }

    private String inPath; // 输入文件路径
    private String[] keys; // 关键字数组
    private String outPath; // 输出文件路径
    private boolean isEmpty;
}
