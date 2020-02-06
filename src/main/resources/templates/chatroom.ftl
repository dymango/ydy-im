<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>聊天室</title>
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
    <script  type="text/javascript">

        $(function(){
        });

        function connectWebSocket()
        {
            $('#messageList').scrollTop(3000);
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
                        $('#messageList').append('<p style="word-wrap: break-word" >'+ receivedMsg +'</p>');
                        $('#messageList').animate({
                            scrollTop: 3000
                        }, 500);
                    }
                };

                ws.onclose = function()
                {
                    // alert("连接已关闭...");
                };
            }
            else
            {
                alert("您的浏览器不支持 WebSocket!");
            }
        }

        function sendMessage() {
            var content = document.getElementById('content').value
            if(content == null || content == '')
            {
                alert("发送内容不能为空")
                return;
            }

            $.ajax({
                url: "/chatRoom/sendMessage",
                type: "post",
                data: {
                    'sender':  document.getElementById('sender').value,
                    'content': content,
                    'nickName': document.getElementById('nickName').value
                },
                success: function (r) {
                    document.getElementById('content').value = ''
                },
                error: function (e) {
                    alert("发送失败")
                }
            });
        }


        var getOnlineNumberTimer = self.setInterval("clock()",1000);
        function clock()
        {
            $.ajax({
                url: "/chatRoom/getOnlineNumber",
                type: "post",
                data: {},
                success: function (r) {
                    // document.getElementById('onlineNumber').value = '聊天室 在线人数:' + r
                    $("#onlineNumber").text('聊天室 在线人数:' + r)
                },
                error: function (e) {
                }
            });
        }

        
        function hideScrollBar() {
            $("#messageList").css("overflow", "hidden");
        }

        function showScrollBar() {
            $("#messageList").css("overflow", "auto");
        }
    </script>
</head>
<body onload="connectWebSocket()">
    <input hidden="hidden" type="text" id="sender" name="sender" value="${userId}"/>
    <div style="position: absolute;left: 50%;top: 50%;transform: translate(-50%,-50%);width: 800px;">
        <div class="panel panel-primary" style="width: 800px;height: 600px">
            <div class="panel-heading">
                <h3 id="onlineNumber" class="panel-title">聊天室 在线人数: 0</h3>
            </div>
            <div id="messageList" class="panel-body" style="width: 100%;height: 550px;overflow: hidden" onmouseover="showScrollBar()" onmouseleave="hideScrollBar()">
                <#--                <ul id="messageList" style="float: left;margin: 0px;padding-left: 0px;overflow: auto; max-height: 320px;width: 100%">-->
                <#if contentList?exists && (contentList?size>0)>
                    <#list contentList as item>
                        <#if (item)?exists>
                        <#--                                <li style="display:block;float:left;">${item}</li>-->
                            <p style="word-wrap: break-word;">${item}</p>
                        </#if>
                    </#list>
                <#else>
<#--                    <li id="default" style="height: 25px;"><a style="color: #5B5B5B;">（无）</a></li>-->
                </#if>
                </ul>
                <#--            </div>-->
            </div>
        </div>

        <div>
            <div class="input-group">
                <span class="input-group-addon">我的昵称</span>
                <input id="nickName" type="text" class="form-control" placeholder="Twitterhandle" value="${nickName}">
            </div><br>
            <div class="input-group">
                <input  type="text" class="form-control" id="content" name="content" onkeypress="if(event.keyCode==13) {sendMessage()}">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" onclick="sendMessage()">发送</button>
                </span>
            </div>
        </div>
    </div>

</body>
</html>