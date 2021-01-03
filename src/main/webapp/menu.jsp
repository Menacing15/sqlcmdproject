<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        p {
            font-size: 1.2em;
        }
    </style>
    <title>sqlcmd</title>
</head>
<body>
    <h3>Worst tool to work with databases. Only for you.♡</h3>

    <c:forEach items="${items}" var = "item">
    <p><a href = "${item}">${item}</a></p>
    </c:forEach>
</body>
</html>
