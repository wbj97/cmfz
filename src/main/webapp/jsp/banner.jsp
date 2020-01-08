<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path" />
<script type="text/javascript">
    $(function(){
        $("#bannerTable").jqGrid(
            {
                url : "${path}/banner/queryByPage",
                datatype : "json",
                colNames : [ 'ID', '标题', '路径', '地址', '日期','描述', '状态' ],
                colModel : [
                    {name : 'id',index : 'id',width : 55},
                    {name : 'title',index : 'title',width : 90,editable:true,editrules:{required:true}},
                    {name : 'url',index : 'url',width : 100,editable:true,edittype:"file",editoptions: {enctype:"multipart/form-data"},formatter:function (data) {
                            return "<img src='"+data+"' style='width: 180px;height: 80px;'>";
                        }},
                    {name : 'href',index : 'href',width : 80,align : "right",editable:true},
                    {name : 'createDate',index : 'createDate',width : 80,align : "right",editable:true,edittype: "date"},
                    {name : 'description',index : 'description',width : 80,align : "right",editable:true},
                    {name : 'status',index : 'status',width : 150,sortable : false,editable:true,formatter:function (data) {
                            if(data=="1"){
                                return "展示";
                            } return "冻结";
                        },edittype:"select",editoptions: {value:"1:展示;2:冻结"}}
                ],
                autowidth:true,
                rowNum : 2,
                rowList : [ 3, 5, 10 ],
                pager : '#bannerPage',
                sortname : 'id',
                mtype : "get",
                viewrecords : true,
                sortorder : "desc",
                styleUI: "Bootstrap",
                caption: "JSON 实例",
                multiselect : true,
                editurl: "${path}/banner/save"
            });
        jQuery("#bannerTable").jqGrid('navGrid', '#bannerPage', {edit : true,add : true,del : true,edittext: "修改",addtext: "添加",deltext: "删除"}
        ,{
            closeAfterEdit: true
        },{
            closeAfterAdd: true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url:"${path}/banner/upload",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{bannerId:bannerId},
                        // 指定上传的input框id
                        fileElementId:"url",
                        success:function (data) {
                            $("#bannerTable").trigger('reloadGrid');
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
        },{
            closeAfterDel: true
        });
    });
    function exportBanner() {
        location.href = "${pageContext.request.contextPath}/banner/exportBanner";
        <%--$.ajax({--%>
        <%--    url: "${pageContext.request.contextPath}/banner/exportBanner",--%>
        <%--    datatype:"json",--%>
        <%--    type: "post",--%>
        <%--    success :function (data) {--%>
        <%--        if(data.status=="200")--%>
        <%--            alert("导出成功")--%>
        <%--    }--%>
        <%--})--%>
    }

    function importBanner(){
        $.ajaxFileUpload({
                url: "${pageContext.request.contextPath}/banner/importBanner",
                datatype:"json",
                type: "post",
                fileElementId:"inputBanner",
                success :function (data) {
                    alert("导入成功")
                    $("#bannerTable").trigger('reloadGrid');
                    $("#myModal2").modal("hide");
                }
            }
        )
    }
    function downloadBanner(){
        location.href = "${pageContext.request.contextPath}/banner/downloadBanner";
        <%--$.ajax({--%>
        <%--    url: "${pageContext.request.contextPath}/banner/downloadBanner",--%>
        <%--    datatype:"json",--%>
        <%--    type: "post",--%>
        <%--    success :function (data) {--%>
        <%--        alert("下载成功")--%>
        <%--    }--%>
        <%--})--%>
    }

</script>
<ul class="nav nav-tabs">
    <li><a>轮播图信息</a></li>
    <li><a data-toggle="modal" data-target="#myModal2">轮播图导入</a></li>
    <li><a onclick="exportBanner()">轮播图导出</a></li>
    <li><a onclick="downloadBanner()">轮播图下载</a></li>
</ul>
<table id="bannerTable"></table>
<div id="bannerPage" style="height: 50px"></div>

