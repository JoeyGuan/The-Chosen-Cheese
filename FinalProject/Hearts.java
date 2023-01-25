import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Hearts here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hearts extends UI
{
    private GreenfootImage fullHeart = new GreenfootImage("FULLHEART.png");
    private GreenfootImage halfHeart = new GreenfootImage("HALFHEART.png");
    
    public Hearts(String type)
    {
        if(type.equals("full"))
        {
            setImage(fullHeart);
        }
        else if(type.equals("half"));
        {
            setImage(halfHeart);
        }
    }
    
    public void act()
    {
        
    }
}
