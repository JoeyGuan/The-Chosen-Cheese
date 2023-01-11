import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (Harishan Ganeshanathan) 
 * @version (January 10th)
 */
public class Player extends SmoothMover
{
    private int direction = 2; //1 = left, 2 = right, 3 = up, 4 = down
    //flag variables
    private boolean ranged = true;
    private boolean attacked;
    private boolean attackSwitched = false;
    private int attackSwitchTimer; 
    private int rangeTimer; 
    private int meleeTimer; 
    
    //upgradable stats
    private double speed = 10;
    private int meleeRadius = 10; 
    private double projectileSpeed = 2;
    private int meleeSpeed = 90; //attack resets every 4 seconds
    private int rangeSpeed = 180; 
    private double attackPower = 10; 
    private double durability = 0; //damage reduction variable
    private double health = 20; 
    public Player()
    {
        setImage("wombat.png");
        attacked = false;
        rangeTimer = 0;
        meleeTimer = 0; 
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
        //timer for attacks
        if(attacked){
            if(ranged){
                rangeTimer++;
            }
            else{
                meleeTimer++;
            }
        }
        if(ranged){
            if(rangeTimer>=rangeSpeed){
                attacked = false; 
                System.out.println("ranged ready");
                rangeTimer = 0; 
            }
        }
        if(!ranged){
            if(meleeTimer>=meleeSpeed){
                attacked = false;
                System.out.println("melee ready");
                meleeTimer = 0;
            }
        }
        //switch/flag for attack type switching
        if(attackSwitched){
            attackSwitchTimer++;
        }
        if(attackSwitchTimer>=150){
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
                    RangedProjectile rp = new RangedProjectile(projectileSpeed, direction);
                    gw.addObject(rp, this.getX(), this.getY()); 
                }
                else{
                    MeleeAttack ma = new MeleeAttack(meleeRadius); 
                    if(direction == 1){
                        gw.addObject(ma, this.getX()-20, this.getY()); 
                    } else if(direction == 2){
                        gw.addObject(ma, this.getX()+20, this.getY()); 
                    } else if(direction == 3){
                        gw.addObject(ma, this.getX(), this.getY()-20); 
                    }else if(direction == 4){
                        gw.addObject(ma, this.getX(), this.getY()+20); 
                    }
                    
                }
                attacked = true; 
            }
        }
    }
    public void switchAttack(){
        if(!attackSwitched){
            if(Greenfoot.isKeyDown("X")){
                if(ranged){
                    ranged = false;
                    System.out.println("melee");
                }
                else if(!ranged){
                    ranged = true; 
                    System.out.println("Ranged");
                }
                attackSwitched = true;
            }
        }
    }
    public double getSpeed(){
        return speed; 
    }
    public void setSpeed(double spd){
        this.speed = spd; 
    }
    public double getAttackPower(){
        return attackPower;
    }
    public void setAttackPower(double attackPwr){
        this.attackPower = attackPwr; 
    }
    public double getDurability(){
        return durability;
    }
    public void setDurability(double dura){
        this.durability = dura; 
    }
    public double getHealth(){
        return health; 
    }
    public void setHealth(double health){
        this.health = health; 
    }
}
