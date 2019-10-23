package model.factory;

import model.items.IEquipableItem;
import model.map.Location;
import model.tactician.Tactician;
import model.units.IUnit;

/**
 * Units Factory
 * Creates instances of units with its respective parameters.
 * @version 2.2
 * @since 2.2
 * @author Bryan Ortiz P
 */
public interface IUnitsFactory {

  /**
   * Creates a new instance of unit
   * @param hp max hitPoints
   * @param movement how much it can moves
   * @param location where it will be placed at the begining
   * @param owner of the unit
   * @param items of the unit
   * @return the unit
   */
  IUnit createUnit(int hp, int movement, Location location, Tactician owner, IEquipableItem... items);

  /**
   * Creates a generic instance of unit
   * @param location where the unit will be set
   * @param owner of the unit
   * @return the unit
   */
  IUnit createGenericUnit(Location location, Tactician owner);

  /**
   * Creates a instance of unit with high levels of hit points and low movement
   * @param location where the unit will be set
   * @param owner of the unit
   * @return the unit
   */
  IUnit createTankUnit(Location location, Tactician owner);

  /**
   * Creates a instance of unit with high movement and low hit points
   * @param location where the unit will be set
   * @param owner of the unit
   * @return the unit
   */
  IUnit createFastUnit(Location location, Tactician owner);

}
