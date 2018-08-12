<!DOCTYPE html>
<html xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <title>秒杀商品列表</title>
    <script src="https://cdn.bootcss.com/vue/2.4.2/vue.min.js"></script>
    <!-- jQuery文件。务必在bootstrap.min.templates.static 之前引入 -->
    <script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>

    <script>
        // new Vue({
        //     el: '#main',
        //     data: {
        //         sites: getData()
        //     }
        // });

        // var infos = getData();

        function getData() {
            var data;
            $.ajax({
                type: 'GET',
                url: "/seckill/json/list",
                dataType: 'json',
                async: false,
                success: function (json) {
                    // json = JSON.stringify(json);
                    // json = eval("(" + json + ")");
                    // json = JSON.parse(json);
                    alert(json);
                    data = json;
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {//请求失败处理函数
                    console.log("请求失败，无法获取分组数据");
                }
            });
            return data;
        }
    </script>

</head>

<body>
<div class="container" id="seckillList">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>


        <div class="panel-body" id="seckillInfo">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>库存</th>
                    <th>创建时间</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>详情页</th>
                </tr>
                </thead>
                <tbody>
                <#list infos as info>
                <tr>
                    <td>${info.name }</td>
                    <td>${info.number}</td>
                    <td>${info.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                    <td>${info.startTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                    <td>${info.endTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                    <td><a class="btn btn-info" href="/seckill/#{ info.seckillId?long }/detail" target="_blank">详情</a></td>
                </tr>
                </#list>

                </tbody>
            </table>

        </div>
    </div>
</div>

</body>
</html>