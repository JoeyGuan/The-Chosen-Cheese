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

    public Door()
    {

    }

    public void act()
    {
        int sideLength = GameWorld.getBlockSize();
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
    
    public boolean getIsOpen()
    {
        return isOpen;
    }

    public void setIsOpen(boolean b)
    {
        isOpen = b;
    }
}
