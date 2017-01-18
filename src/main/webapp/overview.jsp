
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
  <title>Hello MiniUI!</title>
  <%--jQuery js--%>
  <script src="static/lib/jquery-1.6.2.min.js" type="text/javascript"></script>
  <%--MiniUI--%>
  <link href="static/js/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css"/>
  <script src="static/js/miniui/miniui.js" type="text/javascript"></script>
</head>
<body>
您好，<shiro:principal/>!
<br/>
<input id="helloBtn" class="mini-button" text="Hello" onclick="onHelloClick"/>
<script type="text/javascript">
  function onHelloClick(e) {
    var button = e.sender;
    mini.alert("Hello MiniUI!");
  }
</script>
</body>
</html>
