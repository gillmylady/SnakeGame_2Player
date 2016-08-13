/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author gillmylady
 */
public class Apple {
    
    int position;
    boolean alive = true;
    
    public Apple(Graphics g, LinkedList<Integer> snakeBodiesPlay1, LinkedList<Integer> snakeBodiesPlay2){
        nextApple(g, snakeBodiesPlay1, snakeBodiesPlay2);
    }
    
    public Apple nextApple(Graphics g, LinkedList<Integer> snakeBodiesPlay1, LinkedList<Integer> snakeBodiesPlay2){
        Random rd = new Random();
        int pos;
        while(true){
            int randomP = rd.nextInt(SnakeGame.COL * SnakeGame.ROW);
            if(snakeBodiesPlay1.indexOf(randomP) == -1 && snakeBodiesPlay2.indexOf(randomP) == -1){
                pos = randomP;
                break;
            }
        }
        alive = true;
        drawApple(g, pos);
        this.position = pos;
        return this;
    }
    
    public void drawApple(Graphics g, int position){
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int x = position % SnakeGame.COL;
                int y = position / SnakeGame.COL;

                g.setColor(Color.black);
                g.fillRect(x * SnakeGame.SCALE, y * SnakeGame.SCALE, SnakeGame.SCALE, SnakeGame.SCALE);
            }
        });
         
    }
    
    public void remove(){
        alive = false;
    }
    
    public int getPosition(){
        return this.position;
    }
    
    public boolean getStatus(){
        return this.alive;
    }
}
