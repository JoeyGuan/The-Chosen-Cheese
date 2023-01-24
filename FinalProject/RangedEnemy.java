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
        atkCD = 90;
        atkTimer = atkCD;
    }
    
    // Tracks and attacks the player
    public void act()
    {
        super.act();
        if(beenAttacked){
            if(direction == 1){
                setImage("BirdLDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 2){
                setImage("BirdRDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 3){
                setImage("BirdUDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 4){
                setImage("BirdDDamage.png"); 
                getImage().scale(100,100);
            }
            damagedTimer++; 
            if(damagedTimer == 30){
                damagedTimer = 0;
                beenAttacked = false;
            }
        }
        else{
            animate(direction-1); 
        }
        trackPlayer();
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
            atkTimer = atkCD; 
        }
        atkTimer--;
    }
    
    public void takeDamage(double dmg){
        if(hp - dmg > 0){
            hp -= dmg;
            hpBar.update(hp);
            System.out.println("Enemy: Taking damage"+dmg); 
            beenAttacked = true; 
        }
        else{
            hp = 0;
            hpBar.update(hp);
        }
    }
}
