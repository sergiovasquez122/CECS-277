/**
 * Decorator class - represent single instance of a Decorator
 */
public abstract class Decorator extends Enemy {
    /** The enemy to be decorated */
    private Enemy enemy;

    /**
     * Decorator Constructor - constructs single instance of a Decorator
     * @param e The enemy to decorate
     * @param title the title of the deocorator
     * @param healthIncrement the amount to increase the enemy health
     */
    public Decorator(Enemy e, String title, int healthIncrement){
        super(title, e.getLevel(), e.getMaxHP() + healthIncrement, e.getItem());
        this.enemy = e;
    }

    @Override
    public int attack(Entity e){
        return enemy.attack(e);
    }
}
