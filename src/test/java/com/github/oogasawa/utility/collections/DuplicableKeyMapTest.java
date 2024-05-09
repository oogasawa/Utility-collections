package com.github.oogasawa.utility.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DuplicableKeyMapTest {

    private static final Logger logger = LoggerFactory.getLogger(DuplicableKeyMapTest.class.getName());

    
    @Test
    @Order(1)
    public void testPut() {

        DuplicableKeyMap<String, String> map = new DuplicableKeyMap<>();

        map.put("M12674", "taxon:9606");
        map.put("M12674", "taxon:0000");
        map.put("M12674", "taxon:0000");

        assertTrue(map.containsKey("M12674"));
        ArrayList<String> values = map.getValueList("M12674");
        assertTrue(values.contains("taxon:9606"));
        assertTrue(values.contains("taxon:0000"));
        assertEquals(3, values.size());
    }



}
