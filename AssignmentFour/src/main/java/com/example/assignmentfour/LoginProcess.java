/*
  User: Luke
  Date: 2024-03-21
  Time: 5:52 p.m.
*/

package com.example.assignmentfour;

import java.io.*;
import java.sql.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@WebServlet("/LoginProcess")
public class LoginProcess extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        try {
            // Connect to database
            Class.forName("org.postgresql.Driver");
            Connection connection =
                    DriverManager.getConnection(System.getenv("DB_URL"),
                            System.getenv("DB_USERNAME"), System.getenv("DB_PASSWORD"));

            // Print to console that the connection was successful
            System.out.println("Connection successful!");

            // Get the user inputs from the login form
            String inputUsername = request.getParameter("username");
            String inputPassword = request.getParameter("password");

            // SQL query
            String sql = "SELECT * FROM accounts WHERE username = ?";

            // Execute the SQL query by filling in the ? with the users username
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, inputUsername);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean isValid = false;

            // Create BCryptPasswordEncoder object
            BCryptPasswordEncoder checkPassword = new BCryptPasswordEncoder();

            // Loop through results
            while(resultSet.next()) {
                // If user exists go to the welcome page
                if (resultSet.getString("username").equals(inputUsername)
                        && checkPassword.matches(inputPassword, resultSet.getString("password"))) {
                    HttpSession session = request.getSession();
                    preparedStatement.close();
                    resultSet.close();
                    connection.close();
                    session.setAttribute("User", "User");
                    response.sendRedirect("./welcome.jsp");
                    isValid = true;
                    break;
                }
            }
            // If the user is not valid go to the login page is display an error
            if(!isValid) {
                HttpSession session = request.getSession();
                preparedStatement.close();
                resultSet.close();
                connection.close();
                session.setAttribute("Error", "Error");
                response.sendRedirect("./index.jsp");
            }
        }
        // Catch any SQLExceptions
        catch (SQLException e) {
            System.out.println("SQL Error: " + e);
        }
        // Catch any generic Exceptions
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}