package utils;

import main.Game;

import java.awt.geom.Rectangle2D;

public class HelpingMethods {

    public static boolean canMoveHere(float x, float y, float width, float height, int[][] levelData){
        if (!isSolidTile(x, y, levelData)) {
            if (!isSolidTile(x+width, y+height, levelData)){
                if (!isSolidTile(x+width, y, levelData)){
                    if (!isSolidTile(x, y+height, levelData)) return true;
                }
            }
        }
        return false;
    }

    private static boolean isSolidTile(float x, float y, int[][] levelData){
        if (x<0 || x>=Game.GAME_WIDTH) return true;
        if (y<0 || y>=Game.GAME_HEIGHT) return true;

        float xIndex = x/Game.TILES_SIZE;
        float yIndex = y/Game.TILES_SIZE;

        int value = levelData[(int)yIndex][(int)xIndex];
        if (value >= 48 || value<0 || value!=11) return true;
        return false;
    }

    public static float GetEntityNextToWall(Rectangle2D.Float hitbox, float xSpeed){
        int currentTile = (int)(hitbox.x) / Game.TILES_SIZE; //current tile player is on

        if (xSpeed>0){ //collision with right
            int tileXPos = currentTile*Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
            return (tileXPos + xOffset - 1);
        }else{
            return (currentTile*Game.TILES_SIZE);
        }
    }

    public static float GetEntityUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed){
        int currentTile = (int)(hitbox.y) / Game.TILES_SIZE;
        if (airSpeed>0){
            //going down/falling/touching floor
            int tileYPos = currentTile*Game.TILES_SIZE;
            int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
            return (tileYPos + yOffset - 1);
        }else{
            //going up/jumping/under roof
            return (currentTile*Game.TILES_SIZE);
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData){

        //check pixel below bottom-left and bottom-right corners -- if not solid, we are in the air -- make entity fall
        if (!isSolidTile(hitbox.x, hitbox.y+hitbox.height + 1, levelData)){ //bottom-left corner
            if (!isSolidTile(hitbox.x+hitbox.width, hitbox.y+hitbox.height + 1, levelData)){ //bottom right
                return false; //means not on floor
            }
        }
        return true;

    }

}
