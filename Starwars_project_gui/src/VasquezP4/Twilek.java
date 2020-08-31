/**
 * Twi'lek class - represents single representation of a Twi'lek
 */
public class Twilek extends Enemy {
    /**
     * Twilke constructor - creates single instance of Twilek
     * @param level the level of the twilek
     * @param maxHp the max health of the twilek
     * @param item the item of the twilek
     */
    public Twilek(int level, int maxHp, Item item) {
        super("Twilek", level, maxHp, item);
    }

    /**
     * The attack of the Twilek
     * @param e the entity to be attacked
     */
    @Override
    public int attack(Entity e) {
        return super.attack(e);
    }
}
