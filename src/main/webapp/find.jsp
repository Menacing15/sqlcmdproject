<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
    </style>
    <title>sqlcmd</title>
</head>
<body>
<c:choose>
    <c:when test="${table == null}">
        Such table doesn't exist.
        <br>
    </c:when>
    <c:otherwise>
        <table>
            <c:forEach items = "${table}" var = "row">
                <tr>
                    <c:forEach items = "${row}" var = "element">
                        <td>${element}</td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
        <br>
    </c:otherwise>
</c:choose>
</body>
<%@ include file="footer.jsp" %>
</html>
