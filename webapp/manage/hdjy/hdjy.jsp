<%-- 
    Document   : hdjy
    Created on : 2013-3-15, 12:42:41
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://opdps.hbnu.edu.cn/jplus" prefix="z" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<z:path/>jquery-easyui-1.2.5/themes/default/easyui.css"/>
        <link rel="stylesheet" type="text/css" href="<z:path/>jquery-easyui-1.2.5/themes/icon.css"/>
        <script type="text/javascript" src="<z:path/>jquery-easyui-1.2.5/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="<z:path/>jquery-easyui-1.2.5/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="<z:path/>jquery-easyui-1.2.5/locale/easyui-lang-zh_CN.js"></script>
        <script type="text/javascript" src="<z:path/>manage/hdjy/hdjy.js"></script>
        <script type="text/javascript">
            var path = '<z:path/>';
            var mode="";
            var qjtj='${param.searchValue}';//点击加载所有时加载指定类别的列表
            var searchName='${param.searchName}';
            var searchValue='${param.searchValue}';//定义全局的查询条件和查询内容
        </script>
        <title>活动剪影类别</title>
    </head>
    <body>
        <div id="maindatagrid" class="easyui-datagrid" toolbar="#gridTools"></div>
        <div id="gridTools" style="padding:5px;height:auto">
            <div style="margin-bottom:5px" id="test" >
                <a href="javascript:void(0);" class="easyui-linkbutton" onclick="addHdjy()" iconCls="icon-add" plain="true">添加活动剪影</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" onclick="editHdjy()" iconCls="icon-edit" plain="true">查看/修改活动剪影信息</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" onclick="deleteHdjy()" iconCls="icon-remove" plain="true">删除活动剪影</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" onclick="loadAllData()" iconCls="icon-back" plain="true">所有活动剪影</a>
                <input type='text' id='ss' />
            </div>
            <div id="cxtj" style="width:120px;float:right">   
                <div name="jybt">剪影标题</div> 
            </div> 
        </div>
        <div id="add" class="easyui-dialog" closed="true" style="width:480px;" iconCls="icon-add" modal="true">
            <form method="post" id="mainform">
                <input type="reset" class="easyui-linkbutton" style="text-align: center" value="重置"/>
                <input type="button" class="easyui-linkbutton" style="text-align: center" value="提交" onclick="submitForm()"/>
                <fieldset>
                    <legend style="color: #0046D5;" >活动剪影信息:</legend>  
                    <input type="hidden" id="get_jyid" name="jyid"/>
                    剪影标题:<input type="text" id="jybt" name="jybt"/><br/>
                    剪影描述:<br/>
                    <textarea id="jyms" style="width:420px;height:120px;resize:none" name="jyms"></textarea>
                    <br/>
                </fieldset>
            </form>
        </div>
        <div id="jytpdia" class="easyui-dialog" closed="true" style="width:400px;height:500px;" iconCls="icon-search" modal="true">
            <img id="jytp" src="" onerror='showOtherPic()' style="width:384px;height:460px;"/>
        </div>
    </body>
</html>
