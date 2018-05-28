package sample;

import javafx.stage.Stage;

import java.sql.ResultSet;

public class InstancePasser {

    private static Stage Main;
    private static Stage Search;
    private static Stage Info;
    private static Stage StaffInfo;
    private static ResultSet individualRS;

    public static void assignStage(Stage stage, MyStages mine) {

        switch (mine) {
            case MAIN:
                Main = stage;
                break;
            case SEARCH:
                Search = stage;
                break;
            case INFO:
                Info = stage;
                break;
            case STAFFINFO:
                StaffInfo = stage;

        }
    }

    public static Stage getStage(MyStages mine) {

        switch (mine) {
            case MAIN:
                return Main;
            case SEARCH:
                return Search;
            case INFO:
                return Info;
            case STAFFINFO:
                return StaffInfo;
        }
        return null;
    }

    public static ResultSet getIndividualRS() {
        return individualRS;
    }

    public static void setIndividualRS(ResultSet individualRS) {
        InstancePasser.individualRS = individualRS;
    }
}
