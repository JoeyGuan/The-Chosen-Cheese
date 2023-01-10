import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends SmoothMover
{
    private int speed = 10;
    private double arrowSpeed = 2;
    private int direction = 2; 
    private boolean ranged = true;
    private boolean attacked;
    private boolean timerStarted;
    private Timer attackTimer; 
    public Player()
    {
        setImage("wombat.png");
        attacked = false;
        attackTimer = new Timer();
    }

    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        movement();
        attack();
        if(attackTimer.getTimeInMilliseconds() > 20){
            attacked = false;
            attackTimer.resetTimer(); 
            System.out.println("Timer reset"); 
        }
    }

    public void movement()
    {
        if(Greenfoot.isKeyDown("W")) // up
        {
            setLocation(getX(), getY() - speed);
            direction = 3; 
        }
        if(Greenfoot.isKeyDown("A")) // left
        {
            setLocation(getX() - speed, getY());
            direction = 1;
        } 
        if(Greenfoot.isKeyDown("S")) // down
        {
            setLocation(getX(), getY() + speed);
            direction = 4;
        }
        if(Greenfoot.isKeyDown("D")) // right
        {
            setLocation(getX() + speed, getY());
            direction =2;
        }
    }
    public void attack(){
        if(Greenfoot.isKeyDown("E"))//attack
        {
            if(!attacked){
                if(ranged){
                    RangedProjectile rp = new RangedProjectile(2, direction);
                    GameWorld gw = (GameWorld)getWorld();
                    gw.addObject(rp, this.getX(), this.getY()); 

                }
                else{
                    //melee attack
                }
                attacked = true; 
                attackTimer.resetTimer(); 
                System.out.println("Timer started"); 
                attackTimer.startTimer(); 
            }
        }
    }
}
