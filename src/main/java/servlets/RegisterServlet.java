package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;

/**
 * Handles new user registration requests.
 *
 * This servlet validates registration input, creates new user accounts,
 * and returns users to the registration page when validation or database
 * errors occur.
 *
 * @author Kevin Lightfoot
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes registration requests submitted from the registration page.
     *
     * @param request Contains the submitted registration information
     * @param response Sends the appropriate response to the client
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the submitted registration information.
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");

        // Validate all required registration fields.
        if (username.trim().equals("")) {

            returnToRegister(request, response,
                    username, email,
                    "Username is required");
            return;

        } else if (email.trim().equals("")) {

            returnToRegister(request, response,
                    username, email,
                    "Email is required");
            return;

        } else if (password.trim().equals("")) {

            returnToRegister(request, response,
                    username, email,
                    "Password is required");
            return;

        } else if (confirm.trim().equals("")) {

            returnToRegister(request, response,
                    username, email,
                    "Please confirm your password");
            return;

        } else if (!password.trim().equals(confirm.trim())) {

            returnToRegister(request, response,
                    username, email,
                    "Passwords do not match");
            return;
        }

        // Validation successful. Attempt to create the new account.
        try {

            UserDAO userDAO = new UserDAO();

            // Ensure the requested username is available.
            if (!userDAO.usernameExists(username)) {

                // Create the new user account and redirect to the login page.
                userDAO.createUser(username, email, password);

                response.sendRedirect("login.jsp");

            } else {

                returnToRegister(request, response,
                        username, email,
                        "Username already exists");

                return;
            }

        } catch (SQLException e) {

            returnToRegister(request, response,
                    username, email,
                    "Database error.");
        }
    }

    /**
     * Displays the registration page.
     *
     * @param request Current HTTP request
     * @param response Current HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/register.jsp")
                .forward(request, response);
    }

    /**
     * Returns the user to the registration page while preserving
     * previously entered form data and displaying an error message.
     *
     * @param request Current HTTP request
     * @param response Current HTTP response
     * @param username Username entered by the user
     * @param email Email entered by the user
     * @param error Error message to display
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    private void returnToRegister(HttpServletRequest request,
            HttpServletResponse response,
            String username,
            String email,
            String error)
            throws ServletException, IOException {

        // Preserve user input and display the validation message.
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("error", error);

        // Return the user to the registration page.
        request.getRequestDispatcher("/register.jsp")
                .forward(request, response);
    }

}
