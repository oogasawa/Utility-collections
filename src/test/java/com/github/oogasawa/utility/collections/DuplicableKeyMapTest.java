package com.github.oogasawa.utility.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DuplicableKeyMapTest {

    private static final Logger logger = LoggerFactory.getLogger(DuplicableKeyMapTest.class.getName());

    
    @Test
    public void testPut() {

        DuplicableKeyMap<String, String> map = new DuplicableKeyMap<>();

        map.put("M12674", "taxon:9606");
        map.put("M12674", "taxon:0000");
        map.put("M12674", "taxon:0000");

        assertTrue(map.containsKey("M12674"));
        List<String> values = map.getValueList("M12674");
        assertTrue(values.contains("taxon:9606"));
        assertTrue(values.contains("taxon:0000"));
        assertEquals(3, values.size());
    }


    
    @Test
    public void testPutUniquePair() {

        DuplicableKeyMap<String, String> map = new DuplicableKeyMap<>();

        map.putUniquePair("M12674", "taxon:9606");
        map.putUniquePair("M12674", "taxon:0000");
        // If the same key-value pair is put, it is not put.
        map.putUniquePair("M12674", "taxon:0000");

        assertTrue(map.containsKey("M12674"));
        List<String> values = map.getValueList("M12674");
        assertTrue(values.contains("taxon:9606"));
        assertTrue(values.contains("taxon:0000"));
        assertEquals(2, values.size());

        

        // If key is null, NullPointerException is issued.
        assertThrows(NullPointerException.class, () -> {
            map.putUniquePair(null, "taxon:0000");
        });
        // If value is null, NullPointerException is issued.
        assertThrows(NullPointerException.class, () -> {
            map.putUniquePair("M12674", null);
        });
    }


    

    @Test
    public void testGetValueList() {

        DuplicableKeyMap<String, String> map = new DuplicableKeyMap<>();

        map.put("M12674", "taxon:9606");
        map.put("M12674", "taxon:0000");
        map.put("M12674", "taxon:0000");

        List<String> values = map.getValueList("M12674");
        assertTrue(values.contains("taxon:9606"));
        assertTrue(values.contains("taxon:0000"));

        // If a non-existent key is given, null is returned.
        values = map.getValueList("unknown_key");
        assertNull(values);
        
        
    }


    @Test
    public void testContainsKey() {

        DuplicableKeyMap<String, String> map = new DuplicableKeyMap<>();

        map.put("M12674", "taxon:9606");
        map.put("M12674", "taxon:0000");
        map.put("M12674", "taxon:0000");

        assertTrue(map.containsKey("M12674"));
        assertFalse(map.containsKey("unknown"));
        
    }



    @Test
    public void testGet() {

        DuplicableKeyMap<String, String> map = new DuplicableKeyMap<>();

        map.put("M12674", "taxon:9606");
        map.put("M12674", "taxon:0000");
        map.put("M12674", "taxon:0000");

        String value = map.get("M12674");
        logger.info(value);
        assertTrue(value.equals("taxon:9606") || value.equals("taxon:0000"));

        // If a non-existent key is given, RuntimeException is issued.
        assertThrows(RuntimeException.class, () -> {
            map.get("unknown");
        });
        
    }

    
}
