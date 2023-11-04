package org.example.Uitls;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class IKUtil {

    /**
     *  分词
     * @param keyword 需要分词的文本
     * @return result
     */
    public static String splitKeyWord(String keyword) throws IOException {

        String result = new String();
        // 创建一个reader对象
        StringReader reader = new StringReader(keyword);
        // 创建一个分词对象
        IKSegmenter ikSegmenter = new IKSegmenter(reader, true);
        Lexeme next = ikSegmenter.next();

        while ( next != null ) {
            // 获取分词的结果
            result += (next.getLexemeText()+" ");
            next = ikSegmenter.next();
        }
        return result;
    }
}

