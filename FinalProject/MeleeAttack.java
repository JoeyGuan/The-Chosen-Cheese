import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Melee Attack - This is a sword swing, that damages enemies within the range of the swing.
 * 
 * @author (Harishan Ganeshanathan) 
 * @version (January 17)
 */
public class MeleeAttack extends Attack
{
    private int attackRange;
    private int animationOffset; 
    private boolean animated; 
    private Player p;
    private int horiOffset =0; 
    private int vertiOffset =0; 
    /**
     * Simple Constructor 
     * @param attackRange The range of the sword swing
     * @param p The player that the sword swing belongs to
     */
    public MeleeAttack(int attackRange, Player p){
        this.attackRange = attackRange; 
        this.setImage("button-green.png");
        this.p = p; 
        animationOffset = 0; 
        animated = false;
        this.getImage().scale(attackRange, attackRange); 
    }
    /**
     * Simple Act Method - Offsets attack to be in front of the player and also damage enemies
     */
    public void act()
    {
       if(p.getDirection() == 1){
            horiOffset = -25;
            vertiOffset = 0; 
       }else if(p.getDirection()==2){
            horiOffset = 25;
            vertiOffset = 0; 
       }else if(p.getDirection() == 3){
            vertiOffset = -20; 
            horiOffset = 0;
       }else if(p.getDirection() == 4){
            vertiOffset = 20; 
            horiOffset = 0;
       }
       setLocation(p.getX()+horiOffset, p.getY()+vertiOffset); 
       if(!animated){
           //animation
           animated = true; 
       }
       animationOffset++;
       if(animationOffset>=10){
           for(Enemies e : getObjectsInRange(attackRange, Enemies.class)){
               GameWorld w = (GameWorld)getWorld();
               String[] v = w.getArrValues(); 
               double dmg = Double.parseDouble(v[6]); 
               e.takeDamage(dmg); 
           }
           p.setAttackStatus(false); 
           getWorld().removeObject(this);
       }
       
    }
}
