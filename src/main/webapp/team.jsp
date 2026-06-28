<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- ==========================================================
     Retrieve request attributes
     ========================================================== --%>
<%
    model.Team team =
            (model.Team) request.getAttribute("team");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= team.getTeamName() %></title>
    <link rel="stylesheet" href="css/team.css">
</head>

<body class="team-body">

<div class="team-container">

    <%-- ======================================================
         Team Header
         ====================================================== --%>

    <div class="team-hero">
        <img class="team-logo"
             src="<%= team.getLogoPath() %>"
             alt="<%= team.getTeamName() %> Logo">

        <h1>
            <%= team.getTeamName() %>
        </h1>

        <p class="team-location">
            <%= team.getCity() %>
        </p>

        <p class="team-league">
            <%= team.getLeague() %> League
            <%= team.getDivision() %>
        </p>
    </div>

    <%-- ======================================================
         Featured Statistics
         ====================================================== --%>

    <div class="stat-grid">
        <div class="stat-card">
            <div class="stat-value"><%= team.getWins() %></div>
            <div class="stat-label">Wins</div>
        </div>

        <div class="stat-card">
            <div class="stat-value"><%= team.getLosses() %></div>
            <div class="stat-label">Losses</div>
        </div>

        <div class="stat-card">
            <div class="stat-value"><%= team.getOps() %></div>
            <div class="stat-label">OPS</div>
        </div>

        <div class="stat-card">
            <div class="stat-value"><%= team.getEra() %></div>
            <div class="stat-label">ERA</div>
        </div>

        <div class="stat-card">
            <div class="stat-value"><%= team.getWhip() %></div>
            <div class="stat-label">WHIP</div>
        </div>

        <div class="stat-card">
            <div class="stat-value"><%= team.getHomeRuns() %></div>
            <div class="stat-label">Home Runs</div>
        </div>
    </div>

    <%-- ======================================================
         Complete Statistics
         ====================================================== --%>

    <div class="table-card">
        <h2 class="stats-heading">
            Complete Team Statistics
        </h2>

        <table class="stats-table">

            <tr>
                <td>Wins</td>
                <td><%= team.getWins() %></td>
            </tr>

            <tr>
                <td>Losses</td>
                <td><%= team.getLosses() %></td>
            </tr>

            <tr>
                <td>Runs Scored</td>
                <td><%= team.getRunsScored() %></td>
            </tr>

            <tr>
                <td>Batting Average</td>
                <td><%= team.getBattingAverage() %></td>
            </tr>

            <tr>
                <td>On Base %</td>
                <td><%= team.getOnBasePercentage() %></td>
            </tr>

            <tr>
                <td>Slugging %</td>
                <td><%= team.getSluggingPercentage() %></td>
            </tr>

            <tr>
                <td>OPS</td>
                <td><%= team.getOps() %></td>
            </tr>

            <tr>
                <td>ERA</td>
                <td><%= team.getEra() %></td>
            </tr>

            <tr>
                <td>WHIP</td>
                <td><%= team.getWhip() %></td>
            </tr>

            <tr>
                <td>Strikeouts/Game</td>
                <td><%= team.getStrikeoutsPerGame() %></td>
            </tr>

            <tr>
                <td>Home Runs</td>
                <td><%= team.getHomeRuns() %></td>
            </tr>

            <tr>
                <td>Runs Allowed</td>
                <td><%= team.getRunsAllowed() %></td>
            </tr>

        </table>
    </div>

    <%-- ======================================================
         Team Actions
         ====================================================== --%>

    <div class="action-buttons">

        <form action="FavoriteServlet" method="post">
            <input type="hidden"
                   name="teamId"
                   value="<%= team.getTeamId() %>">

            <button class="primary-btn">
                Set Favorite Team
            </button>
        </form>

        <form action="CompareServlet" method="get">

            <input type="hidden"
                   name="slot"
                   value="1">

            <input type="hidden"
                   name="teamId"
                   value="<%= team.getTeamId() %>">

            <button class="secondary-btn">
                Compare This Team
            </button>
        </form>
    </div>

<%
if (request.getAttribute("message") != null) {
%>

    <%-- Success Message --%>

    <p class="success-message">
        <%= request.getAttribute("message") %>
    </p>

<%
}
%>

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
