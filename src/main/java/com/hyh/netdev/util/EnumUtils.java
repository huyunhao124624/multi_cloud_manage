package com.hyh.netdev.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumUtils {
    public static <T> T getEnumByCode(Class<T> clazz, String code) {
        if (clazz == null || StringUtils.isBlank(code) || !clazz.isEnum()) {
            return null;
        }

        T[] objs = (T[]) clazz.getEnumConstants();
        for (int i = 0; i < objs.length; i++) {
            try {
                Method m = clazz.getMethod("getCode");
                if (code.equals(m.invoke(objs[i]))) {
                    return objs[i];
                }
            } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
