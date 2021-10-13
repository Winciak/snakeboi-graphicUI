package main;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import snake.Block;
import snake.Board;
import snake.Play;
import snake.Snake;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gameController implements Initializable {

    public Stage stage;

    public void setStage(Stage stage){
        this.stage=stage;
    }

    @FXML
    ImageView boardId;

    @FXML
    Canvas canvasId;

    public GraphicsContext gc;

    @FXML
    StackPane sceneId;
    @FXML
    Label score;
    @FXML
    Label level;
    @FXML
    Label enter;

    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
    //  StackPane main = (StackPane) loader.load();
//      MainController controller2 = loader2.<MainController>getController();

    final Image image = new Image("images/snakeHead64.png");

    private static final int BOARD_WIDTH = 10, BOARD_HEIGTH = 10;
    Play newGame;

    int d = 1;
    boolean begin = true;
    boolean gameStarted = false;
    Scene scene;
    Timeline timeline = null;
    public gameController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boardId.setImage(new Image("images/board.png"));
        gc = canvasId.getGraphicsContext2D();
      //  gc.drawImage(image, 9*63+1, 9*63+1);
      //  gc.drawImage(image, 2*63+1, 2*63+1);
        enter.setVisible(true);



        Block initPos = new Block(BOARD_WIDTH/2, BOARD_HEIGTH/2);
        Snake initSnake = new Snake(initPos);
        initSnake.evolve();
        Board board = new Board(BOARD_WIDTH, BOARD_HEIGTH);
        newGame = new Play(initSnake, board, gc);
        newGame.gameOver = false;
        newGame.direction = 0;



    }


    public void keyPressed(KeyEvent event) {
        if(gameStarted) {
            if (event.getCode() == KeyCode.UP) {
                newGame.direction = 2;
                System.out.println("up");
            }
            if (event.getCode() == KeyCode.DOWN) {
                newGame.direction = -2;
                System.out.println("down");
            }
            if (event.getCode() == KeyCode.LEFT) {
                newGame.direction = -1;
                System.out.println("left");
            }
            if (event.getCode() == KeyCode.RIGHT) {
                newGame.direction = 1;
                System.out.println("right");
            }
        } else
        if (event.getCode() == KeyCode.ENTER){
            if(begin) {
                gameStarted = true;
                newGame.diff = d;
                newGame.score = 0;
                enter.setVisible(false);
                gameme();
                System.out.println("eneter");
            }
            else{
                begin = true;
                enter.setText("Press ENTER to start, play with arrows");
                score.setFont(new Font(12));
                stage.setScene(scene);
            }
        }
    }


    public void focus() {
        sceneId.requestFocus();
    }

    public void gameme(){

        newGame.gameOver = false;
        newGame.board.generateFood();
        newGame.drawInit();
        Thread.currentThread();

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(newGame.slow),
                        event -> handle()
                )
        );
        timeline.play();


    }

    public void handle()
    {


        newGame.check();
        score.setText(String.valueOf(newGame.score));
        level.setText(String.valueOf(newGame.level));
        if(!newGame.gameOver)
            newGame.drawSnek();
        else{
            enter.setVisible(true);
            enter.setText("Press Enter to go back to the Main Menu!");
            score.setFont(new Font(20));

            gc.clearRect(0,0,631,631);
            newGame.gameOver = !newGame.gameOver;
            gameStarted = !gameStarted;
            begin = false;
            timeline.stop();
        }



    }
}
