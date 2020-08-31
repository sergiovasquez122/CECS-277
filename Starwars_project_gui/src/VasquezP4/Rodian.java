/**
 * Rodian class - Represents single instance of a Rodian
 */
public class Rodian extends Enemy {
    /**
     * Rodian constructor - represents single instance of a Rodian
     * @param level the level of a rodian
     * @param maxHp the max health of a rodian
     * @param item the item of the rodian
     */
    public Rodian(int level, int maxHp, Item item) {
        super("Rodian", level, maxHp, item);
    }

    /**
     * The attack of the rodian
     * @param e the entity to be attacked
     */
    @Override
    public int attack(Entity e) {
        return super.attack(e);
    }
}
