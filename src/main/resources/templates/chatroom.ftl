<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>chat</title>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
    <script  type="text/javascript">
        function connectWebSocket()
        {
            $('#messageList').scrollTop(1300);
            if ("WebSocket" in window)
            {
                var sender = [[${userId}]];
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
                        $("default").hide();
                        $('#messageList').append('<li style="list-style-type:disc;height: 25px;" >'+ receivedMsg +'</li>');
                        $('#messageList').animate({
                            scrollTop: 1300
                        }, 500);
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
                    'sender':  document.getElementById('sender').value,
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
    <input hidden="hidden" type="text" id="sender" name="sender" value="${userId}"/>
    <div>
        <p id="sender">我的ID: ${userId}</p>
<#--        <p>接收人: <input type="text" id="receiver" name="receiver"/></p>-->
        <p>内容: <input type="text"  id="content" name="content"/></p>
        <p><button type="button" title="发送" onclick="sendMessage()">发送</button></p>
    </div>
    <div id="content" style="height: 220px;overflow: auto">
        <ul id="messageList" style="float: left;margin: 0px;padding-left: 0px;overflow: auto; max-height: 220px;">
            <#if contentList?exists && (contentList?size>0)>
                <#list contentList as item>
                    <#if (item)?exists>
                        <li style="list-style-type:disc;height: 25px;">${item}</li>
                    </#if>
                </#list>
            <#else>
                <li id="default" style="height: 25px;"><a style="color: #5B5B5B;">（无）</a></li>
            </#if>
        </ul>
    </div>
</body>
</html>