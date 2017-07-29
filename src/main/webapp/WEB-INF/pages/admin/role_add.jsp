<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<!-- 导入ztree类库 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		// 授权树初始化
		var setting = {
			data : {
				key : {
					title : "t"
				},
				simpleData : {
					enable : true
				}
			},
			check : {//启用zTree插件勾选效果
				enable : true
			}
		};

		$.ajax({

			//修改ajax请求地址，从数据库获取数据
			url : '${pageContext.request.contextPath}/functionAction_list.action',
			type : 'POST',
			dataType : 'text',
			success : function(data) {
				var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#functionTree"), setting, zNodes);
			},
			error : function(msg) {
				alert('树加载异常!');
			}
		});



		// 点击保存
		$('#save').click(function(){
			<%--location.href='${pageContext.request.contextPath}/page_admin_privilege.action';--%>
			<%--修改“保存”按钮的事件--%>
            var v = $("#roleForm").form("validate");
            if(v) {
                //在提交表单前，使用zTree提供的方式获取选中的节点
                var zTreeObj = $.fn.zTree.getZTreeObj("functionTree");//获得zTree对象
                var checkedNodes = zTreeObj.getCheckedNodes(true);//在提交表单前将选中的checkbox节点收集
                var array = new Array();
                for(var i=0;i<checkedNodes.length;i++) {
                    var id = checkedNodes[i].id;
                    array.push(id);
                }
                var ids = array.join(",");
                //在提交表单之前，将ids字符串赋值给隐藏域
                $("input[name=functionIds]").val(ids);
                $("#roleForm").submit();
            }
		});

		//创建下拉列表框(关键字处)
		$("#keyWord").combobox({
//			//查询function中权限名称与关键字
			url:"${pageContext.request.contextPath}/functionAction_list.action"
			,valueField:"code"
			,textField:"name"
		});
	});
</script>
</head>
<body class="easyui-layout">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
            <%--给表单提供一个action地址--%>
			<form id="roleForm" method="post" action="${pageContext.request.contextPath}/roleAction_save.action">
				<%--增加一个隐藏域--%>
				<input type="hidden" name="functionIds">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">角色信息</td>
					</tr>
					<tr>
						<%--将编码改为关键字--%>
						<td width="200">关键字</td>
						<td>
							<input id="keyWord" type="text" name="code" <%--class="easyui-validatebox" data-options="required:true"--%> />
						</td>
					</tr>
					<tr>
						<td>名称</td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td>描述</td>
						<td>
							<textarea name="description" rows="4" cols="60"></textarea>
						</td>
					</tr>
					<tr>
						<td>授权</td>
						<td>
							<ul id="functionTree" class="ztree"></ul>
						</td>
					</tr>
				</table>
			</form>
		</div>
</body>
</html>