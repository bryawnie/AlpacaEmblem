package model.tatician;

import model.factory.items.DarknessFactory;
import model.factory.items.SpearFactory;
import model.factory.items.SwordFactory;
import model.factory.units.AlpacaFactory;
import model.factory.units.HeroFactory;
import model.items.healing.Staff;
import model.items.spellbooks.Darkness;
import model.items.spellbooks.Light;
import model.items.spellbooks.Spirit;
import model.items.weapons.Axe;
import model.items.weapons.Bow;
import model.items.weapons.Spear;
import model.items.weapons.Sword;
import model.map.Field;
import model.map.Location;
import model.tactician.Tactician;
import model.units.carriers.Alpaca;
import model.units.healers.Cleric;
import model.units.magic.Sorcerer;
import model.units.warriors.Archer;
import model.units.warriors.Fighter;
import model.units.warriors.Hero;
import model.units.warriors.SwordMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TacticianTest {

  // Units
  private Alpaca alpaca;
  private Archer archer;
  private Cleric cleric;
  private Hero hero;
  private Fighter fighter;
  private Sorcerer sorcerer;
  private SwordMaster swordMaster;

  // Items
  private Axe axe;
  private Bow bow;
  private Darkness darkness;
  private Light light;
  private Spear spear;
  private Spirit spirit;
  private Staff staff;
  private Sword sword;

  // Map
  private Field map;
  private Tactician player;

  @BeforeEach
  public void setUp(){
    map = new Field();
    map.addCells(true, new Location(0,0), new Location(0,1),
            new Location(0,2), new Location(0,3), new Location(0,4),
            new Location(1,0), new Location(1,1), new Location(1,2),
            new Location(1,3), new Location(1,4), new Location(2,0),
            new Location(2,1), new Location(2,2), new Location(2,3),
            new Location(2,4), new Location(3,0), new Location(3,1),
            new Location(3,2), new Location(3,3), new Location(3,4),
            new Location(4,0), new Location(4,1), new Location(4,2),
            new Location(4,3), new Location(4,4));
  }

  @Test
  public void selfTest(){
    player = new Tactician("Super Slater",map);
    assertEquals("Super Slater", player.getName());
    assertEquals(map, player.getField());
    assertEquals(0, player.getUnits().size());
    player.setUnitsFactory(new AlpacaFactory());
    player.addGenericUnit(map.getCell(0,0));
    assertEquals(1, player.getUnits().size());
    player.selectUnit(player.getUnits().get(0));
    assertEquals(player.getUnits().get(0), player.getSelectedUnit());
    player.moveUnitTo(1,0);
    assertEquals(player.getSelectedUnit(), map.getCell(1,0).getUnit());

    player.unselectUnit();
    assertNull(player.getSelectedUnit());
    player.setName("Ultra Slater");
    assertEquals("Ultra Slater", player.getName());

  }

  @Test
  public void getUnitsTest(){
    player = new Tactician("Dave el Barbaro",map);
    player.setUnitsFactory(new HeroFactory());
    player.addGenericUnit(map.getCell(0,1));
    player.setItemsFactory(new SpearFactory());
    player.selectUnit(player.getUnits().get(0));
    player.addPowerfulItem("Lanza del metro");
    player.equipItem(player.getItemInInventoryByIndex(0));
    assertEquals(player.getItemInInventoryByIndex(0), player.getEquippedItem());
    assertEquals(player.getSelectedUnit().getEquippedItem(), player.getEquippedItem());
  }

  @Test
  public void unSelectTest(){
    player = new Tactician("Doggo",map);
    player.setUnitsFactory(new AlpacaFactory());
    player.addGenericUnit(map.getCell(0,0));
    player.selectUnit(player.getUnits().get(0));
    assertEquals(player.getUnits().get(0), player.getSelectedUnit());
    player.unselectUnit();
    assertNull(player.getSelectedUnit());
  }

  @Test
  public void addNewUnitAndItemsTest(){
    player = new Tactician("Doggo",map);
    player.setUnitsFactory(new AlpacaFactory());
    player.addNewUnit(100,3, map.getCell(0,0));
    player.addFastUnit( map.getCell(0,0) );
    player.selectUnit(player.getUnits().get(0));
    assertEquals(player.getUnits().get(0), player.getSelectedUnit());
    assertEquals(1, player.getUnits().size());
    player.setItemsFactory(new SwordFactory());
    player.addGenericItem("La HeZkal!Vur");
    player.setItemsFactory(new SpearFactory());
    player.addLongDistanceItem("Kevin");
    player.setItemsFactory(new DarknessFactory());
    player.addNewItem("Guia de vida de Bart Simpson",20,1,10);
    assertEquals(3 ,player.getItems().size());

    player.selectUnitFromUnitsByIndex(0);
    assertEquals(player.getSelectedUnit(), player.getUnits().get(0));
  }

  @Test
  public void addUnitInNonEmptyCellTest(){
    player = new Tactician("Edward Elric",map);
    player.setUnitsFactory(new AlpacaFactory());
    player.addGenericUnit(map.getCell(0,0));
    assertEquals( player.getUnits().get(0), map.getCell(0,0).getUnit() );
    player.addFastUnit(map.getCell(0,0));
    assertEquals(1,player.getUnits().size());
    assertEquals( player.getUnits().get(0),map.getCell(0,0).getUnit() );
  }



}
