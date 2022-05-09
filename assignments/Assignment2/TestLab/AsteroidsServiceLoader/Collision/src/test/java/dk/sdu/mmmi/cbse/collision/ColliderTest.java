/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author sk
 */
public class ColliderTest {
    
    public ColliderTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of Collides method, of class Collider.
     */
    @Test
    public void testCollides() {
        System.out.println("Collides");
        
        Entity entity = new Entity();
        PositionPart pos = new PositionPart(1,2,3);
        Entity entity2 = new Entity();
        PositionPart pos2 = new PositionPart(1,2,3);
        entity.setRadius(1);
        entity2.setRadius(1);
        entity.add(pos);
        entity2.add(pos2);
        
        Collider instance = new Collider();
        Boolean expResult = true;
        Boolean result = instance.Collides(entity, entity2);
        assertEquals(expResult, result);
    }
    
}
