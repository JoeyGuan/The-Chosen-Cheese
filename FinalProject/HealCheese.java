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
            for(Player p: getIntersectingObjects(Player.class)){
                  p.setHealth(p.getHealth()+6); 
                  System.out.println("Player health: "+p.getHealth()); 
            }
            getWorld().removeObject(this); 
        }
    }
}
