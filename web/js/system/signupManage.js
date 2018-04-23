$(function(){
    $('#signup').datagrid({
        pageList: [10,20,50,100],
        pagination:true,
        rownumbers:true,
        pagePosition:"bottom",
        idField: 'signupInfoId',
        width: ($("#list").width()),
        height:$("#list").height(),
        fitColumns:true,
        toolbar:"#btn",
        singleSelect:true,
        columns:[[
            {field:'signupInfoId',title:'',hidden:true},
            {field:'number',title:'序号',width:'90',align:'center'},
            {field:'name',title:'姓名',width:'90',align:'center'},
            {field:'sex',title:'性别',width:'60',align:'center'},
            {field:'telephone',title:'电话',width:'90',align:'center'},
            {field:'idCard',title:'身份证',width:'150',align:'center'},
            {field:'signupTime',title:'报名时间',width:'150',align:'center'},
            {field:'status',title:'状态',width:'60',align:'center'},
            {field:'identifyNo',title:'物资领取号',width:'110',align:'center'},
            {field:'isTakeMaterial',title:'是否已领取物资',width:'80',align:'center'},
        ]]
    });
});



//查询方法
function searchActivity(){
    $('#signup').datagrid("options").url=ctx+"/signupInfo/searchSignupinfo.action?ids=" + Math.random(),
    $('#signup').datagrid("options").queryParams={
        "signupTimeBegin": $("#signupTimeBegin").datebox('getValue'),
        "signupTimeEnd": $("#signupTimeEnd").datebox('getValue'),
        "name": $("#name").val(),
        "status" : status,
        "number" : $("#number").val(),
        "activityId" : $('#activityId').combobox('getValue'),
        "isTakeMaterial" : $('#isTakeMaterial').combobox('getValue')
    };
    $('#signup').datagrid("load");
}

//导出
function exportAll(){
    var xlsName = "";
    if(status == 1){
        xlsName = "未缴费列表";
    }else if(status == 2){
        xlsName = "支付中列表";
    }else if (status == 3){
        xlsName = "已缴费列表";
    }
    var params =[
        {name: 'signupTimeBegin', value: $("#signupTimeBegin").datebox('getValue')}
        ,{name: 'signupTimeEnd', value: $("#signupTimeEnd").datebox('getValue')}
        ,{name: 'name', value:$("#name").val()}
        ,{name: 'status', value:status}
        ,{name: 'activityId',value : $('#activityId').combobox('getValue')}
        ,{name: 'isTakeMaterial',value : $('#isTakeMaterial').combobox('getValue')}
        ,{name: 'xlsName',value : xlsName + ".xls"}
        ,{name: 'number',value : $("#number").val()}
        ,{name: 'serviceName',value : "signupInfoService"}
    ];
    exportToExcel(params);
}