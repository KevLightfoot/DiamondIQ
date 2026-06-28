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
 * Handles team search requests from the comparison page.
 *
 * This servlet searches the database for MLB teams matching the user's
 * search criteria and forwards the results back to the comparison page
 * for team selection.
 *
 * @author Kevin Lightfoot
 */
@WebServlet("/CompareSearchServlet")
public class CompareSearchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes team search requests submitted from compare.jsp.
     *
     * @param request  Contains the search term and team selection slot
     * @param response Sends the search results back to the client
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the user's search term and identify which team slot
        // (Team 1 or Team 2) is being populated.
        String search = request.getParameter("teamName");
        String slot = request.getParameter("slot");

        // Ensure a team name was entered before performing the search.
        if (search == null || search.trim().equals("")) {

            request.setAttribute("error", "Please enter a team name.");
            request.setAttribute("slot", slot);

            request.getRequestDispatcher("/compare.jsp")
                    .forward(request, response);

            return;
        }

        try {

            TeamDAO dao = new TeamDAO();

            // Retrieve all teams matching the user's search term.
            List<Team> teams = dao.searchTeams(search);

            // Make the search results available to the JSP.
            request.setAttribute("teams", teams);
            request.setAttribute("slot", slot);

            request.getRequestDispatcher("/compare.jsp")
                    .forward(request, response);

        } catch (SQLException e) {

            // Wrap database exceptions as servlet exceptions for centralized handling.
            throw new ServletException(e);
        }

    }

}