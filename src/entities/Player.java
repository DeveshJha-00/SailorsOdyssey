package entities;

import main.Game;
import utils.LoadSave;
import java.awt.*;
import java.awt.image.BufferedImage;
import static utils.Constants.PlayerConstants.*;
import static utils.HelpingMethods.*;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed=25;
    private int playerAction=IDLE;
    private boolean moving=false, attacking=false;
    private boolean left, right, up, down;
    private float playerSpeed=2.0f;

    private int[][] levelData;
    private float xDrawOffset = 21*Game.SCALE;
    private float yDrawOffset = 4*Game.SCALE;

    //Jumping/Gravity
    private boolean jump;
    private float airSpeed=0f;
    private float gravity=0.04f*Game.SCALE;
    private float jumpSpeed=-2.25f*Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f*Game.SCALE;
    private boolean inAir = false;


    public Player(float x, float y, int width, int height) {
        super(x, y, width,height);
        loadAnimations();
        initHitbox(x, y, 20*Game.SCALE, 27*Game.SCALE);
    }

    private void loadAnimations() {
        //initializing 'animations' BufferedImage
        BufferedImage playerStripe = LoadSave.getStripeAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[9][6];
        for (int i=0; i<animations.length; i++){
            for (int j=0; j<animations[i].length; j++){
                animations[i][j] = playerStripe.getSubimage(j*64,i*40, 64, 40);
            }
        }

    }

    public void render(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x-xDrawOffset), (int)(hitbox.y-yDrawOffset), width, height, null);
//        drawHitbox(g);
    }


    public void loadLevelData(int[][] levelData){
        this.levelData = levelData;
        if (!IsEntityOnFloor(hitbox, levelData)) inAir=true;
    }

    public void update(){
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    private void updatePosition() {
        moving = false;

        if (jump){
            jump();
        }
        if (!left && !right && !inAir)
            return;

        float xSpeed=0;

        if (left) xSpeed -= playerSpeed;
        if (right) xSpeed += playerSpeed;

        if (!inAir){
            if (!IsEntityOnFloor(hitbox, levelData)){
                 inAir = true;
            }
        }

        if (inAir){
            if (canMoveHere(hitbox.x, hitbox.y+airSpeed, hitbox.width, hitbox.height, levelData)){
                //can move up or down
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }else{
                hitbox.y = GetEntityUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed>0) resetInAir(); //going down and hit something --- hit the floor
                else airSpeed = fallSpeedAfterCollision;

                updateXPos(xSpeed);
            }
        }else{
            updateXPos(xSpeed);
        }

        moving = true;
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed){
            aniTick=0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)){
                aniIndex=0;
                attacking=false;
            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving) playerAction = RUNNING;
        else playerAction = IDLE;

        if (inAir){
            if (airSpeed < 0) playerAction = JUMP;
            else playerAction = FALLING;
        }

        if (attacking) playerAction=ATTACK_1;

        if (startAni != playerAction){
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick=0;
        aniIndex=0;
    }

    private void jump() {
        if (inAir) return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed=0;
    }

    private void updateXPos(float xSpeed) {
        if (canMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)){
            hitbox.x += xSpeed;
        }else{
            hitbox.x = GetEntityNextToWall(hitbox, xSpeed);
        }
    }



    public void resetAllBooleans(){
        left=false;
        right=false;
        up=false;
        down=false;
    }

    public void setAttacking(boolean attacking){
        this.attacking=attacking;
    }
    public void setJump(boolean jump){
        this.jump=jump;
    }

    //getters and setters
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }


}
