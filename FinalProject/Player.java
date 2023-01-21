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
    private boolean isAttacking; 
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
        super("Player");
        //player stats
        this.ranged = Boolean.parseBoolean(values[0]);  
        this.meleeRadius = Integer.parseInt(values[1]); //100
        this.meleeReset = Integer.parseInt(values[2]); //90
        this.rangeReset = Integer.parseInt(values[3]); //90
        this.projectileSpeed = Double.parseDouble(values[4]); //5 
        this.speed = Double.parseDouble(values[5]); //5
        this.attackPower = Double.parseDouble(values[6]); //10 
        this.armour = Double.parseDouble(values[7]); //0
        this.health = Double.parseDouble(values[8]); //20
        
        attacked = false;
        isAttacking = false;
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
        moving = false; 
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
                attacking = false; 
                attacked = false; 
                System.out.println("ranged ready");
                rangeTimer = 0; 
            }
        }
        if(!ranged){
            if(meleeTimer>=meleeReset){
                attacking = false; 
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
        animate (direction - 1); 
        actCounter++; 
    }

    public void movement()
    {
        if(!isAttacking){
            if(Greenfoot.isKeyDown("W")) // up
            {
                Wall wall = (Wall) getOneObjectAtOffset(0, getImage().getHeight()/-2, Wall.class);
                Door door = (Door) getOneObjectAtOffset(0, getImage().getHeight()/-2, Door.class);
                
                if(wall == null || door == null || door.getIsOpen()){
                    setLocation(getX(), getY() - speed);
                }  
                direction = 3; 
                moving = true; 
            }
            if(Greenfoot.isKeyDown("A")) // left
            {
                Wall wall = (Wall) getOneObjectAtOffset(getImage().getWidth()/-2, 0, Wall.class);
                Door door = (Door) getOneObjectAtOffset(getImage().getWidth()/-2, 0, Door.class);
                
                if(wall == null || door == null || door.getIsOpen()){
                    setLocation(getX() - speed, getY());
                } 
                direction = 1;
                moving = true; 
            } 
            if(Greenfoot.isKeyDown("S")) // down
            {
                Wall wall = (Wall) getOneObjectAtOffset(0, getImage().getHeight()/2, Wall.class);
                Door door = (Door) getOneObjectAtOffset(0, getImage().getHeight()/2, Door.class);
                
                if(wall == null || door == null || door.getIsOpen()){
                    setLocation(getX(), getY() + speed);
                } 
                direction = 4;
                moving = true; 
            }
            if(Greenfoot.isKeyDown("D")) // right
            {
                Wall wall = (Wall) getOneObjectAtOffset(getImage().getWidth()/2, 0, Wall.class);
                Door door = (Door) getOneObjectAtOffset(getImage().getWidth()/2, 0, Door.class);
                
                if(wall == null || door == null || door.getIsOpen()){
                    setLocation(getX() + speed, getY());
                } 
                direction =2;
                moving = true; 
            }
        }
    }
    public void attack(){
        if(Greenfoot.isKeyDown("SPACE"))//attack
        {
            attacking = true; 
            GameWorld gw = (GameWorld)getWorld();
            if(!attacked){
                if(ranged){
                    PlayerProjectile rp = new PlayerProjectile(projectileSpeed, direction, this);
                    gw.addObject(rp, this.getX(), this.getY()); 
                }
                else{
                    PlayerMelee ma = new PlayerMelee(meleeRadius, this); 
                    if(direction == 1){
                        gw.addObject(ma, this.getX()-10, this.getY()); 
                    } else if(direction == 2){
                        gw.addObject(ma, this.getX()+10, this.getY()); 
                    } else if(direction == 3){
                        gw.addObject(ma, this.getX(), this.getY()-10); 
                    }else if(direction == 4){
                        gw.addObject(ma, this.getX(), this.getY()+10); 
                    }  
                    isAttacking = true; 
                }
                attacked = true; 
                attacking = false; //for the smooth mover animation
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
            Greenfoot.setWorld(new EndScreen()); 
        }
    }
    public int getDirection(){
        return direction; 
    }
    public void setAttackStatus(boolean b){
        isAttacking = b; 
    }
}
