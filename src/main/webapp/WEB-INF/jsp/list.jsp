<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@include file="common/tag.jsp"%><!--引入JSTL-->
<!DOCTYPE html>
<html>
<head>
    <title>秒杀商品列表页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="common/head.jsp"%>
    <style type="text/css">
        .table
        th {
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center"><h2>秒杀列表</h2></div>
            <div class="panel-body text-center">
                <table class="table table-hover table-bordered">
                    <thead>
                        <tr>
                            <th>名称</th>
                            <th>库存</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>创建时间</th>
                            <th>详情页</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="sk" items="${list}">
                            <tr>
                                <td>${sk.name}</td>
                                <td>${sk.number}</td>
                                <td>
                                    <fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                                </td>
                                <td>
                                    <fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                                </td>
                                <td>
                                    <fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                                </td>
                                <td>
                                    <a class="btn btn-info" href="/seckill/${sk.seckillId}/detail" target="_blank">详情</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <c:if test="${username != null}">
                <div class="panel-footer text-center">
                    系统当前用户：${username}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="/user/logout" class="btn btn-default">退出系统</a>
                </div>
            </c:if>
        </div>
    </div>
</body>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</html>
