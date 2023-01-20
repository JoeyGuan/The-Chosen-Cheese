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
    public MeleeAttack(int attackRange, Player p){
        this.attackRange = attackRange; 
        this.setImage("button-green.png");
        animationOffset = 0; 
   
        this.p =p; 
        
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
 
        
       animationOffset++;
       if(animationOffset>=30){
           System.out.println("attacked");
           for(Enemies e : getObjectsInRange(attackRange, Enemies.class)){
               e.takeDamage(p.getAttackPower()); 
           }
           getWorld().removeObject(this);
       }
           
        
    }
}
