<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<center><h1>Welcome to my website!</h1></center>
<center>
    <form action="./index.jsp" method="POST">
        <input type="hidden" value="logout" id="hidden" name="hidden"/>
        <input type="submit" value="Logout" name="logout" id="logout"/>
    </form>
</center>
<%
    if(session.getAttribute("User") == null) {
        response.sendRedirect("./index.jsp");
    }
%>
</body>
</html>
