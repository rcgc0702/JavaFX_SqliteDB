package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.SQLiteConnection;

import java.sql.*;

public abstract class StaffInfo {

    private static ResultSet processStatement(String s) {

        Connection conn = TheConnection.getConnection();
        ResultSet resultSet = null;
        try {
            PreparedStatement pStmt = conn.prepareStatement(s);
            resultSet = pStmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public static ObservableList<db_object> searchDatabase(String query) {

        // THIS CREATES THE DB_OBJECT THAT GETS ENTERED INTO THE LISTVIEW
        ResultSet resultSet = processStatement(query);
        ObservableList<db_object> oblist = FXCollections.observableArrayList();

        int reference;
        String firstname;
        String lastname;

        try {
            while (resultSet.next()) {
                db_object databaseObject = new db_object();
                reference = resultSet.getInt(dbValues.ID.toString());
                firstname = resultSet.getString(dbValues.FIRSTNAME.toString());
                lastname = resultSet.getString(dbValues.LASTNAME.toString());
                databaseObject.setReference(reference);
                databaseObject.setFirstname(firstname);
                databaseObject.setLastname(lastname);
                oblist.add(databaseObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oblist;
    }

}
