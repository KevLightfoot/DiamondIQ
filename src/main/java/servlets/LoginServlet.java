package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;

/**
 * Handles user authentication requests.
 *
 * This servlet validates login credentials, creates the user's session,
 * and redirects authenticated users to the dashboard. Invalid login
 * attempts are returned to the login page with an appropriate error message.
 *
 * @author Kevin Lightfoot
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes login requests submitted from the login page.
     *
     * @param request Contains the submitted login credentials
     * @param response Sends the appropriate response to the client
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the submitted login credentials.
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        // Validate that all required fields have been provided.
        if (username.equals("")) {

            returnToLogin(request, response,
                    username,
                    "Email is Required");

        } else if (password.equals("")) {

            returnToLogin(request, response,
                    username,
                    "Password is Required");

        } else {

            try {

                UserDAO userDAO = new UserDAO();

                // Verify the supplied username and password.
                if (userDAO.validateLogin(username, password)) {

                    User user = userDAO.getUser(username);

                    // Store the authenticated user's information in the session.
                    session.setAttribute("user", user);
                    session.setAttribute("username", username);

                    response.sendRedirect("dashboard.jsp");

                } else {

                    returnToLogin(request, response,
                            username,
                            "Invalid Username or Password");
                }

            } catch (SQLException e) {

                returnToLogin(request, response,
                        username,
                        "Database Error");
            }
        }
    }

    /**
     * Returns the user to the login page while preserving the entered
     * username and displaying an error message.
     *
     * @param request Current HTTP request
     * @param response Current HTTP response
     * @param username Username entered by the user
     * @param error Error message to display
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    private void returnToLogin(HttpServletRequest request,
            HttpServletResponse response,
            String username,
            String error)
            throws ServletException, IOException {

        // Preserve the submitted username and display the error message.
        request.setAttribute("username", username);
        request.setAttribute("error", error);

        // Forward the request back to the login page.
        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }

}