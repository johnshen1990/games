<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1,minimum-scale=1" />
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