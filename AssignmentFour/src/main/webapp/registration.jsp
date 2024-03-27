<%--
  Created by IntelliJ IDEA.
  User: Luke
  Date: 2024-03-16
  Time: 7:13 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<center>
    <h1>Register Page</h1>
    <form action="RegisterProcess" method="POST">
        <label for="registerUsername">Username</label>
        <input type="text" id="registerUsername" name="registerUsername" required /><br/>
        <label for="registerPassword">Password</label>
        <input type="password" id="registerPassword" name="registerPassword" required /><br/>
        <input type="submit" value="Login">
    </form>
    <p>Already have an account log in <a href="./index.jsp">here</a></p>
</center>

<%  // Display an error message to the user if the "Error" session attribute is set
    if(session.getAttribute("Error") != null) {
%> <center><h2><%= session.getAttribute("ErrorMessage") %></h2></center> <%

        // Remove the "Error" session attribute
        session.removeAttribute("Error");
        session.removeAttribute("ErrorMessage");
    }
    // If the user is logged in send them to the welcome page
    if (session.getAttribute("User") != null) {
        response.sendRedirect("./welcome.jsp");
    }

%>
</body>
</html>
