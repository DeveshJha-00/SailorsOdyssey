package inputs;

import gameStates.GameState;
import main.GamePanel;

import java.awt.event.*;


public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.currentState){
            case PLAYING :
                gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
            default:
                System.out.println("Invalid game state");
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.currentState) {
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mousePressed(e);
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.currentState) {
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseReleased(e);
            default:
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameState.currentState) {
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(e);
            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.currentState) {
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(e);
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
