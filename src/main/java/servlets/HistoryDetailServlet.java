package servlets;

import dao.MatchupDAO;
import model.Matchup;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Displays the details of a previously generated matchup prediction.
 *
 * This servlet retrieves a saved matchup from the database using its
 * unique identifier and forwards the information to the history detail page.
 *
 * @author Kevin Lightfoot
 */
@WebServlet("/HistoryDetailServlet")
public class HistoryDetailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Retrieves a saved matchup prediction and forwards it to the
     * history detail page for display.
     *
     * @param request Contains the matchup ID to retrieve
     * @param response Sends the matchup details to the client
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // Retrieve the requested matchup identifier.
            int matchupId =
                    Integer.parseInt(
                    request.getParameter("id"));

            MatchupDAO dao = new MatchupDAO();

            // Load the saved matchup from the database.
            Matchup matchup =
                    dao.getMatchup(matchupId);

            request.setAttribute(
                    "matchup",
                    matchup);

            // Forward the matchup information to the detail page.
            request.getRequestDispatcher(
                    "/historyDetail.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            // Wrap any unexpected exceptions as servlet exceptions.
            throw new ServletException(e);

        }
    }
}