import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Walls surround the room. Players cannot go into or past a wall. 
 * 
 * @author (Joey Guan) 
 * @version (January 13)
 */
public class Wall extends Structures
{
    /**
     * Simple Constructor for Wall 
     */
    public Wall()
    {
        GreenfootImage image = getImage();
        int sideLength = GameWorld.getBlockSize();
        image.scale(sideLength, sideLength);
        image.setTransparency(0);
        setImage(image);
    }
    /**
     * Act - do whatever the Wall wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
}
