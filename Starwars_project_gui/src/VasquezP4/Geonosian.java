/**
 * Geonosian class - represents single instance of Geonosian object
 */
public class Geonosian extends Enemy {
    /**
     * Geonosian constructor - constructs single instance of Geonosian constructor
     * @param level
     * @param maxHp
     * @param item
     */
    public Geonosian(int level, int maxHp, Item item) {
        super("Geonosian", level, maxHp, item);
    }

    /**
     * calculate the damage
     * @param e the entity to attack
     * @return the amount of damage
     */
    @Override
    public int attack(Entity e) {
        return super.attack(e);
    }
}
