<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>messager消息提示框</title>
	<!-- 引入easyui相关的资源文件 -->
	<link rel="stylesheet" type="text/css"
	 href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>

    <script type="text/javascript"
    src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
    <%--引入国际化文件--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>

	<%--消息提示框--%>
    <script type="text/javascript">
        $(function(){
            <%--show--%>
            $.messager.show({
                title:'提示信息',// 标题
                msg:'欢迎您登录BOS系统',// 信息
                timeout:3000,// 3秒后自动消失
                showType:'slide'// 显示方式
            });
            /*alert*/
//            $.messager.alert('我的消息','这是一个提示信息！','question');
            /*confirm-确认提示框*/
           /* $.messager.confirm('确认对话框', '您想要退出该系统吗？', function(r){
                if (r){
                    alert(r);
                }
            });*/

            // prompt
            /*$.messager.prompt('标题','请输入您的姓名',function(data){
                if (data){
                    alert('您的姓名：' + data);
                }
            });*/

            // progress
            $.messager.progress();

            // 3秒后关闭
            window.setTimeout(function () {
                $.messager.progress('close');
            }, 3000);
        });
    </script>
</head>
<body>

</body>
</html>