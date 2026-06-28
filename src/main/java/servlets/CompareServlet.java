package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MatchupDAO;
import dao.TeamDAO;
import model.Team;
import model.User;
import prediction.Prediction;
import prediction.Predictor;

/**
 * Handles team comparison requests and generates matchup predictions.
 *
 * This servlet retrieves the selected teams, stores them in the user's
 * session, generates a prediction when both teams have been selected,
 * and saves the prediction to the user's history if they are logged in.
 *
 * @author Kevin Lightfoot
 */
@WebServlet("/CompareServlet")
public class CompareServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes team selection requests from the comparison page.
     *
     * When both teams have been selected, a prediction is generated and
     * displayed. Logged-in users also have their prediction saved to
     * their matchup history.
     *
     * @param request  Contains the selected team and comparison slot
     * @param response Sends the completed comparison back to the client
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Determine which comparison slot is being populated and
        // retrieve the selected team's identifier.
        String slot = request.getParameter("slot");
        int teamId = Integer.parseInt(request.getParameter("teamId"));

        try {

            TeamDAO teamDAO = new TeamDAO();

            // Retrieve the selected team's information from the database.
            Team team = teamDAO.getTeam(teamId);

            // Store the selected team in the user's session until both
            // teams have been chosen.
            if ("1".equals(slot)) {
                request.getSession().setAttribute("compareTeam1", team);
            } else {
                request.getSession().setAttribute("compareTeam2", team);
            }

            Team compareTeam1 =
                    (Team) request.getSession().getAttribute("compareTeam1");

            Team compareTeam2 =
                    (Team) request.getSession().getAttribute("compareTeam2");

            // Generate a prediction once both comparison teams are available.
            if (compareTeam1 != null && compareTeam2 != null) {

                Prediction prediction =
                        Predictor.predict(compareTeam1, compareTeam2);

                request.setAttribute("prediction", prediction);

                // Save the prediction for authenticated users.
                User user =
                        (User) request.getSession().getAttribute("user");

                if (user != null) {

                    MatchupDAO matchupDAO = new MatchupDAO();

                    matchupDAO.savePrediction(user.getUserId(),
                            compareTeam1,
                            compareTeam2,
                            prediction.getWinner(),
                            prediction.getReasoning(),
                            prediction.getTeam1Score(),
                            prediction.getTeam2Score(),
                            prediction.getConfidence());
                }
            }

            // Return to the comparison page with the selected teams and,
            // if available, the generated prediction.
            request.getRequestDispatcher("/compare.jsp")
                   .forward(request, response);

        } catch (SQLException e) {

            // Convert database exceptions into servlet exceptions.
            throw new ServletException(e);
        }
    }
}