import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Door here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Door extends Structures
{
    private boolean isOpen = true;
    private String type;
    
    public Door(/*String t*/)
    {
        //type = t;
        setImage("bluerock.jpg");
        GreenfootImage image = getImage();
        int sideLength = GameWorld.getBlockSize();
        image.scale(sideLength, sideLength);
        setImage(image);
    }
    
    public void act()
    {
        //if intersects player and isOpen, then move to other room
    }
}
