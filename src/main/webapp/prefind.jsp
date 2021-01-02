<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
<form action="connect" method="post">
    <table>
        <tr>
            <td>Table name</td>
            <td><input type = "text" name = "tableName"/></td>
        </tr>
    </table>
</form>
</body>
<%@ include file="footer.jsp" %>
</html>
