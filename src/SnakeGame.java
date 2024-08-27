import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        int x; 
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    int width;
    int height;
    int tileSize = 25;

    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //Game Logic
    Timer gameLoop;
    int speedx;
    int speedy;
    boolean gameOver = false;

    SnakeGame(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(this.width, this.height));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(8,5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(16,10);
        random = new Random();
        placeFood();

        speedx = 0;
        speedy = 1;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //Grid
        //for(int i=0; i<width/tileSize; i++){
            //x1,y1,x2,y2
        //    g.drawLine(i*tileSize, 0, i*tileSize, height);
        //    g.drawLine(0, i*tileSize, width, i*tileSize);
        //}

        //Food
        g.setColor(Color.green);
        //g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        //Snake Head
        if(gameOver == false){
            g.setColor(Color.blue);
        }
        else{
            g.setColor(Color.red);
        }
        //g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // Snake Body
        for(int i=0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
            if(gameOver){
                g.setColor(Color.red);
            }
        }

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else{
            g.setColor(Color.white);
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }
    
    public void placeFood(){
        food.x = random.nextInt(width/tileSize);  // 650/25 = 26
        food.y = random.nextInt(height/tileSize); // 650/25 = 26
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){
        //Food Eaten
        if (collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //Snake Body
        for(int i=snakeBody.size()-1; i>=0; i--){
            Tile snakePart = snakeBody.get(i);
            if(i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake Head
        snakeHead.x += speedx;
        snakeHead.y += speedy;

        //Game Over
        //1. Snake bites itself
        for(int i=0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //Collides with Snake Head
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }

        //2. Snake hits the wall
        if(snakeHead.x * tileSize < 0 || snakeHead.x *tileSize > width || snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > height){
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && speedy != 1){
            speedx = 0;
            speedy = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && speedy != -1){
            speedx = 0;
            speedy = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && speedx != 1){
            speedx = -1;
            speedy = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT&& speedx != -1){
            speedx = 1;
            speedy = 0;
        }
    }

    //No Need
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
