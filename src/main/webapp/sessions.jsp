<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>在线用户</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <link href="static/css/demo.css" rel="stylesheet" type="text/css"/>

    <script src="static/js/boot.js" type="text/javascript"></script>


</head>
<body>
<h1>在线用户</h1>

<div id="datagrid1" class="mini-datagrid" style="width:650px;height:280px;" idField="id" pageSize="10"
     multiSelect="true"
        >
    <div property="columns">
        <div type="indexcolumn"></div>
        <!--<div type="checkcolumn"></div>-->
        <div field="userName" width="80" align="center" headerAlign="center" allowSort="true">用户名</div>
        <div field="sessionId" width="200" allowSort="true" align="center" headerAlign="center">会话ID</div>
        <div field="host" width="80" allowSort="true" align="center" headerAlign="center">主机地址</div>
        <div field="lastAccessTime" width="100" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss">最后访问时间</div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    mini.parse();
    var grid = mini.get("datagrid1");

    // 分页填充细节处理
    function fillData(pageIndex, pageSize, dataResult, grid) {

        var data = dataResult.data, totalCount = dataResult.total;

        var arr = [];
        var start = pageIndex * pageSize, end = start + pageSize;
        for (var i = start, l = end; i < l; i++) {
            var record = data[i];
            if (!record) continue;
            arr.push(record);
        }


        grid.setTotalCount(totalCount);
        grid.setPageIndex(pageIndex);
        grid.setPageSize(pageSize);

        grid.setData(arr);
    }

    // 监听分页前事件，阻止后自行设置当前数据和分页信息
    grid.on("beforeload", function (e) {
        e.cancel = true;

        var pageIndex = e.data.pageIndex, pageSize = e.data.pageSize;
        fillData(pageIndex, pageSize, dataResult, grid);
    });

    ////////////////////////////////////////////////////////////////////////

    // 获取所有数据和总记录数 { total: 100, data: [...] }
    var dataResult = null;
    $.ajax({
        url: 'session/list',
//        dataType: 'text',
        async: false,
        success: function (text) {
            dataResult = mini.decode(text);
        }
    });

    // 第一次设置
    fillData(0, grid.getPageSize(), dataResult, grid);


</script>