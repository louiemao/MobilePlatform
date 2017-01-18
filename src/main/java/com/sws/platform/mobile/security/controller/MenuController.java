package com.sws.platform.mobile.security.controller;

import com.sws.platform.mobile.security.dto.Menu;
import com.sws.platform.mobile.security.service.RestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/2/20.
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private RestService restService;

    private static Logger logger = LogManager.getLogger(MenuController.class.getName());

    @RequestMapping("/getMenus")
    @ResponseBody
    public List<Menu> getMenus() {
        String userCd = (String) SecurityUtils.getSubject().getPrincipal();
//        logger.info("获取用户{}的菜单",userCd);
        return restService.getMenuInfos(userCd);
    }
}
