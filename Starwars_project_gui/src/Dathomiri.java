/**
 * Dathomoriri class - represents single instance of a Dathomiri
 */
public class Dathomiri extends Enemy {
    /**
     * Dathomomirir constructor - constructs single instance of a Dathomirir
     * @param level the level of the the Dathomiri
     * @param maxHp the max health of the Dathomiri
     * @param item the item of the Dathomiri
     */
    public Dathomiri(int level, int maxHp, Item item) {
        super("Dathomiri", level, maxHp, item);
    }

    /**
     * How the Dathomiri attacks
     * @param e the entity to be attacked
     */
    @Override
    public int attack(Entity e) {
        return super.attack(e);
    }
}
