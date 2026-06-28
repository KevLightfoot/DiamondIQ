package util;

/**
 * Utility class for generating the relative file path to MLB team logo images.
 *
 * If a team abbreviation is unavailable, a default baseball image is returned.
 *
 * @author Kevin Lightfoot
 */
public class LogoUtil {

    /**
     * Returns the relative path to a team's logo image.
     *
     * @param abbreviation Team abbreviation (e.g., "NYY", "LAD")
     * @return Relative path to the corresponding logo image, or a default
     *         baseball image if no abbreviation is provided
     */
    public static String getLogo(String abbreviation) {

        // Return a default image when no team abbreviation is available.
        if (abbreviation == null || abbreviation.isEmpty()) {
            return "images/logos/baseball.jpg";
        }

        // Build the logo filename using the team's abbreviation.
        return "images/logos/" + abbreviation.toLowerCase() + ".png";
    }
}