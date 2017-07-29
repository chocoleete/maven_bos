<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%--使用shiro标签根据权限展示页面元素--%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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

    <%--解决window控件拖出去后无法还原的bug--%>
    <script
            src="${pageContext.request.contextPath }/js/outOfBounds.js"
            type="text/javascript"></script>
    <script
            src="${pageContext.request.contextPath }/js/outOfBounds.js"
            type="text/javascript"></script>
    <script type="text/javascript">
        function doAdd() {
            //alert("增加...");
            $('#addStaffWindow').window("open");
        }

        function doView() {
            alert("查看...");
        }

        function doDelete() {
//            alert("删除...");
            //获得dategrid中选中的行
            var selections = $("#grid").datagrid("getSelections");
            if(selections.length==0) {
                //没有选中行
                $.messager.alert("提示信息", "请选择作废的记录", "warning");
            }else{
                //选中了行，遍历结果集，将所有的id存入一个数据中
                var array = new Array();
                for(var i=0;i<selections.length;i++) {
                    array.push(selections[i].id);
                }
                //将所有的id用","拼接在一个字符串
                var ids = array.join(",");
                //将ids传入到指定的方法中去
                /*$.messager.alert("提示信息", ids, "warning");*/
                window.location.href = "${pageContext.request.contextPath}/staffAction_delete.action?ids=" + ids;
            }
        }

        function doRestore() {
//            alert("将取派员还原...");
            //获得dategrid中选中的行
            var selections = $("#grid").datagrid("getSelections");
            if(selections.length==0) {
                //没有选中行
                $.messager.alert("提示信息", "请选择还原的记录", "warning");
            }else{
                //选中了行，遍历结果集，将所有的id存入一个数据中
                var array = new Array();
                for(var i=0;i<selections.length;i++) {
                    array.push(selections[i].id);
                }
                //将所有的id用","拼接在一个字符串
                var ids = array.join(",");
                //将ids传入到指定的方法中去
                /*$.messager.alert("提示信息", ids, "warning");*/
                window.location.href = "${pageContext.request.contextPath}/staffAction_restore.action?ids=" + ids;
            }
        }
        //工具栏
        var toolbar = [{
            id: 'button-view',
            text: '查询',
            iconCls: 'icon-search',
            handler: doView
        }, {
            id: 'button-add',
            text: '增加',
            iconCls: 'icon-add',
            handler: doAdd
        },
            <shiro:hasPermission name="staff">
            {
                id: 'button-delete',
                text: '作废',
                iconCls: 'icon-cancel',
                handler: doDelete
            }, {
                id: 'button-save',
                text: '还原',
                iconCls: 'icon-save',
                handler: doRestore
            }
            </shiro:hasPermission>
            ];
        // 定义列
        var columns = [[{
            field: 'id',
            checkbox: true
        }, {
            field: 'name',
            title: '姓名',
            width: 120,
            align: 'center'
        }, {
            field: 'telephone',
            title: '手机号',
            width: 120,
            align: 'center'
        }, {
            field: 'haspda',
            title: '是否有PDA',
            width: 120,
            align: 'center',
            formatter: function (data, row, index) {
                if (data == "1") {
                    return "有";
                } else {
                    return "无";
                }
            }
        }, {
            field: 'deltag',
            title: '是否作废',
            width: 120,
            align: 'center',
            formatter: function (data, row, index) {
                if (data == "0") {
                    return "正常使用"
                } else {
                    return "已作废";
                }
            }
        }, {
            field: 'standard',
            title: '取派标准',
            width: 120,
            align: 'center'
        }, {
            field: 'station',
            title: '所属单位',
            width: 200,
            align: 'center'
        }]];

        $(function () {
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility: "visible"});

            // 取派员信息表格
            $('#grid').datagrid({
                iconCls: 'icon-forward',
                fit: true,
                border: false,
                rownumbers: true,
                striped: true,
                pageList: [5,30, 50, 100],
                pagination: true,
                toolbar: toolbar,
//                url: "json/staff.json",
                //修改url属性地址，指向StaffAction的方法，查询数据库中的取派员数据，序列化为json返回
                url:"${pageContext.request.contextPath}/staffAction_pageQuery.action",
                idField: 'id',
                columns: columns,
                onDblClickRow: doDblClickRow
            });

            // 添加取派员窗口
            $('#addStaffWindow').window({
                title: '添加取派员',//窗口的标题
                width: 400,//宽度
                modal: true,//遮罩效果
                shadow: true,//阴影
                closed: true,//关闭(重点)
                height: 400,//高度
                resizable: false//是否可以调整大小
            });
            //使用正则表达式，扩展手机号校验规则
            $.extend($.fn.validatebox.defaults.rules, {
                phoneNumber: {
                    validator: function(value,param){
                        var phone = /^1[3|5|7|8][0-9]{9}$/;
                        return phone.test(value)
                    },
                    message: '手机号输入有误！'
                }
            });

            //为“保存”按钮绑定单击事件
            $("#save").click(function() {
                //进行form表单校验 返回值为boolean值
                var v = $("#saveForm").form("validate");//表单校验
                if(v) {
                    //如果表单校验通过，则提交表单
                    $("#saveForm").submit();
                }
            });

            // 修改取派员窗口
            $("#editStaffWindow").window({
                title: '修改取派员',//窗口的标题
                width: 400,//宽度
                modal: true,//遮罩效果
                shadow: true,//阴影
                closed: true,//关闭(重点)
                height: 400,//高度
                resizable: false//是否可以调整大小
            });

            //为“修改”按钮绑定单击事件
            $("#update").click(function() {
                //进行form表单校验 返回值为boolean值
                var v = $("#updateForm").form("validate");//表单校验
                if(v) {
                    //如果表单校验通过，则提交表单
                    $("#updateForm").submit();
                }
            });
        });

        function doDblClickRow(rowIndex, rowData) {
//            alert("双击表格数据...");
            //打开修改取派员的窗口
            $("#editStaffWindow").window("open");
            //将当前双行的数据加载到指定的表单中去
            $("#updateForm").form("load", rowData);
        }
    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>
<%--添加取派员窗口--%>
<div class="easyui-window" title="对收派员进行添加" id="addStaffWindow" collapsible="false" minimizable="false"
     maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
        <div class="datagrid-toolbar">
            <a id="save" icon="icon-save" href="javascript:void(0)" class="easyui-linkbutton" plain="true">保存</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <%--给表单提供一个id actioin method--%>
        <form id="saveForm" method="post" action="${pageContext.request.contextPath}/staffAction_save.action">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">收派员信息</td>
                </tr>
                <!-- TODO 这里完善收派员添加 table -->
                <tr>
                    <td>取派员编号</td>
                    <td><input type="text" name="id" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>姓名</td>
                    <td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>手机</td>
                    <td><input type="text" validType="phoneNumber" name="telephone" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>单位</td>
                    <td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="checkbox" name="haspda" value="1"/>
                        是否有PDA
                    </td>
                </tr>
                <tr>
                    <td>取派标准</td>
                    <td>
                        <input type="text" name="standard" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<%--修改取派员窗口--%>
<div class="easyui-window" title="对收派员进行修改" id="editStaffWindow" collapsible="false" minimizable="false"
     maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
        <div class="datagrid-toolbar">
            <a id="update" icon="icon-save" href="javascript:void(0)" class="easyui-linkbutton" plain="true">修改</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <%--给表单提供一个id actioin method--%>
        <form id="updateForm" method="post" action="${pageContext.request.contextPath}/staffAction_update.action">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">收派员信息</td>
                </tr>
                <!-- TODO 这里完善收派员添加 table -->
                <tr>
                    <td>取派员编号</td>
                    <td><input type="text" name="id" readonly="readonly" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>姓名</td>
                    <td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>手机</td>
                    <td><input type="text" validType="phoneNumber" name="telephone" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>单位</td>
                    <td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="checkbox" name="haspda" value="1"/>
                        是否有PDA
                    </td>
                </tr>
                <tr>
                    <td>取派标准</td>
                    <td>
                        <input type="text" name="standard" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>