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
<script src="${ctx}/js/system/confirmReceive.js" type="text/javascript"></script>
<style type="text/css">
    ul{
        width: 100%;
    }
    li{
        float: none;
        display: inline-block;
    }
    .labelLi{
        width: 4.5rem;
        text-align: right;
    }
    input{
        width: 8rem;
    }
</style>
<body>
<div id="cc" class="easyui-layout" style="width: 100%; height: 460px">

    <div data-options="region:'north',border:false" id="search"
         style="padding: 5px;height: auto">
        <form id="searchForm" style="margin-top: 10px">
            <ul>
                <li class="labelLi">
                    <label>物资领取号:</label>
                </li>
                <li>
                    <input name="identifyNo" id="identifyNo" class="easyui-validatebox"  />
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
            href="#" class="easyui-linkbutton" id="export" plain="true" onclick="confirmReceive()">确认领取</a>
</div>
</body>

</html>
