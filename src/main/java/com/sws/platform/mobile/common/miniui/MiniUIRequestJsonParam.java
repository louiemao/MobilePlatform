package com.sws.platform.mobile.common.miniui;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Created by MaoLiang on 2016/4/10.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MiniUIRequestJsonParam {
    /**
     * 用于绑定的请求参数名字
     */
    String value() default "";
}
