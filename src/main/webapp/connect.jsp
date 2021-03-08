<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
<c:choose>
    <c:when test="${dbname == null}">
        Now not connected.
        <br>
    </c:when>
    <c:otherwise>
        Now connected to ' ${dbname} '.
        <br>
    </c:otherwise>
</c:choose>
<br>
    <form action="connect" method="post">
        <table>
            <tr>
                <td>Database name</td>
                <td><input type = "text" name = "name"/></td>
            </tr>
            <tr>
                <td>Username</td>
                <td><input type = "text" name = "username"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type = "password" name = "password"/></td>
            </tr>
            <tr>
                <td></td>
                <td><input type = "submit" name = "connect"/></td>
            </tr>
        </table>
    </form>
</body>
<c:choose>
    <c:when test="${manager == null}">
        <br>
    </c:when>
    <c:otherwise>
        <%@ include file="footer.jsp" %>
        <br>
    </c:otherwise>
</c:choose>
</html>
