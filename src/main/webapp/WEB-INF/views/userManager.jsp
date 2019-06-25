<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    /*获取工程路径*/
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //主界面高度自适应
        $(function () {
            //top代表顶层窗口对象
            top.mainHeight();
            if ('${msg}' != '') {
                alert('${msg}');
            }
        });

        //查询事件
        function findData() {
            $('#currentPage').val('1');
            document.forms[0].submit();
        }

        //清空查询条件
        function clearP() {
            $("#name").val("");
            $("#loginName").val("");
        }

        //删除用户
        function del(id) {
            if (confirm("确定要删除该用户吗？")) {
                $.ajax({
                    url: "<%=basePath%>user/del",
                    type: "post",
                    dataType: "json",
                    data: {id: id},
                    success: function () {
                        alert("删除成功！");
                        $("#myform").submit();
                    },
                    error: function () {//不知道为什么有时会走error
                        alert("删除成功！");
                        $("#myform").submit();
                    }
                });
            }
        }

        //编辑用户 先获取当前ID用户信息
        function edit(id) {
            $.ajax({
                url: "<%=basePath%>user/selectUserById",
                type: "post",
                dataType: "json",
                data: {id: id},
                success: function (data) {
                    $("#editModal").modal("show");
                    $("#name2").val(data.name);
                    $("#age2").val(data.age);
                    $("#loginName2").val(data.loginName);
                    $("#rpassword2").val(data.password);
                    $("#password2").val(data.password);
                    $("#myid").val(data.id);
                },
                error: function () {
                    alert("获取用户数据失败！");
                }
            });
        }

        function reply(questionid) {
            $("#replymodal").modal("show");
            $("#question_id").val(questionid);
        }
    </script>
</head>
<body>
<div class="table-responsive">
    <form class="form-inline" role="form" action="<%=basePath%>user/selectAll" method="post" id="myform">
        <div style="margin-left: 80px;">
            姓名：<input type="text" name="name" id="name" value="${userInfo1.name}" class="form-control"
                      style="width: 120px;">
            账号：<input type="text" name="loginName" id="loginName" value="${userInfo1.loginName}" class="form-control"
                      style="width: 120px;">
            <input type="button" onclick="findData();" class="btn btn-info" value="查询"/>
            <input type="button" onclick="clearP();" class="btn btn-info" value="清空"/>
            <input type="button" data-toggle="modal" data-target="#addModal" class="btn btn-info" value="新增"/>
        </div>
        <hr style="margin-top: 10px;"/>
        <div style="margin-left: 20px;">
            <table class="table table-hover">
                <thead>
                <tr style="text-align: center">
                    <th style="text-align: center">序号</th>
                    <th style="text-align: center">姓名</th>
                    <th style="text-align: center">性别</th>
                    <th style="text-align: center">文理科</th>
                    <th style="text-align: center">生源地</th>
                    <th style="text-align: center">高中学校</th>
                    <th style="text-align: center">分数</th>
                    <th style="text-align: center">位次</th>
                    <th style="text-align: center">手机号</th>
                    <th style="text-align: center">问题</th>
                    <th style="text-align: center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${!empty questionList }">
                    <c:forEach items="${questionList}" var="question" varStatus="status">
                        <tr style="text-align: center">
                            <td>${status.count}</td>
                            <td>${question.name   }</td>
                            <td>${question.gender   }</td>
                            <td>${question.art_n_sicence }</td>
                            <td>${question.district }</td>
                            <td>${question.school}</td>
                            <td>${question.score}</td>
                            <td>${question.ranking}</td>
                            <td>${question.phone_number}</td>
                            <td>${question.content}</td>
                            <td>
                                <c:if test="${question.status=='未回复'}">
                                    <button type="button" class="btn btn-success" onclick="reply('${question._id}')">回复</button>
                                </c:if>

                                <c:if test="${question.status=='已回复'}">
                                    <button type="button" class="btn btn-success" onclick="review('${user.id }')">查看</button>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <div>
                <jsp:include page="/common/page.jsp"/>
            </div>
        </div>
    </form>
</div>
<!-- 新增用户模态框（Modal） -->
<form action="<%=basePath%>user/addUser" method="post" id="register_form1">
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 id="mytitle" class="modal-title" id="myModalLabel">
                        新增用户
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <i class="fa fa-user fa-lg"></i>
                        <input class="form-control required" type="text" id="name1" placeholder="Name" name="name"
                               autofocus="autofocus"/>
                    </div>
                    <div class="form-group">
                        <i class="fa fa-user fa-lg"></i>
                        <input class="form-control required" type="text" placeholder="Age" id="age1" name="age"
                               autofocus="autofocus"/>
                    </div>
                    <div class="form-group">
                        <i class="fa fa-user fa-lg"></i>
                        <input class="form-control required" type="text" placeholder="LoginName" id="loginName1"
                               name="loginName" autofocus="autofocus"/>
                    </div>
                    <div class="form-group">
                        <i class="fa fa-lock fa-lg"></i>
                        <input class="form-control required" placeholder="Password" id="password1" name="password"/>
                    </div>
                    <div class="form-group">
                        <i class="fa fa-check fa-lg"></i>
                        <input class="form-control required" placeholder="Re-type Your Password" id="rpassword1"
                               name="rpassword"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="submit" class="btn btn-info">
                        确认
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</form>
<!-- 编辑用户模态框（Modal） -->
<form action="<%=basePath%>user/reply" method="post" id="editModalForm">
    <input type="hidden" id="myid" name="myid"/>
    <div class="modal fade" id="replymodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 id="mytitle1" class="modal-title" id="myModalLabel1">
                        回复咨询
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <i class="fa fa-check fa-lg"></i>
                        <textarea class="form-control required" placeholder="Re-type Your Password" id="reply" name="reply"></textarea>
                        <input type="hidden" id="question_id" name="question_id">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="submit" class="btn btn-info">
                        确认
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</form>
</body>
</html>
