import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

 

/**
 * Write a description of class MeleeAttack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MeleeAttack extends Attack
{
    /**
     * Act - do whatever the MeleeAttack wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int attackRange;
    private int animationOffset;  
    private Player p; 
    GreenfootImage[] swingAnimation = new GreenfootImage[12];
    
    SimpleTimer animationTimer = new SimpleTimer();

    private boolean animated; 

    private int horiOffset =0; 
    private int vertiOffset =0; 
    public MeleeAttack(int attackRange, Player p){
        this.attackRange = attackRange; 
        this.setImage("button-green.png");
        this.p = p; 
        animationOffset = 0; 
        
        animated = false;
        this.getImage().scale(attackRange, attackRange); 
        
        animationTimer.mark();
        for(int i = 0; i <swingAnimation.length; i++){
            swingAnimation[i] = new GreenfootImage ("swordSwing_"+ i +".png");
            swingAnimation[i].scale(200,100);
            //swingAnimation[i].offsetX(100);
        }
    }
    int imageIndex = 0;
    
    public GreenfootImage[] animationCreation(String s){
        for(int i = 0; i <swingAnimation.length; i++){
            swingAnimation[i] = new GreenfootImage (s + i +".png");
            swingAnimation[i].scale(200,100);
            //swingAnimation[i].offsetX(100);
        }
    
    
        return swingAnimation;
    }
    
    
    
    public void animateSwing(GreenfootImage[] animation){
        
        if(animationTimer.millisElapsed() < 25){
            return;
        }
        animationTimer.mark();
        
       
        setImage(animation[imageIndex]);
        

        imageIndex = (imageIndex + 1) % swingAnimation.length;
        
    
    }
    
    public void act()
    {
           //animation
        System.out.println("animated");
        animateSwing(animationCreation("swordSwing_"));
 
        
       if(p.getDirection() == 1){
            horiOffset = -25;
            vertiOffset = 0; 
       }else if(p.getDirection()==2){
            horiOffset = 25;
            vertiOffset = 0; 
       }else if(p.getDirection() == 3){
            vertiOffset = -20; 
            horiOffset = 0;
       }else if(p.getDirection() == 4){
            vertiOffset = 20; 
            horiOffset = 0;
       }
       setLocation(p.getX()+horiOffset, p.getY()+vertiOffset); 
       if(!animated){
           //animation
           animated = true; 
       }
       animationOffset++;
       if(animationOffset>=10){
           for(Enemies e : getObjectsInRange(attackRange, Enemies.class)){
               GameWorld w = (GameWorld)getWorld();
               String[] v = w.getArrValues(); 
               double dmg = Double.parseDouble(v[6]); 
               e.takeDamage(dmg); 
           }
           p.setAttackStatus(false); 
           getWorld().removeObject(this);
       }
       
    }
}
