import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemyMelee here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemyMelee extends MeleeAttack
{
    private Enemies e;
    public EnemyMelee(int attackRange, Enemies e){
        super(attackRange);
        animationOffset = 0; 
        animated = false;
        this.e = e; 
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
       if(animationOffset>=10){
           System.out.println("attacked");
           for(Player p : getObjectsInRange(attackRange, Player.class)){
               p.takeDamage(e.getAttackDamage());
           }
           getWorld().removeObject(this);
       }
    }
}
