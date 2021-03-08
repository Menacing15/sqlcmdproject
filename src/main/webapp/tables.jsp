<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tables</title>
</head>
<body>
Existing tables: <br>
<c:forEach items="${tableNames}" var = "name">
    <p>${name}</p>
</c:forEach>
</body>
<%@ include file="footer.jsp" %>
</html>
