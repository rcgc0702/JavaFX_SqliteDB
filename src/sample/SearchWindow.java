package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class  SearchWindow extends Application {

    /*
    This is probably the best way to open a new window - without creating
    null pointer exception or static issues with the resource.

    In order to work this correctly, an instantiation of this class must be done

    ex. ThisClass thisClass = new ThisClass();
        thisClass.start(thestage);
     */

    public SearchWindow()  {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("UI_SearchWindow.fxml"));

        primaryStage.setScene(new Scene(root,280,300));
        primaryStage.setTitle("Search...");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Stage thestagetoopen = InstancePasser.getStage(MyStages.MAIN);
                thestagetoopen.show();
            }
        });

        primaryStage.show();


    }

}
