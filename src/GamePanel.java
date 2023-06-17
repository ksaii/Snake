import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;



public class GamePanel extends JPanel implements ActionListener {
    
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE =25;
    static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
        
    }
    public void draw(Graphics g){
        if(running){
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
        for(int i = 0;i<bodyParts;i++){
            if(i==0){
                g.setColor(Color.BLUE);
                g.fillOval(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            }else{
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            g.fillOval(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            }

        }
         g.setColor(Color.magenta);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 15);  
        g.setFont(font);
        g.drawString("Score: "+applesEaten, SCREEN_WIDTH/2, 15);
    }
    else{
        gameOver(g);
    }

    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        
    }
    public void move(){
        for(int i = bodyParts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){
            case 'U':
            y[0] = y[0]- UNIT_SIZE;
            break;
            case 'D':
            y[0] = y[0]+ UNIT_SIZE;
            break;
            case 'L' :
            x[0] = x[0] - UNIT_SIZE;
            break;
            case 'R' :
            x[0] = x[0] + UNIT_SIZE;
            break;
             
        }

    }
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
                applesEaten++;
                bodyParts++;
                newApple();
            }

    }
    public void checkCollisions(){
        for(int i = 1;i<bodyParts;i++){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running = false;
            }
        }
        if((x[0]>=SCREEN_WIDTH)||(y[0]>=SCREEN_HEIGHT)||(x[0]<0)||(y[0]<0)){
            running = false;
        }

        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.pink);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 30);  // Example: Creates a bold Sans-serif font with size 14
        g.setFont(font);
        

        g.drawString("GG SKILL ISSUE YOU SCORE WAS: "+applesEaten, 35, SCREEN_HEIGHT/2-100);

    }



    public void actionPerformed(ActionEvent e){
        if(running){
            move();
            checkApple();
            checkCollisions();

        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_A:
                direction = 'L';
                break;
                case KeyEvent.VK_D:
                direction = 'R';
                break;
                case KeyEvent.VK_S:
                direction = 'D';
                break;
                case KeyEvent.VK_W:
                direction = 'U';
                break;
            }
        }
    }


}
