package com.sws.platform.mobile.common.util;

import java.util.Collection;
import java.util.Iterator;

public class StringUtil {
    public static boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str.toString());
    }

    public static boolean isNullOrEmptyOrWhiteSpace(String str) {
        return str == null || "".equals(str.toString().trim());
    }

    public static boolean hasText(String str) {
        return str != null && !"".equals(str.toString().trim());
    }

    public static String toString(Object obj) {
        if (obj == null) return "null";
        return obj.toString();
    }

    public static String padLeft(String str, int size) {
        if (str == null) str = "";
        int str_size = str.length();
        int pad_len = size - str_size;
        StringBuffer retvalue = new StringBuffer();
        for (int i = 0; i < pad_len; i++) {
            retvalue.append("0");
        }
        retvalue.append(str);
        return retvalue.toString();
    }

    public static String join(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }
}
