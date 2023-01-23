import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Melee here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MeleeEnemy extends Enemies
{
    private int meleeRadius = 50;
    public MeleeEnemy(int hp, int spd, double atkDmg){
        super(hp, spd, atkDmg, "Cat");
        range = 1;
        atkCD = 90;
        atkTimer = atkCD;
    }
    
    /**
     * Act - do whatever the Melee wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        atkTimer--;
    }
    
    public void attack(){
        attacking = true;
        GameWorld gw = (GameWorld)getWorld();
        
        if(atkTimer<=0){
            EnemyMelee em = new EnemyMelee(meleeRadius, this); 
            gw.addObject(em, this.getX(), this.getY()); 
            System.out.println("dealing damage"); 
            atkTimer = atkCD; 
        }
    }
}
