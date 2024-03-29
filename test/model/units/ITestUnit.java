package model.units;


import model.items.*;
import model.items.healing.Staff;
import model.items.spellbooks.Darkness;
import model.items.spellbooks.Light;
import model.items.spellbooks.Spirit;
import model.items.weapons.*;
import model.map.Field;
import model.units.carriers.Alpaca;
import org.junit.jupiter.api.Test;

/**
 * Interface that defines the common behaviour of all the test for the units classes
 *
 * @author Ignacio Slater Muñoz
 * @since 1.0
 */
public interface ITestUnit {

  /**
   * Set up the game field
   */
  void setField();

  /**
   * Set up the main unit that's going to be tested in the test set
   */
  void setTestUnit();

  void setTargetAlpaca();

  /**
   * Creates a set of testing weapons
   */
  void setWeapons();

  /**
   * Checks that the constructor works properly.
   */
  @Test
  void constructorTest();

  /**
   * @return the current unit being tested
   */
  IUnit getTestUnit();

  /**
   * Checks if the axe is equipped correctly to the unit
   */
  @Test
  void equipAxeTest();

  /**
   * Tries to equip a weapon to the alpaca and verifies that it was not equipped
   *
   * @param item
   *     to be equipped
   */
  void checkEquippedItem(IEquipableItem item);

  /**
   * @return the test axe
   */
  Axe getAxe();

  @Test
  void equipSwordTest();

  /**
   * @return the test sword
   */
  Sword getSword();

  @Test
  void equipSpearTest();

  /**
   * @return the test spear
   */
  Spear getSpear();

  @Test
  void equipStaffTest();

  /**
   * @return the test staff
   */
  Staff getStaff();

  @Test
  void equipBowTest();

  /**
   * @return the test bow
   */
  Bow getBow();

  /**
   * Checks if the unit moves correctly
   */
  @Test
  void testMovement();

  /**
   * @return the test field
   */
  Field getField();

  /**
   * @return the target Alpaca
   */
  Alpaca getTargetAlpaca();

  /**
   * @return the test darkness book
   */
  Darkness getDarknessBook();

  @Test
  void equipDarknessBookTest();

  /**
   * @return the test light book.
   */
  Light getLightBook();

  @Test
  void equipLightBookTest();

  /**
   * @return the test light book
   */
  Spirit getSpiritBook();

  @Test
  void equipSpiritBookTest();

  @Test
  void testHasEquippedItem();

  @Test
  void testIsAbleToAttack();

  /**
   * @return the current unit being tested with an item equipped.
   */
  IUnit getEquippedTestUnit();

  @Test
  void testIsAlive();

  @Test
  void testGiveItem();

  @Test
  void testHealing();

  @Test
  void testCombatState();

  @Test
  void alpacaAttackTest();

  @Test
  void clericAttackTest();

}
