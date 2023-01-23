import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Enemies here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Enemies extends SmoothMover
{
    // Player tracking variables
    protected int playerX = 0, playerY = 0, enemyX = 0, enemyY = 0;
    private int NUM_TILES_X = 24, NUM_TILES_Y = 14;
    private int[][] roomLayout = new int[NUM_TILES_Y][NUM_TILES_X];
    private int TILE_SIZE = 50;
    
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
    
    // animation variables
    private boolean flipped = false;
    private int direction = 2;
    
    public Enemies(int hp, int spd, double atkDmg, String type){
        super(type);
        this.hp = hp;
        this.spd = spd;
        this.atkDmg = atkDmg; 
        hpBar = new SuperStatBar(hp, hp, this, getImage().getWidth(), hpBarHeight, - getImage().getHeight() / 2 - hpBarHeight, fillColor, barColor, false, barColor, 3);
    }
    
    public void addedToWorld(World w){
        w.addObject(hpBar, getX(), getY());
    }
    
    protected abstract void attack();
    
    public void act(){
        trackPlayer();
        if(isInRange()){
            moving = false;
            attack();
        }
        else{
            attacking = false;
            moving = true;
            move(spd);
        }
        setRotation(0);
        animate(direction - 1);
    }
    
    // Make the world a 12x7 grid (?)
    // Each grid space will have a value:
    // Walls/Obstacles will have a value of 1
    // The player's location is will be a value of 100
    // The grid value will decrease as it goes further from the player
    // Enemies will move towards coordinates of a higher value, checking
    // in a one tile radius around themselves.
    protected void trackPlayer(){
        World w = (GameWorld) getWorld();
        Player player = (Player) ((ArrayList) w.getObjects(Player.class)).get(0);
        playerX = getXCell(player.getX());
        playerY = getYCell(player.getY());
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
        
        enemyX = getXCell(getX());
        enemyY = getYCell(getY());
        roomLayout[enemyY][enemyX] = 2;
        
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
            direction = 1; // facing left}
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
    
    public void takeDamage(double dmg){
        if(hp - dmg > 0){
            hp -= dmg;
            hpBar.update(hp);
            System.out.println("Enemy: Taking damage"+dmg); 
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
    protected void death(){
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
