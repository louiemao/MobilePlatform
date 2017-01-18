package com.sws.platform.mobile.security.controller;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by Administrator on 2016/2/25.
 */
@Controller
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private SessionDAO sessionDAO;

    @RequestMapping("/list")
    @ResponseBody
    public HashMap list() {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        List<Map> list = new ArrayList<>(sessions.size());
        for (Session session : sessions) {
            PrincipalCollection principalCollection =
                    (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            String principal = (String) principalCollection.getPrimaryPrincipal();

            Map map = new HashMap<>();
            map.put("userName", principal);
            map.put("sessionId", session.getId());
            map.put("host", session.getHost());
            map.put("lastAccessTime", session.getLastAccessTime());
            //map.put("session", session);
            list.add(map);
        }

        HashMap result = new HashMap();
        result.put("total", list.size());
        result.put("data", list);
        return result;
    }
}
