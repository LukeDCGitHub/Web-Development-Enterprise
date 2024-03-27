<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
  <title>Login</title>
</head>
<body>
<center><h1>Login Page</h1></center>
<center>
  <form action="LoginProcess" method="POST">
    <label for="username">Username</label>
    <input type="text" id="username" name="username" required /><br/>
    <label for="password">Password</label>
    <input type="password" id="password" name="password" required /><br/>
    <input type="submit" value="Login">
  </form>
  <p>Don't have an account register one <a href="./registration.jsp">here</a></p>
  <%  // Display an error message to the user if the "Error" session attribute is set
    if(session.getAttribute("Error") != null) {
  %> <h2>Invalid Login Credentials Please Try Again</h2> <%

    // Remove the "Error" session attribute
    session.removeAttribute("Error");
  }
  // If the user just registered an account display a message to them
  if(session.getAttribute("Registered") != null) {
  %> <h2>Account Registered!</h2> <%

    // Remove the "Error" session attribute
    session.removeAttribute("Registered");
  }
  // When the user clicks logout remove the user session
  if (request.getParameter("logout") != null) {
    session.removeAttribute("User");
  }
  // When the user is logged in send them to the welcome page
  if (session.getAttribute("User") != null) {
    response.sendRedirect("./welcome.jsp");
  }
%>
</center>
</body>
</html>