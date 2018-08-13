<!DOCTYPE html>
<html xmlns:th="http://www.w3.org/1999/xhtml">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
<!-- 可选的Bootstrap主题文件（一般不使用） -->
<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap-theme.min.css" rel="stylesheet">
<head>
    <title>秒杀详情页</title>
</head>
<body>
<body>

    <form action="${pageContext.request.contextPath }/app/login" method="post">
        账号<input type="text" name="name"/><br/>
        密码<input type="text" name="password"/><br/>
        <input type="submit" value="登陆"/><br/>
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
<script>
    $(function () {
        //使用EL表达式传入参数
        seckill.detail.init({
            seckillId:#{seckill.seckillId},
            startTime:#{seckill.startTime?long},//毫秒
            endTime:#{seckill.endTime?long}
        });
        alert(${seckill.startTime?long})
    })
</script>
</html>