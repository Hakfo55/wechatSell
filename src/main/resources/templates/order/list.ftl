<!DOCTYPE html>
    <#include "../common/header.ftl">
    <body>
        <div id="wrapper" class="toggled">
            <#--边栏sidebar-->
            <#include "../common/nav.ftl">
            <#--主要内容-->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <table class="table table-bordered table-hover table-condensed">
                                <thead>
                                <tr>
                                    <th>订单id</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>地址</th>
                                    <th>金额</th>
                                    <th>订单状态</th>
                                    <th>支付状态</th>
                                    <th>创建时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list orderDTOPage.content as orderDTO>
                                    <tr>
                                        <td>${orderDTO.orderId}</td>
                                        <td>${orderDTO.buyerName}</td>
                                        <td>${orderDTO.buyerPhone}</td>
                                        <td>${orderDTO.buyerAddress}</td>
                                        <td>${orderDTO.orderAmount}</td>
                                        <td>${orderDTO.getOrderStatusEnum().getMsg()}</td>
                                        <td>${orderDTO.getPayStatusEnum().getMsg()}</td>
                                        <td>${orderDTO.createTime}</td>
                                        <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                        <td>
                                            <#if orderDTO.getOrderStatusEnum().getMsg() == "新订单">
                                                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                            <#else>
                                            </#if>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>

                        <#-- 分页 -->
                        <div class="col-md-12 column">
                            <ul class="pagination pull-right">
                                <#if currentPage lte 1>
                                    <li class="disabled"><a href="#">上一页</a></li>
                                <#else>
                                    <li ><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                                </#if>

                                <#list 1..orderDTOPage.getTotalPages() as index>
                                    <#if currentPage == index>
                                        <li class="disabled"><a href="#">${index}</a></li>
                                    <#else>
                                        <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                    </#if>
                                </#list>

                                <#if currentPage gte orderDTOPage.getTotalPages()>
                                    <li class="disabled"><a href="#">下一页</a></li>
                                <#else>
                                    <li ><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                                </#if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="myModal" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" aria-hidden="true" type="button" data-dismiss="modal">×</button>
                        <h4 class="modal-title" id="myModalLabel">
                            提醒
                        </h4>
                    </div>
                    <div class="modal-body">
                        您有新的订单
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-default" type="button" data-dismiss="modal" onclick="javascript:document.getElementById('notice').pause()">关闭</button>
                        <button onclick="location.reload()" class="btn btn-primary" type="button">查看新的订单</button>
<#--                        <button onclick="orderDetail()" class="btn btn-primary" type="button">查看新的订单</button>-->
<#--                        <input hidden id="newOrderId" type="text" />-->
                    </div>
                </div>
            </div>
        </div>

        <#-- 播放音乐-->
        <audio id="notice" loop="loop">
            <source src="/sell/song/song.mp3" />
        </audio>

        <script>
            // function orderDetail() {
            //     var newOrderId = $('#newOrderId').val();
            //     window.location.href="/sell/seller/order/detail?orderId=" + newOrderId;
            // }
            var websocket = null;
            //先判断浏览器是否支持websocket
            if ('WebSocket' in window){
                websocket = new WebSocket('ws://cyjsell.natapp1.cc/sell/webSocket');
            }else{
                alert('该浏览器不支持websocket！');
            }
    
            //websocket事件
            websocket.onopen = function (e) {
                console.log('建立连接');
            }
            websocket.onclose = function (e) {
                console.log('连接关闭');
            }
            websocket.onmessage = function (e) {
                console.log('收到消息：' + e.data);
                // $('#newOrderId').val(e.data);

                //弹窗提醒
                $('#myModal').modal('show');
                //播放音乐
                document.getElementById('notice').play();
            }
            websocket.onerror = function (e) {
                alert('websocket通信发生错误！');
            }
    
            //窗口关闭的时候要把websocket给关闭掉
            window.onbeforeunload = function () {
                websocket.close();
            }
        </script>
    </body>
</html>