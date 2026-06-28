package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TeamDAO;
import model.Team;

/**
 * Handles team search requests submitted from the dashboard.
 *
 * This servlet validates the user's search input, retrieves matching
 * MLB teams from the database, and forwards the results back to the
 * dashboard for display.
 *
 * @author Kevin Lightfoot
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes team search requests.
     *
     * @param request Contains the submitted team search term
     * @param response Sends the search results back to the client
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the team name entered by the user.
        String search = request.getParameter("teamName");

        // Ensure a search term was provided.
        if (search.trim().equals("")) {

            returnToDash(request, response,
                    "Please enter a team name.");

            return;

        } else {

            try {

                TeamDAO teamDAO = new TeamDAO();

                // Search the database for matching teams.
                List<Team> teams = teamDAO.searchTeams(search);

                request.setAttribute("teams", teams);

                returnToDash(request, response, "");

            } catch (SQLException e) {

                returnToDash(request, response,
                        "Database error.");
            }
        }
    }

    /**
     * Returns the user to the dashboard while preserving search results
     * and displaying any validation or database error messages.
     *
     * @param request Current HTTP request
     * @param response Current HTTP response
     * @param error Error message to display
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    private void returnToDash(HttpServletRequest request,
            HttpServletResponse response,
            String error)
            throws ServletException, IOException {

        request.setAttribute("error", error);

        request.getRequestDispatcher("/dashboard.jsp")
                .forward(request, response);
    }

}