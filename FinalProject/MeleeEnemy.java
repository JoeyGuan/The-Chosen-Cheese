import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Melee here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MeleeEnemy extends Enemies
{
    
    
    public MeleeEnemy(int hp, int spd){
        super(hp, spd);
        attack = new GreenfootImage("placeholderEnemyAtk.png"); // change once available
        
    }
    
    /**
     * Act - do whatever the Melee wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        trackPlayer();
        move(spd);
    }
    
    // Melee will spawn an image in the direction of movement
    // If player is caught in that range, deal dmg
    public void attack(){
        
    }
}
