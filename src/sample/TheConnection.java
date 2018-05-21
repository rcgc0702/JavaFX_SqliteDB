package sample;

import javafx.beans.property.StringProperty;

import java.io.File;
import java.sql.*;

public class TheConnection {

    private static Connection connection = null;

    private TheConnection() {
    }

    public static Connection getConnection()  {

        if (connection == null) {
            final String afile = "C:/rizzacanete/mydatabase.db";
            final String theURL = "jdbc:sqlite:" + afile;
            final String tableExists = "SELECT name FROM sqlite_master WHERE type='table' AND name='thestaffinformation';";
            final String db = "CREATE TABLE thestaffinformation (" +
                    " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    dbValues.FIRSTNAME + ", " +
                    dbValues.LASTNAME + ", " +
                    dbValues.AGE + ", " +
                    dbValues.POSITION + ", " +
                    dbValues.SUPERVISOR + ", " +
                    dbValues.WORKADDRESS + "); ";

            File thefile = new File(afile);

            if (thefile.exists()) {

                try {
                    connection = DriverManager.getConnection(theURL);
                    System.out.println("The connetion" + connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return connection;
            }

            try {
                connection = DriverManager.getConnection(theURL);
                Statement stmt = connection.createStatement();
                Boolean exists = stmt.execute(tableExists);
                System.out.println(exists);
                stmt.executeUpdate(db);
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage() +"" + e.getSQLState());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {

        }
    }
}
