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
    private double speed;
    private int meleeRadius; 
    private double projectileSpeed;
    private int meleeReset; //attack resets every 4 seconds
    private int rangeReset; 
    private double attackPower; 
    private double armour; //damage reduction variable
    private double health; 
    
    //public Player(boolean ranged, int meleeRadius, int meleeSpeed, int rangeSpeed, double projectileSpeed, double speed,  double attackPower, double armour, double health)
    public Player(String[] values)//updated player constructor using an array of strings to manage parameters 
    {
        
        setImage("wombat.png");
        //player stats
        this.ranged = Boolean.parseBoolean(values[0]);  
        this.meleeRadius = Integer.parseInt(values[1]); 
        this.meleeReset = Integer.parseInt(values[2]); 
        this.rangeReset = Integer.parseInt(values[3]);
        this.projectileSpeed = Double.parseDouble(values[4]); 
        this.speed = Double.parseDouble(values[5]); 
        this.attackPower = Double.parseDouble(values[6]); 
        this.armour = Double.parseDouble(values[7]);
        this.health = Double.parseDouble(values[8]); 
        
        attacked = false;
        rangeTimer = 0;
        meleeTimer = 0; 
        attackSwitchTimer = 0;
    }
        
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
        checkWall();
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
                    MeleeAttack ma = new MeleeAttack(meleeRadius, this); 
                    if(direction == 1){
                        gw.addObject(ma, this.getX()-10, this.getY()); 
                    } else if(direction == 2){
                        gw.addObject(ma, this.getX()+10, this.getY()); 
                    } else if(direction == 3){
                        gw.addObject(ma, this.getX(), this.getY()-10); 
                    }else if(direction == 4){
                        gw.addObject(ma, this.getX(), this.getY()+10); 
                    }
                }
                attacked = true; 
            }
        }
    }
    public void switchAttack(){
        if(!attackSwitched){
            if(Greenfoot.isKeyDown("X")){
                GameWorld w = (GameWorld)getWorld(); 
                if(ranged){
                    ranged = false;
                    String[] v = w.getArrValues(); 
                    v[0] = Boolean.toString(ranged);
                    w.setArrValues(v);
                    System.out.println("melee");
                }
                else if(!ranged){
                    ranged = true;
                    String[] v = w.getArrValues(); 
                    v[0] = Boolean.toString(ranged);
                    w.setArrValues(v);
                    System.out.println("Ranged");
                }
                attackSwitched = true;
            }
        }
    }
    public void takeDamage(double atkDmg){
        System.out.println("health: "+health); 
        System.out.println("atkDmg: "+atkDmg); 
        if(this.health - atkDmg>0){
            if(armour<atkDmg){
                GameWorld w = (GameWorld)getWorld();
                String[] v = w.getArrValues(); 
                this.health -= atkDmg; 
                v[8] = Double.toString(this.health); 
                w.setArrValues(v); 
                System.out.println("takingDamage"); 
            }
        }
        else{
            System.out.println("died"); 
            //death();
        }
    }
    public void checkWall(){
        if(getOneObjectAtOffset(5, 0, Wall.class)!=null){
            setLocation(this.getX()-speed, this.getY()); 
        }
        if(getOneObjectAtOffset(0, 5, Wall.class)!=null){
            setLocation(this.getX(), this.getY()-speed); 
        }
        if(getOneObjectAtOffset(-5, 0, Wall.class)!=null){
            setLocation(this.getX()+speed, this.getY()); 
        }
        if(getOneObjectAtOffset(0, -5, Wall.class)!=null){
            setLocation(this.getX(), this.getY()+speed); 
        }
    }
}
