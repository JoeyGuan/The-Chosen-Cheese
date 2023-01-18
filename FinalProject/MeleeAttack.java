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
    private int horiOffset =0; 
    private int vertiOffset =0; 
    public MeleeAttack(int attackRange, Player p){
        this.attackRange = attackRange; 
        this.setImage("button-green.png");
        this.p = p; 
        animationOffset = 0; 
        animated = false;
        this.getImage().scale(attackRange, attackRange); 
    }
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
           getWorld().removeObject(this);
       }
       
    }
}
