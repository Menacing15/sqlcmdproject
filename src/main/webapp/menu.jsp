<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
    <c:forEach items="${items}" var = "item">
    <a href = "${item}">${item}</a><br>
    </c:forEach>
</body>
</html>
