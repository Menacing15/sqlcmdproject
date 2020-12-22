<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
There are such commands:<br>
help - to see all commands available.<br>
connect:database,username,password  - to connect to a certain database<br>
tables -  to get all table names of the database you are connected to.<br>
create:tableName,column1,column2,...,columnN -  to create a table with a given name and columns<br>
insert:tableName,column1,value1,column2,value2,...,columnN,valueN - to make a new record in the table<br>
update:tableName,column1,value1,column2,value2,...,columnN,valueN - to update a record in a table , where there is a record column1 = value1<br>
find:tableName - to draw the table<br>
drop:tableName - to drop the table<br>
clear:tableName - to clear table's content<br>
delete:tableName,column,value - to delete a record in a table<br>
exit - to shut down the program.<br>
<a href = "menu">Menu</a>
</body>
</html>
