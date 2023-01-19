import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AttackCheese here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AttackCheese extends Cheeses
{
    public AttackCheese(){
        this.setImage("chips-2.png");
    }
    /**
     * Act - do whatever the AttackCheese wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(this.isTouching(Player.class)){
            GameWorld w = (GameWorld)getWorld(); 
            String[] v = w.getArrValues(); 
            v[6] = Double.toString(Double.parseDouble(v[6])+2); 
            w.setArrValues(v); 
            getWorld().removeObject(this); 
        }
    }
}
