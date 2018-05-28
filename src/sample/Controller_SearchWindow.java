package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Controller_SearchWindow {

    @FXML
    Button submitbutton;
    @FXML
    TextField tf_search;
    @FXML
    TableView tbView;
    @FXML
    TableColumn<db_object,String> tc1;
    @FXML
    TableColumn<db_object,String> tc2;
    @FXML
    GridPane grid;
    ObservableList<db_object> db = FXCollections.observableArrayList();

    public void initialize() {

        submitbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getResult();
            }
        });

        tf_search.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                getResult();
            }
        });

        grid.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    Stage search = InstancePasser.getStage(MyStages.SEARCH);
                    Stage main = InstancePasser.getStage(MyStages.MAIN);
                    main.show();
                    search.close();
                }
            }
        });

    }

    private void getResult() {
        boolean goThrough = trimAndValidate(tf_search);

        if (goThrough) {
            ResultSet resultSet = StaffInfo.findStaff(tf_search.getText());
            processRS(resultSet);
        }
    }

    private boolean trimAndValidate(TextField val) {
        if (val.getText().isEmpty()) {

            Alert anotherAlert = new Alert(Alert.AlertType.WARNING,"Your Entry is required.",ButtonType.CLOSE);
            anotherAlert.showAndWait();
            return false;
        }

        String nv = val.getText().trim();
        val.setText(nv);
        return true;
    }

    private void processRS(ResultSet resultSet) {

        db.clear();

        try {
            while (resultSet.next()) {
                // THIS CODE WORKS
                db_object dbo = new db_object();
                dbo.setFirstname(resultSet.getString(1));
                dbo.setReference(resultSet.getInt(2));
                dbo.setLastname(resultSet.getString(3));
                db.add(dbo);

            }
        } catch (SQLException e ){
            System.out.println(e.getMessage());
        }

        Alert anAlert = new Alert(Alert.AlertType.INFORMATION,"Query total result: " + db.size(),ButtonType.OK);
        anAlert.showAndWait();

        tc1.setText(dbValues.FIRSTNAME.toString());
        tc2.setText(dbValues.LASTNAME.toString());

        tc1.setCellValueFactory(new PropertyValueFactory<db_object,String>(dbValues.FIRSTNAME.toString()));
        tc2.setCellValueFactory(new PropertyValueFactory<db_object,String>(dbValues.LASTNAME.toString()));

        tbView.setItems(db);
    }
}
