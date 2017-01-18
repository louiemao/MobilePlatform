package com.sws.platform.mobile.common.miniui;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by MaoLiang on 2016/4/9.
 */
public class MiniUIPageableResolver implements HandlerMethodArgumentResolver {
    private Pageable fallbackPageable;
    private String pageParameterName;
    private String sizeParameterName;
    private String sortParameterName;
    private String directionParameterName;

    public MiniUIPageableResolver() {
        this.fallbackPageable = new PageRequest(0, 20, new Sort(Sort.Direction.DESC, "createTime"));
        this.pageParameterName = "pageIndex";
        this.sizeParameterName = "pageSize";
        this.sortParameterName = "sortField";
        this.directionParameterName = "sortOrder";
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return Pageable.class.equals(methodParameter.getParameterType());
    }

    @Override
    public PageRequest resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String pageString = nativeWebRequest.getParameter(this.pageParameterName);
        String sizeString = nativeWebRequest.getParameter(this.sizeParameterName);
        String field = nativeWebRequest.getParameter(this.sortParameterName);
        String direction = nativeWebRequest.getParameter(this.directionParameterName);

        int page = StringUtils.hasText(pageString) ? Integer.valueOf(pageString) : fallbackPageable.getPageNumber();
        int size = StringUtils.hasText(sizeString) ? Integer.valueOf(sizeString) : fallbackPageable.getPageSize();
        Sort sort = StringUtils.hasText(field) ? new Sort(Sort.Direction.fromStringOrNull(direction), field) : fallbackPageable.getSort();
        return new PageRequest(page, size, sort);
    }
}
