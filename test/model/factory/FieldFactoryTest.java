package model.factory;

import model.map.Field;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldFactoryTest {

  @Test
  public void createMapTest(){
    FieldFactory factory = new FieldFactory();
    long seed = new Random().nextLong();
    int size = Math.abs(new Random().nextInt())%5+3;
    Field map1 = factory.createMap(seed, size, false);
    Field map2 = factory.createMap(seed, size, false);
    assertTrue(map1.isConnected());
    assertEquals(size, map1.getSize());
    assertTrue(map2.isConnected());
    assertEquals(size, map2.getSize());
    assertTrue(map1.equals(map2));
  }
}
