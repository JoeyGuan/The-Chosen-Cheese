import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Ranged Projectile that is fired by a player or an enemy
 * 
 * @author (Harishan Ganeshanathan) 
 * @version (January 15)
 */
public class RangedProjectile extends Attack
{
    private double speed = 0;
    private int direction = 0; 
    /**
     * Simple Constructor for Ranged Projectile 
     * @param speed The speed of the projectile 
     * @param facing The direction that the projectile needs to be fired in, passed in as an int value
     */
    public RangedProjectile(double speed, int facing){
        this.getImage().scale(25,25);
        this.setImage("button-blue.png");
        this.speed = speed;
        //1 is left, 2 is right, 3 is up, 4 is down
        this.direction = facing; 
    }
    /**
     * Simple act method for RangedProjectile
     */
    public void act(){
        if(direction == 1){
            setLocation(this.getExactX()-speed, this.getExactY());
        }
        if(direction == 2){
            setLocation(this.getExactX()+speed, this.getExactY());
        }
        if(direction == 3){
           setLocation(this.getExactX(), this.getExactY()-speed);
        }
        if(direction == 4){
           setLocation(this.getExactX(),this.getExactY()+speed);
        }
        //if the arrow hits the end of the world or a player, it removes itself
        if(getOneIntersectingObject(Enemies.class)!=null){
            Enemies e = (Enemies)getOneIntersectingObject(Enemies.class);
            GameWorld w = (GameWorld)getWorld();
            String[] v = w.getArrValues(); 
            double dmg = Double.parseDouble(v[6]); 
            e.takeDamage(dmg); 
            getWorld().removeObject(this);
        }
        else if(isTouching(Wall.class) || isTouching(Door.class)){  
            getWorld().removeObject(this);
        }
    }
}
