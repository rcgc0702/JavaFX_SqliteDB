package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UI_Main.fxml"));
        primaryStage.setTitle("Staff Entry");
        primaryStage.setScene(new Scene(root, 500, 290));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void createDatabase() {
        Path path = Paths.get("C:/rizzacanete/");

        if (Files.exists(path)) {
            return;
        } else {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        createDatabase();
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        TheConnection.closeConnection();
        super.stop();
    }
}
