/**
 * Created by john on 17/5/18.
 */
$(function(){
    var socket;
    if(!window.WebSocket){
        window.WebSocket = window.MozWebSocket;
    }
    if(window.WebSocket){
        // socket = new WebSocket("ws://104.224.157.172:8087/");
        socket = new WebSocket("ws://23.106.137.141:8087/");
        socket.onmessage = function(event){
            var origin = $('#responseText').val();
            $('#responseText').val(origin+"\n"+event.data);
        };
        socket.onopen = function(event){
            $('#responseText').val("打开WebSocket服务正常，浏览器支持WebSocket!");
        };
        socket.onclose = function(event){
            var origin = $('#responseText').val();
            $('#responseText').val(origin+"\n"+"WebSocket关闭！");
        };
    }
    else{
        alert("你的浏览器不支持WebSocket！");
    }

    //绑定发送按钮点击事件
    $('#sendButton').click(function(){
        send($('#myMsg').val());
    });

    $(document).keydown(function(event){
        if(event.keyCode==13){
            $('#sendButton').click();
        }
    });

    //发送消息方法
    function send(message){
        if(!window.WebSocket){return;}
        if($('#myMsg').val().trim()=='') {
            $('#myMsg').val('');
            return;
        }
        if(socket.readyState == WebSocket.OPEN){
            socket.send(message);
            $('#myMsg').val('');
        }else{
            alert("WebSocket连接没有建立成功！");
        }
    }
});
