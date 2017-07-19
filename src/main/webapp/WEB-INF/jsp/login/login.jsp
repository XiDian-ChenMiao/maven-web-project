<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>系统登录首页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
    <h1 style="text-align: center">系统登录</h1><hr />
    <div style="text-align: center">
        <form method="post" action="/user/login">
            用户名称：<input type="text" placeholder="请输入用户名称" name="username"><br><br>
            用户密码：<input type="password" placeholder="请输入用户密码" name="password"><br>
            <input type="submit" value="登录系统">
        </form>
    </div>
</body>
</html>
