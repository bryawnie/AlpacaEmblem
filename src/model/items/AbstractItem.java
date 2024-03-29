package model.items;

import model.units.IUnit;

/**
 * Abstract class that defines some common information and behaviour between all items.
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 * @version 2.2
 */
public abstract class AbstractItem implements IEquipableItem {

  private final String name;
  private int power;
  private int maxRange;
  private int minRange;
  private IUnit owner;

  /**
   * Constructor for a default item without any special behaviour.
   *
   * @param name
   *     the name of the item
   * @param power
   *     the power of the item (this could be the amount of damage or healing the item does)
   * @param minRange
   *     the minimum range of the item
   * @param maxRange
   *     the maximum range of the item
   */
  public AbstractItem(final String name, final int power, final int minRange, final int maxRange) {
    this.name = name;
    this.power = power;
    this.minRange = Math.max(minRange, 1);
    this.maxRange = Math.max(maxRange, this.minRange);
  }

  @Override
  public IUnit getOwner() {
    return owner;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getPower() {
    return power;
  }

  @Override
  public int getMinRange() {
    return minRange;
  }

  @Override
  public int getMaxRange() {
    return maxRange;
  }

  @Override
  public boolean isReachable(double distance){
    return minRange <= distance && maxRange >= distance;
  }

  @Override
  public void setOwner(IUnit owner){
    this.owner = owner;
  }

  @Override
  public void setMaxRange(int value){
    maxRange = value;
  }

  @Override
  public void setMinRange(int value){
    minRange = value;
  }

  @Override
  public void takeInStrongAttack(double power){
    getOwner().modifyCurrentHitPoints(- power*1.5);
  }

  @Override
  public void takeInWeakAttack(double power){
    getOwner().modifyCurrentHitPoints(Math.min(- power + 20, 0));
  }

  @Override
  public void takeInNormalAttack(double power){
    getOwner().modifyCurrentHitPoints(- power);
  }
}
