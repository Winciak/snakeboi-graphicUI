package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {



    public void startGame(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("gameWindow.fxml"));
        StackPane root = (StackPane) loader.load();
        StackPane gameru = (StackPane) loader1.load();
        //root.getChildrenUnmodifiable().add(gameru);

        ((MainController) loader.getController()).setStage(primaryStage);
        ((gameController) loader1.getController()).setStage(primaryStage);

        Button backButton = new Button("Back to Menu");
        backButton.setPadding(new Insets(100,600,0,0));
        backButton.toFront();
        backButton.setAlignment(Pos.TOP_LEFT);


        gameru.getChildren().add(backButton);


        primaryStage.getIcons().add(new Image("images/mythical_snake.png"));
        primaryStage.setTitle("Snakeboi");
        primaryStage.setResizable(false);
        primaryStage.setMinHeight(500.0);
        primaryStage.setMinWidth(600.0);
        primaryStage.setOnCloseRequest((e) -> {MainController.closeProgram();});

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    @Override
    public void start(Stage primaryStage) throws Exception{


        startGame(primaryStage);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
