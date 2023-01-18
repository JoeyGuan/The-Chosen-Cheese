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
        hpBar = new SuperStatBar(hp, hp, this, getImage().getWidth(), hpBarHeight, - getImage().getHeight() / 2 - hpBarHeight, fillColor, barColor, false, barColor, 3);
    }
    
    public void addedToWorld(World w){
        w.addObject(hpBar, getX(), getY());
    }
    public void act(){
        doDamage(); 
    }
    public void doDamage(){
        if(this.isTouching(Player.class)){
            attackTimer--; 
            GameWorld w = (GameWorld)getWorld(); 
            String[] v = w.getArrValues(); 
            if(attackTimer<=0){
                if(Double.parseDouble(v[8])-atkDmg >0){
                    v[8] = Double.toString(Double.parseDouble(v[8])-atkDmg); 
                    w.setArrValues(v);  
                    System.out.println("dealing damage"); 
                    System.out.println("player health: "+v[8]); 
                    attackTimer = 90; 
                }
                if(Double.parseDouble(v[8])-atkDmg<=0){ //else is not used for sequencing reasons
                    System.out.println("died"); 
                    w.playerDeath(); 
                }
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