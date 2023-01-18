import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DuraCheese here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ArmourCheese extends Cheeses
{
    public ArmourCheese(){
        setImage("hamburger.png");
    }
    /**
     * Act - do whatever the DuraCheese wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(this.isTouching(Player.class)){
            GameWorld w = (GameWorld)getWorld(); 
            String[] v = w.getArrValues(); 
            v[7] = Double.toString(Double.parseDouble(v[7])+2); 
            w.setArrValues(v); 
            getWorld().removeObject(this); 
        }
    }
}
