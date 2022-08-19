<%-- 
    Document   : register
    Created on : Feb 9, 2020, 10:41:17 AM
    Author     : gugsy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <h1>Hello World!</h1>
        <form action="${pageContext.request.contextPath}/register" method="post">
        <label> Salutation</label>        
        <input type="text" name="salutation">
        <label> First Name</label>        
        <input type="text" name="fname">
        <label> Surname</label>        
        <input type="text" name="surname">
        <label> Email</label>        
        <input type="text" name="email">
        <label> Password</label>        
        <input type="password" name="password">
        <label> Address</label>        
        <input type="text" name="address">
        <label> City</label>        
        <input type="text" name="city">
        <label> Farmer Id</label>        
        <input type="text" name="farmerId">
        
        <button type="submit" >Register</button>
        </form>
         </body>
</html>
