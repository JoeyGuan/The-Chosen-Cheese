import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Enemies will pathfind around the room to attack the player. There are ranged enemies and 
 * melee enemies. 
 * 
 * @author (Marco Luong) 
 * @version (January 20)
 */
public abstract class Enemies extends SmoothMover
{
    private int NUM_TILES_X = 24, NUM_TILES_Y = 14;
    private int[][] roomLayout = new int[NUM_TILES_Y][NUM_TILES_X];
    private int TILE_SIZE = 50;
    
    // Bar settings
    private int hpBarHeight = 10;
    private Color fillColor = new Color(145, 10, 10), barColor = Color.MAGENTA;
    
    // Stats - will need to implement a stat scaling system based on the 'level'
    protected int level, hp, def;
    protected double spd, atkDmg; 
    protected SuperStatBar hpBar;
    protected int attackTimer;
    protected int atkTimer = 90;
    protected GreenfootImage attack;
    /**
     * Constructor for setting enemy values 
     * @param hp Health of the enemy 
     * @param spd Speed of the enemy 
     * @param atkDmg Attack Damage of the enemy 
     * @param type Enemy type 
     */
    public Enemies(int hp, int spd, double atkDmg, String type){
        super(type);
        this.hp = hp;
        this.spd = spd;
        this.atkDmg = atkDmg; 
        this.attackTimer = 90; 
        moving = false; //animation variable 
        attacking = false; //animation variable
        hpBar = new SuperStatBar(hp, hp, this, getImage().getWidth(), hpBarHeight, - getImage().getHeight() / 2 - hpBarHeight, fillColor, barColor, false, barColor, 3);
    }
    /**
     * Added to World method
     */
    public void addedToWorld(World w){
        w.addObject(hpBar, getX(), getY());
    }
    
    //protected abstract void attack();
    /**
     * Attack method for enemies 
     */
    protected void attack(){
        if(isTouching(Player.class) && atkTimer == 0){
            atkTimer = 90;
            // return atkDmg ?
        }
        else{
            atkTimer--;
        }
    }
    /**
     * Move method for enemies 
     */
    protected void move(){
        
    }
    /**
     * Act method for enemies
     */
    public void act(){
        doDamage(); 
        animate (1); 
        actCounter++; //animation variable
    }
    /**
     * Enemy damage dealing method.
     */
    public void doDamage(){
        if(this.isTouching(Player.class)){
            attackTimer--; 
            GameWorld w = (GameWorld)getWorld(); 
            String[] v = w.getArrValues(); 
            if(attackTimer<=0){
                if(Double.parseDouble(v[8])-atkDmg >0){
                    v[8] = Double.toString(Double.parseDouble(v[8])-atkDmg); 
                    w.setArrValues(v);  
                    System.out.println("dealing damage"); 
                    System.out.println("player health: "+v[8]); 
                    attackTimer = 90; 
                }
                if(Double.parseDouble(v[8])-atkDmg<=0){ //else is not used for sequencing reasons
                    System.out.println("died"); 
                    Greenfoot.setWorld(new EndScreen()); 
                }
                attacking = true; 
            }
        }
    }
    
    // Make the world a 12x7 grid (?)
    // Each grid space will have a value:
    // Walls/Obstacles will have a value of 1
    // The player's location is will be a value of 100
    // The grid value will decrease as it goes further from the player
    // Enemies will move towards coordinates of a higher value, checking
    // in a one tile radius around themselves.
    /**
     * Method for tracking player 
     */
    protected void trackPlayer(){
        World w = (GameWorld) getWorld();
        Player player = (Player) ((ArrayList) w.getObjects(Player.class)).get(0);
        int playerX = getXCell(player.getX()), playerY = getYCell(player.getY());
        roomLayout[playerY][playerX] = 100;
        
        for(int i = 0; i < NUM_TILES_Y; i++){
            roomLayout[i][0] = 1;
            for(int j = 1; j < NUM_TILES_X - 1; j++){
                if(i == 0 || i == NUM_TILES_Y - 1){
                    roomLayout[i][j] = 1;
                }
                else{
                    roomLayout[i][j] = 100 - (Math.abs(i - playerY) + Math.abs(j - playerX));
                }
            }
            roomLayout[i][NUM_TILES_X - 1] = 1;
        }
        
        /* When obstacles are implemented
        ArrayList<Obstacle> obstacles = (ArrayList) w.getObjects(Obstacle.class);
        for(Obstacle o: obstacles){
            obstacleX = getXCell(o.getX()), obstacleY = getYCell(o.getY());
            roomLayout[obstacleY][obstacleX] = 1;
        }
        */
        
        int enemyX = getXCell(getX()), enemyY = getYCell(getY());
        roomLayout[enemyY][enemyX] = 2;
        
        int largestGrid = 0, turnToX = 0, turnToY = 0;
        for(int i = enemyY - 1; i < enemyY + 2; i++){
            for(int j = enemyX - 1; j < enemyX + 2; j++){
                if(roomLayout[i][j] > largestGrid){
                    turnToY = i;
                    turnToX = j;
                    largestGrid = roomLayout[i][j];
                }
            }
        }
        
        turnTowards(getXCoordinate(turnToX), getYCoordinate(turnToY));
    }
    /**
     * Take damage method for enemy 
     * @param dmg Damage dealt to enemy
     */
    public void takeDamage(double dmg){
        if(hp - dmg > 0){
            hp -= dmg;
            hpBar.update(hp);
            System.out.println("Enemy: Taking damage"); 
        }
        else{
            hp = 0;
            hpBar.update(hp);
            death();
        }
    }
    /**
     * Gets attack damage of enemy 
     * @return double Returns enemy Attack Damage
     */
    public double getAttackDamage(){
        return atkDmg; 
    }
    /**
     * Sets attack damage of enemy 
     * @param atkDmg New attack damage for enemy 
     */
    public void setAttackDamage(double atkDmg){
        this.atkDmg = atkDmg; 
    }
    /**
     * Death method for enemy (removes from world)
     */
    protected void death(){
        getWorld().removeObject(this);
    }
    /**
     * Returns the x coordiante of the enemy 
     * @param cellNumber Cell Number of the enemy 
     * @return int Returns the Cell number*Tile Size to get an X coordinate
     */
    private int getXCoordinate (int cellNumber){
        return (cellNumber * TILE_SIZE);
    }
    /**
     * Returns the X cell of the enemy 
     * @param coorindate Coordinate of the enemy
     * @return int Returns the Coordinate/tilesize to get the X cell
     */
    private int getXCell(int coordinate){
        return (coordinate) / TILE_SIZE;
    }
    /**
     * Returns the y coordiante of the enemy 
     * @param cellNumber Cell Number of the enemy 
     * @return int Returns the Cell number*Tile Size to get a y coordinate
     */
    private int getYCoordinate (int cellNumber){
        return (cellNumber * TILE_SIZE);
    }
    /**
     * Returns the Y cell of the enemy 
     * @param coorindate Coordinate of the enemy
     * @return int Returns the Coordinate/tilesize to get the Y cell
     */
    private int getYCell(int coordinate){
        return (coordinate) / TILE_SIZE;
    }
}
