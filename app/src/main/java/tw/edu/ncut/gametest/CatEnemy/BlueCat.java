package tw.edu.ncut.gametest.CatEnemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/3.
 */

//see annotation on Character.java
public class BlueCat extends CatCharacter {
    private int stepSize = 1;
    private float attackSpeed = 1;
    private float timer = 0;

    public BlueCat(int heal, int attack, int x, int y, int w, int h){
        super(heal, attack, x, y, w, h);
        tag = "BLUE TEAM";
    }
    public BlueCat(Animation animation, int heal, int attack, int x, int y, int w, int h) {
        super(animation, heal, attack, x, y, w, h);
        tag = "BLUE TEAM";
        animation.start();
    }

    public void setStepSize(int stepSize){
        this.stepSize = stepSize;
    }

    public int getStepSize(){ return stepSize; }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getAttackSpeed() { return attackSpeed; }

    @Override
    protected void update(int screenWidth, int screenHeight) {
        List<Character> collisionList = this.getCollisionList();

        boolean hasTarget = false;
        for(Character c : collisionList) {
            if(c.getTag().equals("RED TEAM") && c.getState() != CharacterState.WAIT_FOR_DESTROY){
                if(animation != null) animation.playAnimation(ATTACK_ANIMATION);
                if(timer >= attackSpeed) {
                    timer = 0;
                    c.onHit(this);
                } else {
                    timer += getDelTime();
                }
                hasTarget = true;
                break;
            }
        }

        if(!hasTarget) {
            timer = 0;
            if(animation != null) animation.playAnimation(WALK_ANIMATION);
            moveLeft(stepSize);
        }
    }

    @Override
    public void onHit(Character character) {
        heal -= character.getAttack();
        if(heal <= 0) {
            this.state = CharacterState.WAIT_FOR_DESTROY;
        }
    }
}
