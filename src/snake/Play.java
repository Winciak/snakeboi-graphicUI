package snake;




import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.LinkedList;

public class Play {
    private static final int DIRECTION_NONE = 0, DIRECTION_RIGHT = 1, DIRECTION_LEFT = -1,
            DIRECTION_UP=2, DIRECTION_DOWN=-2;
    private static final int BOARD_WIDTH = 10, BOARD_HEIGTH = 10;
    public int score = 0, slow = 800;
    public int level = 1;
    public int diff = 1;
    private Snake snake;
    public Board board;
    public int direction;
    public boolean gameOver;
    final GraphicsContext gc;
    final Image imageHead = new Image("images/snakeHead64.png");
    final Image imageHeadRight = new Image("images/snakeHead64_right.png");
    final Image imageBody = new Image("images/snakeBody64.png");
    final Image cookie = new Image("images/cookie.png");




    public Play(Snake snake, Board board, GraphicsContext gc){
        this.snake=snake;
        this.board=board;
        this.gc = gc;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public void setGameOver(boolean gameOver){
        this.gameOver=gameOver;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void check(){
       //System.out.println("checking");
        if(!gameOver){
            if(direction!=DIRECTION_NONE){
                Block nextBlock = getNextBlock(snake.getHead());


                if(snake.checkCrash(nextBlock) ){

                    slow = 800;
                    setDirection(DIRECTION_NONE);
                    Block Pos = new Block(BOARD_WIDTH/2, BOARD_HEIGTH/2);
                    snake = new Snake(Pos);
                    snake.evolve();
                    board = new Board(BOARD_WIDTH, BOARD_HEIGTH);
                    gameOver=true;
                }
                else{
                    //screen.putString(snake.getSnakePartList().getLast().getX(),snake.getSnakePartList().getLast().getY()," ", Terminal.Color.DEFAULT, Terminal.Color.DEFAULT);
                    gc.clearRect(snake.getSnakePartList().getLast().getX()*63+1,snake.getSnakePartList().getLast().getY()*63+1,62,62);
                    snake.move(nextBlock);

                    if(nextBlock.getBlocktype()==blockType.FOOD){
                        System.out.println("evolve");
                        snake.evolve();

                        LinkedList<Block> snek = snake.getSnakePartList();
                        int snakeLength=snek.size();

                        for (int i=0;i<snakeLength;i++){
                            int x=snek.get(i).getX();
                            int y=snek.get(i).getY();
                            board.getBlocks()[x][y].setBlocktype(blockType.SNAKEPART);
                        }

                        score = score + 5*level;
                        if(score==10) {level++; slow=slow-75;}
                        if(score==50) {level++; slow=slow-75;}
                        if(score==110) {level++; slow=slow-75;}
                        if(score==190) {level++; slow=slow-75;}

                        Block Foodblock = board.generateFood();

                        gc.drawImage(cookie,Foodblock.getX()*63+1,Foodblock.getY()*63+1);


                    }
                }

            }
        }
    }

    private Block getNextBlock(Block currentBlock){
        //System.out.println("getNExtBlock");
        int x = currentBlock.getX();
        int y = currentBlock.getY();
        int error=0;
        Block nextBlock = board.getBlocks()[BOARD_WIDTH/2][ BOARD_HEIGTH/2];
        if(direction == DIRECTION_LEFT){
            x--;
        }
        if(direction == DIRECTION_RIGHT){
            x++;
        }
        if(direction == DIRECTION_UP){
            y--;
        }
        if(direction == DIRECTION_DOWN){
            y++;
        }

        try {
            nextBlock = board.getBlocks()[x][y];
        } catch (ArrayIndexOutOfBoundsException e){
            //e.printStackTrace();
            error=1;
        }

        if(error==0){
            return nextBlock;
        }
        else if(error==1){


            slow = 800;
            setDirection(DIRECTION_NONE);
            Block Pos = new Block(BOARD_WIDTH/2, BOARD_HEIGTH/2);
            snake = new Snake(Pos);
            snake.evolve();
            board = new Board(BOARD_WIDTH, BOARD_HEIGTH);
            gameOver=true;

            return nextBlock;
        }
        return nextBlock;
    }



    public void drawSnek(){

        LinkedList<Block> snek = snake.getSnakePartList();
        int snakeLength=snek.size();

        for (int i=0;i<snakeLength;i++){
            if(i==0) {
                if(direction==DIRECTION_RIGHT) gc.drawImage(imageHeadRight, snek.get(i).getX() * 63 + 1, snek.get(i).getY() * 63 + 1);//rysuj glowe
                else
                gc.drawImage(imageHead, snek.get(i).getX() * 63 + 1, snek.get(i).getY() * 63 + 1);//rysuj glowe

            }
            else gc.drawImage(imageBody, snek.get(i).getX()*63+1, snek.get(i).getY()*63+1); //rysuj cialo
        }

    }

    public void drawInit(){

        level = 1;
        if(diff==1) {
            slow=500;

        }
        if(diff==2) {
            slow=350;

        }
        if(diff==3) {
            slow=200;

        }

        for(int i=0;i<board.YY;i++) {
            //terminal.moveCursor(0,i);
            for (int j = 0; j < board.XX; j++) {
              //  if (board.getBlocks()[j][i].getBlocktype() == blockType.EMPTY)

                    if (board.getBlocks()[j][i].getBlocktype() == blockType.FOOD) {
                        //rysuj zarcie
                        gc.drawImage(cookie,board.getBlocks()[j][i].getX()*63+1,board.getBlocks()[j][i].getY()*63+1);
                }
            }
        }



    }



}


