import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Once the player defeats the boss in the boss room, a trapdoor will appear and the player can move down to the next floor
 * 
 * @author (Joey Guan) 
 * @version (January 16)
 */
public class Trapdoor extends Structures
{   
    /**
     * Simple Constructor for Trapdoor
     */
    public void Trapdoor()
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
