<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- ==========================================================
     Retrieve session and request attributes
     ========================================================== --%>
<%
    model.Team compareTeam1 =
            (model.Team) session.getAttribute("compareTeam1");

    model.Team compareTeam2 =
            (model.Team) session.getAttribute("compareTeam2");

    prediction.Prediction prediction =
            (prediction.Prediction) request.getAttribute("prediction");

    java.util.List<model.Team> teams =
            (java.util.List<model.Team>) request.getAttribute("teams");

    String slot = (String) request.getAttribute("slot");
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>DiamondIQ | Compare Teams</title>

    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/compare.css">

</head>

<body class="compare-body">

<div class="compare-container">

    <%-- ======================================================
         Page Header
         ====================================================== --%>

    <div class="compare-header">

        <h1>Compare Teams</h1>

        <p>
            Select two MLB teams and let DiamondIQ predict the matchup.
        </p>

    </div>

    <%-- ======================================================
         Team Selection
         ====================================================== --%>

    <div class="team-selection">

        <%-- Team 1 Selection --%>

        <div class="team-card">

            <h3>Team 1</h3>

<%
if (compareTeam1 == null) {
%>

            <img class="team-logo"
                 src="images/logos/baseball.jpg">

            <form action="CompareSearchServlet" method="post">

                <input type="hidden"
                       name="slot"
                       value="1">

                <input type="text"
                       name="teamName"
                       placeholder="Search Team">

                <button>Search</button>

            </form>

<%
} else {
%>

            <img class="team-logo"
                 src="<%= compareTeam1.getLogoPath() %>">

            <h2><%= compareTeam1.getTeamName() %></h2>

            <p><%= compareTeam1.getCity() %></p>

<%
}
%>

        </div>

        <div class="vs-text">

            VS

        </div>

        <%-- Team 2 Selection --%>

        <div class="team-card">

            <h3>Team 2</h3>

<%
if (compareTeam2 == null) {
%>

            <img class="team-logo"
                 src="images/logos/baseball.jpg">

            <form action="CompareSearchServlet" method="post">

                <input type="hidden"
                       name="slot"
                       value="2">

                <input type="text"
                       name="teamName"
                       placeholder="Search Team">

                <button>Search</button>

            </form>

<%
} else {
%>

            <img class="team-logo"
                 src="<%= compareTeam2.getLogoPath() %>">

            <h2><%= compareTeam2.getTeamName() %></h2>

            <p><%= compareTeam2.getCity() %></p>

<%
}
%>

        </div>

    </div>

    <p style="color:red;">${error}</p>

    <%-- ======================================================
         Team Search Results
         ====================================================== --%>

<%
if (teams != null) {
%>

    <hr>

    <h3>Select a Team</h3>

<%
    for (model.Team team : teams) {
%>

    <p>

        <a href="CompareServlet?slot=<%= slot %>&teamId=<%= team.getTeamId() %>">
            <%= team.getTeamName() %>
        </a>

        (<%= team.getCity() %>)

    </p>

<%
    }
}
%>

<%-- ======================================================
     Team Comparison Table
     ====================================================== --%>

<%
if (compareTeam1 != null && compareTeam2 != null) {
%>

<h2>

    <%= compareTeam1.getTeamName() %> vs
    <%= compareTeam2.getTeamName() %>

</h2>

<table>

    <tr>
        <th>Statistic</th>
        <th><%= compareTeam1.getTeamName() %></th>
        <th><%= compareTeam2.getTeamName() %></th>
    </tr>

    <tr>
        <td>Wins</td>
        <td class="<%= compareTeam1.getWins() > compareTeam2.getWins() ? "better-stat" : "" %>">
            <%= compareTeam1.getWins() %>
        </td>
        <td class="<%= compareTeam2.getWins() > compareTeam1.getWins() ? "better-stat" : "" %>">
            <%= compareTeam2.getWins() %>
        </td>
    </tr>

    <tr>
        <td>Losses</td>
        <td class="<%= compareTeam1.getLosses() < compareTeam2.getLosses() ? "better-stat" : "" %>">
            <%= compareTeam1.getLosses() %>
        </td>
        <td class="<%= compareTeam2.getLosses() < compareTeam1.getLosses() ? "better-stat" : "" %>">
            <%= compareTeam2.getLosses() %>
        </td>
    </tr>

    <tr>
        <td>Runs Scored</td>
        <td class="<%= compareTeam1.getRunsScored() > compareTeam2.getRunsScored() ? "better-stat" : "" %>">
            <%= compareTeam1.getRunsScored() %>
        </td>
        <td class="<%= compareTeam2.getRunsScored() > compareTeam1.getRunsScored() ? "better-stat" : "" %>">
            <%= compareTeam2.getRunsScored() %>
        </td>
    </tr>

    <tr>
        <td>Batting Average</td>
        <td class="<%= compareTeam1.getBattingAverage() > compareTeam2.getBattingAverage() ? "better-stat" : "" %>">
            <%= compareTeam1.getBattingAverage() %>
        </td>
        <td class="<%= compareTeam2.getBattingAverage() > compareTeam1.getBattingAverage() ? "better-stat" : "" %>">
            <%= compareTeam2.getBattingAverage() %>
        </td>
    </tr>

    <tr>
        <td>On Base %</td>
        <td class="<%= compareTeam1.getOnBasePercentage() > compareTeam2.getOnBasePercentage() ? "better-stat" : "" %>">
            <%= compareTeam1.getOnBasePercentage() %>
        </td>
        <td class="<%= compareTeam2.getOnBasePercentage() > compareTeam1.getOnBasePercentage() ? "better-stat" : "" %>">
            <%= compareTeam2.getOnBasePercentage() %>
        </td>
    </tr>

    <tr>
        <td>Slugging %</td>
        <td class="<%= compareTeam1.getSluggingPercentage() > compareTeam2.getSluggingPercentage() ? "better-stat" : "" %>">
            <%= compareTeam1.getSluggingPercentage() %>
        </td>
        <td class="<%= compareTeam2.getSluggingPercentage() > compareTeam1.getSluggingPercentage() ? "better-stat" : "" %>">
            <%= compareTeam2.getSluggingPercentage() %>
        </td>
    </tr>

    <tr>
        <td>OPS</td>
        <td class="<%= compareTeam1.getOps() > compareTeam2.getOps() ? "better-stat" : "" %>">
            <%= compareTeam1.getOps() %>
        </td>
        <td class="<%= compareTeam2.getOps() > compareTeam1.getOps() ? "better-stat" : "" %>">
            <%= compareTeam2.getOps() %>
        </td>
    </tr>

    <tr>
        <td>ERA</td>
        <td class="<%= compareTeam1.getEra() < compareTeam2.getEra() ? "better-stat" : "" %>">
            <%= compareTeam1.getEra() %>
        </td>
        <td class="<%= compareTeam2.getEra() < compareTeam1.getEra() ? "better-stat" : "" %>">
            <%= compareTeam2.getEra() %>
        </td>
    </tr>

    <tr>
        <td>Runs Allowed</td>
        <td class="<%= compareTeam1.getRunsAllowed() < compareTeam2.getRunsAllowed() ? "better-stat" : "" %>">
            <%= compareTeam1.getRunsAllowed() %>
        </td>
        <td class="<%= compareTeam2.getRunsAllowed() < compareTeam1.getRunsAllowed() ? "better-stat" : "" %>">
            <%= compareTeam2.getRunsAllowed() %>
        </td>
    </tr>

    <tr>
        <td>WHIP</td>
        <td class="<%= compareTeam1.getWhip() < compareTeam2.getWhip() ? "better-stat" : "" %>">
            <%= compareTeam1.getWhip() %>
        </td>
        <td class="<%= compareTeam2.getWhip() < compareTeam1.getWhip() ? "better-stat" : "" %>">
            <%= compareTeam2.getWhip() %>
        </td>
    </tr>

    <tr>
        <td>Strikeouts</td>
        <td class="<%= compareTeam1.getStrikeoutsPerGame() > compareTeam2.getStrikeoutsPerGame() ? "better-stat" : "" %>">
            <%= compareTeam1.getStrikeoutsPerGame() %>
        </td>
        <td class="<%= compareTeam2.getStrikeoutsPerGame() > compareTeam1.getStrikeoutsPerGame() ? "better-stat" : "" %>">
            <%= compareTeam2.getStrikeoutsPerGame() %>
        </td>
    </tr>

    <tr>
        <td>Home Runs</td>
        <td class="<%= compareTeam1.getHomeRuns() > compareTeam2.getHomeRuns() ? "better-stat" : "" %>">
            <%= compareTeam1.getHomeRuns() %>
        </td>
        <td class="<%= compareTeam2.getHomeRuns() > compareTeam1.getHomeRuns() ? "better-stat" : "" %>">
            <%= compareTeam2.getHomeRuns() %>
        </td>
    </tr>

</table>

<%
}
%>

<%-- ======================================================
     Prediction Results
     ====================================================== --%>

<%
if (prediction != null) {
%>

<hr>

<div class="prediction-card">

    <h2>🏆 DiamondIQ Prediction</h2>

    <img class="winner-logo"
         src="<%= prediction.getWinner().getLogoPath() %>">

    <h1 class="winner-name">
        <%= prediction.getWinner().getTeamName() %>
    </h1>

    <div class="confidence-number">
        <%= prediction.getConfidence() %>%
    </div>

    <p class="confidence-label">
        Prediction Confidence
    </p>

    <div class="score-grid">

        <div>

            <h3><%= compareTeam1.getAbbreviation() %></h3>

            <div class="score">
                <%= String.format("%.2f", prediction.getTeam1Score()) %>
            </div>

        </div>

        <div>

            <h3><%= compareTeam2.getAbbreviation() %></h3>

            <div class="score">
                <%= String.format("%.2f", prediction.getTeam2Score()) %>
            </div>

        </div>

    </div>

</div>

<%-- ======================================================
     DiamondIQ Analysis
     ====================================================== --%>

<div class="analysis-card">

    <h2>DiamondIQ Analysis</h2>

<%
String[] reasons = prediction.getReasoning().split("\\n");

for (int i = 0; i < reasons.length; i++) {

    String reason = reasons[i];

    if (reason.trim().isEmpty()) {
        continue;
    }

    if (i == 0) {
%>

    <h3 class="analysis-heading">

        <%= reason %>

    </h3>

<%
    } else {
%>

    <div class="analysis-item">

        <span class="analysis-check">✓</span>

        <span><%= reason %></span>

    </div>

<%
    }
}
%>

</div>

<%
}
%>

<%-- ======================================================
     Navigation
     ====================================================== --%>

<br><br>

<a href="dashboard.jsp">Back to Dashboard</a>

</div>

</body>
</html>

