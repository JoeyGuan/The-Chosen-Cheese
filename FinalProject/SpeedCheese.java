import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SpeedCheese here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpeedCheese extends Cheeses
{
    public SpeedCheese(){
        setImage("apple1.png");
    }
    /**
     * Act - do whatever the SpeedCheese wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(this.isTouching(Player.class)){
            for(Player p: getIntersectingObjects(Player.class)){
                p.setSpeed(p.getSpeed()+0.5); 
            }
            getWorld().removeObject(this); 
        }
    }
}
