package model.units.warriors;

import model.items.healing.Staff;
import model.items.spellbooks.Darkness;
import model.items.spellbooks.Light;
import model.items.spellbooks.Spirit;
import model.items.weapons.Axe;
import model.items.IEquipableItem;
import model.items.weapons.Bow;
import model.items.weapons.Spear;
import model.items.weapons.Sword;
import model.map.Location;
import model.units.AbstractUnit;
import model.units.IUnit;

/**
 * This class represents a fighter type unit.
 * A fighter is a unit that can only use axe type weapons.
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public class Fighter extends AbstractUnit {

  public Fighter(final int hitPoints, final int movement, final Location location,
      IEquipableItem... items) {
    super(hitPoints, movement, location, 3, items);
  }

  @Override
  public void equipStaff(Staff staff) {
    // Fighter can't equip this item
  }

  @Override
  public void equipDarknessBook(Darkness darkness) {
    // Fighter can't equip this item
  }

  @Override
  public void equipLightBook(Light light) {
    // Fighter can't equip this item
  }

  @Override
  public void equipSpiritBook(Spirit spirit) {
    // Fighter can't equip this item
  }

  @Override
  public void equipAxe(Axe axe) {
    if (getItems().contains(axe)){
      setEquippedItem(axe);
    }
  }

  @Override
  public void equipBow(Bow bow) {
    // Fighter can't equip this item
  }

  @Override
  public void equipSpear(Spear spear) {
    // Fighter can't equip this item
  }

  @Override
  public void equipSword(Sword sword) {
    // Fighter can't equip this item
  }

  @Override
  public void useItemOn(IUnit target) {
    if(canUseItemOn(target)){
      startCombatWith(target);
      if(target.hasEquippedItem()){
        getEquippedItem().useOn(target);
      } else {
        target.modifyCurrentHitPoints(- getEquippedItem().getPower() );
      }
      target.counterAttack(this);
    }
  }

  @Override
  public void counterAttack(IUnit aggressor) {
    if(canUseItemOn(aggressor)){
      getEquippedItem().useOn(aggressor);
    }
    endCombatWith(aggressor);
  }

}
