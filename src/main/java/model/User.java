package model;

/**
 * Represents a registered user of the DiamondIQ application.
 *
 * This model stores account information along with the user's
 * selected favorite MLB team.
 *
 * @author Kevin Lightfoot
 */
public class User {

    /** Unique identifier for the user. */
    private int userId;

    /** User's account username. */
    private String username;

    /** User's registered email address. */
    private String email;

    /** ID of the user's selected favorite team. */
    private Integer favoriteTeamId;

    /**
     * Creates an empty User object.
     */
    public User() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFavoriteTeamId() {
        return favoriteTeamId;
    }

    public void setFavoriteTeamId(Integer favoriteTeamId) {
        this.favoriteTeamId = favoriteTeamId;
    }
}