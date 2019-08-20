package model.units;

import java.util.List;
import model.items.IEquipableItem;
import model.map.Location;

/**
 * This interface represents all units in the game.
 * <p>
 * The signature of all the common methods that a unit can execute are defined here. All units
 * except some special ones can carry at most 3 weapons.
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public interface IUnit {

  /**
   * Sets the currently equipped item of this unit.
   *
   * @param item
   *     the item to equip
   */
  void equipItem(IEquipableItem item);

  /**
   * @return hit points of the unit
   */
  double getCurrentHitPoints();

  /**
   * @return the items carried by this unit
   */
  List<IEquipableItem> getItems();

  /**
   * @return the currently equipped item
   */
  IEquipableItem getEquippedItem();

  /**
   * @param item
   *     the item to be equipped
   */
  void setEquippedItem(IEquipableItem item);

  /**
   * @return the current location of the unit
   */
  Location getLocation();

  /**
   * Sets a new location for this unit,
   */
  void setLocation(final Location location);

  /**
   * @return the number of cells this unit can move
   */
  int getMovement();

  /**
   * Moves this unit to another location.
   * <p>
   * If the other location is out of this unit's movement range, the unit doesn't move.
   */
  void moveTo(Location targetLocation);

  //--

  /**
   * @return the maximum amount of items that the unit can carry.
   */
  int getMaxItems();

  /**
   * Adds an item to be carried by the unit
   * @param item
   */
  void addItem(IEquipableItem item);

  /**
   * Removes an item from the inventory
   * @param item
   */
  void removeItem(IEquipableItem item);

  /**
   * Transfers the item from player to receptor only if
   * 1- The receptor didn't reach te maximum amount of items
   * 2- The units are at 1 of distance
   * @param receptor
   * @param item
   */
  void giveItem(IUnit receptor, IEquipableItem item);

  /**
   * The unit receives damage (-) or healing (+)
   * @param value
   */
  void modifyCurrentHitPoints(double value);

  /**
   * @return the maximum hit points of the unit
   */
  double getMaxHitPoints();

  /**
   * Attacks another unit with the equipped item
   * @param target
   */
  void attack(IUnit target);

  /**
   * The unit is not alive anymore and is out of combat
   */
  void die();

  /**
   * @return if the unit is alive
   */
  boolean isAlive();

  /**
   * The unit is now in combat
   */
  void startCombat();

  /**
   * Both the Unit and the enemy are in combat together
   * @param enemy the other fighter
   */
  void startCombatWith(IUnit enemy);

  /**
   * The unit is not in combat
   */
  void endCombat();

  /**
   * The combat between the unit and the enemy has finished
   * @param enemy the other fighter
   */
  void endCombatWith(IUnit enemy);

  /**
   * @param target who would receive the attack
   * @return if the unit is able to attack the target considering its equipped item
   * its distance, if its cleric, and if the fighters are alive.
   */
  boolean isAbleToAttack(IUnit target);
}
