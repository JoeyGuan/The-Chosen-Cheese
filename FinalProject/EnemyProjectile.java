import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemyProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemyProjectile extends RangedProjectile
{
    private Enemies e; 
    private int pX, pY;
    public EnemyProjectile(double speed, int pX, int pY, Enemies e){
        super(speed);
        setImage("BirdProjectile.png");
        getImage().scale(25, 25);
        this.e = e;
        this.pX = pX;
        this.pY = pY;
    }
    
    public void addedToWorld(World w){
        turnTowards(pX, pY);        
    }
    /**
     * Act - do whatever the EnemyProjectile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        move(speed);
        //if the projectily hits a wall or door, it removes itself
        if(getOneIntersectingObject(Player.class)!=null){
            Player p = (Player)getOneIntersectingObject(Player.class);
            p.takeDamage(e.getAttackDamage()); 
            getWorld().removeObject(this);
        }
        else if(this.isTouching(Door.class)||this.isTouching(Wall.class)){ 
            getWorld().removeObject(this);
        }
    }
}
