import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DuraCheese here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DuraCheese extends Cheeses
{
    /**
     * Act - do whatever the DuraCheese wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(this.isTouching(Player.class)){
            for(Player p: getIntersectingObjects(Player.class)){
                  p.setDurability(p.getDurability()+2); 
            }
            getWorld().removeObject(this); 
        }
    }
}
