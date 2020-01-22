<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>chat</title>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
    <script  type="text/javascript">
        function connectWebSocket()
        {
            if ("WebSocket" in window)
            {
                var sender = [[${sender}]];
                var receiver = [[${receiver}]];
                var ws = new WebSocket("ws://127.0.0.1:8082/ws?" + sender);
                ws.onopen = function()
                {
                    alert("成功连接到服务器");
                };

                ws.onmessage = function (evt)
                {
                    var receivedMsg = evt.data;
                    if(receivedMsg != 'HEARTBAT')
                    {
                        alert(receivedMsg)
                    }
                };

                ws.onclose = function()
                {
                    alert("连接已关闭...");
                };
            }
            else
            {
                alert("您的浏览器不支持 WebSocket!");
            }
        }

        function sendMessage() {
            $.ajax({
                url: "/chatRoom/sendMessage",
                type: "post",
                data: {
                    'sender': document.getElementById('sender').value,
                    'receiver': document.getElementById('receiver').value,
                    'content': document.getElementById('content').value
                },
                success: function (r) {
                },
                error: function (e) {
                    alert("对方不在线")
                }
            });
        }
    </script>
</head>
<body onload="connectWebSocket()">
    <p>发送人: <input type="text" id="sender" name="sender" th:value="${sender}"/></p>
    <p>接收人: <input type="text" id="receiver" name="receiver"/></p>
    <p>内容: <input type="text"  id="content" name="content"/></p>
    <p><button type="button" title="发送" onclick="sendMessage()">发送</button></p>
</body>
</html>