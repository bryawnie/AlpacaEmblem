package model.items;

import model.items.spellbooks.Darkness;
import model.items.spellbooks.Light;
import model.items.spellbooks.Spirit;
import model.items.weapons.Bow;
import model.units.IUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractTestWeapon extends AbstractTestItem {

  @Test
  public void takeInDarknessSpellTest(){
    Darkness darkness = new Darkness("darkness book", 10, 1,2);
    IEquipableItem item = getTestItem();
    IUnit unit = getTestUnit();
    unit.addItem(item);
    item.equipTo(unit);

    item.takeInDarknessSpell(darkness);
    assertEquals(unit.getMaxHitPoints() - 15, unit.getCurrentHitPoints());
  }

  @Test
  public void takeInSpiritSpellTest(){
    Spirit spirit = new Spirit("spirit book", 10, 1,2);
    IEquipableItem item = getTestItem();
    IUnit unit = getTestUnit();
    unit.addItem(item);
    item.equipTo(unit);

    item.takeInSpiritSpell(spirit);
    assertEquals(unit.getMaxHitPoints() - 15, unit.getCurrentHitPoints());
  }

  @Test
  public void takeInLightSpellTest(){
    Light light = new Light("light book", 10, 1,2);
    IEquipableItem item = getTestItem();
    IUnit unit = getTestUnit();
    unit.addItem(item);
    item.equipTo(unit);

    item.takeInLightSpell(light);
    assertEquals(unit.getMaxHitPoints() - 15, unit.getCurrentHitPoints());
  }

  @Test
  public void takeInPhysicalAttackTest(){
    Bow bow = new Bow("bow", 10, 1,2);
    IEquipableItem item = getTestItem();
    IUnit unit = getTestUnit();
    unit.addItem(item);
    item.equipTo(unit);

    item.takeInPhysicalAttack(bow);
    assertEquals(unit.getMaxHitPoints() - 10, unit.getCurrentHitPoints());
  }
}
