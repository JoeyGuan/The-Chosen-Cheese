import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math.*;
/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameWorld extends World
{
    private static int BLOCK_SIZE = 100;
    private static int X_OFFSET = 50;
    private static int Y_OFFSET = 50;

    private int floorDepth = 0;
    //formula for room amount is: 3 * floorDepth + 5;
    private int totalRoomAmount = 10;

    //0 is empty, 1 is a room, 2 is boss room, 9 for starting room?
    private int[][] dungeonFloor;

    private boolean dungeonGenerated = false;
    private boolean doneSpawning = false;

    //The room player is currently in (starting location is dungeonFloor[3][3])
    private int currentRoomX = 3;
    private int currentRoomY = 3;
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public GameWorld()
    {    
        super(1300, 700, 1); 
    }

    public void act()
    {
        if(!dungeonGenerated) generateDungeonFloor();
        if(dungeonGenerated && !doneSpawning) spawnRoom();
    }

    public void spawnRoom()
    {
        //Adding in Player
        addObject(new Player(), getXCoordinate(6), getYCoordinate(3));
        //Adding in walls
        for(int i = 0; i <= 6; i++) //Left Wall
        {
            Wall wall = new Wall();
            addObject(new Wall(), getXCoordinate(0), getYCoordinate(i));
        }
        for(int i = 0; i <= 6; i++) //Right Wall
        {
            Wall wall = new Wall();
            addObject(new Wall(), getXCoordinate(12), getYCoordinate(i));
        }
        for(int i = 1; i <= 11; i++) //Top Wall
        {
            Wall wall = new Wall();
            addObject(new Wall(), getXCoordinate(i), getYCoordinate(0));
        }
        for(int i = 1; i <= 11; i++) //Bottom Wall
        {
            Wall wall = new Wall();
            addObject(new Wall(), getXCoordinate(i), getYCoordinate(6));
        }
        //Adding in doors
        if(hasNeighborUp(dungeonFloor, currentRoomX, currentRoomY))
        {
            Door doorUp = new Door();
            addObject(doorUp, getXCoordinate(6), getYCoordinate(0));
        }
        if(hasNeighborDown(dungeonFloor, currentRoomX, currentRoomY))
        {
            Door doorDown = new Door();
            addObject(doorDown, getXCoordinate(6), getYCoordinate(6));
        }
        if(hasNeighborRight(dungeonFloor, currentRoomX, currentRoomY))
        {
            Door doorRight = new Door();
            addObject(doorRight, getXCoordinate(12), getYCoordinate(3));
        }
        if(hasNeighborLeft(dungeonFloor, currentRoomX, currentRoomY))
        {
            Door doorLeft = new Door();
            addObject(doorLeft, getXCoordinate(0), getYCoordinate(3));
        }
        doneSpawning = true;
    }
    
    public void generateDungeonFloor()
    {
        dungeonFloor = new int[][]{
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,9,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0} 
        };
        int x = 3;
        int y = 3;
        int roomAmount = 1;
        while(roomAmount < totalRoomAmount)
        { 
            int direction = Greenfoot.getRandomNumber(4);
            switch (direction){
                case 0: //up
                    if(y != 0) y -= 1;
                    break;
                case 1: //right
                    if(x != 6) x += 1;
                    break;
                case 2: //down
                    if(y != 6) y += 1;
                    break;
                case 3: //left
                    if(x != 0) x -= 1;
                    break;
            }
            if(dungeonFloor[y][x] == 0)
            {
                dungeonFloor[y][x] = 1;
                roomAmount++;
            }
        }
        dungeonGenerated = true;

        //Look for room farthest away to set as boss room to progress to next floor
        int farthestX = 3;
        int farthestY = 3;
        int farthestTotalDistance = Math.abs(3 - farthestX) + Math.abs(3 - farthestY);
        for(int i = 0; i < dungeonFloor.length; i++)
        {
            for(int j = 0; j < dungeonFloor[0].length; j++)
            {
                if(dungeonFloor[i][j] == 1)
                {
                    int totalDistance = Math.abs(3 - j) + Math.abs(3 - i);
                    if(totalDistance > farthestTotalDistance)
                    {
                        farthestTotalDistance = totalDistance;
                        farthestX = j;
                        farthestY = i;
                    }
                }
            }
        }
        dungeonFloor[farthestY][farthestX] = 2;

        //Prints out floor for testing purposes
        for(int i = 0; i < dungeonFloor.length; i++)
        {
            for(int j = 0; j < dungeonFloor[0].length; j++)
            {
                System.out.print(dungeonFloor[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void moveRooms(int direction)
    {
        switch (direction){
            case 0: //up
                /*
                if(currentRoomY != 0 && dungeonFloor[currentRoomY-1][currentRoomX] != 0)
                {
                    
                }
                */
                currentRoomY--;
                break;
            case 1: //right
                currentRoomX++;
                break;
            case 2: //down
                currentRoomY++;
                break;
            case 3: //left
                currentRoomX--;
                break;
        }
    }

    public void setDoneSpawning(boolean b)
    {
        doneSpawning = b;
    }
    
    public boolean hasNeighborUp(int[][] floor, int x, int y)
    {
        if(y > 0 && floor[y-1][x] != 0)
        {
            return true;
        }
        return false;
    }

    public boolean hasNeighborDown(int[][] floor, int x, int y)
    {
        if(y < 6 && floor[y+1][x] != 0)
        {
            return true;
        }
        return false;
    }

    public boolean hasNeighborRight(int[][] floor, int x, int y)
    {
        if(x < 6 && floor[y][x+1] != 0)
        {
            return true;
        }
        return false;
    }

    public boolean hasNeighborLeft(int[][] floor, int x, int y)
    {
        if(x > 0 && floor[y][x-1] != 0)
        {
            return true;
        }
        return false;
    }

    public int[][] getDungeonFloor()
    {
        return dungeonFloor;
    }

    public static int getXCoordinate (int cellNumber){
        return (cellNumber * BLOCK_SIZE) + X_OFFSET;
    }

    public static int getXCell(int coordinate){
        return (coordinate - X_OFFSET) % BLOCK_SIZE;
    }

    public static int getYCoordinate (int cellNumber){
        return (cellNumber * BLOCK_SIZE) + Y_OFFSET;
    }

    public static int getYCell(int coordinate){
        return (coordinate - Y_OFFSET) % BLOCK_SIZE;
    }

    public static int getBlockSize()
    {
        return BLOCK_SIZE;
    }

}
