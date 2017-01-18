<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>海工文控系统 - 登录</title>
    <link href="<c:url value="/static/css/login.css"/>" rel="stylesheet" type="text/css"/>
</head>

<body>
<div class="main-login">

    <div class="login-content">
        <h2>海工文控系统 - 登录</h2>

        <form:form modelAttribute="loginCommand">

        <form:errors path="*" element="div" cssClass="errors"/>

        <%--<form action="" method="post" id="login-form" name="login-form">--%>
            <div class="login-info">
                <span class="user">&nbsp;</span>
                <%--<input name="username" id="username" type="text" value="" class="login-input"/>--%>
                <form:input path="username" cssClass="login-input"/>
            </div>
            <div class="login-info">
                <span class="pwd">&nbsp;</span>
                <%--<input name="password" id="password" type="password" value="" class="login-input"/>--%>
                <form:password path="password" cssClass="login-input"/>
            </div>
            <div class="login-oper">
                <%--<input style="margin:1px 10px 0px 2px; float:left;" name="" type="checkbox" value="" checked="checked"/>--%>
                    <form:checkbox path="rememberMe" cssStyle="margin:1px 10px 0px 2px; float:left;" checked="checked"/>
                <span>记住我</span>
            </div>
            <div class="login-oper">
                <input name="" type="submit" value="登 录" class="login-btn"/>
                <input name="" type="reset" value="重 置" class="login-reset"/>
            </div>
        <%--</form>--%>
        </form:form>
    </div>

</div>

</body>

</html>
