package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TeamDAO;
import dao.UserDAO;
import model.Team;

/**
 * Handles requests for updating a user's favorite MLB team.
 *
 * This servlet validates that the user is logged in, updates the
 * favorite team stored in the database, and returns the user to
 * the selected team's information page.
 *
 * @author Kevin Lightfoot
 */
@WebServlet("/FavoriteServlet")
public class FavoriteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes requests to update the authenticated user's
     * favorite team.
     *
     * @param request  Contains the selected team ID
     * @param response Sends the updated team page back to the client
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the selected team and the currently logged-in user.
        String teamId = request.getParameter("teamId");
        int id = Integer.parseInt(teamId);

        String username =
                (String) request.getSession().getAttribute("username");

        try {

            // Ensure the user is authenticated before allowing updates.
            if (username == null) {

                returnToPage(request, response, id,
                        "You must be logged in to set a favorite team.");

                return;
            }

            // Update the user's favorite team in the database.
            UserDAO userDAO = new UserDAO();
            userDAO.setFavoriteTeam(username, id);

            model.User user =
                    (model.User) request.getSession().getAttribute("user");

            if (user != null) {
                user.setFavoriteTeamId(id);
            }
            
            returnToPage(request, response, id,
                    "Favorite team updated successfully!");

        } catch (SQLException e) {

            // Display a generic error message if the update fails.
            returnToPage(request, response, id,
                    "Database error.");
        }
    }

    /**
     * Reloads the selected team's information page with a status message.
     *
     * @param request Current HTTP request
     * @param response Current HTTP response
     * @param teamId ID of the selected team
     * @param message Status message to display to the user
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    private void returnToPage(HttpServletRequest request,
            HttpServletResponse response,
            int teamId,
            String message) throws ServletException, IOException {

        try {

            TeamDAO teamDAO = new TeamDAO();

            // Retrieve the team's information for redisplay.
            Team team = teamDAO.getTeam(teamId);

            request.setAttribute("team", team);
            request.setAttribute("message", message);

            request.getRequestDispatcher("/team.jsp")
                   .forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(e);
        }
    }
}