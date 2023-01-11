import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RangedProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RangedProjectile extends SmoothMover
{
    /**
     * Act - do whatever the RangedProjectile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private double speed = 0;
    private int direction = 0; 
    public RangedProjectile(double speed, int facing){
        this.getImage().scale(25,25);
        this.speed = speed;
        //1 is left, 2 is right, 3 is up, 4 is down
        this.direction = facing; 
    }
    public void act(){
        // Add your action code here.
        if(direction == 1){
            setLocation(this.getExactX()-speed, this.getExactY());  
        }
        if(direction == 2){
            move(speed); 
        }
        if(direction == 3){
            setLocation(this.getExactX(), this.getExactY()-speed); 
        }
        if(direction == 4){
            setLocation(this.getExactX(), this.getExactY()+speed); 
        }
        //if the arrow hits the end of the world, it removes itself
        if(this.getX() > getWorld().getWidth() -5 || this.getX()<5 || this.getY() < 5 || this.getY() > 695){  
            getWorld().removeObject(this);
        }
    }
}
