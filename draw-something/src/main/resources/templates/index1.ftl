<!DOCTYPE html>
<html style="-webkit-text-size-adjust: 100%; line-height: 1.60">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
    <script type="application/javascript" src="aes.js"></script>
    <script type="application/javascript" src="mode-cfb-min.js"></script>
    <!-- <link rel="icon" href="data:;base64,iVBORw0KGgo="> -->

    <style>
        #myMsg {
            border: solid 2px #000;
            width: 300px;
            height: 30px;
            line-height: 30px;
        }
        #sendButton {
            width: 304px;
            margin-top: 10px;
            border: solid 2px #999;
        }
        #responseText {
            border: solid 2px #555;
            width: 300px;
            height: 450px;
            font-size: 12px;
        }
    </style>
</head>
<body>

<form onsubmit="return false;">
    <input id="myMsg" type="text" name="message" value="" />
    <br>
    <input id="sendButton" type="button" value=" 发送 " />
    <hr>
    <div id="responseText"></div>
</form>
<script type="application/javascript" src="index.js"></script>
</body>
</html>