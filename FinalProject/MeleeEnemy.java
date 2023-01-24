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
