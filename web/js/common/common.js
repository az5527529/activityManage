/**
 * 
 */
/**
 * 新开tab页方法
 */
 function addTab(title,url){  
              
	 		var jq = top.jQuery;  
            if(jq("#tt").tabs("exists",title)){//存在则选中，不存在则新增
            	jq("#tt").tabs("select",title);
    		}else{
    			jq("#tt").tabs("add",{
    				title:title,
    				content:'<iframe src="'+url+'" frameborder="0" width="100%" height="100%" />',
    				//href:默认是通过url加载body内容，不加载head
    				//href:href,
    				closable:true
    			});
    		}
              
        }
 
 /**
  * 关闭tab页
  * @param title
  */
 function closeTab(title){
	 var jq = top.jQuery;  
	 jq("#tt").tabs("close",title);
 }
 //警告
 function newAlert(msg){
	 $.messager.alert('警告',msg);
 }
 
 //提示
 function newShow(msg){
	 $.messager.show({
 		title:'提示',
 		msg:msg,
 		timeout:1000,
 		showType:'slide',
 		style:{
 			right:'',
 			top:document.body.scrollTop+document.documentElement.scrollTop,
 			bottom:''
 		}

 	});
 }
/**
 * 获取url上的数据方法
 * @param name
 * @returns {null}
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = decodeURI(window.location.search).substr(1).match(reg);
	if (r != null) return unescape(r[2]);
	return null;

	/**
	 * 获取url上的数据的例子
	 */
	//var channelId = getQueryString('channelId');
}
/**
 * 导出excel的方法
 * @param params 参数
 * @param xlsName excel名，可未空
 */
function exportToExcel(params){

	var method = "post";
	var form = document.createElement("form");
	form.setAttribute("method", method);
	form.setAttribute("action", ctx + '/export/exportToExcel.action');
	for(var i=0; i<params.length; i++) {
		var hiddenField = document.createElement("input");
		hiddenField.setAttribute("type", "hidden");
		hiddenField.setAttribute("name", params[i].name);
		hiddenField.setAttribute("value", params[i].value);
		form.appendChild(hiddenField);
	}
	document.body.appendChild(form);
	form.submit();
}