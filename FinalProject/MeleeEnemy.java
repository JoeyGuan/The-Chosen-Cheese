import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Melee Enemies are enemies with melee attacks. 
 * 
 * @author (Marco Luong) 
 * @version (January 20)
 */
public class MeleeEnemy extends Enemies
{
    /**
     * Simple Constructor for Melee Enemy 
     * @param hp Health of the enemy 
     * @param spd Speed of the enemy 
     * @param atkDmg Attack Damage of the enemy 
     * @param type Enemy type 
     */
    public MeleeEnemy(int hp, int spd, double atkDmg){
        super(hp, spd, atkDmg, "Cat");
    }
    
    /**
     * Act - do whatever the Melee wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
}
