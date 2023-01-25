import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Main class for enemies.
 * 
 * @author Marco Luong
 */
public abstract class Enemies extends SmoothMover
{
    // Player tracking variables
    protected int playerX = 0, playerY = 0, enemyX = 0, enemyY = 0;
    private int NUM_TILES_X = 12, NUM_TILES_Y = 7;
    protected int[][] roomLayout = new int[NUM_TILES_Y][NUM_TILES_X];
    private int TILE_SIZE = 100;
    
    // Bar settings
    private int hpBarHeight = 10;
    private Color fillColor = new Color(145, 10, 10), barColor = Color.MAGENTA;
    
    // Stats - will need to implement a stat scaling system based on the 'level'
    protected int level, hp, def;
    protected double spd, atkDmg; 
    protected int range;
    protected int atkCD, atkTimer; // cooldown as a setting, timer to actually count
    protected SuperStatBar hpBar;
    protected GreenfootImage attack;
    
    // Animation variables
    private boolean flipped = false;
    protected int direction = 2;
    protected int damagedTimer = 0; 
    protected boolean beenAttacked = false;
    
    // Main constructor
    public Enemies(int hp, int spd, double atkDmg, String type){
        super(type);
        this.hp = hp; // health
        this.spd = spd; // movement speed
        this.atkDmg = atkDmg; // damage
        hpBar = new SuperStatBar(hp, hp, this, getImage().getWidth(), hpBarHeight, - getImage().getHeight() / 2 - hpBarHeight, fillColor, barColor, false, barColor, 3);
    }
    
    public void addedToWorld(World w){
        w.addObject(hpBar, getX(), getY()); // Adds health bar
    }
    
    /**
     * Main attack method for the enemies. 
     * Attacks will differ between types of enemies.
     */
    protected abstract void attack();
    
    // Checks if player is in range and attacks. Otherwise, keep moving
    public void act(){
        if(isInRange()){
            attack();
        }
        else{
            attacking = false;
            moving = true;
            move(spd);
        }
        setRotation(0);
    }
    
    /** 
     * Look at each room as a 12x7 grid
     * Each grid space will have a value:
     * - Walls/Walls will have a value of 
     * - The player's location will be at a value of 100
     * - The grid value will decrease as it goes further from the player
     * i.e. 98 99  98
     *      99 100 99
     *      98 99  98
     */
    protected void enemyWorldSetup(){
        World w = (GameWorld) getWorld();
        Player player = (Player) ((ArrayList) w.getObjects(Player.class)).get(0);
        playerX = getXCell(player.getX());
        playerY = getYCell(player.getY());
        roomLayout[playerY][playerX] = 100;
        
        for(int i = 0; i < NUM_TILES_Y; i++){
            for(int j = 1; j < NUM_TILES_X - 1; j++){
                if(i == 0 || i == NUM_TILES_Y - 1){
                    roomLayout[i][j] = 1;
                }
                else{
                    roomLayout[i][j] = 100 - (Math.abs(i - playerY) + Math.abs(j - playerX));
                }
            }
        }
        
        ArrayList<Wall> walls = (ArrayList) w.getObjects(Wall.class);
        for(Wall wl: walls){
            int wallX = getXCell(wl.getX()), wallY = getYCell(wl.getY());
            try{
                roomLayout[wallY][wallX] = 1;
            }
            catch(ArrayIndexOutOfBoundsException e){
                if(wallY == NUM_TILES_Y){
                    roomLayout[wallY - 1][wallX] = 1;
                }
                else if(wallX == NUM_TILES_X){
                    roomLayout[wallY][wallX - 1] = 1;
                }
            }
        }
        
        enemyX = getXCell(getX());
        enemyY = getYCell(getY());
        try{
            roomLayout[enemyY][enemyX] = 2;
        }
        catch(ArrayIndexOutOfBoundsException e){
            roomLayout[enemyY - 1][enemyX - 1] = 2;
        }
    }
    
    /** 
     * Enemies will move towards coordinates of a higher value, checking
     * in a one tile radius around themselves for the highest value.
     */ 
    protected void trackPlayer(){
        enemyWorldSetup();
        int largestGrid = 0, turnToX = 0, turnToY = 0;
        for(int i = enemyY - 1; i < enemyY + 2; i++){
            for(int j = enemyX - 1; j < enemyX + 2; j++){
                try{
                    if(roomLayout[i][j] > largestGrid){
                        turnToY = i;
                        turnToX = j;
                        largestGrid = roomLayout[i][j];
                    }
                }
                catch(ArrayIndexOutOfBoundsException e){
                    continue;
                }
            }
        }
        
        turnTowards(getXCoordinate(turnToX), getYCoordinate(turnToY));
        if(((getRotation() > 90  && getRotation() < 270) && !flipped) || (flipped && (getRotation() > 270 || getRotation() < 90))){
            flipped = !flipped;
        }
        
        if(flipped){
            direction = 1; // facing left
        }
        else{
            direction = 2; // right
        }
    }
    
    protected boolean isInRange(){
        int dist = Math.abs(playerX - enemyX) + Math.abs(playerY - enemyY);
        if(dist <= range){
            return true;
        }return false;
    }
    
    /**
     * Method for player to deal dmg to enemies
     */
    public void takeDamage(double dmg){
        if(hp - dmg > 0){
            hp -= dmg;
            hpBar.update(hp);
            System.out.println("Enemy: Taking damage"+dmg); 
            beenAttacked = true; 
        }
        else{
            hp = 0;
            hpBar.update(hp);
        }
    }
    
    
    public double getAttackDamage(){
        return atkDmg; 
    }
    public void setAttackDamage(double atkDmg){
        this.atkDmg = atkDmg; 
    }
    public double getHp(){
        return hp; 
    }
    protected void death(){ // Run if hp <= 0
        getWorld().removeObject(this);
    }
    private int getXCoordinate (int cellNumber){
        return (cellNumber * TILE_SIZE);
    }    
    private int getXCell(int coordinate){
        return (coordinate) / TILE_SIZE;
    }
    private int getYCoordinate (int cellNumber){
        return (cellNumber * TILE_SIZE);
    }
    private int getYCell(int coordinate){
        return (coordinate) / TILE_SIZE;
    }
}
