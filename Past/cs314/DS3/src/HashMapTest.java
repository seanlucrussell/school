// Sean Russell
// Jason Gardner
// James Filben

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;


public class HashMapTest {
	
	private HashMap<String,String> map;
	
	@Before
	public void makeHashMap()
	{
		map = new HashMap<String,String>();
	}
	
	// TEST put()
	
	@Test
	public void testPut()
	{
		assertNull(map.put("a", "b"));
		assertEquals("{a=b}",map.toString());
	}
	
	@Test
	public void testPutValueAlreadyExists()
	{
		map.put("a", "b");
		assertEquals("b",map.put("a", "c"));
		assertEquals("{a=c}",map.toString());
	}
	
	// TEST get()
	
	@Test
	public void testGetValueExists()
	{
		map.put("a", "b");
		assertEquals("b",map.get("a"));
	}
	
	@Test
	public void testGetValueDoesNotExist()
	{
		assertNull(map.get("a"));
	}
	
	// TEST remove()
	
	@Test
	public void testRemoveValueExists()
	{
		map.put("a", "b");
		assertEquals("b",map.remove("a"));
		assertEquals("{}",map.toString());
	}

	@Test
	public void testRemoveValueDoesNotExist()
	{
		assertNull(map.remove("a"));
	}
	
	// TEST replace()
	
	@Test
	public void testReplaceValueExists()
	{
		map.put("a", "b");
		assertEquals("b",map.replace("a", "c"));
		assertEquals("{a=c}",map.toString());
	}
	
	@Test
	public void testReplaceValueDoesNotExist()
	{
		assertNull(map.replace("a", "b"));
	}
	
	// TEST isEmpty()
	
	@Test
	public void testIsEmptyFalse()
	{
		map.put("a", "b");
		assertFalse(map.isEmpty());
	}
	
	@Test
	public void testIsEmptyTrue()
	{
		assertTrue(map.isEmpty());
	}
	
	
	
}
