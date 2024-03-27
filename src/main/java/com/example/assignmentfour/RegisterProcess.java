/*
  User: Luke
  Date: 2024-03-21
  Time: 5:53 p.m.
*/

package com.example.assignmentfour;

import java.io.*;
import java.sql.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@WebServlet("/RegisterProcess")
public class RegisterProcess extends HttpServlet {
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

            // Get the user inputs from the registration form
            String inputUsername = request.getParameter("registerUsername");
            String inputPassword = request.getParameter("registerPassword");

            // String that will hold error messages that might need to be displayed to the user
            String errorMessage = "";

            // Check if the entered username is the proper length
            if(inputUsername.length() < 2 || inputUsername.length() > 25) {
                errorMessage += "Username must be 2-25 characters. <br/>";
            }

            // Check if the entered password is the proper length
            if(inputPassword.length() < 7 || inputPassword.length() > 25) {
                errorMessage += "Password must be 7-25 characters. <br/>";
            }

            // Query database to get all accounts to see if the username entered by the user already exists
            String sql = "SELECT * FROM accounts";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate through results
            while(resultSet.next()) {

                // Check to see if username already exists in the database
                if(inputUsername.equals(resultSet.getString("username"))) {
                    errorMessage += "Username already exists.";
                    break;
                }
            }

            preparedStatement.close();
            resultSet.close();

            // Check to see if there are any error messages in the errorMessage string
            if(errorMessage.equals("")) {

                // If there are no errors at the account to the database
                BCryptPasswordEncoder encodePassword = new BCryptPasswordEncoder();
                inputPassword = encodePassword.encode(inputPassword);
                String sqlTwo = "INSERT INTO accounts (username, password) VALUES (?, ?)";
                PreparedStatement preparedStatementTwo = connection.prepareStatement(sqlTwo);
                preparedStatementTwo.setString(1, inputUsername);
                preparedStatementTwo.setString(2, inputPassword);
                preparedStatementTwo.executeUpdate();
                HttpSession session = request.getSession();
                session.setAttribute("Registered", "Registered");
                preparedStatementTwo.close();
                connection.close();
                response.sendRedirect("./index.jsp");
            }
            else {
                // Display an error message to the user
                HttpSession session = request.getSession();
                connection.close();
                session.setAttribute("Error", "Error");
                session.setAttribute("ErrorMessage", errorMessage);
                response.sendRedirect("./registration.jsp");
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
