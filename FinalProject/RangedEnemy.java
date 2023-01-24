import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A ranged enemy that will shoot projectiles in the player's direction.
 * 
 * @author Marco Luong
 */
public class RangedEnemy extends Enemies
{
    // Main constructor
    public RangedEnemy(int hp, int spd, double atkDmg){
        super(hp, spd, atkDmg, "Bird");
        range = 8;
        atkCD = 60;
        atkTimer = atkCD;
    }
    
    // Tracks and attacks the player
    public void act()
    {
        trackPlayer();
        super.act();
    }
    
    // Shoot a projectile once attack cooldown is up
    public void attack(){
        attacking = true;
        moving = false;
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
