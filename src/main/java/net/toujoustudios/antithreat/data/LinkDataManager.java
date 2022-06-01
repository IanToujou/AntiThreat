package net.toujoustudios.antithreat.data;

import net.toujoustudios.antithreat.database.DatabaseManager;
import net.toujoustudios.antithreat.link.LinkStatus;
import net.toujoustudios.antithreat.util.LinkUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

/**
 * A class to manage scam links that are stored in a database. You can perform operations
 * on the links as well as fetch information related to these.
 *
 * @author Ian Toujou
 * @since 1.0.0
 */
public class LinkDataManager {

    /**
     * Get the ID of a provided URL to perform database operations.
     *
     * @param url The input URL to get the ID for.
     * @return The given ID for the provided URL. If it does not exist, it will return -1.
     */
    public static int getId(String url) {
        try {
            ResultSet resultSet = DatabaseManager.executeQuery("SELECT link_id FROM link_data WHERE url='" + LinkUtil.getHost(url) + "';");
            if (resultSet != null && resultSet.next()) {
                return resultSet.getInt("link_id");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return -1;
    }

    /**
     * Checks if a link exists or is stored in the database.
     *
     * @param id The ID of the link.
     * @return Either true, if the link does exist, and false if it does not.
     */
    public static boolean doesLinkExist(int id) {
        return (id != -1);
    }

    /**
     * Adds a new link to the database with automatically defined values, such as the status and the ID.
     *
     * @param url The URL of the link.
     * @return True if the link has been successfully added to the database, and false if the link is already stored in the database.
     */
    public static boolean addLink(String url) {
        if (doesLinkExist(getId(url))) return false;
        String host = LinkUtil.getHost(url);
        String status = LinkStatus.NOT_VERIFIED.getName().toUpperCase(Locale.ROOT);
        DatabaseManager.executeUpdate("INSERT INTO link_data (url, status) VALUES ('" + host + "', '" + status + "');");
        return true;
    }

    /**
     * Completely removes a link stored in the database.
     *
     * @param url The input URL that should be removed.
     * @return True if the link has been removed from the database, and false if the link is not in the database and therefore not removed.
     */
    public static boolean removeLink(String url) {
        if (!doesLinkExist(getId(url))) return false;
        String host = LinkUtil.getHost(url);
        DatabaseManager.executeUpdate("DELETE FROM link_data WHERE link_id='" + getId(url) + "'");
        return true;
    }

    /**
     * Sets the status for a specific link using the LinkStatus class.
     *
     * @param id     The ID of the link.
     * @param status The new status for the link.
     * @see net.toujoustudios.antithreat.link.LinkStatus
     */
    public static void setStatus(int id, LinkStatus status) {
        DatabaseManager.executeUpdate("INSERT INTO link_data (link_id, status) VALUES ('" + id + "', '" + status.getName() + "') ON DUPLICATE KEY UPDATE status='" + status.getName() + "';");
    }

    /**
     * Check the current status of a link stored in the database.
     *
     * @param id The ID of the link.
     * @return The status of the given link.
     * @see net.toujoustudios.antithreat.link.LinkStatus
     */
    public static LinkStatus getStatus(int id) {
        try {
            ResultSet resultSet = DatabaseManager.executeQuery("SELECT status FROM link_data WHERE link_id='" + id + "';");
            if (resultSet != null && resultSet.next()) {
                return LinkStatus.valueOf(resultSet.getString("status"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
