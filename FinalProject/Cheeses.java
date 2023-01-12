import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Cheeses here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cheeses extends Actor
{
    /**
     * Act - do whatever the Cheeses wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
public abstract class Enemies extends Actor
{
    // Bar settings
    private int hpBarHeight = 10;
    private Color fillColor = new Color(145, 10, 10), barColor = Color.MAGENTA;
    
    // Stats - will need to implement a stat scaling system based on the 'level'
    protected int level, hp, spd, atkDmg, def;
    protected SuperStatBar hpBar;
    
    public Enemies(int hp, int spd){
        this.hp = hp;
        this.spd = spd;
        
        hpBar = new SuperStatBar(hp, hp, this, getImage().getWidth(), hpBarHeight, - getImage().getHeight() / 2 - hpBarHeight, fillColor, barColor, false, barColor, 3);
    }
    
    public void addedToWorld(World w){
        w.addObject(hpBar, getX(), getY());
    }
    
    public void takeDamage(int dmg){
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
    
    protected void death(){
        getWorld().removeObject(this);
    }
}
