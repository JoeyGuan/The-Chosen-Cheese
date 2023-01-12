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
    private int animationOffset; 
    private boolean animated; 
    public MeleeAttack(int attackRange){
        this.attackRange = attackRange; 
        animationOffset = 0; 
        animated = false;
    }
    public void act()
    {
       if(!animated){
           //animation
           System.out.println("animated");
           animated = true; 
       }
       animationOffset++;
       if(animationOffset>=30){
           System.out.println("attacked");
           getWorld().removeObject(this);
       }
           /*for(Enemy e : getObjectsInRange(attackRange, Enemy.class){
            //e.decreaseHealth(player.getAttackPower()); 
            }
            */
        
    }
}
