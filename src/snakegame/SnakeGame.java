/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.*;
/**
 *
 * @author gillmylady
 */
public class SnakeGame extends JFrame{

    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 10;
    public static int ROW = HEIGHT / SCALE;
    public static int COL = WIDTH / SCALE;
    private int UP = 0;
    private int DOWN = 1;
    private int LEFT = 2;
    private int RIGHT = 3;
    private int FREQUENCY = 200;            //how quick it flush
    private int PAUSETIME = 10000;          //pause time
    private int GAMEOVERTIME = 3000;        //game overe display time
    
    private int directionPlay1 = UP;
    private int lastDirectionPlay1 = UP;
    private int directionPlay2 = DOWN;
    private int lastDirectionPlay2 = DOWN;
    private LinkedList<Integer> snakeBodiesPlay1;
    private LinkedList<Integer> snakeBodiesPlay2;
    private int currentPositionPlay1 = 0;
    private int currentPositionPlay2 = 0;
    
    private boolean pause = false;
    private boolean isAlive = true;
    
    private JPanel jp;
    private Apple apple;
    
    /**
     * to get key and update direction
     * @param e     key event
     */
    public synchronized void setDirection(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if(lastDirectionPlay2 != DOWN)
                    directionPlay2 = UP;
                break;
            case KeyEvent.VK_DOWN:
                if(lastDirectionPlay2 != UP)
                    directionPlay2 = DOWN;
                break;
            case KeyEvent.VK_LEFT:
                if(lastDirectionPlay2 != RIGHT)
                    directionPlay2 = LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                if(lastDirectionPlay2 != LEFT)
                    directionPlay2 = RIGHT;
                break;
            case KeyEvent.VK_W:
                if(lastDirectionPlay1 != DOWN)
                    directionPlay1 = UP;
                break;
            case KeyEvent.VK_S:
                if(lastDirectionPlay1 != UP)
                    directionPlay1 = DOWN;
                break;
            case KeyEvent.VK_A:
                if(lastDirectionPlay1 != RIGHT)
                    directionPlay1 = LEFT;
                break;
            case KeyEvent.VK_D:
                if(lastDirectionPlay1 != LEFT)
                    directionPlay1 = RIGHT;
                break;
            case KeyEvent.VK_SPACE:
                pause = true;
                break;
            default:                            // do nothing
                break;
        }
    }
    
    /**
     * to paint one point (block) of the snake
     * @param position  position of the point
     * @param head      if this is the head of the snake
     * @param removeTail    if this is required to remove the tail of snake
     * @param play1or2      this is play1 of play2
     */
    public void paintOnePieceOfSnake(int position, boolean head, boolean removeTail, boolean play1or2){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int x = position % COL;
                int y = position / COL;

                Graphics g = jp.getGraphics();

                if(removeTail){
                    g.setColor(Color.green);
                }else if(head){
                    g.setColor(Color.red);
                }else{
                    if(play1or2)
                        g.setColor(Color.blue);
                    else
                        g.setColor(Color.yellow);
                }
                g.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
            }
        });
        
    }
    
    /**
     *  to paint the bodies of snake
     */
    public void paintSnakeBodies(){
        for(int i : snakeBodiesPlay1){
            paintOnePieceOfSnake(i, i == snakeBodiesPlay1.getLast(), false, true);
        }
        for(int j : snakeBodiesPlay2){
            paintOnePieceOfSnake(j, j == snakeBodiesPlay2.getLast(), false, false);
        }
    }
    
    /**
     * to remove snake tail
     * @param play1or2 play1 or play2
     */
    public void removeSnakeTail(boolean play1or2){
        //System.out.println(snakeBodies.getFirst());
        if(play1or2)        //true -> 1
            paintOnePieceOfSnake(snakeBodiesPlay1.getFirst(), false, true, true);
        else
            paintOnePieceOfSnake(snakeBodiesPlay2.getFirst(), false, true, false);
    }
    
    /**
     * to show game over 
     */
    public void showGameOver(){
        Graphics g = jp.getGraphics();
        g.drawString("Game Over!", WIDTH/3, HEIGHT/2);
    }
    
    /**
     * to update grade in the title of this frame
     */
    public synchronized void setGrade(){
        this.setTitle("Snake Game | Grade1: " + snakeBodiesPlay1.size() + " | Grade2: " + snakeBodiesPlay2.size());
    }
    
    /**
     * to initial JPanel
     */
    public void initJPanel(){
        jp = new JPanel();
        jp.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        jp.setBackground(Color.green);
    }
    
    public void clearPlayer(boolean play1or2){
        if(play1or2){
            while(snakeBodiesPlay1.size() > 0){
                removeSnakeTail(play1or2);
                snakeBodiesPlay1.remove();
            }
        }else{
            while(snakeBodiesPlay2.size() > 0){
                removeSnakeTail(play1or2);
                snakeBodiesPlay2.remove();
            }
        }
        
    }
    
    public void initPlayer(boolean play1or2){
        if(play1or2){
            snakeBodiesPlay1.add(0);          //from left up corner of the map to right
            snakeBodiesPlay1.add(1);          //from left up corner of the map to right
            directionPlay1 = RIGHT;
            lastDirectionPlay1 = RIGHT;
        }else{
            snakeBodiesPlay2.add(0);          //from left up corner of the map to right
            snakeBodiesPlay2.add(1);          //from left up corner of the map to right
            directionPlay2 = RIGHT;
            lastDirectionPlay2 = RIGHT;
        }
    }
    
    /**
     * to initial two players
     */
    public void initTwoPlayers(){
        currentPositionPlay1 = (ROW * COL) / 2 + 15;
        snakeBodiesPlay1.add(currentPositionPlay1-1);          //from central of the map
        snakeBodiesPlay1.add(currentPositionPlay1);          //from central of the map
        
        currentPositionPlay2 = (ROW * COL) / 2 + 9;
        snakeBodiesPlay2.add(currentPositionPlay2-1);          //from central of the map
        snakeBodiesPlay2.add(currentPositionPlay2);          //from central of the map
    }
    
    /**
     * to clear two players
     */
    public void clearTwoPlayers(){
        snakeBodiesPlay1.clear();
        snakeBodiesPlay2.clear();
    }
    
    /**
     * to decide each process of move
     * 
     */
    public void checkMove(){
        
        if(currentPositionPlay1 != apple.getPosition()){     //if not eat, remove tail
            removeSnakeTail(true);
            snakeBodiesPlay1.remove();
        }
        
        if(currentPositionPlay2 != apple.getPosition()){     //if not eat, remove tail
            removeSnakeTail(false);
            snakeBodiesPlay2.remove();
        }
        
        if(currentPositionPlay1 == apple.getPosition() || currentPositionPlay2 == apple.getPosition()){
            apple.remove();
            apple = apple.nextApple(jp.getGraphics(), snakeBodiesPlay1, snakeBodiesPlay2);       //add one new apple
            setGrade();
        }
    }
    
    /**
     * the construction method of this class
     */
    public SnakeGame(){
        super("Snake Game");
        initJPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(jp);
        this.pack();
        this.setVisible(true);
        
        this.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                //setDirection(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                setDirection(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //key released, then dont change the direction
            }
        });
        
        snakeBodiesPlay1 = new LinkedList<>();
        snakeBodiesPlay2 = new LinkedList<>();
        initTwoPlayers();
        setGrade();
        paintSnakeBodies();
        
        gameRunThread t = new gameRunThread();
        t.start();
        
    }
    
    /**
     * new thread class, override run function
     */
    private class gameRunThread extends Thread{
        @Override
        public synchronized void run() {
                
            apple = new Apple(jp.getGraphics(), snakeBodiesPlay1, snakeBodiesPlay2);
            while(isAlive){
                try {
                    if(pause == true){
                        pause = false;
                        this.wait(PAUSETIME);
                    }else{
                        this.wait(FREQUENCY);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if( (directionPlay1 == UP && (currentPositionPlay1 / COL) == 0) ||
                    (directionPlay1 == DOWN && (currentPositionPlay1 / COL) == (ROW-1)) ||
                    (directionPlay1 == LEFT && (currentPositionPlay1 % COL) == 0) ||
                    (directionPlay1 == RIGHT && (currentPositionPlay1 % COL) == (COL-1)))
                {
                    clearPlayer(true);
                    initPlayer(true);
                    currentPositionPlay1 = snakeBodiesPlay1.getLast();
                }
                if( (directionPlay2 == UP && (currentPositionPlay2 / COL) == 0) ||
                    (directionPlay2 == DOWN && (currentPositionPlay2 / COL) == (ROW-1)) ||
                    (directionPlay2 == LEFT && (currentPositionPlay2 % COL) == 0) ||
                    (directionPlay2 == RIGHT && (currentPositionPlay2 % COL) == (COL-1))
                ){
                    clearPlayer(false);
                    initPlayer(false);
                    currentPositionPlay2 = snakeBodiesPlay2.getLast();
                }
                int newHeadPlay1;
                int newHeadPlay2;
                if(directionPlay1 == UP){
                    newHeadPlay1 = currentPositionPlay1 - COL;
                }else if(directionPlay1 == DOWN){
                    newHeadPlay1 = currentPositionPlay1 + COL;
                }else if(directionPlay1 == LEFT){
                    newHeadPlay1 = currentPositionPlay1 - 1;
                }else{  // if(direction == DOWN)
                    newHeadPlay1 = currentPositionPlay1 + 1;
                }

                if(directionPlay2 == UP){
                    newHeadPlay2 = currentPositionPlay2 - COL;
                }else if(directionPlay2 == DOWN){
                    newHeadPlay2 = currentPositionPlay2 + COL;
                }else if(directionPlay2 == LEFT){
                    newHeadPlay2 = currentPositionPlay2 - 1;
                }else{  // if(direction == DOWN)
                    newHeadPlay2 = currentPositionPlay2 + 1;
                }

                if(snakeBodiesPlay1.indexOf(newHeadPlay1) != -1
                        || snakeBodiesPlay2.indexOf(newHeadPlay1) != -1)
                {
                    clearPlayer(true);
                    initPlayer(true);
                    currentPositionPlay1 = snakeBodiesPlay1.getLast();
                    newHeadPlay1 = currentPositionPlay1 + 1;
                }
                if(snakeBodiesPlay2.indexOf(newHeadPlay2) != -1
                        || snakeBodiesPlay1.indexOf(newHeadPlay2) != -1
                        || (newHeadPlay1 == apple.getPosition() && 
                        newHeadPlay2 == apple.getPosition())){
                    clearPlayer(false);
                    initPlayer(false);
                    currentPositionPlay2 = snakeBodiesPlay2.getLast();
                    newHeadPlay2 = currentPositionPlay2 + 1;
                }

                //update status
                lastDirectionPlay1 = directionPlay1;      //to avoid left-up-right
                lastDirectionPlay2 = directionPlay2;      //to avoid left-up-right
                currentPositionPlay1 = newHeadPlay1;
                currentPositionPlay2 = newHeadPlay2;
                snakeBodiesPlay1.add(currentPositionPlay1);
                snakeBodiesPlay2.add(currentPositionPlay2);
                
                
                
                checkMove();
                paintSnakeBodies();
            }

            showGameOver();
            try {
                this.wait(GAMEOVERTIME);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        new SnakeGame();
    }

}
