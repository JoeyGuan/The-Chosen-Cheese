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
    
    public Enemies(int hp, int spd, double atkDmg){
        this.hp = hp;
        this.spd = spd;
        this.atkDmg = atkDmg; 
        hpBar = new SuperStatBar(hp, hp, this, getImage().getWidth(), hpBarHeight, - getImage().getHeight() / 2 - hpBarHeight, fillColor, barColor, false, barColor, 3);
    }
    
    public void addedToWorld(World w){
        w.addObject(hpBar, getX(), getY());
    }
    
    public void takeDamage(double dmg){
        if(hp - dmg > 0){
            hp -= dmg;
            hpBar.update(hp);
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