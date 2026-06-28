package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TeamDAO;
import model.Team;

/**
 * Displays detailed information for a selected MLB team.
 *
 * This servlet retrieves the requested team's information from the
 * database and forwards it to the team details page.
 *
 * @author Kevin Lightfoot
 */
@WebServlet("/TeamServlet")
public class TeamServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Retrieves the selected team and forwards its information
     * to the team details page.
     *
     * @param request Contains the requested team ID
     * @param response Sends the team information to the client
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the selected team's identifier from the request.
        String teamId = request.getParameter("teamId");
        int id = Integer.parseInt(teamId);

        try {

            TeamDAO teamDAO = new TeamDAO();

            // Retrieve the selected team's information from the database.
            Team team = teamDAO.getTeam(id);

            // Make the team available to the JSP for display.
            request.setAttribute("team", team);

            // Forward the request to the team details page.
            request.getRequestDispatcher("/team.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}