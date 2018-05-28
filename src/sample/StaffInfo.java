package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        System.out.println(resultSet);
        return resultSet;
    }

    public static ResultSet findStaff(String ask) {
        String query = "SELECT " + dbValues.FIRSTNAME + ", " +
                dbValues.ID + "," +
                dbValues.LASTNAME + " FROM thestaffinformation WHERE " +
                dbValues.FIRSTNAME + " LIKE '%" + ask + "%' OR " +
                dbValues.LASTNAME +  " LIKE '%" + ask + "%';";

        System.out.println(query);
        ResultSet rs = null;
        rs = processStatement(query);
        System.out.println("the query" + rs);
        return rs;
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
