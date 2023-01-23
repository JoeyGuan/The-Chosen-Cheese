import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PlayerProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerProjectile extends RangedProjectile
{
    private Player p; 
    public PlayerProjectile(double speed, int facing, Player p){
        super(speed);
        this.setImage("button-blue.png");
        this.getImage().scale(25,25);
        //1 is left, 2 is right, 3 is up, 4 is down
        this.direction = facing; 
        this.p = p; 
    }
    public void act(){
        // Add your action code here.
        if(direction == 1){
            //if(Greenfoot.isKeyDown("A")){setLocation(this.getExactX()-(speed+(p.getSpeed()/1.5)), this.getExactY());}
            setLocation(this.getExactX()-speed, this.getExactY());
        }
        if(direction == 2){
            //if(Greenfoot.isKeyDown("D")){setLocation(this.getExactX()+(speed+(p.getSpeed()/1.5)), this.getExactY());}
            setLocation(this.getExactX()+speed, this.getExactY());
        }
        if(direction == 3){
           // if(Greenfoot.isKeyDown("W")){setLocation(this.getExactX(), this.getExactY()-(speed+(p.getSpeed()/1.5)));}
           setLocation(this.getExactX(), this.getExactY()-speed);
        }
        if(direction == 4){
           // if(Greenfoot.isKeyDown("S")){setLocation(this.getExactX(), this.getExactY()+(speed+(p.getSpeed()/1.5)));}
            setLocation(this.getExactX(),this.getExactY()+speed);
        }
        //if the arrow hits the end of the world, it removes itself
        if(getOneIntersectingObject(Enemies.class)!=null){
            Enemies e = (Enemies)getOneIntersectingObject(Enemies.class);
            GameWorld w = (GameWorld)getWorld();
           String[] v = w.getArrValues(); 
           double dmg = Double.parseDouble(v[6]); 
            e.takeDamage(dmg); 
            getWorld().removeObject(e); 
            getWorld().removeObject(this);
        }
        else if(this.isTouching(Door.class)||this.isTouching(Wall.class)){  
            getWorld().removeObject(this);
        }
    }
}
