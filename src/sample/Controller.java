package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Controller {

    @FXML
    TextField fname;
    @FXML
    MenuItem mb_info;
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
    @FXML
    BorderPane borderpane;
    @FXML
    MenuItem mb_search;
    private static int editmode = 0; // editmore is 1 if editing, zero if not editing
    private static Integer currentID = null;
    private static String currentEdit = "";

    private static ObservableList<TextField> nodeList = FXCollections.observableArrayList();
    private static ObservableList<db_object> stafflist = FXCollections.observableArrayList();

    public void initialize() {

        updateObList();
        nodeList.addAll(fname,lname,age,position,supervisor,workaddress);
        cm_cancelEdit.setVisible(false);

        mb_search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchRecords();
            }
        });

        mb_info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openInfoPage();
            }
        });

        lv.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {

                    db_object dbo = (db_object) lv.getSelectionModel().getSelectedItem();
                    if (dbo == null) {
                        lv.getSelectionModel().selectFirst();
                        dbo = (db_object) lv.getSelectionModel().getSelectedItem();
                        if (dbo == null) return;
                    }

                    InstancePasser.setIndividualRS(dbo.getIndividualStaff());

                    StaffWindow staffWindow = new StaffWindow();
                    Stage astage = InstancePasser.getStage(MyStages.STAFFINFO);
                    if (astage == null) {
                        astage = new Stage();
                        astage.setResizable(false);
                        astage.initModality(Modality.APPLICATION_MODAL);
                    }

                    try {
                        staffWindow.start(astage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        borderpane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                switch (event.getCode()) {
                    case ENTER:
                        submitValues();
                        break;
                    case ESCAPE:
                        if (editmode == 0) return;
                        editmode = 0;
                        currentID = null;
                        switchEditMenus();
                        break;
                    case DELETE:
                        lv.requestFocus();
                        deleteStaff();
                        switchEditMenus();
                        updateObList();
                }
            }
        });

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                submitValues();
            }
        });


        cm_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                deleteStaff();
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

                db_object objEdit = (db_object) lv.getSelectionModel().getSelectedItem();
                if (objEdit == null) return;

                editmode = 1;
                ResultSet rs = objEdit.getIndividualStaff();
                currentEdit = objEdit.getFirstname() + " " + objEdit.getLastname(); // for cancel edit MenuItem

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
                borderpane.requestFocus();
                switchEditMenus();
            }
        });

        lv.setContextMenu(contextMenu);
    }

    private void submitValues() {
        trimValues();
        int theage = (age.getText().compareToIgnoreCase("") > 0) ? 1 : 0;
        if (theage == 0) return;

        Staff astaff = new Staff(fname.getText(),lname.getText(),
                Integer.parseInt(age.getText()),position.getText(),
                supervisor.getText(),workaddress.getText());

        if (editmode == 0) {
            astaff.addIntoDatabase();
        } else {
            astaff.updateDatabase(currentID);
        }
        editmode = 0;
        currentID = null;
        switchEditMenus();
        updateObList();
    }

    private void openInfoPage(){

        InformationWindow infowindow = new InformationWindow();
        Stage stage = InstancePasser.getStage(MyStages.INFO);

        if (stage == null) {
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            InstancePasser.assignStage(stage,MyStages.INFO);
        }

        try {
            infowindow.start(stage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void searchRecords(){

        SearchWindow b = new SearchWindow();
        Stage stage = InstancePasser.getStage(MyStages.SEARCH);
        Stage mainStage = InstancePasser.getStage(MyStages.MAIN);

        if (stage == null) {
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            InstancePasser.assignStage(stage,MyStages.SEARCH);
        }

        try {
            mainStage.hide();
            b.start(stage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void deleteStaff() {

        db_object obfordelete = (db_object) lv.getSelectionModel().getSelectedItem();

        if (obfordelete == null) {
            lv.requestFocus();
            lv.getSelectionModel().selectFirst();
            obfordelete = (db_object) lv.getSelectionModel().getSelectedItem();
            if (obfordelete == null) return;
        }

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
    }

    private void switchEditMenus() {

        if (currentID == null) {
            cm_editfirstname.setVisible(true);
            cm_cancelEdit.setVisible(false);
        } else {
            cm_editfirstname.setVisible(false);
            cm_cancelEdit.setVisible(true);
            cm_cancelEdit.setText("Cancel edit for: " + currentEdit);
            borderpane.requestFocus();
        }

        if (editmode == 0) {
            clearNodes();
        }
    }

    private void trimValues() {
        for (TextField textField: nodeList) {
            String nv = textField.getText().trim();
            textField.setText(nv);
        }
    }

    private void clearNodes() {
        for (TextField textField: nodeList) {
            textField.clear();
        }
    }

    private void updateObList() {

        // THIS IS THE METHOD THAT CREATES THE 10 RECENT ENTRIES

        stafflist.clear();
        lv.getItems().clear();
        stafflist = StaffInfo.searchDatabase ("SELECT * FROM " + dbValues.TABLE1.toString() + " ORDER BY ID DESC LIMIT 10;");
        if (stafflist.size() == 0) return;

        int counter = 0;
        do {
            lv.getItems().add(stafflist.get(counter));
            counter++;
        } while (counter < stafflist.size());
    }
}
