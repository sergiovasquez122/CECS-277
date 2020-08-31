import java.util.Random;

/**
 * Enemy Class - Single instance of an enemy
 * @author Sergio Vasquez
 */
public abstract class Enemy extends Entity {
    /** The item that the enemy is holding */
    private Item item;
    /**
     * Creates an enemy with the specific attributes
     * @param name  The name of the enemy
     * @param level The level of the enemy
     * @param maxHp The level of the enemy
     * @param item  The item of the enemy
     */
    public Enemy(String name, int level, int maxHp, Item item) {
        super(name, level, maxHp);
        this.item = item;
    }

    /**
     * Calculates the damage to an enemy
     * @param e the entity to attack
     * @return the calculated damage
     */
    @Override
    public int attack(Entity e) {
        // Damage range is between [CONSTANT_DAMAGE, e.getLevel() + CONSTANT_DAMAGE]
        final int CONSTANT_DAMAGE = 1;
        int randomDamage = new Random().nextInt(e.getLevel() + 1);
        int attackPower = randomDamage + CONSTANT_DAMAGE;
        e.takeDamage(attackPower);
        return attackPower;
    }
    /**
     * Retrieve the enemy item
     * @return the enemy item
     */
    Item getItem() {
        return item;
    }

}
