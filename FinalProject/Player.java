import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    private int speed = 10;
    
    public Player()
    {
        setImage("wombat.png");
    }
    
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        movement();
    }
    
    public void movement()
    {
        if(Greenfoot.isKeyDown("W")) // up
        {
            setLocation(getX(), getY() - speed);
        }
        if(Greenfoot.isKeyDown("A")) // left
        {
            setLocation(getX() - speed, getY());
        } 
        if(Greenfoot.isKeyDown("S")) // down
        {
            setLocation(getX(), getY() + speed);
        }
        if(Greenfoot.isKeyDown("D")) // right
        {
            setLocation(getX() + speed, getY());
        }
    }
}
