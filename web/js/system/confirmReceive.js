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
            {field:'identifyOrder',title:'物资领取号',width:'110',align:'center'},
            {field:'isTakeMaterial',title:'是否已领取物资',width:'80',align:'center'},
        ]]
    });
});



//查询方法
function searchActivity(){

    var number = $("#number").val();
    var identifyNo = $("#identifyNo").val();
    if(!name && !number && !identifyNo){
        newAlert("物资领取号、序号必须填一个");
        return;
    }
    $('#signup').datagrid("options").url=ctx+"/signupInfo/searchSignupinfo.action?ids=" + Math.random(),
    $('#signup').datagrid("options").queryParams={
        "identifyNo" : $("#identifyNo").val(),
        "number": $("#number").val(),
        "activityId" : $('#activityId').combobox('getValue')
    };
    $('#signup').datagrid("load");
}

function confirmReceive() {
    var number = $("#number").val();
    var identifyNo = $("#identifyNo").val();
    if(!name && !number && !identifyNo){
        newAlert("物资领取号、序号必须填一个");
        return;
    }
    $.messager.confirm('Confirm', '您确定已领取物资?', function (r) {
        if (r) {
            $.ajax({
                type : "post",
                url : ctx + "/identifyOrder/confirmReceive.action",
                data : {"activityId":activityId,"number":number,"identifyNo":identifyNo},
                success : function(data) {
                    data = JSON.parse(data);
                    if(data.success){
                        newShow("操作成功");
                    }else{
                        newAlert(data.errorMsg);
                    }
                },
                async : true
            });
        }
    });

}