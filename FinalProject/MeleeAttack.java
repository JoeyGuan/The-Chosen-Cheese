// import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

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
    protected boolean animated;
    public MeleeAttack(int attackRange){
        this.attackRange = attackRange; 
        this.setImage("button-green.png");
        animationOffset = 0; 
        animated = false;
        this.getImage().scale(attackRange, attackRange); 
    }
}
