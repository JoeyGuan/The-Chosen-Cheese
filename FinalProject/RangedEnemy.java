import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ranged here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RangedEnemy extends Enemies
{
    
    public RangedEnemy(int hp, int spd, double atkDmg){
        super(hp, spd, atkDmg);
        range = 8;
        atkCD = 40;
        atkTimer = atkCD;
    }
    
    /**
     * Act - do whatever the Ranged wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
    
    public void attack(){
        GameWorld gw = (GameWorld)getWorld(); 
        Player p = gw.getObjects(Player.class).get(0);
        
        if(atkTimer<=0){
            EnemyProjectile ep = new EnemyProjectile(spd*2.0, p.getX(), p.getY(), this);
            gw.addObject(ep, this.getX(), this.getY()); 
            System.out.println("dealing damage"); 
            atkTimer = atkCD; 
        }
        atkTimer--;
    }
}
