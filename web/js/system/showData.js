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
            {field:'totalNum',title:'已报名人数',width:150,align:'center'},
            {field:'payedNum',title:'已支付人数',width:150,align:'center'},
            {field:'toPayNum',title:'未支付人数',width:150,align:'center'},
            {field:'payingNum',title:'支付中人数',width:150,align:'center'}
        ]]
    });
});



//查询方法
function showData(){
    $('#signup').datagrid("options").url=ctx+"/signupInfo/showData.action?ids=" + Math.random(),
    $('#signup').datagrid("options").queryParams={
        "signupTimeBegin": $("#signupTimeBegin").datebox('getValue'),
        "signupTimeEnd": $("#signupTimeEnd").datebox('getValue'),

        "activityId" : $('#activityId').combobox('getValue')
    };
    $('#signup').datagrid("load");
}

//导出
function exportAll(){
    var params =[
        {name: 'signupTimeBegin', value: $("#signupTimeBegin").datebox('getValue')}
        ,{name: 'signupTimeEnd', value: $("#signupTimeEnd").datebox('getValue')}
        ,{name: 'activityId',value : $('#activityId').combobox('getValue')}
        ,{name: 'xlsName',value : "数据统计表.xls"}
        ,{name: 'serviceName',value : "showDataService"}
    ];
    exportToExcel(params);
}