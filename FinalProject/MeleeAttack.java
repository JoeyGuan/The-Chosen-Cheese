import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MeleeAttack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MeleeAttack extends Attack
{
    /**
     * Act - do whatever the MeleeAttack wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int attackRange;
    private int animationOffset; 
    private boolean animated; 
    private Player p; 
    public MeleeAttack(int attackRange, Player p){
        this.attackRange = attackRange; 
        this.setImage("button-green.png");
        animationOffset = 0; 
        animated = false;
        this.p =p; 
        this.getImage().scale(attackRange, attackRange); 
    }
    public void act()
    {
       if(!animated){
           //animation
           animated = true; 
       }
       animationOffset++;
       if(animationOffset>=30){
           for(Enemies e : getObjectsInRange(attackRange, Enemies.class)){
               GameWorld w = (GameWorld)getWorld();
               String[] v = w.getArrValues(); 
               double dmg = Double.parseDouble(v[6]); 
               e.takeDamage(dmg); 
           }
           getWorld().removeObject(this);
       }
    }
}
