package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;

public class Staff {

    private StringProperty firstname;
    private StringProperty lastname;
    private IntegerProperty age;
    private StringProperty position;
    private StringProperty supervisor;
    private StringProperty workaddress;

    public Staff(String firstname, String lastname, int age, String position, String supervisor, String workaddress) {
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.age = new SimpleIntegerProperty(age);
        this.position = new SimpleStringProperty(position);
        this.supervisor = new SimpleStringProperty(supervisor);
        this.workaddress = new SimpleStringProperty(workaddress);
    }

    public void addIntoDatabase() {

        String theQuery = "INSERT INTO " + dbValues.TABLE1 +
                " ("+ dbValues.FIRSTNAME +","+ dbValues.LASTNAME +
                ","+ dbValues.AGE +","+ dbValues.POSITION +","+ dbValues.SUPERVISOR +
                ","+ dbValues.WORKADDRESS +") " +
                "VALUES (?,?,?,?,?,?);";

        processTheQuery(theQuery);
    }

    public void updateDatabase(int id) {

        String theQuery = "UPDATE " + dbValues.TABLE1 + " SET " +
                dbValues.FIRSTNAME + "= ?, " +
                dbValues.LASTNAME + "= ?, " +
                dbValues.AGE + "= ?, " +
                dbValues.POSITION + "= ?, " +
                dbValues.SUPERVISOR + "= ?, " +
                dbValues.WORKADDRESS + "= ? " +
                "WHERE ID = " + id + ";";

        processTheQuery(theQuery);
    }

    private void processTheQuery(String query) {

        Connection conn = TheConnection.getConnection();
        try {

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,getFirstname());
            stmt.setString(2,getLastname());
            stmt.setInt(3,getAge());
            stmt.setString(4,getPosition());
            stmt.setString(5,getSupervisor());
            stmt.setString(6,getWorkaddress());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private ResultSet createList(Statement stmt, String tablename) {

        String thequery = "SELECT firstname FROM " + tablename + ";";
        System.out.println(thequery);
        ResultSet theset = null;
        try {
            theset =  stmt.executeQuery(thequery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return theset;
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public void setSupervisor(String supervisor) {
        this.supervisor.set(supervisor);
    }

    public void setWorkaddress(String workaddress) {
        this.workaddress.set(workaddress);
    }

    public String getFirstname() {
        return firstname.get();
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public String getLastname() {
        return lastname.get();
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public String getSupervisor() {
        return supervisor.get();
    }

    public StringProperty supervisorProperty() {
        return supervisor;
    }

    public String getWorkaddress() {
        return workaddress.get();
    }

    public StringProperty workaddressProperty() {
        return workaddress;
    }
}
