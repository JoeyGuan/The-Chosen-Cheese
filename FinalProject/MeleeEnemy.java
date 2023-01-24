import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A short-ranged enemy that will track the player and swipe/scratch in front 
 * of itself to deal damage.
 * 
 * @author Marco Luong
 */
public class MeleeEnemy extends Enemies
{
    // Radius of the attack
    private int meleeRadius = 50;
    
    // Lunge attack variables
    private int lungeCD, lungeTimer;
    private boolean spdUp = false;
    
    // Main constructor
    public MeleeEnemy(int hp, int spd, double atkDmg){
        super(hp, spd, atkDmg, "Cat");
        range = 1;
        atkCD = 90;
        atkTimer = atkCD;
        
        lungeCD = 180;
        lungeTimer = lungeCD;
    }
    
    // Tracks player and attack cooldown will reset without 
    // needing to be in range of the player.
    public void act()
    {
        if(lungeTimer <= 0 && !spdUp){
            range = 3;
        }
        
        trackPlayer();
        super.act();
        atkTimer--;
        lungeTimer--;        
    }
    
    // Adds an image in front of the enemy to check if the player has been hit.
    public void attack(){
        GameWorld gw = (GameWorld)getWorld();        
        if(atkTimer<=0 && range == 1){
            moving = false;
            EnemyMelee em = new EnemyMelee(meleeRadius, this); 
            gw.addObject(em, this.getX(), this.getY());
            
            // resetting variables
            attacking = false;
            atkTimer = atkCD;
            
            if(spdUp){
                spdUp = false;
                spd /= 3;
                lungeTimer = lungeCD;
            }
        }
        
        if(!spdUp && lungeTimer <= 0){
            attacking = true;
            spdUp = true;
            spd *= 3;
            range = 1;
        }
    }
}
