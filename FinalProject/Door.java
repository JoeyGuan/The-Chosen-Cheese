import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Gateways between different rooms. A player cannot enter through a door until all the enemies in the room have been killed
 * 
 * @author Joey Guan
 * @version January 22, 2023
 */
public class Door extends Structures
{
    private boolean isOpen = true;
    /**
     * Simple Constructor for Door
     */
    public Door()
    {
        int sideLength = GameWorld.getBlockSize();
    }

    /**
     * Simple Act Method for Door
     */
    public void act()
    {
        if(getX() == 650 && getY() == 50)//up
        {
            GreenfootImage imageTop = new GreenfootImage("topDoor.png");
            imageTop.scale(sideLength, sideLength);
            setImage(imageTop);
        }
        if(getX() == 650 && getY() == 650)//down
        {
            GreenfootImage imageBottom = new GreenfootImage("bottomDoor.png");
            imageBottom.scale(sideLength, sideLength);
            setImage(imageBottom);
        }
        if(getX() == 1250 && getY() == 350)//right
        {
            GreenfootImage imageRight = new GreenfootImage("rightDoor.png");
            imageRight.scale(sideLength, sideLength);
            setImage(imageRight);
        }
        if(getX() == 50 && getY() == 350)//left
        {
            GreenfootImage imageLeft = new GreenfootImage("leftDoor.png");
            imageLeft.scale(sideLength, sideLength);
            setImage(imageLeft);
        }

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

    /**
     * Checks if the door is open 
     * @return boolean Returns if the door is open or not 
     */
    public boolean getIsOpen()
    {
        return isOpen;
    }
    
    /**
     * Sets whether the door is open or not 
     * @param b Boolean that says whether the door is open or not
     */
    public void setIsOpen(boolean b)
    {
        isOpen = b;
    }
}
