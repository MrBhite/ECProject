package org.example.Uitls;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;

@Data
@AllArgsConstructor
public class DataInit {

    public void doInit() throws Exception{
        InputStreamReader inStream = new InputStreamReader(new FileInputStream(new File(inPath)), "GBK");
        OutputStreamWriter outStream = new OutputStreamWriter(new FileOutputStream(new File(outPath)), "GBK");
        BufferedReader br = new BufferedReader(inStream);
        BufferedWriter bw = new BufferedWriter(outStream);

        String valueString = null;
        String[] tokens = null;

        while((valueString = br.readLine()) != null){
            tokens = valueString.split("\t");
            for(String token : tokens){
                for(String key:args){
                    if (token.contains(key)){
                        bw.append(token);
                        bw.newLine();
                    }
                }
            }
        }

        bw.close();
    }

    private String inPath; // 输入文件路径
    private String[] args; // 关键字数组
    private String outPath; // 输出文件路径
}