import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * End Screen that the player gets switched to after they die 
 * 
 * @author (Harishan Ganeshanathan) 
 * @version (January 12)
 */
public class EndScreen extends World
{
    /**
     * Constructor for objects of class EndScreen.
     * 
     */
    public EndScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 700, 1); 
        getBackground().drawString("YOU DIED", 50, 50);
    }
}
