import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AttackTypeIndicator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AttackTypeIndicator extends UI
{
    private GreenfootImage sword = new GreenfootImage("SWORDUI.png");
    private GreenfootImage dagger = new GreenfootImage("daggerUI.png");
    
    public AttackTypeIndicator()
    {
        dagger.scale(100,100);
        sword.scale(100,100);
    }
    
    public void act()
    {
        GameWorld world = (GameWorld) getWorld();
        String[] playerInfo = world.getArrValues();
        boolean ranged = Boolean.parseBoolean(playerInfo[0]); 
        if(ranged)
        {
            setImage(dagger);
        }
        else
        {
            setImage(sword);
        }
    }
}
