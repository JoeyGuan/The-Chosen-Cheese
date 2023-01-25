import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TrapDoor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Trapdoor extends Structures
{
    /**
     * Act - do whatever the TrapDoor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage img = new GreenfootImage("TrapDoor.png");
    public Trapdoor(){
        img.scale(75,75);
        setImage(img); 
    }
    public void act()
    {
        GameWorld w = (GameWorld)getWorld();
        if(!getIntersectingObjects(Player.class).isEmpty()){
            w.setGoingToNextFloor(true);
            w.setDoneSpawning(false); 
        }
    }
}
