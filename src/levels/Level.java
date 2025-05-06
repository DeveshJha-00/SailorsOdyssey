package levels;

public class Level {

    private int[][] levelData;

    public Level(int[][] lvlData){
        this.levelData = lvlData;
    }

    public int getSpriteIndex(int x, int y){
        return levelData[y][x];
    }

    public int[][] getLevelData(){
        return levelData;
    }

}
