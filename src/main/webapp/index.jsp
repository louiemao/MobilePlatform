<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>海工文控系统</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <link href="static/css/demo.css" rel="stylesheet" type="text/css"/>

    <script src="static/js/boot.js" type="text/javascript"></script>
    <%--&lt;%&ndash;jQuery js&ndash;%&gt;--%>
    <%--<script src="js/jquery-1.6.2.min.js" type="text/javascript"></script>--%>
    <%--&lt;%&ndash;MiniUI&ndash;%&gt;--%>
    <%--<link href="js/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css"/>--%>
    <%--<script src="js/miniui/miniui.js" type="text/javascript"></script>--%>

    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            border: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

        .header {
            background: url(static/images/header.gif) repeat-x 0 -1px;
        }
    </style>
</head>
<body>

<!--Layout-->
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
    <div class="header" region="north" height="70" showSplit="false" showHeader="false">
        <h1 style="margin:0;padding:15px;cursor:default;font-family:微软雅黑,黑体,宋体;">海工文控系统 V1</h1>

        <div style="position:absolute;top:18px;right:10px;">
            <a class="mini-button mini-button-iconTop" iconCls="icon-add" onclick="onQuickClick" plain="true">快捷</a>
            <a class="mini-button mini-button-iconTop" iconCls="icon-edit" onclick="onClick" plain="true">首页</a>
            <a class="mini-button mini-button-iconTop" iconCls="icon-date" onclick="onClick" plain="true">消息</a>
            <a class="mini-button mini-button-iconTop" iconCls="icon-edit" onclick="onClick" plain="true">设置</a>
            <a class="mini-button mini-button-iconTop" iconCls="icon-close" href="logout" plain="true">退出</a>

        </div>

    </div>
    <div title="south" region="south" showSplit="false" showHeader="false" height="30">
        <div style="line-height:28px;text-align:center;cursor:default">Copyright © 上海外高桥造船有限公司版权所有</div>
    </div>
    <div title="center" region="center" style="border:0;" bodyStyle="overflow:hidden;">
        <!--Splitter-->
        <div class="mini-splitter" style="width:100%;height:100%;" borderStyle="border:0;">
            <div size="180" maxSize="250" minSize="100" showCollapseButton="true" style="border:0;">
                <!--OutlookTree 通过 "id" 和 "pid" 组成树形结构，在创建OutlookTree时注意设置 "idField" 和 "parentField" 。-->
                <div id="leftTree" class="mini-outlooktree" url="menu/getMenus" onnodeclick="onNodeSelect"
                     textField="label" idField="id" parentField="parentId"
                        >
                </div>

            </div>
            <div showCollapseButton="false" style="border:0;">
                <!--Tabs-->
                <div id="mainTabs" class="mini-tabs bg-toolbar" activeIndex="0" style="width:100%;height:100%;"
                     bodyStyle="border:0;background:white;"
                     plain="false" onactivechanged="onTabsActiveChanged"
                        >
                    <div title="首页" url="overview.jsp">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    mini.parse();

    var tree = mini.get("leftTree");

    function showTab(node) {
        var tabs = mini.get("mainTabs");

        var id = "tab$" + node.id;
        var tab = tabs.getTab(id);
        if (!tab) {
            tab = {};
            tab._nodeid = node.id;
            tab.name = id;
            tab.title = node.text;
            tab.showCloseButton = true;

            //这里拼接了url，实际项目，应该从后台直接获得完整的url地址
            tab.url = node.link;
            //tab.url = mini_JSPath + "../../docs/api/" + node.id + ".html";

            tabs.addTab(tab);
        }
        tabs.activeTab(tab);
    }

    function onNodeSelect(e) {
        var node = e.node;
        var isLeaf = e.isLeaf;

        if (isLeaf) {
            showTab(node);
        }
    }

    function onClick(e) {
        var text = this.getText();
        alert(text);
    }
    function onQuickClick(e) {
        tree.expandPath("datagrid");
        tree.selectNode("datagrid");
    }

    function onTabsActiveChanged(e) {
        var tabs = e.sender;
        var tab = tabs.getActiveTab();
        if (tab && tab._nodeid) {

            var node = tree.getNode(tab._nodeid);
            if (node && !tree.isSelectedNode(node)) {
                tree.selectNode(node);
            }
        }
    }
</script>

</body>
</html>
