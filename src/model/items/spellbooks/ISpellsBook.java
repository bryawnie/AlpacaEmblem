package model.items.spellbooks;

import model.items.IEquipableItem;
import model.units.IUnit;

/**
 * This interface represents the <i>spells books</i> that magic units of the game can use.
 * <p>
 * The signature for all the common methods of the spells books are defined here.
 * Every book have a base damage and is strong or weak against other type of books and weapons.
 *
 * @author Bryan Ortiz P.
 * @since 1.1
 */
public interface ISpellsBook extends IEquipableItem {

    /**
     * Throws a spell against a target
     * @param target who will receive the spell
     */
    void throwSpell(IUnit target);
}
