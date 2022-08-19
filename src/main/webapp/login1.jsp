<%-- 
    Document   : login
    Created on : Feb 9, 2020, 12:32:39 PM
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
        <form action="${pageContext.request.contextPath}/login" method="post">
            <input type="text" name="farmerId" placeholder="Farmer Id"/>
            <input type="text" name="password" placeholder="Password"/>
            <button type="submit" >Login</button>
        </form>
    </body>
</html>
