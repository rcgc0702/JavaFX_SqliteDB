package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Controller {

    @FXML
    TextField fname;
    @FXML
    TextField lname;
    @FXML
    TextField age;
    @FXML
    TextField position;
    @FXML
    TextField supervisor;
    @FXML
    TextField workaddress;
    @FXML
    Button submit;
    @FXML
    ListView lv;
    @FXML
    ContextMenu contextMenu;
    @FXML
    MenuItem cm_delete;
    @FXML
    MenuItem cm_cancelEdit;
    @FXML
    MenuItem cm_editfirstname;
    private static int editmode = 0; // editmore is 1 if editing, zero if not editing
    private static Integer currentID = null;
    private static String currentEdit = "";

    private final String databasename = "thestaffinformation";
    private static ObservableList<TextField> nodeList = FXCollections.observableArrayList();
    private static ObservableList<db_object> stafflist = FXCollections.observableArrayList();

    public void initialize() {

        updateObList();
        nodeList.addAll(fname,lname,age,position,supervisor,workaddress);

        cm_cancelEdit.setVisible(false);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int theage = (age.getText().compareToIgnoreCase("") > 0) ? 1 : 0;
                if (theage == 0) return;

                Staff astaff = new Staff(fname.getText(),lname.getText(),
                        Integer.parseInt(age.getText()),position.getText(),
                        supervisor.getText(),workaddress.getText());

                if (editmode == 0) {
                    astaff.addToDatabase(databasename);
                } else {
                    astaff.updateQuery(databasename,currentID);
                }
                editmode = 0;
                currentID = null;
                switchEditMenus();
                updateObList();
            }
        });

        cm_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                db_object obfordelete = (db_object) lv.getSelectionModel().getSelectedItem();
                ButtonType del = new ButtonType("Delete", ButtonBar.ButtonData.YES);
                ButtonType can = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to delete "+ obfordelete+"?",
                        del,can);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent()) {

                    if (result.get() == del) {

                        if (currentID == null) {
                            obfordelete.removeStaff();
                        } else if (currentID == obfordelete.getReference()){
                            obfordelete.removeStaff();
                            currentID = null;
                            editmode = 0;
                        } else {
                            obfordelete.removeStaff();
                        }
                    }
                }

                switchEditMenus();
                updateObList();
            }
        });

        cm_cancelEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editmode = 0;
                currentID = null;
                switchEditMenus();
            }
        });

        cm_editfirstname.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                db_object obfordelete = (db_object) lv.getSelectionModel().getSelectedItem();
                if (obfordelete == null) return;

                editmode = 1;
                ResultSet rs = obfordelete.modifyStaff();
                currentEdit = obfordelete.getFirstname() + " " + obfordelete.getLastname(); // for cancel edit MenuItem

                try {
                    do {
                        currentID = Integer.parseInt(rs.getString(dbValues.ID.toString()));
                        fname.setText(rs.getString(dbValues.FIRSTNAME.toString()));
                        lname.setText(rs.getString(dbValues.LASTNAME.toString()));
                        position.setText(rs.getString(dbValues.POSITION.toString()));
                        age.setText(rs.getString(dbValues.AGE.toString()));
                        supervisor.setText(rs.getString(dbValues.SUPERVISOR.toString()));
                        workaddress.setText(rs.getString(dbValues.WORKADDRESS.toString()));
                    } while (rs.next());
                } catch (SQLException s) {
                    System.out.println(s.getMessage());
                }
                switchEditMenus();
            }
        });


        lv.setContextMenu(contextMenu);
    }

    private void switchEditMenus() {

        if (currentID == null) {
            cm_editfirstname.setVisible(true);
            cm_cancelEdit.setVisible(false);
        } else {
            cm_editfirstname.setVisible(false);
            cm_cancelEdit.setVisible(true);
            cm_cancelEdit.setText("Cancel edit for: " + currentEdit);
        }

        if (editmode == 0) {
            clearNodes();
        }
    }

    private void clearNodes() {
        for (TextField textField: nodeList) {
            textField.clear();
        }
    }

    public void updateObList() {

        // THIS IS THE METHOD THAT CREATES THE 10 RECENT ENTRIES

        stafflist.clear();
        lv.getItems().clear();
        stafflist = StaffInfo.searchDatabase ("SELECT * FROM " + databasename + " ORDER BY ID DESC LIMIT 10;");
        if (stafflist.size() == 0) return;

        int counter = 0;
        do {
            lv.getItems().add(stafflist.get(counter));
            counter++;
        } while (counter < stafflist.size());
    }
}
