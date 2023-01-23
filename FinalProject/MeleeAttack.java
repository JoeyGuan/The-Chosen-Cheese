import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

/**
 * Write a description of class MeleeAttack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class MeleeAttack extends Attack
{
    /**
     * Act - do whatever the MeleeAttack wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected int attackRange;
    protected int animationOffset; 
    
    GreenfootImage[] swingAnimation = new GreenfootImage[12];
    
    SimpleTimer animationTimer = new SimpleTimer();

    protected boolean animated; 
    
    protected int horiOffset =0; 
    protected int vertiOffset =0; 
    public MeleeAttack(int attackRange){
        this.attackRange = attackRange; 
        /*this.getImage().scale(attackRange, attackRange); 
        
        animationTimer.mark();
        for(int i = 0; i <swingAnimation.length; i++){
            swingAnimation[i] = new GreenfootImage ("swordSwing_"+ i +".png");
            swingAnimation[i].scale(200,100);
            //swingAnimation[i].offsetX(100);
        }*/
    }
}
