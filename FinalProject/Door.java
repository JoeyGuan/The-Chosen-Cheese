import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Door here.
 * 
 * @author Joey Guan
 * @version January 22, 2023
 */
public class Door extends Structures
{
    private boolean isOpen = true;
    private String type;

    public Door()
    {
        int sideLength = GameWorld.getBlockSize();
    }

    public void act()
    {
        GameWorld w = (GameWorld) getWorld();

        w.removeObject(getOneIntersectingObject(Wall.class));

        if(getX() == 650 && getY() == 50)//up
        {
            GreenfootImage image = new GreenfootImage("topDoor.png");
            image.scale(100,100);
            setImage(image);
        }
        if(getX() == 650 && getY() == 650)//down
        {
            GreenfootImage image = new GreenfootImage("bottomDoor.png");
            image.scale(100,100);
            setImage(image);
        }
        if(getX() == 1250 && getY() == 350)//right
        {
            GreenfootImage image = new GreenfootImage("rightDoor.png");
            image.scale(100,100);
            setImage(image);
        }
        if(getX() == 50 && getY() == 350)//left
        {
            GreenfootImage image = new GreenfootImage("leftDoor.png");
            image.scale(100,100);
            setImage(image);
        }

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
