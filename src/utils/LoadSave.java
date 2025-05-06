package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String PLAYER_ATLAS = "entities/player/player_sprites.png";
    public static final String LEVEL_ATLAS = "levels/outside_sprites.png";
    public static final String LEVEL_ONE_DATA_ATLAS= "levels/level_one_data.png";
    public static final String MENU_BUTTONS= "ui/startMenu/button_atlas.png";
    public static final String MENU_BACKGROUND= "ui/startMenu/menu_background.png";
    public static final String MENU_LOGO = "ui/startMenu/menu_background.png";

    public static BufferedImage getStripeAtlas(String atlasName){
        BufferedImage img=null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + atlasName);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return img;
    }

    public static int[][] getLevelData(){
        int [][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = getStripeAtlas(LEVEL_ONE_DATA_ATLAS);
        for (int i=0; i<img.getHeight(); i++){
            for (int j=0; j<img.getWidth(); j++){
                Color color = new Color(img.getRGB(j,i));
                int val = color.getRed();
                if (val>=48) val=0;
                lvlData[i][j] = val;
            }
        }
        return lvlData;
    }

}
