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
    private boolean attackSwitched = false;
    private int attackSwitchTimer; 
    private int attackTimer; 
    public Player()
    {
        setImage("wombat.png");
        attacked = false;
        attackTimer = 0; 
        attackSwitchTimer = 0;
    }

    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        movement();
        attack();
        switchAttack();
        if(attacked){
            attackTimer++;
        }
        if(attackTimer>=120){
            attacked = false;
            attackTimer = 0; 
        }
        if(attackSwitched){
            attackSwitchTimer++;
        }if(attackSwitchTimer>=150){
            attackSwitched = false;
            attackSwitchTimer = 0;
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
            GameWorld gw = (GameWorld)getWorld();
            if(!attacked){
                if(ranged){
                    RangedProjectile rp = new RangedProjectile(2, direction);
                    gw.addObject(rp, this.getX(), this.getY()); 
                    attacked = true;
                }
                else{
                    MeleeAttack ma = new MeleeAttack(10); 
                    gw.addObject(ma, this.getX(), this.getY()); 
                    attacked = true; 
                }
            }
        }
    }
    public void switchAttack(){
        if(!attackSwitched){
            if(Greenfoot.isKeyDown("X")){
                if(ranged){
                    ranged = false;
                    System.out.println("melee");
                    attackSwitched = true;
                }
                else if(!ranged){
                    ranged = true; 
                    System.out.println("Ranged");
                    attackSwitched = true;
                }
            }
        }
    }
}
