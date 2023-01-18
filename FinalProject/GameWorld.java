import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math.*;
import java.util.List;

/**
 * Write a description of class MyWorld here.
 * 
 * @author Joey Guan
 * @version (a version number or a date)
 */
public class GameWorld extends World
{
    private static int BLOCK_SIZE = 100;
    private static int X_OFFSET = 50;
    private static int Y_OFFSET = 50;

    private int floorDepth = 0;
    private int maxFloorDepth = 5;
    private int totalRoomAmount = 5 + (3 * floorDepth);

    private int enemyNumber;
    //0 is empty, 1 is a room, -1 is starting room, -2 is boss room
    private int[][] dungeonFloor;
    private int[][] dungeonMap = new int[7][7]; // keeps track of rooms that are clear. 1 for not cleared, 2 for cleared

    private boolean dungeonGenerated = false;
    private boolean doneSpawning = false;
    private boolean goingToNextFloor = false;

    //The room player is currently in (starting location is dungeonFloor[3][3])
    private int currentRoomX = 3;
    private int currentRoomY = 3;

    //The cell numbers that player spawns at
    private int playerX = 6;
    private int playerY = 3;
    
    private String[] values = {"true", "50", "90", "180", "2", "5", "10", "0", "20"}; 
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public GameWorld()
    {    
        super(1300, 700, 1); 
        setPaintOrder(Player.class, Cheeses.class, MeleeAttack.class, RangedProjectile.class, Structures.class);
    }

    public void act()
    {
        if(!dungeonGenerated) generateDungeonFloor();
        if(goingToNextFloor)
        {
            if(floorDepth != maxFloorDepth)
            {
                floorDepth++;
                totalRoomAmount = 5 + (3 * floorDepth);

                generateDungeonFloor(); 
                currentRoomX = 3;
                currentRoomY = 3;
                playerX = 6;
                playerY = 3;
            }
            else
            {
                //reaching below maxFloorDepth is win condition
                //Send to end screen with win
            }
            goingToNextFloor = false;
        }
        if(dungeonGenerated && !doneSpawning) 
        {
            spawnRoom();
        }
        roomStatusCheck();
    }
    
    public void roomStatusCheck()
    {
        List<Door> doors = getObjects(Door.class);
        if(getObjects(Enemies.class).isEmpty()) 
        {
            dungeonMap[currentRoomY][currentRoomX] = 2; // set room to cleared
            for(Door d : doors) //opens when there are none
            {
                d.setIsOpen(true);
            }
            if(dungeonFloor[currentRoomY][currentRoomX] == -2) //spawn trapdoor at boss room if all enemies are dead
            {
                addObject(new Trapdoor(), getXCoordinate(6), getYCoordinate(3));
            }
        }
        else
        {
            for(Door d : doors) // locks doors if there are enemies
            {
                d.setIsOpen(false);
            }
        }
    }
    
    public void generateDungeonFloor()
    {
        dungeonFloor = new int[][]{
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,-1,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0} 
        };
        int x = 3;
        int y = 3;
        int roomAmount = 1;
        while(roomAmount < totalRoomAmount) // generates floor in a randomly moving, snake-like way
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

        //Room characteristics setting
        //Look for room farthest away to set as boss room to progress to next floor
        int farthestX = 3;
        int farthestY = 3;
        int farthestTotalDistance = Math.abs(3 - farthestX) + Math.abs(3 - farthestY);
        for(int i = 0; i < dungeonFloor.length; i++)
        {
            for(int j = 0; j < dungeonFloor[0].length; j++)
            {
                dungeonMap[i][j] = dungeonFloor[i][j]; // copies over the floor to a map that keeps track of cleared rooms
                if(dungeonFloor[i][j] > 0)
                {
                    dungeonFloor[i][j] = 1 + Greenfoot.getRandomNumber(7); // sets room layout type (obstacles, etc.)
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
        dungeonFloor[farthestY][farthestX] = -2;

        dungeonGenerated = true;

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

    public void spawnRoom()
    {
        enemyNumber = 2 + Greenfoot.getRandomNumber(floorDepth+1);
        int roomType = dungeonFloor[currentRoomY][currentRoomX];
        switch (roomType)
        {
            case 0: 
                room0();
                break;
            case 1: 
                room1();
                break;
            case 2:
                room2();
                break;
            case 3:
                room3();
                break;
            case 4:
                room4();
                break;
            case 5:
                room5();
                break;
            case 6:
                room6();
                break;
            case 7:
                room7();
                break;
        }
        //Clear Screen
        removeObjects(getObjects(Actor.class));
        //Adding in Player
        addObject(new Player(values), getXCoordinate(playerX), getYCoordinate(playerY));
        addObject(new MeleeEnemy(20, 5, 7), 300, 300); 
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
        //Spawn enemies randomly if room hasn't been cleared before
        if(dungeonMap[currentRoomY][currentRoomX] == 1)
        {
            spawnEnemies();
        }
        doneSpawning = true;
    }
    
    public void spawnEnemies()
    {
        for(int i = 0; i < enemyNumber; i++) 
        {
            boolean coordinateGenerated = false;
            String enemyType;
            int x = 0;
            int y = 0;
            while(!coordinateGenerated)
            {
                x = 1 + Greenfoot.getRandomNumber(11);
                y = 1 + Greenfoot.getRandomNumber(5);
                if(getObjectsAt(getXCoordinate(x), getYCoordinate(y), Enemies.class).isEmpty())
                {
                    coordinateGenerated = true;
                }
            }
            if(Greenfoot.getRandomNumber(1) == 1)
            {
                enemyType = "melee";
            }
            else
            {
                enemyType = "ranged";
            }
            if(enemyType.equals("melee"))
            {
                MeleeEnemy enemy = new MeleeEnemy(1,1,1);
                addObject(enemy, getXCoordinate(x), getYCoordinate(y));
            }
            else if(enemyType.equals("ranged"))
            {
                RangedEnemy enemy = new RangedEnemy(1,1,1);
                addObject(enemy, getXCoordinate(x), getYCoordinate(y));
            }
        }
    }

    public void moveRooms(int direction)
    {
        switch (direction){
            case 0: //up
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

    public void setGoingToNextFloor(boolean b)
    {
        goingToNextFloor = b;
    }

    public void setPlayerX(int x)
    {
        playerX = x;
    }

    public void setPlayerY(int y)
    {
        playerY = y;
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
    public void playerDeath(){
        Greenfoot.setWorld(new EndScreen()); 
    }

    public void room0()
    {

    }

    public void room1()
    {

    }

    public void room2()
    {

    }

    public void room3()
    {

    }

    public void room4()
    {

    }

    public void room5()
    {

    }

    public void room6()
    {

    }

    public void room7()
    {

    }
    public String[] getArrValues(){
        return values;
    }
    public void setArrValues(String[] v){
        for(int i = 0; i<v.length;i++){
            values[i] = v[i]; 
        }
    }
}
