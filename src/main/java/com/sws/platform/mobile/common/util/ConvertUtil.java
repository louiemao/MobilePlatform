package com.sws.platform.mobile.common.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ConvertUtil {

    public static int ToInt(Object o) {
        if (o == null) return 0;
        double d = Double.parseDouble(o.toString());
        int i = 0;
        i -= d;
        return -i;
    }

    public static String ToString(Object o) {
        if (o == null) return "";
        return o.toString();
    }

    public static Timestamp ToDate(Object o) {
        try {
            if (o.getClass() == String.class) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                o = format.parse(o.toString());
                return new Timestamp(((Date) o).getTime());
            }
            return o != null ? new Timestamp(((Date) o).getTime()) : null;
        } catch (Exception ex) {
            return null;
        }
    }

    public static <T> T mapToObject(Map map,Class<T> beanClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if(map==null){
            return null;
        }
        T obj=beanClass.newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(obj,map);
        return obj;
    }
}
