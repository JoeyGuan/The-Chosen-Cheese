import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Ranged Enemies are enemies with ranged attacks. 
 * 
 * @author (Marco Luong) 
 * @version (January 20)
 */
public class RangedEnemy extends Enemies
{
    /**
     * Simple Constructor for Melee Enemy 
     * @param hp Health of the enemy 
     * @param spd Speed of the enemy 
     * @param atkDmg Attack Damage of the enemy 
     * @param type Enemy type 
     */
    public RangedEnemy(int hp, int spd, double atkDmg){
        super(hp, spd, atkDmg, "Bird");
    }
    
    /**
     * Act - do whatever the Ranged wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
}
