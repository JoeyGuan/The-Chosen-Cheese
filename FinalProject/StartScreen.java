import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartScreen extends World
{
    private Button b;
    /**
     * Constructor for objects of class StartScreen.
     * 
     */
    public StartScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1300, 700, 1); 
        setBackground(new GreenfootImage("StartScreen.png"));
        
        GreenfootImage background = new GreenfootImage("startButton.png");
        background.scale(background.getWidth()/8, background.getHeight()/8);
        
        b = new Button(background, background);
        addObject(b,getWidth()/2,getHeight()/2 + 100);
        b.setLocation(840,450);
    }
    
    public void act(){
        if (b.getClick()){
            Greenfoot.setWorld(new Instructions());
        }
    }
    
    
}
