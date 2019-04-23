package com.suke.RentalSystem.util;

import net.logstash.logback.encoder.org.apache.commons.lang.StringEscapeUtils;

public class QueryUtil {

    /**
     * 模糊查询处理"%","_","\"等字符
     * @param content
     * @return
     */
    public static String replaceSpecialCharactorsForLikeParam(String content){
        content = StringEscapeUtils.unescapeJava(content);
        StringBuffer stringBuffer = new StringBuffer();
        char[] charArrays = content.toCharArray();
        for (char c: charArrays) {
            if (c == '_' || c == '%' || c == '\'') {
                stringBuffer.append("\\" + c);
                } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }
}
