package personal.wuyi.boost.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.CoreMatchers.is;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import personal.wuyi.boost.core.BoostMultimap;

/**
 * Test class for {@code BoostMultimap}.
 * 
 * @author  Wuyi Chen
 * @date    06/22/2018
 * @version 1.1
 * @since   1.1
 */
public class BoostMultimapJunitTest {
	private BoostMultimap<String, Integer> map;
	
	@Before
	public void initialize() {
		map = new BoostMultimap<>();
		map.put("aaa", 1);
		map.put("aaa", 2);
		map.put("aaa", 3);
		map.put("bbb", 4);
		map.put("bbb", 5);
		map.put("bbb", 6);
	}
	
	@Test 
	public void getTest() {
		List<Integer> values = map.get("aaa");
		
		assertThat(values, hasSize(3));
		assertThat(values, contains(1, 2, 3));
	}
	
	@Test
	public void putTest() {
		map.put("ccc", 9);
		assertThat(map.size(), is(7));
	}
	
	@Test
	public void sizeTest() {
		Assert.assertEquals(6, map.size());
		map.put("ccc", 9);
		Assert.assertEquals(7, map.size());
	}
	
	@Test
	public void isEmptyTest() {
		Assert.assertFalse(map.isEmpty());
	}
	
	@Test 
	public void containsKeyTest() {
		Assert.assertTrue(map.containsKey("aaa"));
		Assert.assertFalse(map.containsKey("eee"));
	}
	
	@Test 
	public void containsValueTest() {
		Assert.assertTrue(map.containsValue(5));
		Assert.assertFalse(map.containsValue(20));
	}
	
	@Test
	public void containsEntryTest() {
		Assert.assertTrue(map.containsEntry("aaa", 1));
		Assert.assertFalse(map.containsEntry("aaa", 20));
		Assert.assertFalse(map.containsEntry("zzz", 1));
	}
	
	@Test
	public void removeTest() {
		map.remove("aaa", 1);
		Assert.assertFalse(map.containsEntry("aaa", 1));
		long size1 = map.size();
		map.remove("xxx", 99);
		long size2 = map.size();
		Assert.assertTrue(size1 == size2);
	}
	
	@Test
	public void removeAllTest() {
		map.removeAll("aaa");
		Assert.assertEquals(3, map.size());
	}
	
	@Test
	public void sortByKeyTest() {
		BoostMultimap<String, Integer> map = new BoostMultimap<>();
		
		map.put("ppp", 2);
		map.put("aaa", 1);
		map.put("zzz", 5);
		map.sortByKey();
		
		// No proper assertion tools for BoostMultimap, just use print for now.
		for(Entry<String, Integer> entry : map.entries()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	@Test
	public void keySetTest() {
		assertThat(map.keySet(), contains("aaa", "bbb"));
	}
	
	@Test
	public void entriesTest() {
		// No proper assertion tools for BoostMultimap, just use print for now.
		for(Entry<String, Integer> entry : map.entries()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	@Test
	public void iteratorTest() {
		Iterator<Entry<String,Integer>> iterator = map.entries().iterator();
		
		// No proper assertion tools for BoostMultimap, just use print for now.
		while (iterator.hasNext()) {
			Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>) iterator.next();
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
}
