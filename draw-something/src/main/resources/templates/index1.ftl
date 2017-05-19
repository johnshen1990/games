<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
    <!-- <link rel="icon" href="data:;base64,iVBORw0KGgo="> -->
</head>
<body>

<form onsubmit="return false;">
    <input type="text" id="myMsg" name="message" value="" />
    <br>
    <input id="sendButton" type="button" value=" 发送 " />
    <hr>
    <textarea id="responseText" style="width:500px;height:300px;" disabled></textarea>
</form>
<script type="application/javascript" src="index.js"></script>
<script type="application/javascript" src="https://raw.githubusercontent.com/bitwiseshiftleft/sjcl/master/core/aes.js"></script>

</body>
</html>