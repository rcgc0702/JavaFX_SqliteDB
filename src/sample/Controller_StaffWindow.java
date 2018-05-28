package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.ResultSet;

public class Controller_StaffWindow {

    @FXML
    Label fname;
    @FXML
    Label lname;
    @FXML
    Label age;
    @FXML
    Label position;
    @FXML
    Label supervisor;
    @FXML
    Label workaddress;

    public void initialize() {

        ResultSet individual = InstancePasser.getIndividualRS();
        System.out.println(individual);

        try {
            do {
                fname.setText(individual.getString(dbValues.FIRSTNAME.toString()));
                lname.setText(individual.getString(dbValues.LASTNAME.toString()));
                age.setText(individual.getString(dbValues.AGE.toString()));
                position.setText(individual.getString(dbValues.POSITION.toString()));
                supervisor.setText(individual.getString(dbValues.SUPERVISOR.toString()));
                workaddress.setText(individual.getString(dbValues.WORKADDRESS.toString()));
            } while (individual.next());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
