/*
 * Copyright 2018 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package personal.wuyi.boost.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
		
		Set<String> keySet = map.keySet();
		List<String> keyList = new ArrayList<>(keySet);
		assertThat(keyList, contains("aaa", "ppp", "zzz"));
	}
	
	@Test
	public void keySetTest() {
		assertThat(map.keySet(), contains("aaa", "bbb"));
	}
	
	@Test
	public void entriesTest() {
		Collection<Entry<String, Integer>> entryCollection = map.entries();
		Iterator<Entry<String,Integer>> iter = entryCollection.iterator();
		Entry<String, Integer> entry1 = iter.next();
		Assert.assertEquals("aaa",          entry1.getKey());
		Assert.assertEquals(new Integer(1), entry1.getValue());
		Entry<String, Integer> entry2 = iter.next();
		Assert.assertEquals("aaa",          entry2.getKey());
		Assert.assertEquals(new Integer(2), entry2.getValue());
		Entry<String, Integer> entry3 = iter.next();
		Assert.assertEquals("aaa",          entry3.getKey());
		Assert.assertEquals(new Integer(3), entry3.getValue());
		Entry<String, Integer> entry4 = iter.next();
		Assert.assertEquals("bbb",          entry4.getKey());
		Assert.assertEquals(new Integer(4), entry4.getValue());
		Entry<String, Integer> entry5 = iter.next();
		Assert.assertEquals("bbb",          entry5.getKey());
		Assert.assertEquals(new Integer(5), entry5.getValue());
		Entry<String, Integer> entry6 = iter.next();
		Assert.assertEquals("bbb",          entry6.getKey());
		Assert.assertEquals(new Integer(6), entry6.getValue());
	}
	
	@Test
	public void iteratorTest() {
		// test hasNext on an empty collection (returns false)
		BoostMultimap<String, Integer> emptyMap = new BoostMultimap<>();
		Iterator<Entry<String,Integer>> emptyMapItr = emptyMap.entries().iterator();
		Assert.assertFalse(emptyMapItr.hasNext());
		
		// test hasNext on a collection with one item (returns true, several times)
		BoostMultimap<String, Integer> nonEmptyMap1 = new BoostMultimap<>();
		nonEmptyMap1.put("aaa", 1);
		Iterator<Entry<String,Integer>> nonEmptyMapItr1 = nonEmptyMap1.entries().iterator();
		Assert.assertTrue(nonEmptyMapItr1.hasNext());
		Assert.assertTrue(nonEmptyMapItr1.hasNext());
		Assert.assertTrue(nonEmptyMapItr1.hasNext());
		Entry<String, Integer> mapEntry1 = nonEmptyMapItr1.next();
		Assert.assertEquals("aaa",          mapEntry1.getKey());
		Assert.assertEquals(new Integer(1), mapEntry1.getValue());
		
		// test remove on that collection: check size is 0 after
		nonEmptyMapItr1.remove();
		Assert.assertEquals(0, nonEmptyMap1.size());
		
		// test with a collection with several items, make sure the iterator goes through each item, in the correct order
		BoostMultimap<String, Integer> nonEmptyMap2 = new BoostMultimap<>();
		nonEmptyMap2.put("aaa", 1);
		nonEmptyMap2.put("bbb", 2);
		nonEmptyMap2.put("ccc", 3);
		nonEmptyMap2.put("ddd", 4);
		
		Iterator<Entry<String,Integer>> nonEmptyMapItr2 = nonEmptyMap2.entries().iterator();
		Entry<String, Integer> mapEntry2a = nonEmptyMapItr2.next();
		Assert.assertEquals("aaa",          mapEntry2a.getKey());
		Assert.assertEquals(new Integer(1), mapEntry2a.getValue());
		Entry<String, Integer> mapEntry2b = nonEmptyMapItr2.next();
		Assert.assertEquals("bbb",          mapEntry2b.getKey());
		Assert.assertEquals(new Integer(2), mapEntry2b.getValue());
		Entry<String, Integer> mapEntry2c = nonEmptyMapItr2.next();
		Assert.assertEquals("ccc",          mapEntry2c.getKey());
		Assert.assertEquals(new Integer(3), mapEntry2c.getValue());
		Entry<String, Integer> mapEntry2d = nonEmptyMapItr2.next();
		Assert.assertEquals("ddd",          mapEntry2d.getKey());
		Assert.assertEquals(new Integer(4), mapEntry2d.getValue());
		
		// remove all elements from the collection: collection is now empty
		Iterator<Entry<String,Integer>> nonEmptyMapItr3 = nonEmptyMap2.entries().iterator();
		nonEmptyMapItr3.next();
		nonEmptyMapItr3.remove();
		Assert.assertEquals(3, nonEmptyMap2.size());
		nonEmptyMapItr3.next();
		nonEmptyMapItr3.remove();
		Assert.assertEquals(2, nonEmptyMap2.size());
		nonEmptyMapItr3.next();
		nonEmptyMapItr3.remove();
		Assert.assertEquals(1, nonEmptyMap2.size());
		nonEmptyMapItr3.next();
		nonEmptyMapItr3.remove();
		Assert.assertEquals(0, nonEmptyMap2.size());
		Assert.assertTrue(nonEmptyMap2.isEmpty());
	}
}
