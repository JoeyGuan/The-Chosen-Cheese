import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * End Screen that the player gets switched to after they die 
 * 
 * @author (Harishan Ganeshanathan) 
 * @version (January 12)
 */
public class EndScreen extends World
{
    private Button b;
    /**
     * Constructor for objects of class EndScreen.
     * 
     */
    public EndScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1300, 700, 1); 
        setBackground(new GreenfootImage("LoseScreen.png")); 
        
        GreenfootImage background = new GreenfootImage("playAgainButton.png");
        background.scale(background.getWidth()/8, background.getHeight()/8);
        
        b = new Button(background, background);
        addObject(b,getWidth()/2,getHeight()/2 + 100);
        b.setLocation(650,300);
    }
    
    public void act(){
        if (b.getClick()){
            Greenfoot.setWorld(new StartScreen());
        }
    }
    
    
}
