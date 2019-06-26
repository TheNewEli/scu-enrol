<%@ page contentType="text/html;charset=UTF-8" %>
<%
    /*获取工程路径*/
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>登入页面</title>
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <!--用百度的静态资源库的cdn安装bootstrap环境-->
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!--表单校验JS：jquery.validate-->
    <script type="text/javascript" src="<%=basePath%>js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/login.js"></script>
    <style type="text/css">
        body {
            background: url(<%=basePath%>img/4.jpg) no-repeat;
            background-size: cover;
            font-size: 16px;
        }

        .form {
            background: rgba(255, 255, 255, 0.2);
            width: 400px;
            margin: 100px auto;
        }

        #login_form {
            display: block;
        }

        #register_form {
            display: none;
        }

        .fa {
            display: inline-block;
            top: 27px;
            left: 6px;
            position: relative;
            color: #ccc;
        }

        input[type="text"], input[type="password"] {
            padding-left: 26px;
        }

        .checkbox {
            padding-left: 21px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            if ('${msg}' != '') {
                $("#register_form").css("display", "block");
                $("#login_form").css("display", "none");
                alert('${msg}');
            }
        });
    </script>
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
</head>
<body>
<div class="container">
    <div class="form row">
        <form action="<%=basePath%>user/login" method="post" class="form-horizontal col-sm-offset-3 col-md-offset-3"
              id="login_form">
            <h3 class="form-title">登录到您的账号</h3>
            <div class="col-sm-9 col-md-9">
                <div class="form-group">
                    <i class="fa fa-user fa-lg"></i>
                    <input class="form-control required" type="text" placeholder="用户名" name="name" autofocus="autofocus"
                           maxlength="20"/>
                </div>
                <div class="form-group">
                    <i class="fa fa-lock fa-lg"></i>
                    <input class="form-control required" type="password" placeholder="密码" name="password" autofocus="autofocus"
                           maxlength="20"/>
                </div>
                <%--                <div class="form-group">--%>
                <%--                    <label class="checkbox">--%>
                <%--                        <input type="checkbox" name="remember" value="1"/> 记住我--%>
                <%--                    </label>--%>
                <%--                    <hr />--%>
                <%--                    <a id="register_btn" class=""></a>--%>
                <%--                </div>--%>
                <div class="form-group">
                    <input type="submit" class="btn btn-success pull-right" value="登录"/>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
