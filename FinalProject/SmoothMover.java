import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.Queue; 
import java.util.LinkedList; 
/**
 * A variation of an actor that maintains a precise location (using doubles for the co-ordinates
 * instead of ints).  This allows small precise movements (e.g. movements of 1 pixel or less)
 * that do not lose precision.
 * 
 * @author Poul Henriksen
 * @author Michael Kolling
 * @author Neil Brown
 * 
 * @version 3.0
 */
public abstract class SmoothMover extends Actor
{
    private double exactX;
    private double exactY;
    
    private int direction; //direction that its facing
    private int currentDirection; //tracking of its current direction
    private GreenfootImage[][] frames; //2D array of all its frames while walking
    private Queue<GreenfootImage> animation = new LinkedList<GreenfootImage> (); //queue of the frames that are to be played
    private String actorType; //ie. "Player", "Cat", "Snake" 
    private int framesPerDirection = 0; //how many frames that are available for any given direction
    private GreenfootImage[] attackFrames; //array of the frames where it's attacking 
    
    //inherited variables - variables that need to be present in subclasses 
    protected int actCounter = 0; 
    protected boolean moving;
    protected boolean attacking; 
    
    /**
    public SmoothMover(String type) {
        actorType = type; 
        initGraphics(); 
        direction = 2; 
        setImage(frames[2][0]); 
    }
    */
    
    /**
     * Move forward by the specified distance.
     * (Overrides the method in Actor).
     */
    @Override
    public void move(int distance)
    {
        move((double)distance);
    }
    
    /**
     * Move forward by the specified exact distance.
     */
    public void move(double distance)
    {
        double radians = Math.toRadians(getRotation());
        double dx = Math.cos(radians) * distance;
        double dy = Math.sin(radians) * distance;
        setLocation(exactX + dx, exactY + dy);
    }
    
    /**
     * Set the location using exact coordinates.
     */
    public void setLocation(double x, double y) 
    {
        exactX = x;
        exactY = y;
        super.setLocation((int) (x + 0.5), (int) (y + 0.5));
    }
    
    /**
     * Set the location using integer coordinates.
     * (Overrides the method in Actor.)
     */
    @Override
    public void setLocation(int x, int y) 
    {
        exactX = x;
        exactY = y;
        super.setLocation(x, y);
    }

    /**
     * Return the exact x-coordinate (as a double).
     */
    public double getExactX() 
    {
        return exactX;
    }

    /**
     * Return the exact y-coordinate (as a double).
     */
    public double getExactY() 
    {
        return exactY;
    }
    
    public void initGraphics() {
        String[] directions = {"L", "R", "U", "D"}; 
        if (!actorType.equals("")) {
            if (actorType.equals("Player") || actorType.equals("Cat") || actorType.equals("Bird")) {
                framesPerDirection = 4; 
            } else if (actorType.equals("Snake")) {
                framesPerDirection = 1; 
            }
            frames = new GreenfootImage[4][framesPerDirection]; 
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < frames[i].length; j++) {
                    GreenfootImage image = new GreenfootImage(actorType + directions[i] + "Walk" + j + ".png"); 
                    image.scale(200,200); 
                    frames[i][j] = image; 
                }
            }
        }
    }
    
    //adds frames to the queue
    public void addFrames() {
        if (moving) {
            for (int i = 0; i < framesPerDirection; i++) {
                animation.add(frames[direction][i]); 
            }
        } 
    }
    
    //players and enemies need to call on this in their respective act methods
    public void animate(int d) {
        currentDirection = d; 
        if (currentDirection != direction) {
            direction = currentDirection; 
            animation.clear(); 
            addFrames(); 
        } else if (moving && animation.peek() == null) {
            addFrames(); 
        }
        if (actCounter % 5 == 0) {
            if (!moving) {
                animation.clear(); 
            }
            if (animation.peek() == null) {
                setImage(frames[direction][0]); 
            } else{
                setImage(animation.peek()); 
                animation.remove(); 
            }
        }
    }
}
