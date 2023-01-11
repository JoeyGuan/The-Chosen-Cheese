import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MeleeAttack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MeleeAttack extends Actor
{
    /**
     * Act - do whatever the MeleeAttack wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int attackRange;
    public MeleeAttack(int attackRange){
        this.attackRange = attackRange; 
    }
    public void act()
    {
        /*for(Enemy e : getObjectsInRange(attackRange, Enemy.class){
            //e.decreaseHealth(player.getAttackPower()); 
        }
        */
       getWorld().removeObject(this); 
    }
}
