package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StaffWindow extends Application {

    public StaffWindow() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UI_StaffWindow.fxml"));
        InstancePasser.assignStage(primaryStage,MyStages.STAFFINFO);
        primaryStage.setTitle("Staff Information");
        primaryStage.setScene(new Scene(root,230,155));
        primaryStage.show();
    }
}
