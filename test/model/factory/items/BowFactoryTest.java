package model.factory.items;

import model.factory.AbstractItemFactoryTest;
import model.factory.IItemsFactory;
import model.items.IEquipableItem;
import model.items.weapons.Bow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowFactoryTest {

  private IItemsFactory bowF;

  @BeforeEach
  public void setUp(){
    bowF = new BowFactory();
  }

  public IEquipableItem getCreatedGenericItem() {
    return bowF.createGenericItem("uwu");
  }


  public IEquipableItem getCreatedPowerfulItem() {
    return bowF.createPowerfulItem("awa");
  }


  public IEquipableItem getCreatedLongDistanceItem() {
    return bowF.createLongDistanceItem("owo");
  }

  @Test
  public void typeTest() {
    assertEquals(getCreatedGenericItem().getClass(), Bow.class);
    assertEquals(getCreatedPowerfulItem().getClass(), Bow.class);
    assertEquals(getCreatedLongDistanceItem().getClass(), Bow.class);
  }

  @Test
  public void createdItemsTest(){
    assertEquals(15,getCreatedGenericItem().getPower());
    assertEquals(2, getCreatedGenericItem().getMinRange());
    assertEquals(8, getCreatedGenericItem().getMaxRange());
    assertEquals(20,getCreatedPowerfulItem().getPower());
    assertEquals(2, getCreatedPowerfulItem().getMinRange());
    assertEquals(6, getCreatedPowerfulItem().getMaxRange());
    assertEquals(10,getCreatedLongDistanceItem().getPower());
    assertEquals(2, getCreatedLongDistanceItem().getMinRange());
    assertEquals(10, getCreatedLongDistanceItem().getMaxRange());
  }
}