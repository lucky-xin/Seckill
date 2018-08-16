<!DOCTYPE html>
<html xmlns:th="http://www.w3.org/1999/xhtml">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
<!-- 可选的Bootstrap主题文件（一般不使用） -->
<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap-theme.min.css" rel="stylesheet">
<head>
    <title>登录页面</title>
</head>
<body>
<body>
<form name="loginForm" action="/app/login" method="post">
    账号： <input type="text" id="account" name="name"/><br/>
    密码： <input type="password" id="password" name="password"/><br/>
    认证码：<input type="text" id="imageCode" name="imageCode" style="width:92px;">
    <img src="/app/verify/check_code" onclick="changeImg(this)" alt="换一张">
<#--style="cursor:hand"-->
    <input id="randomNumber" name="randomCode" type="hidden" value=""/><br/>

    <input type="button" value="登陆" onclick="login()"/>
    <input type="hidden" name="token" value="${token}"/>
</form>

</body>
<#--jQery文件,务必在bootstrap.min.js之前引入-->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<#--使用CDN 获取公共js http://www.bootcdn.cn-->
<#--jQuery Cookie操作插件-->
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<#--jQuery countDown倒计时插件-->
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
<script src="/js/aes.js"></script>
<script>
    $(function () {
        // var key = CryptoJS.enc.Base64.parse('ZGIyMTM5NTYxYzlmZTA2OA==');
        // var iv = CryptoJS.enc.Base64.parse('ZGIyMTM5NTYxYzlmZTA2OA==');
        var i = "${base64key}";
        var key = CryptoJS.enc.Base64.parse('dufy20170329java');
        var iv = CryptoJS.enc.Base64.parse('dufy20170329java');


        var encrypted = encrypt('Hello World', key, iv);
        var cipherText = encrypted.ciphertext.toString();
        //java 使用 34439a96e68b129093105b67de81c0fc
        console.log(cipherText);

        // 拿到字符串类型的密文需要先将其用Hex方法parse一下
        var cipherTextHexStr = CryptoJS.enc.Hex.parse(cipherText);

        // 将密文转为Base64的字符串
        // 只有Base64类型的字符串密文才能对其进行解密
        var cipherTextBase64Str = CryptoJS.enc.Base64.stringify(cipherTextHexStr);

        //下面三种解密都可以
        var decrypted = CryptoJS.AES.decrypt(cipherTextBase64Str, key, {
            iv: iv,
            padding: CryptoJS.pad.Pkcs7,
            mode: CryptoJS.mode.CBC
        });

        decrypted = decrypt(CryptoJS.enc.Base64.parse(cipherTextBase64Str), key, iv);
        decrypted = decrypt(cipherTextHexStr, key, iv);

        console.log(decrypted.toString(CryptoJS.enc.Utf8));


    });

    function encrypt(msg, key, iv) {
        return CryptoJS.AES.encrypt(msg, key, {
            iv: iv,
            padding: CryptoJS.pad.Pkcs7,
            mode: CryptoJS.mode.CBC
        });
    }

    function decrypt(cipherText, key, iv) {
        return CryptoJS.AES.decrypt({ciphertext: cipherText}, key, {
            iv: iv,
            padding: CryptoJS.pad.Pkcs7,
            mode: CryptoJS.mode.CBC

        });
    }

    function login() {
        var key = CryptoJS.enc.Base64.parse("${base64key}");
        var iv = CryptoJS.enc.Base64.parse("${base64iv}");

        var account = $("#account").val();
        var password = $("#password").val();
        var encryptedPwd = encrypt(password, key, iv);

        console.log(encryptedPwd.ciphertext.toString());
        $("#password").val(encryptedPwd.ciphertext.toString());
        var url = "/app/login";

        // $.post(url, {"name": encryptedPwd, "password": account},
        //         function (data) {
        //             if (result.success == "success") {
        //                 //登录成功的操作
        //             } else {
        //                 //登录失败的操作
        //             }
        //         });

        document.loginForm.submit();
        // $.ajax({
        //     type:'post',
        //     url:url,
        //     contentType:'application/json;charset=utf-8',
        //     //数据格式是json串，商品信息
        //     data:{"name":account,"password":encryptedPwd,"imageCode":$("#imageCode").val()},
        //     success:function(data){//返回json结果
        //         alert(data);
        //     }
        //
        // });
    }

    function changeImg(img) {
        img.src = img.src + "?" + new Date().getTime();
    }
</script>
</html>