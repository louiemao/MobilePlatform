package com.sws.platform.mobile.common.exception;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义异常处理器
 * Created by MaoLiang on 2016/4/20.
 */
public class ExceptionHandler implements HandlerExceptionResolver {
    private static Logger logger = LogManager.getLogger(ExceptionHandler.class.getName());

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        HandlerMethod handlerMethod = (HandlerMethod) o;
        if (!(e instanceof BusinessException)) {
            logger.error(handlerMethod.getMethod().getDeclaringClass().getName() + "." + handlerMethod.getMethod().getName(), e);
        }

        //判断是否有ResponseBody
        ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
        if (responseBody != null) {
            return returnJson(httpServletResponse, e);
        }

        //判断是否是ajax请求
        String xRequestedWith = httpServletRequest.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            return returnJson(httpServletResponse, e);
        }

        return returnView(e);
    }

    private ModelAndView returnJson(HttpServletResponse httpServletResponse, Exception e) {
        //httpServletResponse.setStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Cache-Control", "no-cache,must-revalidate");

        JSONObject jsonObject = new JSONObject();
        if (e instanceof BusinessException) {
            jsonObject.put("error", e.getMessage());
        } else {
            jsonObject.put("error", "系统异常！异常类：" + e.getClass().getSimpleName());
        }
        try {
            httpServletResponse.getWriter().write(jsonObject.toJSONString());
        } catch (IOException e1) {
            logger.error(e1);
        }
        return new ModelAndView();
    }

    private ModelAndView returnView(Exception e) {
        Map<String, Object> model = new HashMap<>();
        model.put("ex", e);

        //根据不同错误转向不同页面
        if (e instanceof BusinessException) {
            return new ModelAndView("error_business", model);
        } else {
            return new ModelAndView("error", model);
        }
    }

    //获取最底层的错误
    private Throwable parseException(Throwable e) {
        Throwable tmp = e;
        int breakPoint = 0;
        while (tmp.getCause() != null) {
            if (tmp.equals(tmp.getCause())) {
                break;
            }
            tmp = tmp.getCause();
            breakPoint++;
            if (breakPoint > 1000) {
                break;
            }
        }
        return tmp;
    }
}
