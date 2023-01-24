import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math.*;
import java.util.List;

/**
 * Write a description of class MyWorld here.
 * 
 * @author Joey Guan, Harishan Ganeshanathan, Marco Luong, Anthony Ung
 * @version January 19, 2023
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
    private int[][] cheeseMap = new int[7][7]; // -1 for no cheese, other numbers means there's that number type of cheese, set to -1 by Cheese object when it gets picked up
    
    //Booleans to make sure certain events only happen once
    private boolean dungeonGenerated = false;
    private boolean doneSpawning = false;
    private boolean cheeseSpawned = false;
    private boolean trapdoorSpawned = false; 
    private boolean goingToNextFloor = false; 
    private boolean mapAdded = false;
    
    //The room player is currently in (starting location is dungeonFloor[3][3])
    private int currentRoomX = 3;
    private int currentRoomY = 3;

    //The cell numbers that player spawns at
    private int playerX = 6;
    private int playerY = 3;
    
    private String[] values = {"false", "100", "30", "30", "5.5", "4", "8", "0", "30", "0", "30"}; 
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public GameWorld()
    {    
        super(1300, 700, 1); 
        GreenfootImage background = new GreenfootImage("backgroundnoDoor.png");
        background.scale(1300,700);
        setBackground(background);
        setPaintOrder(UI.class, Player.class, Cheese.class, MeleeAttack.class, RangedProjectile.class, Structures.class);
    }

    public void act()
    {
        if(!dungeonGenerated) generateDungeonFloor();
        if(goingToNextFloor)
        {
            if(floorDepth != maxFloorDepth)
            {
                floorDepth++;
                totalRoomAmount = 5 + (2 * floorDepth);

                generateDungeonFloor();
                currentRoomX = 3;
                currentRoomY = 3;
                playerX = 6;
                playerY = 3;
            }
            else
            {
                Greenfoot.setWorld(new EndScreen());
            }
            goingToNextFloor = false;
        }
        if(dungeonGenerated && !doneSpawning) 
        {
            spawnRoom();
        }
        roomStatusCheck();
        displayMap();
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
            if(dungeonFloor[currentRoomY][currentRoomX] == -2 && !trapdoorSpawned) //spawn trapdoor at boss room if all enemies are dead
            {
                addObject(new Trapdoor(), getXCoordinate(6), getYCoordinate(3));
                trapdoorSpawned = true;
            }
            if(cheeseMap[currentRoomY][currentRoomX] >= 0 && !cheeseSpawned) //spawn a cheese of indicated type by the cheeseMap, -1 means no cheese
            {
                addObject(new Cheese(cheeseMap[currentRoomY][currentRoomX]), getXCoordinate(6), getYCoordinate(2));
                cheeseSpawned = true;
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
                cheeseMap[i][j] = Greenfoot.getRandomNumber(4); // generates the room's cheese type that will spawn
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
        trapdoorSpawned = false;
        cheeseSpawned = false;
        enemyNumber = 2 + Greenfoot.getRandomNumber(floorDepth+1);
        //Clear Screen
        removeObjects(getObjects(Actor.class));
        //Adding UI elements
        addObject(new AttackTypeIndicator(), 50, 50);
        //Adds room layout
        int roomType = dungeonFloor[currentRoomY][currentRoomX];
        switch (roomType)
        {
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
        //Adding in Player
        addObject(new Player(values), getXCoordinate(playerX), getYCoordinate(playerY));
        //Adding in walls
        for(int i = 0; i <= 6; i++) //Left Wall
        {
            addObject(new Wall("wall"), getXCoordinate(0), getYCoordinate(i));
        }
        for(int i = 0; i <= 6; i++) //Right Wall
        {
            addObject(new Wall("wall"), getXCoordinate(12), getYCoordinate(i));
        }
        for(int i = 1; i <= 11; i++) //Top Wall
        {
            addObject(new Wall("wall"), getXCoordinate(i), getYCoordinate(0));
        }
        for(int i = 1; i <= 11; i++) //Bottom Wall
        {
            addObject(new Wall("wall"), getXCoordinate(i), getYCoordinate(6));
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
    /**
     * Spawns enemies into the world 
     */
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
                if(getObjectsAt(getXCoordinate(x), getYCoordinate(y), Actor.class).isEmpty())
                {
                    coordinateGenerated = true;
                }
            }
            int random = Greenfoot.getRandomNumber(3);
            if(random == 1)
            {
                enemyType = "melee";
            }
            else if(random == 2)
            {
                enemyType = "snake";
            }
            else
            {
                enemyType = "ranged";
            }
            if(enemyType.equals("melee"))
            {
                int hp = 15 + Greenfoot.getRandomNumber(floorDepth+1);
                int attack = 2 + Greenfoot.getRandomNumber(floorDepth+1);
                int speed = 3 + Greenfoot.getRandomNumber(floorDepth+1);
                addObject(new MeleeEnemy(hp,speed,attack), getXCoordinate(x), getYCoordinate(y));
            }
            else if(enemyType.equals("ranged"))
            {
                int hp = 8 + Greenfoot.getRandomNumber(floorDepth+1);
                double attack = 2 + Greenfoot.getRandomNumber(floorDepth+1);
                int speed = 4;
                addObject(new RangedEnemy(hp,speed,attack), getXCoordinate(x), getYCoordinate(y));
            }else if(enemyType.equals("snake"))
            {
                int hp = 8 + Greenfoot.getRandomNumber(floorDepth+1);
                double attack = 2 + Greenfoot.getRandomNumber(floorDepth+1);
                int speed = 4;
                addObject(new Snake(hp,speed,attack), getXCoordinate(x), getYCoordinate(y));
            }
        }
    }
    
    public void displayMap()
    {
        removeObjects(getObjects(Map.class));
        mapAdded = false;
        if(Greenfoot.isKeyDown("M") && mapAdded == false)
        {
            addObject(new Map(dungeonFloor), 650, 350);
            mapAdded = true;
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
    /**
     * Set doneSpawning variable
     * @param b Boolean for doneSpawning variables to be set to 
     */
    public void setDoneSpawning(boolean b)
    {
        doneSpawning = b;
    }
    /**
     * Set goingToNextFloor variable 
     * @param b Boolean to set goingToNextFloor variable to
     */
    public void setGoingToNextFloor(boolean b)
    {
        goingToNextFloor = b;
    }
    /**
     * Set player x coordinate
     * @param x New x coordinate for player 
     */
    public void setPlayerX(int x)
    {
        playerX = x;
    }
    /**
     * Set player y coordinate 
     * @param y New y coordinate for player 
     */
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
    /**
     * Marks if the cheese in the room is taken on the map 
     */
    public void markCheeseMap() // marks current room's cheese as taken
    {
        cheeseMap[currentRoomY][currentRoomX] = -1;
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
    /**
     * Get the BLOCK_SIZE variable
     * return int Return the BLOCK_SIZE variable 
     */
    public static int getBlockSize()
    {
        return BLOCK_SIZE;
    }
    /**
     * Get the currentRoomY variable
     * return int Return the currentRoomY variable 
     */
    public int getCurrentRoomY() {
        return currentRoomY; 
    }
    /**
     * Get the currentRoomX variable
     * return int Return the currentRoomX variable 
     */
    public int getCurrentRoomX() {
        return currentRoomX; 
    }
    /**
     * Get the array holding the player values 
     * @return String[] Returns the string array holding player values
     */
    public String[] getArrValues(){
        return values;
    }
    /**
     * Set the array holding the player values 
     * @param v String array with the updated player values 
     */
    public void setArrValues(String[] v){
        for(int i = 0; i<v.length;i++){
            values[i] = v[i]; 
        }
    }
    
    /**
     * Methods for adding room layouts into the world
     */

    public void room1()
    {
        addObject(new Wall("statue"), getXCoordinate(1), getYCoordinate(1));
        addObject(new Wall("statue"), getXCoordinate(1), getYCoordinate(5));
        addObject(new Wall("statue"), getXCoordinate(11), getYCoordinate(1));
        addObject(new Wall("statue"), getXCoordinate(11), getYCoordinate(5));
        addObject(new Wall("fountain"), getXCoordinate(6), getYCoordinate(3));
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
    
}
