<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>login</title>
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
    <script  type="text/javascript">
        function login() {
            $.ajax({
                url: "/login",
                type: "post",
                data: {
                    'username': document.getElementById('username').value,
                    'password': document.getElementById('password').value
                },
                success: function (r) {
                },
                error: function (e) {
                    alert("账号或密码错误")
                }
            });
        }
    </script>
</head>
<body onload="connectWebSocket()">
    <div style="position: absolute;left: 50%;top: 50%;transform: translate(-50%,-50%);">
        <form name="frmLogin" action="/login" method="post">
            <div class="form-group">
                <label for="name">账号:</label>
                <input type="text"  class="form-control" name="username" value="root"><br/>
            </div>
            <div class="form-group">
                <label for="inputfile">密码:</label>
                <input type="password"  class="form-control" name="password" value="root"><br/>
            </div>
<#--            账号: <input type="text" name="username"><br/>-->
<#--            密码: <input type="password" name="password"><br/>-->
            <input type="submit" class="btn btn-primary" title="登录" value="登录" style="width: 200px">
        </form>
    </div>

</body>
</html>