package main;

import gameStates.GameState;
import gameStates.Playing;
import gameStates.Menu;


import java.awt.*;

//everything will start
public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS=120;
    private final int UPS=200;

    private Playing playing;
    private Menu menu;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.25f;
    public final static int TILES_SIZE= (int)(TILES_DEFAULT_SIZE*SCALE);
    public final static int TILES_IN_WIDTH=26;
    public final static int TILES_IN_HEIGHT=14;
    public final static int GAME_WIDTH = TILES_SIZE*TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE*TILES_IN_HEIGHT;

    public Game(){
        initializeClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
//        gamePanel.requestFocus();
        gamePanel.requestFocusInWindow();

        startGameLoop();
    }

    private void initializeClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        switch (GameState.currentState){
            case MENU :
                menu.update();
                break;
            case PLAYING :
                playing.update();
                break;
            default:
                System.out.println("Invalid game state");
                break;
        }
    }

    public void render(Graphics g){
        switch (GameState.currentState){
            case MENU :
                menu.draw(g);
                break;
            case PLAYING :
                playing.draw(g);
                break;
            default:
                System.out.println("Invalid game state");
                break;
        }

    }

    @Override
    public void run() {
        double timePerFrame = 1000000000/FPS;
        double timePerUpdate = 1000000000/UPS;

        long previousTime = System.nanoTime();

        int frames=0, updates=0;
        long lastCheck=System.currentTimeMillis();

        double deltaF=0, deltaU=0;

        while (true){
            long currentTime = System.nanoTime();
            deltaF += (currentTime - previousTime) / timePerFrame;
            deltaU += (currentTime - previousTime) / timePerUpdate;
            previousTime = currentTime;
            if (deltaU >= 1){
                update();
                updates++;
                deltaU--;
            }
            if (deltaF >= 1){
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck=System.currentTimeMillis();
                System.out.println("FPS : "+ frames + " | UPS : "+ updates);
                frames=0;
                updates=0;
            }

        }


    }

    public void lostWindowFocus() {
        if (GameState.currentState == GameState.PLAYING){
            playing.getPlayer().resetAllBooleans();
            playing.lostWindowFocus();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

}
