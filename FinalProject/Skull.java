import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Skull is a popup that will appear when an enemy dies.
 * 
 * @author Clara Hong
 * @version Jan 2023
 */
public class Skull extends Actor
{
    private GreenfootImage skull = new GreenfootImage("skull.png"); 
    private int transparency = 250; 
    
    //constructor 
    public Skull() {
        skull.scale(50,50); //scale down the image
        setImage(skull); 
    }
    //Every act the transparency will decrease by 3
    public void act()
    {
        transparency -= 3; 
        GreenfootImage image = new GreenfootImage(skull); 
        //if it's lower than 10, then the object will be removed 
        if (transparency > 10) {
            image.setTransparency(transparency); 
            setImage(image); 
        } else {
            getWorld().removeObject(this); 
        }
    }
}
