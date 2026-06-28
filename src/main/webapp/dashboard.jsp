<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="dao.TeamDAO" %>
<%@ page import="util.LogoUtil" %>

<%-- ==========================================================
     Retrieve session and request attributes
     ========================================================== --%>
<%
    model.User user =
            (model.User) session.getAttribute("user");

    model.Team favoriteTeam = null;

    if (user != null && user.getFavoriteTeamId() != null) {

        TeamDAO dao = new TeamDAO();

        favoriteTeam = dao.getTeamBasic(user.getFavoriteTeamId());
    }

    java.util.List<model.Team> teams =
            (java.util.List<model.Team>) request.getAttribute("teams");
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>DiamondIQ Dashboard</title>

    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/dashboard.css">

</head>

<body class="dashboard-body">

<%-- ==========================================================
     User Panel
     ========================================================== --%>

<div class="user-panel">

    <div class="user-info">

        <div class="user-avatar">

<%
if (favoriteTeam != null) {
%>

            <img class="profile-logo"
                 src="<%= LogoUtil.getLogo(favoriteTeam.getAbbreviation()) %>"
                 alt="Favorite Team">

<%
} else {
%>

            <img class="profile-logo"
                 src="images/logos/baseball.jpg"
                 alt="Baseball">

<%
}
%>

        </div>

        <div>

            <div class="welcome-text">
                Welcome
            </div>

            <div class="username">
                <%= user.getUsername() %>
            </div>

        </div>

    </div>

    <form action="LogoutServlet" method="post">

        <button class="logout-btn">
            Logout
        </button>

    </form>

</div>

<%-- ==========================================================
     Dashboard
     ========================================================== --%>

<div class="dashboard-card">

    <h1 class="dashboard-title">
        DiamondIQ
    </h1>

    <p class="dashboard-subtitle">
        MLB Analytics and Prediction Engine
    </p>

    <%-- Team Search --%>

    <form class="search-form"
          action="SearchServlet"
          method="post">

        <input class="search-box"
               type="text"
               name="teamName"
               placeholder="Search Team">

        <button class="primary-btn"
                type="submit">

            Search Teams

        </button>

    </form>

    <p class="error">
        ${error}
    </p>

<%
if (teams != null) {
%>

    <%-- Search Results --%>

    <div class="search-results">

<%
    for (model.Team team : teams) {
%>

        <a class="team-link"
           href="TeamServlet?teamId=<%= team.getTeamId() %>">

            <%= team.getTeamName() %>

        </a>

<%
    }
%>

    </div>

<%
}
%>

    <%-- Dashboard Actions --%>

    <div class="dashboard-actions">

        <a class="action-card"
           href="CompareStartServlet">

            <h3>Compare Teams</h3>

            <p>
                Predict MLB matchups
            </p>

        </a>

        <a class="action-card"
           href="HistoryServlet">


            <h3>Prediction History</h3>

            <p>
                View previous analyses
            </p>

        </a>

    </div>

</div>

<script src="js/dashboard.js"></script>

</body>

</html>
