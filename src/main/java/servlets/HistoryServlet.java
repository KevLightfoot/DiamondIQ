package servlets;

import dao.MatchupDAO;
import dao.UserDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

/**
 * Displays the authenticated user's saved matchup prediction history.
 *
 * This servlet verifies that the user is logged in, retrieves their
 * saved matchup history from the database, and forwards the results
 * to the history page.
 *
 * @author Kevin Lightfoot
 */
@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Retrieves the logged-in user's matchup history and forwards it
     * to the history page for display.
     *
     * @param request Contains the current user session
     * @param response Sends the matchup history page to the client
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // Retrieve the username stored in the current session.
            String username =
                    (String) request.getSession().getAttribute("username");

            // Prevent unauthenticated users from viewing matchup history.
            if (username == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            UserDAO userDAO = new UserDAO();

            // Retrieve the user's unique identifier.
            int userId = userDAO.getUserId(username);

            MatchupDAO dao = new MatchupDAO();

            // Load the user's saved matchup history.
            request.setAttribute(
                    "history",
                    dao.getHistory(userId));

            // Forward the results to the history page.
            request.getRequestDispatcher("/history.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            // Convert unexpected exceptions into servlet exceptions.
            throw new ServletException(e);

        }

    }

}