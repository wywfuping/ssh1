<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>bookList</title>
    <link rel="stylesheet" href="/repo/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="page-header">
        <h3>SSH</h3>
    </div>
    <div class="well well-sm">
        <form method="get" class="form-inline">
            <div class="form-group">
                <input type="text" placeholder="书籍名称 或 作者" name="q_s_like_bookname_or_bookauthor" value="${q_s_like_bookname_or_bookauthor}" class="form-control">
            </div>
            <div class="form-group">
                <input type="text" placeholder="最低价格" name="q_f_ge_bookprice" value="${q_f_ge_bookprice}" class="form-control"> - <input type="text" placeholder="最高价格" name="q_f_le_bookprice" value="${q_f_le_bookprice}" class="form-control">
            </div>
            <div class="form-group">
                <input type="text" placeholder="类型名称" name="q_s_like_bookType.booktype" value="${requestScope['q_s_like_bookType.booktype']}" class="form-control">
            </div>
            <div class="form-group">
                <select name="q_i_eq_bookType.id" class="form-control">
                    <option value="">请选择类型</option>
                    <c:forEach items="${bookTypeList}" var="type">
                        <option value="${type.id}" ${requestScope['q_i_eq_bookType.id']==type.id?'selected':''}>${type.booktype}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <select name="q_i_eq_publisher.id" class="form-control">
                    <option value="">请选择类型</option>
                    <c:forEach items="${publisherList}" var="pub">
                        <option value="${pub.id}" ${requestScope['q_i_eq_publisher.id']==pub.id?'selected':''}>${pub.pubname}</option>
                    </c:forEach>
                </select>
            </div>
            <button class="btn btn-default">搜索</button>
        </form>
    </div>
    <a href="/book/new.action" class="btn btn-success pull-right">添加新书籍</a>
    <table class="table">
        <thead>
        <tr>
            <td>书名</td>
            <td>作者</td>
            <td>价格</td>
            <td>数量</td>
            <td>出版社</td>
            <td>分类</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.items}" var="book">
            <tr>
                <td>${book.bookname}</td>
                <td>${book.bookauthor}</td>
                <td>${book.bookprice}</td>
                <td>${book.booknum}</td>
                <td>${book.publisher.pubname}</td>
                <td>${book.bookType.booktype}</td>
                <td>
                    <a href="/book/edit?id=${book.id}">修改</a>
                    <a href="/book/del?id=${book.id}">删除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <ul class="pagination pull-right" id="page"></ul>
</div>

<script src="/repo/js/jquery-2.2.3.min.js"></script>
<script src="/repo/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function () {
        $("#page").twbsPagination({
            totalPages:${page.totalPages},
            visiblePages:5,
            first:'首页',
            prev:'上一页',
            next:'下一页',
            last:'末页',
            href:'?p={{number}}&q_s_like_bookname_or_bookauthor=${q_s_like_bookname_or_bookauthor}&q_f_ge_bookprice=${q_f_ge_bookprice}&q_f_le_bookprice=${q_f_le_bookprice}&q_i_eq_bookType.id=${requestScope['q_i_eq_bookType.id']}&q_i_eq_publisher.id=${requestScope['q_i_eq_publisher.id']}'
        });
    });

</script>
</body>
</html>
