import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A short-ranged enemy that will track the player and swipe/scratch in front 
 * of itself to deal damage.
 * 
 * @author Marco Luong, Harishan Ganeshanathan
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
        range = 0;
        atkCD = 90;
        atkTimer = atkCD;
        
        lungeCD = 240;
        lungeTimer = lungeCD;
    }
    
    // Tracks player and attack cooldown will reset without 
    // needing to be in range of the player.
    public void act()
    {
        // If lunge is ready, increase range of player detection
        if(lungeTimer <= 0 && !spdUp){
            range = 3;
        }
        
        if(beenAttacked){
            if(direction == 1){
                setImage("CatRDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 2){
                setImage("CatLDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 3){
                setImage("CatUDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 4){
                setImage("CatDDamage.png"); 
                getImage().scale(100,100);
            }
            damagedTimer++; 
            if(damagedTimer == 15){
                damagedTimer = 0;
                beenAttacked = false;
            }
        }
        else{
            animate(direction - 1);
        }
        
        trackPlayer();
        super.act();
        atkTimer--;
        lungeTimer--;        
    }
    
    // Adds an image in front of the enemy to check if the player has been hit.
    public void attack(){
        GameWorld gw = (GameWorld)getWorld();        
        if(atkTimer<=0 && range == 0){ // Only attack if in normal range
            moving = false;
            EnemyMelee em = new EnemyMelee(meleeRadius, this); 
            gw.addObject(em, this.getX(), this.getY());
            
            // resetting variables
            attacking = false;
            atkTimer = atkCD;
            
            if(spdUp){ // Reset speeds if attack was a lunge attack
                spdUp = false;
                spd /= 3;
                lungeTimer = lungeCD;
            }
        }
        
        if(!spdUp && lungeTimer <= 0){ // Increase the movement speed for a split second
            attacking = true;
            spdUp = true;
            spd *= 3;
            range = 0;
        }
    }
}
