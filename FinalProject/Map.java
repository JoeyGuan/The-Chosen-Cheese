import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Map Class can take a 2D number array and generate a graphic where there are rooms and hallways connecting adjacent 
 * rooms. 
 * 
 * @author (Clara) 
 * @version (Jan 17th)
 */
public class Map extends UI
{
    private GreenfootImage map = new GreenfootImage (1500, 900); //the map graphic
    private int[][] numMap; 
    private int hallwayLength = 30; //length in pixels for the hallways 
    private GreenfootImage roomImage = new GreenfootImage ("mapRoom.png"); //image for an empty room 
    private GreenfootImage currentRoomImage = new GreenfootImage("currentRoom.png"); 
    
    private int[] xCoordinates = new int[7]; //x-coordinates for possible placements of rooms in the map
    private int[] yCoordinates = new int[7]; //y-coordinates for possible placements of rooms in the map
    /**
     * Simple Constructor for Map
     * @param map 2D number array of the world map to generate a minimap
     */
    public Map(int[][] map) {
        numMap = map; 
        roomImage.scale(130, 70); 
        currentRoomImage.scale(130,70); 
        generateRoomCoordinates(); 
        updateMap(); 
    }
    
    //updates the map, redraws the map
    /**
     * Method for updating and redrawing the map 
     */
    public void updateMap() {
        drawRooms(); 
        drawHallways(); 
        map.scale(750, 450); 
        setImage(map);
    }
    /**
     * Generates coordinates for the room to properly to scale the image 
     */
    public void generateRoomCoordinates() {
        for (int j = 0; j < xCoordinates.length; j++) {
            xCoordinates[j] = (j * roomImage.getWidth()) + (j * hallwayLength) + (roomImage.getWidth() / 2); 
            yCoordinates[j] = (j * roomImage.getHeight()) + (j * hallwayLength) + (roomImage.getHeight() / 2); 
        }
    }
    /**
     * Draws the rooms and their orientation on the minimap
     */
    public void drawRooms() {
        GameWorld w =  (GameWorld) getWorld(); 
        int y = w.getCurrentRoomY(); 
        int x =  w.getCurrentRoomX(); 
        map.scale(1500, 900); 
        map.clear(); 
        for (int i = 0; i < numMap.length; i++) {
            for (int j = 0; j < numMap[i].length; j++) {
                if (i == y && j == x) {
                    map.drawImage(new GreenfootImage(currentRoomImage), xCoordinates[j], yCoordinates[i]); 
                } else if (numMap[i][j] != 0) {
                    map.drawImage(new GreenfootImage(roomImage), xCoordinates[j], yCoordinates[i]); 
                }
            }
        }
    }
    /**
     * Draws a hallway that connects rooms if a hallway exists 
     */
    public void drawHallways() {
        for (int i = 0; i < numMap.length - 1; i++) {
            for (int j = 0; j < numMap[i].length - 1; j++) {
                if (numMap[i][j] != 0) {
                    if (numMap[i][j + 1] != 0) { 
                        map.fillRect(xCoordinates[j] + roomImage.getWidth(), yCoordinates[i] + roomImage.getHeight()/2, hallwayLength, hallwayLength/2); 
                    }
                    if (numMap[i + 1][j] != 0) {
                        map.fillRect(xCoordinates[j] + roomImage.getWidth()/2, yCoordinates[i] + roomImage.getHeight(), hallwayLength / 2, hallwayLength); 
                    }
                }
            }
        }
    }
}
