package gameStates;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods{

    private MenuButton[] menuButtons = new MenuButton[3];
    private BufferedImage backgroundImage;
    private int backgroundImageIndex = 0;


    private BufferedImage logoImage;
    private int logoX, logoY, logoWidth, logoHeight;

    private StringBuilder welcomeText = new StringBuilder();

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
//        loadLogo();
    }


    private void loadButtons() {
        menuButtons[0] = new MenuButton((int) (120 * Game.SCALE), Game.GAME_HEIGHT - 100, 0, GameState.PLAYING);
        menuButtons[1] = new MenuButton(Game.GAME_WIDTH / 2, Game.GAME_HEIGHT - 100, 1, GameState.OPTIONS);
        menuButtons[2] = new MenuButton((int) (Game.GAME_WIDTH - 120 * Game.SCALE), Game.GAME_HEIGHT - 100, 2, GameState.QUIT);
    }

    private void loadBackground() {
        backgroundImage = LoadSave.getStripeAtlas(LoadSave.MENU_BACKGROUND);
    }

    private void loadLogo() {
        logoImage = LoadSave.getStripeAtlas(LoadSave.MENU_LOGO);
        logoWidth = (int) (logoImage.getWidth() * Game.SCALE / 2.5);
        logoHeight = (int) (logoImage.getHeight() * Game.SCALE / 2.5);
        logoX = Game.GAME_WIDTH / 6 - logoWidth / 2;
        logoY = (int) (10 * Game.SCALE);
    }


    private void resetButtons() {
        for (MenuButton menubutton : menuButtons) {
            menubutton.resetBooleans();
        }
    }

    @Override
    public void update() {
        for (MenuButton menubutton : menuButtons) {
            menubutton.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(logoImage, logoX, logoY, logoWidth, logoHeight, null);
        for (MenuButton menubutton : menuButtons) {
            menubutton.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("IN MENU CLICKED MOUSE");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton menubutton : menuButtons) {
            if (isIn(e, menubutton)){
                menubutton.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton menubutton : menuButtons) {
            if (isIn(e, menubutton)){
                if (menubutton.isMousePressed()){
                    menubutton.applyGameState();
                    break;
                }
            }
        }
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton menubutton : menuButtons) {
            menubutton.setMouseOver(false);
        }
        for (MenuButton menubutton : menuButtons) {
            if (isIn(e, menubutton)){
                menubutton.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            GameState.currentState = GameState.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
