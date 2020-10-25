# Sqlcmd Project
```sh
 Sqlcmd - console clinet for working with databases, created in PostreSql.
```
#### To run the project you need:
1. JDK 8
2. IntelliJ IDEA
3. PostgreSQL server

#### To run test successfully you need create a database with: 
1. name - "sqlcmd"
2. user - "postgres"
3. password - "1234"

##### There are such commands


* **сonnect**

        Purpose: Connects to the DB.

        Input: connect:database,user,password
            database - name of the database
            user - name of the user
            password - password to connect the server

        Output: Message about successful execution of the command.

* **help**

        Purpose: Shows all available commands and their description 

        Input: help

        Output: Command names and their description. 
        
* **tables**

        Purpose: Print all table names in the database

        Input: tables

        Output: Names of all tables in database.
        
* **create**

        Purpose: Creates a table with given column names

        Input: create: tableName, column1, type1, column2, type2,..., columnN, typeN
            tableName - name of the new table
            column1 - name of the first column
            type1 - type of the first column
            column2 - name of the second column
            type2 - type of the second column
            columnN - name of the N column
            typeN - type of the N column

        Output: Message about successful execution of the command

* **find**

        Purpose: Prints a table 

        Input: find:tableName
            tableName -  name of the new table

        Output: Stylized table.
* **insert**

        Purpose: Inserts a value in a column

        Input: insert: tableName, column1, value1, column2, value2,...,columnN, valueN
            tableName - name of the new table
            column1 - name of the first column
            value1 - value for the firsrt column
            column2 - name of the second column
            value2 - value for the second column
            columnN - name of the N column
            valueN - value for the N column

        Output: Message about successful execution of the command.
        
* **update**

        Purpose: Updates record, sets column2 = value2, if column1 = value1

        Input: update: tableName, column1, value1, column2, value2
            tableName - name of the new table
            column1 - name of the checked column
            value1 - value that equates to the first column
            column2 - name of the first column whose value is going to be updated
            value2 - first updated value
            columnN - name of the N column whose value is going to be updated
            valueN - N updated value

        Output: Stylized table.
        
* **clear**

        Purpose: Clears all records in a table

        Input: clear:tableName
          tableName - name of the cleared table
     
        Output: Message about successful execution of the command.
        
* **drop**

        Purpose: Deletes a table

        Input: drop:tableName
            tableName - name of the deleted table

        Output: Message about successful execution of the command.      
         
* **delete**

        Purpose: Deletes a specific records in a talbe

        Input: delete: tableName, column, value
            tableName  - name of the table
            column - column name whose value is going to be deleted
            value - deleted value

        Output: Stylized table. 

* **exit**

        Purpose: Shuts down a programm

        Input: exit

        Output: Message about successful execution of the command.

