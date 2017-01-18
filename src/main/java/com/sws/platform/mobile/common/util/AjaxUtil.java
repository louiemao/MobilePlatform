package com.sws.platform.mobile.common.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by MaoLiang on 2016/8/26.
 */
public class AjaxUtil {
    /**
     * 判断是否是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String xRequestedWith = httpServletRequest.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(xRequestedWith)) {
                return true;
            }
        }
        return false;
    }
}
