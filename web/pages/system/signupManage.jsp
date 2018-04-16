<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 2018/2/28
  Time: 9:49
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@include file="/pages/common/head.jsp"%>
<%
    String status = request.getParameter("status");
%>
<html>
<head>
    <title>活动管理</title>

</head>
<script src="${ctx}/js/system/signupManage.js" type="text/javascript"></script>
<style type="text/css">
    ul{
        width: 100%;
    }
    li{
        float: none;
        display: inline-block;
    }
    .labelLi{
        width: 6rem;
        text-align: right;
    }
    input{
        width: 10rem;
    }
</style>
<body>
<div id="cc" class="easyui-layout" style="width: 100%; height: 460px">

    <div data-options="region:'north',border:false" id="search"
         style="padding: 5px; height: 100rem;">
        <form id="searchForm" style="margin-top: 10px">
            <ul>
                <li class="labelLi"><label>活动:</label>

                <li style="width: 10rem;">
                    <select id="activityId"  style="width: 100%;" name='activityId' class="easyui-combobox"
                            data-options="editable:false" >
                    </select>
                </li>
                <li class="labelLi"><label>报名日期:</label>
                </li>
                <li>
                    <input id="signupTimeBegin" class="easyui-datebox" data-options="editable:false"></input>
                    -
                    <input id="signupTimeEnd" class="easyui-datebox" data-options="editable:false"></input>
                </li>
            </ul>

            <ul>
                <li class="labelLi"><label>是否已领取物资:</label>

                <li style="width: 10rem;">
                    <select id="isTakeMaterial"  style="width: 100%;" name='isTakeMaterial' class="easyui-combobox"
                            data-options="editable:false" >
                        <option value=""></option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </li>
                <li class="labelLi">
                    <label>姓名:</label>

                </li>
                <li>
                    <input name="name" id="name" class="easyui-validatebox"  />
                </li>
                <li class="labelLi">
                    <label>序号:</label>
                </li>
                <li>
                    <input name="number" id="number" class="easyui-validatebox"  />
                </li>
            </ul>

        </form>
    </div>
    <div data-options="region:'center',split:true" style="height: 350px"
         id="list">
        <table id="signup"></table>
    </div>
</div>
<div id="btn">
    <a href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-search'" onclick="searchActivity()"
       plain="true"></a>
    <a
            href="#" class="easyui-linkbutton" id="export" plain="true" onclick="exportAll()">导出</a>
</div>
</body>
<script type="text/javascript">
    var status = "<%=status%>";
    $(function () {
        $("#activityId").combobox({
            url:ctx + '/activity/loadAllActivity.action',
            valueField:'activityId',//相当于option的value值
            textField:'topic',//相当于<option></option>之间的显示值 value:1000    //默认显示值
            onLoadSuccess: function () { //加载完成后,设置选中第一项
                var val = $(this).combobox('getData');
                for (var item in val[0]) {
                    if (item == 'activityId') {
                        $(this).combobox('select', val[0][item]);
                    }
                }
            }
        });
    });

</script>

</html>
