package com.sws.platform.mobile.common.miniui;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaoLiang on 2016/4/10.
 */
public class MiniUIEntityResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(MiniUIRequestJsonParam.class);
    }

    @Override
    public List<MiniUIEntity<Object>> resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //获取参数名
        String parameterName = methodParameter.getParameterAnnotation(MiniUIRequestJsonParam.class).value();
        if (!StringUtils.hasText(parameterName)) {
            parameterName = methodParameter.getParameterName();
        }
        //获取json字符串
        String data = nativeWebRequest.getParameter(parameterName);
        if (!StringUtils.hasText(data)) {
            return null;
        }
        //获取标签参数对应的实体的类型，参数必须是List<MiniUIEntity<Object>>类型，这里就是获取Object的具体类型
        ParameterizedType type1 = (ParameterizedType) methodParameter.getGenericParameterType();
        ParameterizedType type2 = (ParameterizedType) type1.getActualTypeArguments()[0];
        Class type3 = (Class) type2.getActualTypeArguments()[0];
        //开始解析json字符串
        JSONArray rows = JSONArray.parseArray(data);
        List<MiniUIEntity<Object>> list = new ArrayList<>();
        for (int i = 0, l = rows.size(); i < l; i++) {
            JSONObject row = rows.getJSONObject(i);
            MiniUIEntity miniUIEntity = new MiniUIEntity();
            //解析miniui标记的状态
            String state = row.getString("_state");
            if (row.containsKey("id")) {
                String id = row.getString("id");
                if ("".equals(id)) {
                    state = "added";
                }
            }
            miniUIEntity.setState(state);
            if (row.containsKey("_id")) {
                row.remove("_id");
            }
            //将其他属性转为对象
            miniUIEntity.setEntity(JSON.parseObject(row.toJSONString(), type3));
            list.add(miniUIEntity);
        }
        return list;
    }
}