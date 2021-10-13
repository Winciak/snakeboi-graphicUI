package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Stage stage;

    public void setStage(Stage stage){
        this.stage=stage;
    }

    FXMLLoader loader = new FXMLLoader(getClass().getResource("gameWindow.fxml"));
    StackPane game = (StackPane) loader.load();
    gameController controller = loader.<gameController>getController();

    @FXML
    Label mainLabel1;

    @FXML
    Button mainButton1;
    @FXML
    Button mainButton2;
    @FXML
    Button mainButton3;

    public int getD() {
        return d;
    }

    int d = 1;
    public MainController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainLabel1.setText("SNAKEBOI");
        mainButton1.setText("New game");
        mainButton2.setText("Change difficulty: EASY");
        mainButton3.setText("Quit");
        mainButton3.setOnAction(actionEvent -> closeProgram());
//        System.out.println(mainLabel1.getScene().getRoot());


        Scene scene = new Scene(game);

        controller.focus();

        mainButton1.setOnAction(actionEvent -> {
            controller.d = d;
            System.out.println(d);
            controller.scene = mainLabel1.getScene();
            controller.stage = stage;
            stage.setScene(scene);
        });


    }


    public static void closeProgram(){
        Platform.exit();
        System.exit(0);
    }

    public void mainButton2Clicked(){
        if(d==1) {
            mainButton2.setText("Change difficulty: MEDIUM");
            d=2;
        } else
        if(d==2){
            mainButton2.setText("Change difficulty: HARD");
            d=3;
        } else
        if(d==3){
            mainButton2.setText("Change difficulty: EASY");
            d=1;
        }

    }


}
