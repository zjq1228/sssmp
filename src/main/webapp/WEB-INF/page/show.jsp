<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/res/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/res/layer/layer.js"></script>
<script type="text/javascript">

    //赋选中复选框的值
    function selectedValue() {
        proids = $('input[name="ids"]:checked');
        var chk_value = [];
        $.each(ids,function(){
            chk_value.push($(this).val());
        })
        $("#findByIds").val(chk_value);
        //alert(chk_value);
        return chk_value;
    }

    //初始加载函数
    $(function(){
        search();
    })

    //展示
    function search() {
        $.post("<%=request.getContextPath()%>/user/show",
            $("#fm").serialize(),
            function(data){
                if (data.code != 200) {
                    alert(data.msg);
                    return;
                }
                var html = "";
                var pageHtml = "";
                for (var i = 0; i <  data.data.list.length; i++) {
                    var u = data.data.list[i];
                    html += "<tr>";
                    html += "<td><input type = 'checkbox' name = 'ids' value = '"+u.id+"'/></td>"
                    html += "<td>"+u.userName+"</td>";
                    html += "<td>"+u.userPwd +"</td>";
                    html += "<td>"+u.age +"</td>";
                    if (u.sex==1){
                        html += "<td>男</td>";
                    }else{
                        html += "<td>女</td>";
                    }

                    html += "<td>"+u.email +"</td>";

                    if (u.hobby==1){
                        html += "<td>看书</td>";
                    }if (u.hobby==2){
                        html += "<td>运动</td>";
                    }
                    if (u.hobby==3){
                        html += "<td>游戏</td>";
                    }


                    html += "</tr>";
                }
                $("#tbd").html(html);
                pageHtml += "<input type = 'button' value='上一页' onclick='page(0, "+data.data.pages+")'/>";
                if (data.data.pages==0) {
                    pageHtml +="第0页";
                }else{
                    pageHtml +="第"+$("#pageNo").val()+"/"+data.data.pages+"页";
                }
                pageHtml += "<input type = 'button' value='下一页' onclick='page(1, "+data.data.pages+")'/>";
                $("#pageDiv").html(pageHtml);
            });
    }

    //分页
    function page(q,pages){
        var page = $("#pageNo").val();
        if (q == 0) {
            if (page == 1) {
                layer.msg("已是首页", {icon: 5, time: 2000});
                return;
            }
            $("#pageNo").val(parseInt(page) - 1);
        }
        if (q == 1) {
            if (parseInt(page) + 1 > pages ) {
                layer.msg("已是尾页", {icon: 5, time: 2000});
                return;
            }
            $("#pageNo").val(parseInt(page) + 1);
        }
        search();
    }


    //批量新增
    function adds() {
        var index = layer.load(0,{shade:0.3});
        window.location.href="<%=request.getContextPath()%>/user/toAdds",
            layer.close(index);
    }

    //删除批量
    function dels(){
        if(selectedValue().length == 0){
            alert("无法进行删除操作");
            return;
        }
        $.post("<%=request.getContextPath()%>/user/delIds",
            {"ids":selectedValue()},
            function(data){
                if(data.code == 200){
                    alert("删除成功");
                    window.location.href ="<%=request.getContextPath()%>/user/toShow";
                }
            })
    }

    //条件查询要重新分页
    function selectBy(){
        $("#pageNo").val(1);
        search();
    }
</script>
</head>
<body style="text-align: center">
    <input type="button" value="批量删除" onclick="dels()"/>
    <input type="button" value="批量新增" onclick="adds()"/>

    <form id="fm">
        <input type="hidden" value="1"  id="pageNo" name ="pageNo">

        名字:<input type="text" name="userName">
        <br>
        <br>
        性别:
        <input type="radio" name="sex" value="1">男
        <input type="radio" name="sex" value="2">女
        <br>
        <br>
        爱好：
        <input type="checkbox" name="hobbys" value="1">运动
        <input type="checkbox" name="hobbys" value="2">看书
        <input type="checkbox" name="hobbys" value="3">游戏
        <br>
        <br>
        <input type="button" value="查询" onclick="selectBy()">
        <br>
        <br>
    </form>

    <table align="center">
        <tr>
            <td>id</td>
            <td>用户名</td>
            <td>密码</td>
            <td>年龄</td>
            <td>性别</td>
            <td>邮箱</td>
            <td>爱好</td>
        </tr>
        <tbody id ="tbd">

        </tbody>
     </table>

    <div id = "pageDiv"></div>
</body>
</html>