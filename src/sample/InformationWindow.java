package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InformationWindow extends Application {

    public InformationWindow() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("UI_Info.fxml"));
        InstancePasser.assignStage(primaryStage,MyStages.INFO);
        primaryStage.setTitle("Keyboard Shortcuts");
        primaryStage.setScene(new Scene(root,350,100));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


}
