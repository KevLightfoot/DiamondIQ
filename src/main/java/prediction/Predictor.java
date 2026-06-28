package prediction;

import model.Team;

/**
 * Provides the prediction engine used by DiamondIQ.
 *
 * This class compares two MLB teams using season performance statistics,
 * calculates projected scores, determines a predicted winner, assigns a
 * confidence rating, and generates an explanation supporting the prediction.
 *
 * @author Kevin Lightfoot
 */
public class Predictor {

    /**
     * Generates a prediction for a matchup between two teams.
     *
     * The prediction includes projected scores, the predicted winner,
     * a confidence percentage, and a summary explaining the key factors
     * influencing the decision.
     *
     * @param team1 First team in the matchup
     * @param team2 Second team in the matchup
     * @return Completed Prediction object
     */
    public static Prediction predict(Team team1, Team team2) {

        Prediction prediction = new Prediction();

        // Calculate an overall performance score for each team.
        double score1 = calculateScore(team1);
        double score2 = calculateScore(team2);

        prediction.setTeam1Score(score1);
        prediction.setTeam2Score(score2);

        Team winner;
        Team loser;

        // Determine the predicted winner based on the higher calculated score.
        if (score1 >= score2) {
            winner = team1;
            loser = team2;
        } else {
            winner = team2;
            loser = team1;
        }

        prediction.setWinner(winner);

        double diff = Math.abs(score1 - score2);
        double averageScore = (score1 + score2) / 2.0;

        // Calculate a relative performance gap to estimate confidence.
        double relativeDifference = diff / averageScore;

        int confidence = (int) (55 + (relativeDifference * 300));

        // Limit confidence to a realistic range.
        confidence = Math.max(55, Math.min(confidence, 85));

        prediction.setConfidence(confidence);

        StringBuilder reasons = new StringBuilder();

        reasons.append("Why The ")
               .append(winner.getTeamName())
               .append(" Are Favored\n");

        // Highlight statistical advantages held by the predicted winner.

        // Better overall record.
        if (winner.getWins() > loser.getWins()) {
            reasons.append("Better overall record.\n");
        }

        // More productive offense.
        if (winner.getOps() > loser.getOps()) {
            reasons.append("More productive offense (higher OPS).\n");
        }

        // Stronger pitching staff.
        if (winner.getEra() < loser.getEra()) {
            reasons.append("Stronger pitching staff (lower ERA).\n");
        }

        // Better ability to limit baserunners.
        if (winner.getWhip() < loser.getWhip()) {
            reasons.append("Allows fewer baserunners (lower WHIP).\n");
        }

        // Greater offensive run production.
        if (winner.getRunsScored() > loser.getRunsScored()) {
            reasons.append("Scores more runs offensively.\n");
        }

        // Mention a notable strength of the opposing team when applicable.
        if (loser.getRunsScored() > winner.getRunsScored()) {

            reasons.append("\n");

            reasons.append(loser.getTeamName())
                   .append("'s biggest advantage is its run production, ")
                   .append("but DiamondIQ projects ")
                   .append(winner.getTeamName())
                   .append("'s overall profile to be stronger.");
        }

        prediction.setReasoning(reasons.toString());

        return prediction;
    }

    /**
     * Calculates a weighted performance score for a team using season
     * statistics.
     *
     * Positive offensive metrics increase the score, while weaker
     * pitching metrics reduce it. The resulting value is used to compare
     * both teams during prediction generation.
     *
     * @param team Team whose statistics will be evaluated
     * @return Weighted performance score
     */
    private static double calculateScore(Team team) {

        double score = 0;

        // Win-loss performance.
        score += team.getWins() * 4;
        score -= team.getLosses() * 2;

        // Offensive production versus defensive performance.
        score += team.getRunsScored() * 0.05;
        score -= team.getRunsAllowed() * 0.05;

        // Hitting statistics.
        score += team.getBattingAverage() * 100;
        score += team.getOnBasePercentage() * 100;
        score += team.getSluggingPercentage() * 100;
        score += team.getOps() * 100;

        // Pitching effectiveness.
        score -= team.getEra() * 15;
        score -= team.getWhip() * 40;

        // Additional offensive and pitching contributions.
        score += team.getStrikeoutsPerGame() * 0.02;
        score += team.getHomeRuns() * 0.15;

        return score;
    }

}