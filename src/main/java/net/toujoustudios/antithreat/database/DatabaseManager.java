package net.toujoustudios.antithreat.database;

import net.toujoustudios.antithreat.config.Config;
import net.toujoustudios.antithreat.log.LogLevel;
import net.toujoustudios.antithreat.log.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This file has been created by Ian Toujou.
 * Project: Kazunya
 * Date: 26/08/2021
 * Time: 23:23
 */
public class DatabaseManager {

    private static Connection connection;
    private static Config config = Config.getFile("database.yml");

    public static void connect() {

        if (isConnected()) {
            disconnect();
        }

        try {

            String url = "jdbc:mysql://" + config.getString("database.host") + ":" + config.getString("database.port") + "/" + config.getString("database.name") + "?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(url, config.getString("database.user"), config.getString("database.password"));
            Logger.log(LogLevel.INFORMATION, "The connection to the database has been established.");

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public static void disconnect() {

        if (isConnected()) {

            try {
                connection.close();
                Logger.log(LogLevel.INFORMATION, "The database has been disconnected.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }

    }

    public static boolean isConnected() {
        return connection != null;
    }

    public static ResultSet executeQuery(String query) {

        try {
            return connection.prepareStatement(query).executeQuery();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;

    }

    public static void executeUpdate(String update) {

        try {
            connection.prepareStatement(update).executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setup() {
        executeUpdate("CREATE TABLE IF NOT EXISTS `link_data` ( `link_id` INT NOT NULL AUTO_INCREMENT , `url` VARCHAR(256) NOT NULL , `status` VARCHAR(256) NOT NULL DEFAULT 'NOT_VERIFIED' , PRIMARY KEY (`link_id`)) ENGINE = InnoDB;");
    }

}
