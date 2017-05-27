package io.github.johnshen1990.games.drawsomething.utils;

/**
 * Author: zhun.shen
 * Date: 2017-05-27 18:36
 * Description:
 */
public class XssUtil {
    public static String xssEncode(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '&':
                    sb.append("＆");
                    break;
                case '<':
                    sb.append("《");
                    break;
                case '>':
                    sb.append("》");
                    break;
                case '"':
                    sb.append("“");
                    break;
                case '\'':
                    sb.append("＼");
                    break;
                case '/':
                    sb.append("／");
                    break;
                default:
                    sb.append(ch);
            }
        }
        return sb.toString();
    }
}
