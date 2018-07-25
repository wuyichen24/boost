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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import personal.wuyi.boost.api.FlatMapFunction;
import personal.wuyi.boost.api.Function;
import personal.wuyi.boost.api.PairFunction;
import personal.wuyi.boost.api.VoidFunction;
import personal.wuyi.boost.core.BoostList;
import personal.wuyi.boost.core.BoostMap;
import personal.wuyi.boost.entity.BoostPair;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test class for {@code BoostList}.
 * 
 * @author  Wuyi Chen
 * @date    06/22/2018
 * @version 1.1
 * @since   1.1
 */
public class BoostListJunitTest {
	public BoostList<String> buildBoostList1() {
		BoostList<String> list = new BoostList<String>();
		list.add("AAA");
		list.add("BBB");
		list.add("ZZZ");
		list.add("CCC");
		list.add("CCC");
		list.add("CCC");
		list.add("EEE");
		list.add("FFF");
		list.add("FFF");
		return list;
	}
	
	public BoostList<String> buildBoostList2() {
		BoostList<String> list = new BoostList<String>();
		list.add("apple,tree,car,king");
		list.add("open,door,window,run");
		list.add("high,small,tall,computer,team");
		return list;
	}
	
	public BoostList<String> buildBoostList3() {
		BoostList<String> list = new BoostList<String>();
		list.add("111");
		list.add("222");
		list.add("333");
		list.add("444");
		list.add("555");
		list.add("666");
		list.add("777");
		return list;
	}
	
	public void basicTest() {
		BoostList<String> list = buildBoostList1();
		for (String str : list) {
			System.out.println(str);
		}
	}
	
	@Test
	public void filterTest() {
		BoostList<String> list = buildBoostList1();
		
		BoostList<String> newList = list.filter(new Function<String, Boolean>() {
			public Boolean call(String str) {
				if (str.equalsIgnoreCase("CCC")) {
					return false;
				} else {
					return true;
				}
			}
		});
		
		assertThat(newList, hasSize(6));
		assertThat(newList, contains("AAA", "BBB", "ZZZ", "EEE", "FFF", "FFF"));
	}
	
	@Test
	public void mapTest() {
		BoostList<String> list = buildBoostList3();
		
		BoostList<Integer> newList = list.map(new Function<String, Integer>() {
			  public Integer call(String s) { 
			  		return Integer.parseInt(s) + 1; 
			  }
		});
		
		assertThat(newList, hasSize(7));
		assertThat(newList, contains(112, 223, 334, 445, 556, 667, 778));
	}
	
	@Test
	public void flatMapTest() {
		BoostList<String> list = buildBoostList2();
		
		BoostList<String> newList = list.flatMap(new FlatMapFunction<String, String>() {
			  public Iterable<String> call(String s) { 
			  		return Arrays.asList(s.split(",")); 
			  }
		});
		
		assertThat(newList, hasSize(13));
		assertThat(newList, contains("apple", "tree", "car", "king", "open", "door", "window", "run", "high", "small", "tall", "computer", "team"));
	}
	
	@Test
	public void mapToPairListTest() {
		BoostList<String> list = buildBoostList1();
		
		BoostMap<String, Integer> newMap = list.mapToPair(new PairFunction<String, String, Integer>() {
			public BoostPair<String, Integer> call(String s) {
				return new BoostPair<String, Integer>(s, 1);
			}
		});
		
		// No proper assertion tools for BoostMap, just use print for now.
		for (Entry<String, Integer> entry : newMap.entries()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	@Test
	public void distinctTest() {
		BoostList<String> list = buildBoostList1();
		BoostList<String> newList = list.distinct();
		
		assertThat(newList, hasSize(6));
		assertThat(newList, contains("AAA", "BBB", "ZZZ", "CCC", "EEE", "FFF"));
	}
	
	@Test
	public void unionTest() {
		BoostList<String> list  = buildBoostList1();
		BoostList<String> other = buildBoostList3();
		BoostList<String> newList = list.union(other);
		
		assertThat(newList, hasSize(16));
		assertThat(newList, contains("AAA", "BBB", "ZZZ", "CCC", "CCC", "CCC", "EEE", "FFF", "FFF", "111", "222", "333", "444", "555", "666", "777"));
	}
	
	@Test
	public void intersectionTest() {
		BoostList<String> list  = new BoostList<String>();
		list.add("AAA");
		list.add("BBB");
		list.add("CCC");
		
		BoostList<String> other = new BoostList<String>();
		other.add("CCC");
		other.add("BBB");
		other.add("EEE");
		
		BoostList<String> newList = list.intersection(other);
		
		assertThat(newList, hasSize(2));
		assertThat(newList, containsInAnyOrder("BBB", "CCC"));
	}
	
	@Test 
	public void subtractTest() {
		BoostList<String> list  = new BoostList<String>();
		list.add("AAA");
		list.add("BBB");
		list.add("CCC");
		
		BoostList<String> other = new BoostList<String>();
		other.add("CCC");
		other.add("BBB");
		other.add("EEE");
		
		BoostList<String> newList = list.subtract(other);
		
		assertThat(newList, hasSize(1));
		assertThat(newList, containsInAnyOrder("AAA"));
	}
	
	@Test
	public void cartesianTest() {
		BoostList<String> list  = buildBoostList1();
		BoostList<String> other = buildBoostList3();
		BoostMap<String, String> newMap = list.cartesian(other);
		
		// No proper assertion tools for BoostMap, just use print for now.
		for (Entry<String, String> entry : newMap.entries()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	@Test
	public void foreachTest() {
		BoostList<String> list = buildBoostList1(); 
		list.foreach(new VoidFunction<String>() {
			public void call(String str) {
				System.out.println(str);
			}
		});
	}
	
	@Test
	public void collectTest() {
		BoostList<String> list = buildBoostList1();
		List<String> newList = list.collect();
		
		assertThat(newList, hasSize(9));
		assertThat(newList, contains("AAA", "BBB", "ZZZ", "CCC", "CCC", "CCC", "EEE", "FFF", "FFF"));
	}
	
	@Test
	public void countTest() {
		BoostList<String> list = buildBoostList1();
		long size = list.count();
		
		Assert.assertEquals(9L, size);
	}
	
	@Test 
	public void countByValue() {
		BoostList<String> list = buildBoostList1();
		Map<String, Long> result = list.countByValue();
		
		// No proper assertion tools for BoostMap, just use print for now.
		for (Entry<String, Long> entry : result.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	
}
