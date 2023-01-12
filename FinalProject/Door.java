import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Door here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Door extends Structures
{
    private boolean isOpen = true;
    private String type;
    
    public Door(/*String t*/)
    {
        //type = t;
        setImage("bluerock.jpg");
        GreenfootImage image = getImage();
        int sideLength = GameWorld.getBlockSize();
        image.scale(sideLength, sideLength);
        setImage(image);
    }
    
    public void act()
    {
        GameWorld w = (GameWorld) getWorld();
        
        w.removeObject(getOneIntersectingObject(Wall.class));
        
        if(isOpen && !getIntersectingObjects(Player.class).isEmpty())
        {
            if(getX() == 650 && getY() == 50)//up
            {
                w.moveRooms(0);
                w.setPlayerX(6);
                w.setPlayerY(5);
            }
            if(getX() == 650 && getY() == 650)//down
            {
                w.moveRooms(2);
                w.setPlayerX(6);
                w.setPlayerY(1);
            }
            if(getX() == 1250 && getY() == 350)//right
            {
                w.moveRooms(1);
                w.setPlayerX(1);
                w.setPlayerY(3);
            }
            if(getX() == 50 && getY() == 350)//left
            {
                w.moveRooms(3);
                w.setPlayerX(11);
                w.setPlayerY(3);
            }
            w.setDoneSpawning(false);
        }
    }
}
