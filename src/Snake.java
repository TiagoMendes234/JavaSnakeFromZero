import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends JPanel implements ActionListener {
    private static final int WIDTH = 1300;
    private static final int HEIGHT = 750;
    private static final int UNIT_SIZE = 50;
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private ArrayList<Point> snakeParts = new ArrayList<>();
    static final int DELAY = 500;

    private boolean running = false;
    private String direction = "R";
    private String previousDirection = "";

    private Timer timer;
    private Point apple;
    private Point changeDirection;

    public Snake() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(new KeyListener());
        running = true;
        startGame();
    }

    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        //int xInicial =
        //int yInicial =
        //System.out.println(xInicial + " " + yInicial);
        snakeParts.add(new Point( 0 , 0));
        generateApple();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int x = 0; x <= WIDTH; x = x + UNIT_SIZE) {
            g.drawLine(x, 0, x, WIDTH);
        }

        for (int y = 0; y <= HEIGHT; y = y + UNIT_SIZE) {
            g.drawLine(0, y, WIDTH, y);
        }

        for(int i=0;i<snakeParts.size();i++){
            g.setColor(Color.GREEN);
            g.fillOval((int)(snakeParts.get(i).getX()),(int)(snakeParts.get(i).getY()),UNIT_SIZE,UNIT_SIZE);
        }

        try {
            draw(g);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics g) throws IOException {
        g.setColor(Color.RED);
        BufferedImage image = ImageIO.read(new File("res/apple.png"));
        g.drawImage(image,(int)(apple.getX()), (int)(apple.getY()),UNIT_SIZE,UNIT_SIZE,null);
        //g.fillOval((int)(apple.getX()), (int)(apple.getY()), UNIT_SIZE, UNIT_SIZE);
    }

    public void generateApple(){
        Random r = new Random();
        int xApple = r.nextInt((WIDTH / UNIT_SIZE));
        int yApple = r.nextInt((HEIGHT / UNIT_SIZE));
        apple = new Point(xApple * UNIT_SIZE,yApple * UNIT_SIZE);
    }

    private void checkCollision() {
        if (snakeParts.get(0).getX() == apple.getX() && snakeParts.get(0).getY() == apple.getY()) {
            System.out.println("Apanhaste a maçã");
            generateApple();
            increaseSnake();
        }
    }

    private void move(){
        switch( direction ) {
            case "U":
                for(int i=snakeParts.size()-1;i>=0;i--){
                    if(snakeParts.size()>1 && i >= 1) {
                        snakeParts.set(i,snakeParts.get(i-1));
                    }else{
                        snakeParts.set(i, new Point((int) (snakeParts.get(i).getX()), (int) (snakeParts.get(i).getY() - UNIT_SIZE)));
                    }
                }
                break;
            case "D":
                for(int i=snakeParts.size()-1;i>=0;i--){
                    if(snakeParts.size()>1 && i >= 1) {
                        snakeParts.set(i,snakeParts.get(i-1));
                    }else{
                        snakeParts.set(i, new Point((int) (snakeParts.get(i).getX()), (int) (snakeParts.get(i).getY() + UNIT_SIZE)));
                    }
                }
                break;
            case "L":
                for(int i=snakeParts.size()-1;i>=0;i--){
                    if(snakeParts.size()>1 && i >= 1) {
                        snakeParts.set(i,snakeParts.get(i-1));
                    }else{
                        snakeParts.set(i, new Point((int) (snakeParts.get(i).getX() - UNIT_SIZE), (int) (snakeParts.get(i).getY())));
                    }
                }
                break;
            case "R":
                for(int i=snakeParts.size()-1;i>=0;i--) {
                    if (i<1) {
                        snakeParts.set(i, new Point((int) (snakeParts.get(i).getX() + UNIT_SIZE), (int) (snakeParts.get(i).getY())));
                    }else{
                        snakeParts.set(i,snakeParts.get(i-1));
                    }
                }
                break;
        }

    }

    private void wallCollisions(){
            for(int i=0;i<snakeParts.size();i++){
                if(snakeParts.get(i).getX()>=WIDTH)
                    snakeParts.set(i,new Point(0,(int)(snakeParts.get(i).getY()) ));
                else if(snakeParts.get(i).getX()<0)
                    snakeParts.set(i,new Point(WIDTH,(int)(snakeParts.get(i).getY()) ));
                else if(snakeParts.get(i).getY()<0)
                    snakeParts.set(i,new Point((int)(snakeParts.get(i).getX()),HEIGHT ));
                else if (snakeParts.get(i).getY()>HEIGHT)
                    snakeParts.set(i,new Point((int)(snakeParts.get(i).getX()),0 ));
            }
    }

    private void increaseSnake(){
        switch( direction ) {
            case "U":

                snakeParts.add(new Point((int) (snakeParts.get(snakeParts.size()-1).getX()),(int) (snakeParts.get(snakeParts.size()-1).getY()+UNIT_SIZE) ));
                break;

            case "D":

                snakeParts.add(new Point((int) (snakeParts.get(snakeParts.size()-1).getX()),(int) (snakeParts.get(snakeParts.size()-1).getY()-UNIT_SIZE) ));
                break;

            case "L":

                snakeParts.add(new Point((int) (snakeParts.get(snakeParts.size()-1).getX()+UNIT_SIZE),(int) (snakeParts.get(snakeParts.size()-1).getY()) ));
                break;

            case "R":

                snakeParts.add(new Point((int) (snakeParts.get(snakeParts.size()-1).getX()-UNIT_SIZE),(int) (snakeParts.get(snakeParts.size()-1).getY()) ));
                break;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollision();
            wallCollisions();
        }
        repaint();
    }


    public class KeyListener implements java.awt.event.KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch( keyCode ) {
                case KeyEvent.VK_UP:
                    if(!direction.equals("D"))
                        direction = "U";
                    break;
                case KeyEvent.VK_DOWN:
                    if(!direction.equals("U"))
                        direction = "D";
                    break;
                case KeyEvent.VK_LEFT:
                    if(!direction.equals("R"))
                        direction = "L";
                    break;
                case KeyEvent.VK_RIGHT:
                    if(!direction.equals("L"))
                        direction = "R";
                    break;
            }
            //System.out.println(direction);

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }


}
