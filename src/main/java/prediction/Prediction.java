package prediction;

import model.Team;

/**
 * Represents the result of a generated matchup prediction.
 *
 * A Prediction object stores the projected winner, estimated scores,
 * confidence level, and AI-generated reasoning produced by the
 * DiamondIQ prediction engine.
 *
 * @author Kevin Lightfoot
 */
public class Prediction {

    /** Team predicted to win the matchup. */
    private Team winner;

    /** Projected score for the first team. */
    private double team1Score;

    /** Projected score for the second team. */
    private double team2Score;

    /** Confidence level assigned to the prediction. */
    private int confidence;

    /** Explanation describing how the prediction was generated. */
    private String reasoning;

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public double getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(double team1Score) {
        this.team1Score = team1Score;
    }

    public double getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(double team2Score) {
        this.team2Score = team2Score;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }
}