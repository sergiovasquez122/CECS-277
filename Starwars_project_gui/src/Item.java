import java.awt.*;

/**
 * Item class - Representation of a single item
 * @author Sergio Vasquez
 */
public class Item implements Cloneable {
    /** The name of the item */
    private String name;
    /**
     * Constructor - Create an item with the specified name
     * @param n sets the name of the item
     */
    public Item(String n) {
        name = n;
    }

    /**
     * Constructor - Creates an item from an other item
     * @param other the item to be copied from
     */
   public Item(Item other){
        if(other != null){
            name = other.name;
        }
    }


    /**
     * Draws the item to the screen
     * @param g graphic object
     * @param x the x position
     * @param y the y position
     */
    public void draw(Graphics g, int x, int y){
       g.setColor(Color.BLACK);
       String character = name.substring(0,1);
       g.drawString(character, x, y);
    }
    /**
     * Clones object's content and makes new one
     * @return new object with content of the cloned object
     */
    public Item clone(){
       return new Item(this);
    }

    /**
     * Retrieve the item's name
     * @return the item's name
     */
    public String getName() {
        return name;
    }

}
