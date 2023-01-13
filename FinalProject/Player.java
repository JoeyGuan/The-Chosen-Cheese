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
    private boolean ranged;
    private boolean attacked;
    private boolean attackSwitched = false;
    private int attackSwitchTimer; 
    private int rangeTimer; 
    private int meleeTimer; 
    
    //upgradable stats
    private double speed = 5;
    private int meleeRadius = 50; 
    private double projectileSpeed = 2;
    private int meleeReset = 90; //attack resets every 4 seconds
    private int rangeReset = 180; 
    private double attackPower = 10; 
    private double armour = 0; //damage reduction variable
    private double health = 20; 
    
    //public Player(boolean ranged, double speed, int meleeRadius, double projectileSpeed, int meleeSpeed, int rangeSpeed, double attackPower, double armour, double health)
    /**public Player(String[] values)
    {
        
        setImage("wombat.png");
        //player stats
        this.ranged = ranged; 
        this.speed = speed; 
        this.meleeRadius = meleeRadius; 
        this.projectileSpeed = projectileSpeed; 
        this.meleeReset = meleeSpeed; 
        this.rangeReset = rangeSpeed;
        this.attackPower = attackPower; 
        this.armour = armour;
        this.health = health; 
        
        attacked = false;
        rangeTimer = 0;
        meleeTimer = 0; 
        attackSwitchTimer = 0;
    }**/
    public Player(){ //default constructor for now (january 11)
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
        //checkWall();
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
            if(rangeTimer>=rangeReset){
                attacked = false; 
                System.out.println("ranged ready");
                rangeTimer = 0; 
            }
        }
        if(!ranged){
            if(meleeTimer>=meleeReset){
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
                    RangedProjectile rp = new RangedProjectile(projectileSpeed, direction, this);
                    gw.addObject(rp, this.getX(), this.getY()); 
                }
                else{
                    MeleeAttack ma = new MeleeAttack(meleeRadius); 
                    if(direction == 1){
                        gw.addObject(ma, this.getX()-10, this.getY()); 
                    } else if(direction == 2){
                        gw.addObject(ma, this.getX()+10, this.getY()); 
                    } else if(direction == 3){
                        gw.addObject(ma, this.getX(), this.getY()-10); 
                    }else if(direction == 4){
                        gw.addObject(ma, this.getX(), this.getY()+10); 
                    }
                    
                    MeleeAttack ma = new MeleeAttack(meleeRadius, this); 
                    gw.addObject(ma, this.getX(), this.getY()); 
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
    public void takeDamage(double atkDmg){
        if(health-atkDmg>0){
            if(armour<atkDmg){
                this.health -= atkDmg; 
                System.out.println("takingDamage"); 
            }
        }
        else{
            death();
        }
    }
    public void death(){
        Greenfoot.setWorld(new EndScreen()); 
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
    public double getArmour(){
        return armour;
    }
    public void setArmour(double dura){
        this.armour = dura; 
    }
    public double getHealth(){
        return health; 
    }
    public void setHealth(double health){
        this.health = health; 
    }
}
