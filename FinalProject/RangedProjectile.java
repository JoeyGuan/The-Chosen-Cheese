import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RangedProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RangedProjectile extends Attack
{
    /**
     * Act - do whatever the RangedProjectile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private double speed = 0;
    private int direction = 0; 
    private Player p; 
    public RangedProjectile(double speed, int facing, Player p){
        this.getImage().scale(25,25);
        this.setImage("button-blue.png");
        this.speed = speed;
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
            getWorld().removeObject(this);
        }
        else if(this.getX() > getWorld().getWidth() -5 || this.getX()<5 || this.getY() < 5 || this.getY() > 695){  
            /**
             * running into a problem where since there are two removeObject methods being called, the game returns an error
             * use try catch?
             */
            getWorld().removeObject(this);
        }
    }
}
