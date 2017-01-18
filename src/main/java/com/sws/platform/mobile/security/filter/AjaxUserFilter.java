package com.sws.platform.mobile.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.sws.platform.mobile.common.util.AjaxUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MaoLiang on 2016/8/25.
 */
public class AjaxUserFilter extends UserFilter {
    private static Logger logger = LogManager.getLogger(AjaxUserFilter.class.getName());

//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        if (this.isLoginRequest(request, response)) {
//            return true;
//        } else {
//            Subject subject = this.getSubject(request, response);
////            if (subject.getPrincipal() == null) {
////                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
////                StringBuffer url = httpServletRequest.getRequestURL();
//////                Cookie[] cookies = httpServletRequest.getCookies();
////                Session session = subject.getSession(false);
////                StringBuilder sb = new StringBuilder();
////                sb.append("url:").append(url);
////                sb.append("session:").append(session == null ? "null" : session.getId());
////                logger.debug(sb);
////            }
//            return subject.getPrincipal() != null;
//        }
//    }

    //表示当访问拒绝时是否已经处理了；如果返回 true 表示需要继续处理；如果返回 false 表示该拦截器实例已经处理了，将直接返回即可。
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //判断是否是ajax请求
        if (AjaxUtil.isAjaxRequest(request)) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setHeader("userStatus", "NoUserInfo");
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setCharacterEncoding("UTF-8");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", "没有获取到用户信息");
            httpServletResponse.getWriter().write(jsonObject.toJSONString());
            return false;
        }
        this.saveRequest(request);
//        String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "errorCode=2";
        String loginUrl = getLoginUrl();
        WebUtils.issueRedirect(request, response, loginUrl);
        return false;
    }
}
