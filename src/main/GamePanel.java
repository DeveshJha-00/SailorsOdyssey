package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import javax.swing.*;
import java.awt.*;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;


//drawing and rendering components
public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game){
        this.game=game;
        mouseInputs = new MouseInputs(this);

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }


    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
    }



    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame(){
        return game;
    }

}
