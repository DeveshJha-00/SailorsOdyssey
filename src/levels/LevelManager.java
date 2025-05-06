package levels;

import main.Game;
import utils.LoadSave;
import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.TILES_SIZE;


public class LevelManager {

    Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;


    public LevelManager(Game game){
        this.game=game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.getLevelData());
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.getStripeAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48]; //outside sprite atlas is 4*12
        for (int i=0; i<4; i++){
            for (int j=0; j<12; j++){
                int index = (i*12)+j;
                levelSprite[index] = img.getSubimage(j*32, i*32, 32,32);
            }
        }
    }


    public void render(Graphics g){
        for (int i=0; i< game.TILES_IN_HEIGHT; i++) {
            for (int j = 0; j < game.TILES_IN_WIDTH; j++) {
                int index = levelOne.getSpriteIndex(j, i);
                g.drawImage(levelSprite[index], j*TILES_SIZE, i*TILES_SIZE, TILES_SIZE, TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel(){
        return levelOne;
    }


}
