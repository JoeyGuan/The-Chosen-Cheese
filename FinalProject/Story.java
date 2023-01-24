import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Story World is where the storyline slideshow for the game will play. 
 * The backgrounds will flip and show the sequences of the game's story. 
 * 
 * @author Clara Hong
 * @version Jan 23
 */
public class Story extends World
{
    private GreenfootImage[] slides = new GreenfootImage[6]; 
    private int actCounter = 0; 
    private int currentSlide = 0; 
    
    public Story()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1300, 700, 1); 
        initGraphics();         
    }
    
    //every 200 acts, the image will change 
    public void act() {
        if (actCounter % 200 == 0) {
            if (currentSlide == 6) {
                
            } else {
                setBackground(slides[currentSlide]); 
                currentSlide++; 
            }
        }
        actCounter++; 
    }
    
    //fill an array with the pictures used as the slides
    public void initGraphics() {
        for (int i = 0; i < slides.length; i++) {
            slides[i] = new GreenfootImage("Story" + i + ".png"); 
        }
    }
}
