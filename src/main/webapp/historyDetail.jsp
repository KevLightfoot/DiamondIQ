<%@ page import="model.Matchup"
    language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- ==========================================================
     Retrieve request attributes
     ========================================================== --%>
<%
    Matchup matchup =
            (Matchup) request.getAttribute("matchup");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Prediction Analysis</title>
    <link rel="stylesheet" href="css/historyDetail.css">
</head>

<body>

<div class="detail-container">
    <%-- ======================================================
         Page Header
         ====================================================== --%>

    <div class="hero-card">
        <h1>Prediction Analysis</h1>

        <h2>
            <%= matchup.getTeamOne().getTeamName() %>
            vs
            <%= matchup.getTeamTwo().getTeamName() %>
        </h2>
    </div>

    <%-- ======================================================
         Team Comparison
         ====================================================== --%>

    <div class="team-row">
        <div class="team-card">
            <h3>Team 1</h3>

            <img class="team-logo"
                 src="<%= matchup.getTeamOne().getLogoPath() %>"
                 alt="<%= matchup.getTeamOne().getTeamName() %>">

            <h2>
                <%= matchup.getTeamOne().getTeamName() %>
            </h2>

            <p>
                <%= matchup.getTeamOne().getCity() %>
            </p>
        </div>

        <div class="vs-text">
            VS
        </div>

        <div class="team-card">
            <h3>Team 2</h3>

            <img class="team-logo"
                 src="<%= matchup.getTeamTwo().getLogoPath() %>"
                 alt="<%= matchup.getTeamTwo().getTeamName() %>">

            <h2>
                <%= matchup.getTeamTwo().getTeamName() %>
            </h2>
            
            <p>
                <%= matchup.getTeamTwo().getCity() %>
            </p>
        </div>
    </div>

    <%-- ======================================================
         Predicted Winner
         ====================================================== --%>

    <div class="winner-card">
        <h3>Predicted Winner</h3>

        <img class="winner-logo"
             src="<%= matchup.getPredictedWinner().getLogoPath() %>"
             alt="<%= matchup.getPredictedWinner().getTeamName() %>">

        <div class="winner-name">
            <%= matchup.getPredictedWinner().getTeamName() %>
        </div>
    </div>

    <%-- ======================================================
         DiamondIQ Analysis
         ====================================================== --%>

    <div class="analysis-card">
        <h2>DiamondIQ Analysis</h2>

        <pre class="analysis-text"><%= matchup.getAnalysisSummary() %></pre>
    </div>

    <%-- ======================================================
         Navigation
         ====================================================== --%>

    <a class="back-link"
       href="HistoryServlet">
       Back to Prediction History
    </a>
</div>

</body>
</html>

