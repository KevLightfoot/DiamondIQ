package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Initializes a new team comparison session.
 *
 * This servlet clears any previously selected teams and prediction
 * data before forwarding the user to a fresh comparison page.
 *
 * @author Kevin Lightfoot
 */
@WebServlet("/CompareStartServlet")
public class CompareStartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Resets the current comparison and displays a blank comparison page.
     *
     * @param request  Contains the current user session
     * @param response Sends the refreshed comparison page to the client
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Remove any previously selected comparison teams from the session.
        request.getSession().removeAttribute("compareTeam1");
        request.getSession().removeAttribute("compareTeam2");

        // Forward the user to a fresh comparison page.
        request.getRequestDispatcher("/compare.jsp")
               .forward(request, response);
    }
}