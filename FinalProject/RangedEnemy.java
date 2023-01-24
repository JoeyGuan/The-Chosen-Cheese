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
        super(hp, spd, atkDmg, "Bird");
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
        attacking = true;
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
        }
        else{
            hp = 0;
            hpBar.update(hp);
        }
    }
}
