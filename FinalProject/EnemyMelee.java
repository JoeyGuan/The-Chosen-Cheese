import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Melee attack for the cat
 * 
 * @author Marco Luong
 */
public class EnemyMelee extends MeleeAttack
{
    private Enemies e;
    
    /**
     * Main constructor
     * 
     * @param e Enemy that spawns this attack
     */
    public EnemyMelee(int attackRange, Enemies e){
        super(attackRange);
        animationOffset = 0; 
        animated = false;
        this.e = e; 
    }
    
    
    public void act()
    {
       if(!animated){
           //animation
           animated = true; 
       }
       animationOffset++;
       if(animationOffset>=10){
           for(Player p : getObjectsInRange(attackRange, Player.class)){
               p.takeDamage(e.getAttackDamage());
           }
           getWorld().removeObject(this);
       }
    }
}
