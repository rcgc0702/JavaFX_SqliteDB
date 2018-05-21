package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;

public class db_object {

    private IntegerProperty reference = new SimpleIntegerProperty(0);
    private StringProperty firstname = new SimpleStringProperty("");
    private StringProperty lastname = new SimpleStringProperty("");

    @Override
    public String toString() {
        return getFirstname() +" "+ getLastname() ;
    }

    public void removeStaff() {

        String query = "DELETE FROM thestaffinformation WHERE ID=?";
        processStatement(query,Task.DELETE);
    }

    public ResultSet modifyStaff() {

        String query = "SELECT * FROM thestaffinformation WHERE ID =?";
        System.out.println(query);
        ResultSet rs;
        rs = processStatement(query,Task.UPDATE);

        return rs;
    }

    private ResultSet processStatement(String query, Task en) {

        Connection conn = TheConnection.getConnection();
        ResultSet rs;
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,getReference());

            switch (en) {
                case UPDATE:
                case SEARCH:
                    rs = stmt.executeQuery();
                    return rs;
                case DELETE:
                    stmt.execute();
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getReference() {
        return reference.get();
    }

    public IntegerProperty referenceProperty() {
        return reference;
    }

    public String getFirstname() {
        return firstname.get();
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public void setReference(int reference) {
        this.reference.set(reference);
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public String getLastname() {
        return lastname.get();
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }
}
