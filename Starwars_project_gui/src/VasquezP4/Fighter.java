/**
 * Fighter class - single representation of Fighter
 */
public class Fighter extends Decorator{

    /**
     * Fighter constructor - single instance of a fighter
     * @param e the enemy to decorate
     */
    public Fighter(Enemy e){
        super(e, e.getName().contains("Fighter") ? e.getName() : e.getName() + " Fighter", 1);
    }

    /**
     * How the fighter attacks an entity
     * @param e the enemy to be attacked
     */
    @Override
    public int attack(Entity e) {
        int initialDamage = super.attack(e);
        int additionalDamage = 1;
        e.takeDamage(additionalDamage);
        return initialDamage + additionalDamage;
    }
}
