<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
<html lang="en">
<head>
    <!-- 将https协议改为http协议 -->
    <script type="text/javascript">
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singap ore.goeasy.io】
            appkey: "BC-26723bbc45c047538396794c5d42c167", //替换为您的应用appkey
        });
        goEasy.subscribe({
            channel: "wbj", //替换为您自己的channel
            onMessage: function (message) {
                alert(message);
            }
        });
    </script>
</head>