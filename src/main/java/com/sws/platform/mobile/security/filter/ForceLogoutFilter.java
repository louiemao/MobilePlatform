package com.sws.platform.mobile.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.sws.platform.mobile.common.util.AjaxUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MaoLiang on 2016/8/26.
 */
public class ForceLogoutFilter extends AccessControlFilter {
    public static final String SESSION_FORCE_LOGOUT_KEY = "ForceLogout";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (this.isLoginRequest(request, response)) {
            return true;
        }
        Session session = getSubject(request, response).getSession(false);
        if (session == null) {
            return true;
        }
        return session.getAttribute(SESSION_FORCE_LOGOUT_KEY) == null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            getSubject(request, response).logout();//强制退出
        } catch (Exception e) {
            /*ignore exception*/
            e.printStackTrace();
        }
        //判断是否是ajax请求
        if (AjaxUtil.isAjaxRequest(request)) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setHeader(SESSION_FORCE_LOGOUT_KEY, "true");
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setCharacterEncoding("UTF-8");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", "已被强制退出，请重新登录");
            httpServletResponse.getWriter().write(jsonObject.toJSONString());
            return false;
        }
        this.saveRequest(request);
        String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "errorCode=1";
        WebUtils.issueRedirect(request, response, loginUrl);
        return false;
    }
}
