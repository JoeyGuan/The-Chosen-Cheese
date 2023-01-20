import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PlayerMelee here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerMelee extends MeleeAttack
{
    private Player p;
    public PlayerMelee(int attackRange, Player p){
        super(attackRange);
        animationOffset = 0; 
        animated = false;
        this.p = p; 
    }
    
    /**
     * Act - do whatever the MeleeAttack wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
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
           for(Enemies e : getObjectsInRange(attackRange, Enemies.class)){
               e.takeDamage(p.getAttackPower()); 
           }
           getWorld().removeObject(this);
       }
           
        
    }
}
