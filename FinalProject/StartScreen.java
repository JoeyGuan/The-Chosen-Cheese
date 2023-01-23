import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartScreen extends World
{

    /**
     * Constructor for objects of class StartScreen.
     * 
     */
    public StartScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1300, 700, 1); 
        setBackground(new GreenfootImage("StartScreen.png"));
    }
    public void act(){
        if(Greenfoot.isKeyDown("SPACE")){
            Greenfoot.setWorld(new GameWorld()); 
        }
    }
}
