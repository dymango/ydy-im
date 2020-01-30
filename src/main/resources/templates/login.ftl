<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>chat</title>
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
    <form name="frmLogin" action="/login" method="post">
        账号: <input type="text" name="username"><br/>
        密码: <input type="password" name="password"><br/>
        <input type="submit">
    </form>

</body>
</html>