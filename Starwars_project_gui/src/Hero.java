import java.awt.*;
import java.util.ArrayList;

/**
 * Hero Class - Representation of a Hero
 * @author Sergio Vasquez
 */
public class Hero extends Entity implements Force {
    /** Inventory of the Hero */
    private ArrayList<Item> items;
    /** The map that the current currently is in */
    private Map map;
    /** The current position of the Hero */
    private Point location;

    /** The bounds for the inventory drawing */
    private Rectangle rectangles[];

    /**
     * Constructor - Constructs a hero at the specified map
     * @param name the name of the hero
     * @param map  the current map
     */
    public Hero(String name, Map map) {
        // Default the hero level is one and their maxHp is 15
        super(name, 1, 15);
        items = new ArrayList<>();
        location = map.findStart();
        this.map = map;
        final int RECTANGLE_SIZE = 50;
        // create the rectangles where they are being displayed for the game
        final int maximum_inventory_size = 5;
        final int Y_OFFSET_FROM_ORIGIN = 150;
        rectangles = new Rectangle[maximum_inventory_size];
        for (int i = 0; i < maximum_inventory_size; ++i) {
            rectangles[i] = new Rectangle(RECTANGLE_SIZE*(i+1) ,Y_OFFSET_FROM_ORIGIN,RECTANGLE_SIZE,RECTANGLE_SIZE);
        }
    }

    /**
     * Attacks an entity with a blaster attack
     * @param e the entity to attack
     * @return the calculated damage
     */
    public int attack(Entity e) {
        // By default hero will use the blaster
        final int BLASTER_DAMAGE = 3;
        int attackDamage = BLASTER_DAMAGE + getLevel();
        e.takeDamage(attackDamage);
        return attackDamage;
    }
    /**
     * Display the attributes of the hero
     */
    public void display() {
        super.display();
    }
    /**
     * Display the inventory of the hero
     */
    public void displayItems() {
        System.out.println("Inventory:");
        for ( int i = 0; i < items.size(); ++i ) {
            System.out.println((i + 1) + ": " + items.get(i).getName());
        }
    }
    /**
     * Retrieve the amount of items the hero is carrying
     * @return the amount of items the hero is carrying
     */
    public int getNumItems() {
        return items.size();
    }
    /**
     * Pick up an item
     * @param i the item to be picked up
     * @return true if an item was picked up
     */
    public boolean pickUpItem(Item i) {
        // Five is the max amount of items that the hero can carry
        if ( items.size() == 5 ) {
            return false;
        }
        items.add(i);
        return true;
    }
    /**
     * Remove a item with the given name
     * @param name the name of the item to be removed
     */
    public void removeItem(String name) {
        for ( int i = 0; i < items.size(); ++i ) {
            Item item = items.get(i);
            if ( name.equals(item.getName()) ) {
                items.remove(i);
                // Exit method so that only one item is removed if there are duplicates
                return;
            }
        }
    }
    /**
     * Remove the item at the indicated index
     * @param index the index of the item to be removed
     */
    public void removeItem(int index) {
        items.remove(index);
    }
    /**
     * Checks if the hero has a Med Kit
     * @return true if the hero has a Med Kit
     */
    public boolean hasMedKit() {
        for ( Item item : items ) {
            String itemName = item.getName();
            if ( itemName.equals("Med Kit") ) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if the hero has a key
     * @return true if the hero has a key
     */
    public boolean hasKey() {
        for ( Item item : items ) {
            String itemName = item.getName();
            if ( itemName.equals("Key") ) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if the hero has armor
     * @return true if the hero has armor
     */
    public boolean hasArmor() {
        for ( Item item : items ) {
            String itemName = item.getName();
            if ( itemName.equals("Helmet") || itemName.equals("Shield") || itemName.equals("Chestplate") ) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if the hero has a Holocron
     * @return true if the hero has a Holocron
     */
    public boolean hasHolocron() {
        for ( Item item : items ) {
            String itemName = item.getName();
            if ( itemName.equals("Holocron") ) {
                return true;
            }
        }
        return false;
    }
    /**
     * Return the hero's location
     * @return the Point indicating the hero's location
     */
    public Point getLocation() {
        return location;
    }
    /**
     * Move the hero to the north
     * @return the character to the north of the hero
     */
    public char goNorth() {
        // Try to move north if not possible then move back to where you were
        try {
            map.reveal(location);
            int x = location.x - 1;
            location = new Point(x, location.y);
            return map.getCharAtLoc(location);
        } catch (IndexOutOfBoundsException e) {
            int x = location.x + 1;
            location = new Point(x, location.y);
            return map.getCharAtLoc(location);
        }
    }
    /**
     * Move the hero the south
     * @return the character to the south of the hero
     */
    public char goSouth() {
        // Try to move south if not possible then move back to where you were
        try {
            map.reveal(location);
            int x = location.x + 1;
            location = new Point(x, location.y);
            return map.getCharAtLoc(location);
        } catch (IndexOutOfBoundsException e) {
            int x = location.x - 1;
            location = new Point(x, location.y);
            return map.getCharAtLoc(location);
        }
    }
    /**
     * Move the hero to the east
     * @return the character to the east of the hero
     */
    public char goEast() {
        try {
            // Try to move east if not possible then move back to where you were
            map.reveal(location);
            int y = location.y + 1;
            location = new Point(location.x, y);
            return map.getCharAtLoc(location);
        } catch ( IndexOutOfBoundsException e ) {
            int y = location.y - 1;
            location = new Point(location.x, y);
            return map.getCharAtLoc(location);
        }
    }
    /**
     * Move the hero to the west
     * @return the character to the west of the hero
     */
    public char goWest() {
        try {
            // Try to move West if not possible then move back to where you were
            map.reveal(location);
            int y = location.y - 1;
            location = new Point(location.x, y);
            return map.getCharAtLoc(location);
        } catch ( IndexOutOfBoundsException e ) {
            int y = location.y + 1;
            location = new Point(location.x, y);
            return map.getCharAtLoc(location);
        } }
    /**
     * Perform a force push
     * @return the attack power of the force push
     */
    @Override
    public int forcePush() {
        double prob = Math.random();
        final double THRESHOLD = 0.5;
        /* Force Choke is medium-risk medium-reward attack
         * deal medium damage if doesn't pass threshold of success */
        if (Double.compare(prob, THRESHOLD) < 0) {
            final int MEDIUM_DAMAGE = 3;
            return MEDIUM_DAMAGE * getLevel();
        } else {
            final int HIGH_DAMAGE = 5;
            return HIGH_DAMAGE * getLevel();
        }
    }
    /**
     * Perform a force choke
     * @return the attack power of the force choke
     */
    @Override
    public int forceChoke() {
        final int MULTIPLER = 2;
        return MULTIPLER * getLevel();
    }
    /**
     * Remove the first item in the hero inventory
     * @return the name of the item removed, null string if no item exists
     */
    public String removeFirstArmorItem(){
        for( int i = 0;i < items.size();++i ){
            Item item = items.get(i);
            if( item.getName().equals("Chestplate") || item.getName().equals("Helmet") || item.getName().equals("Shield") ){
                items.remove(i);
                return item.getName();
            }
        }
        return null;
    }

    /**
     * Draw the hero to the screen
     * @param g graphic object
     */
    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        final int RECTANGLE_SIZE = 50;
        g.drawString("Character information", 0,25);
        g.drawString("Name: " + getName(),0,50);
        g.drawString("Health: " + getHP() + "/" + getMaxHP(),0,75);
        g.drawString("Level: " + getLevel(),0,100);
        g.drawString("Inventory: " ,0, 125);
        for(int i = 0;i < 5;++i){
            g.setColor(Color.WHITE);
            g.fillRect(RECTANGLE_SIZE * (i + 1), 150,RECTANGLE_SIZE,RECTANGLE_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(RECTANGLE_SIZE * (i+1), 150,RECTANGLE_SIZE,RECTANGLE_SIZE);
        }
        int center_of_rectangle = RECTANGLE_SIZE / 2;
        for(int i = 0;i < items.size();++i){
            Item item = items.get(i);
            item.draw(g, RECTANGLE_SIZE * (i + 1) + center_of_rectangle,150 + center_of_rectangle);
        }
    }

    public Rectangle[] getRectangle(){
        return rectangles;
    }
    /**
     * Perform a force slam
     * @return the attack power of the force slam
     */
    @Override
    public int forceSlam() {
        int damage = (int) (Math.random() * getLevel()) + 1;
        double prob = Math.random();
        final double THRESHOLD = 0.5;
        /* Force Slam is high-risk high-reward attack
         * deal minimal damage if doesn't pass threshold of success */
        if ( Double.compare(prob, THRESHOLD) < 0 ) {
            final int LOW_DAMAGE = 1;
            return LOW_DAMAGE * getLevel();
        } else {
            return damage * getLevel();
        }
    }
}
