<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Matchup" %>

<%-- ==========================================================
     Retrieve request attributes
     ========================================================== --%>
<%
    List<Matchup> history =
            (List<Matchup>) request.getAttribute("history");

    SimpleDateFormat sdf =
            new SimpleDateFormat("MMM d, yyyy ' | ' h:mm a");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Prediction History</title>
    <link rel="stylesheet" href="css/history.css">
</head>
<body>

<div class="history-container">
    <%-- ======================================================
         Page Header
         ====================================================== --%>
         
    <div class="history-header">
        <h1>Prediction History</h1>

        <p>
            View every DiamondIQ prediction you've generated.
        </p>
    </div>

    <%-- ======================================================
         Prediction History Table
         ====================================================== --%>
         
    <div class="history-table-card">
        <h2>Prediction History</h2>
        
        <table class="history-table">
            <tr>
                <th>Date</th>
                <th>Matchup</th>
                <th>Predicted Winner</th>
                <th>Action</th>
            </tr>

<%
if (history != null) {
    for (Matchup m : history) {
%>

            <tr>
                <td>
                    <%= sdf.format(m.getCreatedAt()) %>
                </td>

                <td>
                    <%= m.getTeamOne().getTeamName() %>
                    vs
                    <%= m.getTeamTwo().getTeamName() %>
                </td>

                <td>
                    <%= m.getPredictedWinner().getTeamName() %>
                </td>

                <td>
                    <a class="view-btn"
                       href="HistoryDetailServlet?id=<%= m.getMatchupId() %>">
                        View Analysis
                    </a>
                </td>
            </tr>
<%
    }
}
%>

        </table>
    </div>

    <%-- ======================================================
         Navigation
         ====================================================== --%>

    <a class="back-link"
       href="dashboard.jsp">
        Back to Dashboard
    </a>

</div>
</body>
</html>
