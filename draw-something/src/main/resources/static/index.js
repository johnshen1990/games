/**
 * Created by john on 17/5/18.
 */
$(function(){
    var socket;
    var key;
    var iv;

    if(!window.WebSocket){ window.WebSocket = window.MozWebSocket; }

    if(window.WebSocket){
        // socket = new WebSocket("ws://104.224.157.172:8087/");
        //socket = new WebSocket("ws://23.106.137.141:8087/");
        socket = new WebSocket("ws://127.0.0.1:8087/");
        socket.onmessage = function(event){
            if(event.data.startsWith('RES:KEY:')) {
                key = event.data.substring(8);
                console.log("密钥:" + key);
                key = CryptoJS.enc.Utf8.parse(key);
                iv = key;
                var origin = $('#responseText').html();
                $('#responseText').html(origin + "<p style='color: red'>[密钥交换成功,您的聊天的内容已处在nb算法的保护之下。请放肆,毋克制!]</p>");
                return;
            }

            console.log("响应的内容:" + event.data);

            // 解密数据
            decryptedText = decryptFromAES(event.data);

            console.log("解密后的内容" + decryptedText);

            var origin = $('#responseText').html();
            $('#responseText').html(origin + "<p>" + decryptedText + "</p>");


            //var eventObj = JSON.parse(event.data);
            //if(eventObj.messageType == "ROOM_INFO") {
            //    var userCount = eventObj.roomInfo.userList.length;
            //    console.log(userCount + "人");
            //} else if(eventObj.messageType == "CHAT_TEXT") {
            //
            //} else {
            //
            //}
        };
        socket.onopen = function(event){
            $('#responseText').html("<p>[打开WebSocket服务正常，浏览器支持WebSocket!]</p>");
            socket.send("REQ:KEY");
        };
        socket.onclose = function(event){
            var origin = $('#responseText').html();
            $('#responseText').html(origin + "<p>WebSocket关闭!</p>");
        };
    } else{ alert("你的浏览器不支持WebSocket！"); }

    // 绑定发送按钮点击事件
    $('#sendButton').click(function(){
        send($('#myMsg').val());
    });

    // 绑定回车键发送事件
    $(document).keydown(function(event){
        if(event.keyCode==13){
            $('#sendButton').click();
        }
    });

    // 发送消息方法
    function send(message){
        if(!window.WebSocket){return;}
        if($('#myMsg').val().trim()=='') {
            $('#myMsg').val('');
            return;
        }
        if(socket.readyState == WebSocket.OPEN){
            console.log("原始内容:" + message);
            encryptedText = encryptToAES(message);
            console.log("发送的内容:" + encryptedText);
            socket.send(encryptedText);
            $('#myMsg').val('');
        }else{
            alert("WebSocket连接没有建立成功！");
        }
    }


    function encryptToAES(decrypted){
        var encrypted = CryptoJS.AES.encrypt(decrypted, key, {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
        return encrypted.toString();
    }

    function decryptFromAES(encrypted) {
        var decrypted = CryptoJS.AES.decrypt(encrypted, key, {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
        return CryptoJS.enc.Utf8.stringify(decrypted);
    }

    //window.setTimeout(function(){
    //    var text = "123456";
    //    console.log(encryptToAES(text));
    //    console.log(decryptFromAES(encryptToAES(text)));
    //}, 5000)


});
