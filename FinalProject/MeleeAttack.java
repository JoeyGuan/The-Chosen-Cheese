import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

/**
 * Write a description of class MeleeAttack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class MeleeAttack extends Attack
{
    /**
     * Act - do whatever the MeleeAttack wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected int attackRange;
    protected int animationOffset; 
    
    GreenfootImage[] swingAnimation = new GreenfootImage[12];
    
    SimpleTimer animationTimer = new SimpleTimer();

    protected boolean animated; 
    
    protected int horiOffset =0; 
    protected int vertiOffset =0; 
    public MeleeAttack(int attackRange){
        this.attackRange = attackRange; 
        this.setImage("button-green.png");
        animationOffset = 0; 
        
        animated = false;
        
        
        animationTimer.mark();
        if(p.getDirection() == 1){
            setImage(new GreenfootImage("images/swordSwingLeft/swordSwing_0.png"));
            this.getImage().scale(attackRange, attackRange); 
            for(int i = 0; i <left.length; i++){
            left[i] = new GreenfootImage ("images/swordSwingLeft/swordSwing_"+ i +".png");
            left[i].scale(200,100);
            //swingAnimation[i].offsetX(100);
            }
            
        }else if(p.getDirection() == 2){
            setImage(new GreenfootImage("images/swordSwingRight/swordSwing_0.png"));
            this.getImage().scale(attackRange, attackRange);
            for(int i = 0; i <right.length; i++){
            right[i] = new GreenfootImage ("images/swordSwingRight/swordSwing_"+ i +".png");
            right[i].scale(200,100);
            //swingAnimation[i].offsetX(100);
            }
            
        }else if(p.getDirection() == 3){
            setImage(new GreenfootImage("images/swordSwingUp/swordSwing_0.png"));
            this.getImage().scale(attackRange, attackRange);
            for(int i = 0; i <up.length; i++){
            up[i] = new GreenfootImage ("images/swordSwingUp/swordSwing_"+ i +".png");
            up[i].scale(200,100);
            //swingAnimation[i].offsetX(100);
            }
        }else if(p.getDirection() == 4){
            setImage(new GreenfootImage("images/swordSwingDown/swordSwing_0.png"));
            this.getImage().scale(attackRange, attackRange);
            for(int i = 0; i <down.length; i++){
            down[i] = new GreenfootImage ("images/swordSwingDown/swordSwing_"+ i +".png");
            down[i].scale(200,100);
            //swingAnimation[i].offsetX(100);
            }
        }
    }
}
