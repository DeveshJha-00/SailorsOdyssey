package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

//for keeping game window (jframe)
public class GameWindow {
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();

        // title and icon
        jframe.setTitle("Sailor's Odyssey!");
//        jframe.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
//        jframe.setIconImage(ICON);

        // settings
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setResizable(false);

        //add actual game panel
        jframe.add(gamePanel);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().lostWindowFocus();
            }
        });
    }
}
