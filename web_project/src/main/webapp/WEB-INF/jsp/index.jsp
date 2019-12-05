<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 宋嘉伦
  Date: 2019/10/30
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body style="background-image: none;">
    <div class="body_wrap">
        <div class="container">
            <div class="alert alert-success text-center" role="alert">Sptring Boot学习资源大奉送，爱我就关注嘟嘟公众号：嘟爷java超神学堂</div>
            <table class="table table-striped table-bordered">
                <tr>
                    <td>作者</td>
                    <td>教程名称</td>
                    <td>地址</td>
                </tr>
                <tr>----------------------------------- ${learnList.size()}</tr>
                <c:forEach var="learn" items="${learnList}">
                    <tr class="text-info">
                        <td >${learn.author}</td>
                        <td >${learn.title}</td>
                        <td><a href="${learn.url}" class="btn btn-search btn-green" target="_blank"><span>点我</span></a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</body>
</html>
