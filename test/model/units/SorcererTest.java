package model.units;

import model.units.magic.Sorcerer;
import model.units.warriors.Archer;
import model.units.warriors.Hero;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Bryan Ortiz P
 */
public class SorcererTest extends AbstractTestUnit {

  private Sorcerer sorcerer;

  /**
   * Set up the main unit that's going to be tested in the test set
   */
  @Override
  public void setTestUnit() {
    sorcerer = new Sorcerer(50, 2, field.getCell(0, 0));
  }

  /**
   * @return the current unit being tested
   */
  @Override
  public IUnit getTestUnit() {
    return sorcerer;
  }

  @Test
  @Override
  public void equipDarknessBookTest() {
    assertNull(sorcerer.getEquippedItem());
    sorcerer.addItem(darkness);
    darkness.equipTo(sorcerer);
    assertEquals(darkness, sorcerer.getEquippedItem());
  }

  @Test
  @Override
  public void equipLightBookTest() {
    assertNull(sorcerer.getEquippedItem());
    sorcerer.addItem(light);
    light.equipTo(sorcerer);
    assertEquals(light, sorcerer.getEquippedItem());
  }

  @Test
  @Override
  public void equipSpiritBookTest() {
    assertNull(sorcerer.getEquippedItem());
    sorcerer.addItem(spirit);
    spirit.equipTo(sorcerer);
    assertEquals(spirit, sorcerer.getEquippedItem());
  }

  @Test
  public void testNormalAttack(){
    sorcerer.addItem(spirit);
    spirit.equipTo(sorcerer);
    sorcerer.attack(getTargetAlpaca());
    double expectedHP = getTargetAlpaca().getMaxHitPoints()-spirit.getPower();
    double currentHP = getTargetAlpaca().getCurrentHitPoints();
    assertEquals(expectedHP,currentHP);
  }

  @Test
  public void testStrongAttack(){
    sorcerer.addItem(darkness);
    darkness.equipTo(sorcerer);

    Sorcerer target = new Sorcerer(50, 2, field.getCell(1, 1));
    target.addItem(spirit);
    spirit.equipTo(target);

    sorcerer.attack(target);
    double expectedSorcererHP = target.getMaxHitPoints()-darkness.getPower()*1.5;
    double currentSorcererHP = target.getCurrentHitPoints();
    assertEquals(expectedSorcererHP,currentSorcererHP);


    Archer archer = new Archer(50, 2, field.getCell(0, 2));
    archer.addItem(bow);
    bow.equipTo(archer);

    sorcerer.attack(archer);
    double expectedArcherHP = archer.getMaxHitPoints()-darkness.getPower()*1.5;
    double currentArcherHP = archer.getCurrentHitPoints();
    assertEquals(expectedArcherHP, currentArcherHP);
  }

  @Override
  public IUnit getEquippedTestUnit() {
    sorcerer.addItem(light);
    light.equipTo(sorcerer);
    return sorcerer;
  }
}