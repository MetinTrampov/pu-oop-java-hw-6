import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int WIDTH_SIZE = 1300;
    static final int HEIGHT_SIZE = 750;
    static final int UNIT_SIZE = 50;
    static final int GAME_UNITS = (WIDTH_SIZE * HEIGHT_SIZE)/(UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 175;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 1;
    int applesEaten;
    int appleX;
    int appleY;
    int obstacleX ;
    int obstacleY ;
    int obstacle2Y ;
    int  obstacle2X;
    int  obstacle3X;
    int   obstacle3Y ;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;


    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH_SIZE, HEIGHT_SIZE));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
	/**
	*Method wichh draw  objectives when game starts 
	/
    public void startGame() {
        newApple();
        newObstacle();
        newObstacle2();
        //newObstacle3();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {

        if(running) {
			/*
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			*/
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            g.setColor(Color.WHITE);
            g.fillRect(obstacleX,obstacleY,UNIT_SIZE,UNIT_SIZE);

            g.setColor(Color.CYAN);
            g.fillRect(obstacle2X,obstacle2Y,UNIT_SIZE,UNIT_SIZE);

           //g.setColor(Color.ORANGE);
          // g.fillRect(obstacle3X,obstacle3Y,UNIT_SIZE,UNIT_SIZE);

            for(int i = 0; i< bodyParts;i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(45,180,0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (WIDTH_SIZE - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }

    }
    public void  newObstacle (){
        obstacleX = random.nextInt((int)(WIDTH_SIZE /UNIT_SIZE))*UNIT_SIZE;
        obstacleY = random.nextInt((int)(HEIGHT_SIZE /UNIT_SIZE))*UNIT_SIZE;

    }
    public void  newObstacle2 (){
        obstacleX = random.nextInt((int)(WIDTH_SIZE /UNIT_SIZE))*UNIT_SIZE;
        obstacleY = random.nextInt((int)(HEIGHT_SIZE /UNIT_SIZE))*UNIT_SIZE;

    }
    public void  newObstacle3 (){
        obstacleX = random.nextInt((int)(HEIGHT_SIZE /UNIT_SIZE))*UNIT_SIZE;
        obstacleY = random.nextInt((int)(WIDTH_SIZE /UNIT_SIZE))*UNIT_SIZE;

    }


    /**
     * Method wich generate apple on random space in game
     */
    public void newApple(){
         appleX = random.nextInt((int)(WIDTH_SIZE /UNIT_SIZE))*UNIT_SIZE;
         appleY = random.nextInt((int)(HEIGHT_SIZE /UNIT_SIZE))*UNIT_SIZE;
    }

    /**
     * Method which implements controllers of game
     */
    public void move(){
        for(int i = bodyParts;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    /**
     * Method wich chack if snake hits the apple to calculate score
     * and lengthen the body of the snake
     */
    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten+=10;
            newApple();
        }
    }
    /*public void checkObstacle(Graphics g) {
        if((x[0] == obstacleX) && (y[0] == obstacleY)) {
           gameOver(g);
        }
    }*/

    public void checkCollisions() {
        //checks if head collides with body
        for(int i = bodyParts;i>0;i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                running = false;
            }
        }
        //check if head touches left border
        if(x[0] < 0) {
            running = false;
        }
        //check if head touches right border
        if(x[0] > WIDTH_SIZE) {
            running = false;
        }
        //check if head touches top border
        if(y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if(y[0] > HEIGHT_SIZE) {
            running = false;
        }
        //check if snake hit white  obstacle to rebirth
        if((x[0] == obstacleX) && (y[0] == obstacleY)) {
            applesEaten+=15;
            running=false;
        }
        if((x[0] == obstacle2X) && (y[0] == obstacle2Y)) {

            running=false;
        }


        if(!running) {
            timer.stop();
        }
    }

    /**
     * Method which displays in a text box the score of the snake and a message about the end of the game
     * and bonus text box if she hits white obstacle
     * @param g Drawing text
     */
    public void gameOver(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (WIDTH_SIZE - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Serif",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (WIDTH_SIZE - metrics2.stringWidth("Game Over"))/2, HEIGHT_SIZE /2);
         //Review Text  if hits obstacle
        if(((x[0] == obstacleX) && (y[0] == obstacleY))) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Serif", Font.BOLD, 25));
            FontMetrics metrics3 = getFontMetrics(g.getFont());
            g.drawString("You hit white obstacle click space to continue .....", (WIDTH_SIZE - metrics3.stringWidth("You hit white obstacle click space to continue ..... ")) / 2, HEIGHT_SIZE / 3);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    /**
     * Method which  controls  moves of snake (up, down , right or left )
     * and space button which rebirth snake if it  hits white obstacle and game continues...
     */


    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE)
            {

                startGame();
                running = true;
                repaint();

            }

            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
