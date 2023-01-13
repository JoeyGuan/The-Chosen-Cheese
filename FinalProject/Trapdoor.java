import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Trapdoor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Trapdoor extends Structures
{
    public void Door()
    {
        
    }
    /**
     * Act - do whatever the Trapdoor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        GameWorld w = (GameWorld) getWorld();
        
        if(!getIntersectingObjects(Player.class).isEmpty())
        {
            w.setGoingToNextFloor(true);
            w.setDoneSpawning(false);
        }
    }
}
