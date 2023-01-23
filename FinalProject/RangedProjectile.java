import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RangedProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RangedProjectile extends Attack
{
    /**
     * Act - do whatever the RangedProjectile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected double speed = 0;
    protected int direction;
    public RangedProjectile(double speed){
        this.setImage("button-blue.png");
        this.getImage().scale(25,25);
        this.speed = speed;
    }
}
