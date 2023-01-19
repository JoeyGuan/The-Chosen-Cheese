import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HealCheese here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealCheese extends Cheeses
{
    public HealCheese(){
        setImage("muffin.png"); 
    }
    /**
     * Act - do whatever the HealCheese wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(this.isTouching(Player.class)){
            GameWorld w = (GameWorld)getWorld(); 
            String[] v = w.getArrValues(); 
            v[8] = Double.toString(Double.parseDouble(v[8])+6); 
            w.setArrValues(v); 
            System.out.println(v[8]); 
            getWorld().removeObject(this); 
        }
    }
}
