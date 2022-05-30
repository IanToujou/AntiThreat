package net.toujoustudios.antithreat.data;

import net.toujoustudios.antithreat.database.DatabaseManager;
import net.toujoustudios.antithreat.link.LinkStatus;
import net.toujoustudios.antithreat.util.LinkUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 29/05/2022
 * Time: 22:12
 */
public class LinkDataManager {

    public static void setStatus(int id, LinkStatus status) {
        DatabaseManager.executeUpdate("INSERT INTO link_data (link_id, status) VALUES ('" + id + "', '" + status.getName() + "') ON DUPLICATE KEY UPDATE status='" + status.getName() + "';");
    }

    public static LinkStatus getStatus(int id) {
        try {
            ResultSet resultSet = DatabaseManager.executeQuery("SELECT status FROM link_data WHERE link_id='" + id + "';");
            if (resultSet != null && resultSet.next()) {
                return LinkStatus.valueOf(resultSet.getString("status"));
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static int getId(String url) {
        try {
            ResultSet resultSet = DatabaseManager.executeQuery("SELECT link_id FROM link_data WHERE url='" + LinkUtil.getHost(url) + "';");
            if (resultSet != null && resultSet.next()) {
                return resultSet.getInt("link_id");
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
        return -1;
    }

}
