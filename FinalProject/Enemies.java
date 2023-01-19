import greenfoot.*;

/**
 * Write a description of class Enemies here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Enemies extends SmoothMover
{
    // Bar settings
    private int hpBarHeight = 10;
    private Color fillColor = new Color(145, 10, 10), barColor = Color.MAGENTA;
    
    // Stats - will need to implement a stat scaling system based on the 'level'
    protected int level, hp, def;
    protected double spd, atkDmg; 
    protected SuperStatBar hpBar;
    protected int attackTimer; 
    
    public Enemies(int hp, int spd, double atkDmg){
        this.hp = hp;
        this.spd = spd;
        this.atkDmg = atkDmg; 
        this.attackTimer = 90; 
        moving = false; //animation variable 
        attacking = false; //animation variable
        hpBar = new SuperStatBar(hp, hp, this, getImage().getWidth(), hpBarHeight, - getImage().getHeight() / 2 - hpBarHeight, fillColor, barColor, false, barColor, 3);
    }
    
    public void addedToWorld(World w){
        w.addObject(hpBar, getX(), getY());
    }
    public void act(){
        doDamage(); 
        actCounter++; //animation variable
    }
    public void doDamage(){
        GameWorld gw = (GameWorld)getWorld(); 
        Player p = gw.getObjects(Player.class).get(0);
        if(this.isTouching(Player.class)){
            attackTimer--; 
            attacking = true; 
            if(attackTimer<=0){
                p.takeDamage(atkDmg); 
                System.out.println("dealing damage"); 
                attackTimer = 90; 
                attacking = false; 
            }
        }
    }
    public void takeDamage(double dmg){
        if(hp - dmg > 0){
            hp -= dmg;
            hpBar.update(hp);
            System.out.println("Enemy: Taking damage"); 
        }
        else{
            hp = 0;
            hpBar.update(hp);
            death();
        }
    }
    public double getAttackDamage(){
        return atkDmg; 
    }
    public void setAttackDamage(double atkDmg){
        this.atkDmg = atkDmg; 
    }
    protected void death(){
        getWorld().removeObject(this);
    }
}