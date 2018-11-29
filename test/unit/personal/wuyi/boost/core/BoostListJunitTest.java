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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hamcrest.collection.IsMapContaining;
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
		
		assertThat(newMap, IsBoostMapContaining.hasEntry("AAA", 1));
		assertThat(newMap, IsBoostMapContaining.hasEntry("BBB", 1));
		assertThat(newMap, IsBoostMapContaining.hasEntry("ZZZ", 1));
		assertThat(newMap, IsBoostMapContaining.hasEntry("CCC", 1));
		assertThat(newMap, IsBoostMapContaining.hasEntry("EEE", 1));
		assertThat(newMap, IsBoostMapContaining.hasEntry("FFF", 1));
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
		
		assertThat(newMap, IsBoostMapContaining.hasEntry("AAA", "111"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("AAA", "222"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("AAA", "333"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("AAA", "444"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("AAA", "555"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("AAA", "666"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("AAA", "777"));
		
		assertThat(newMap, IsBoostMapContaining.hasEntry("BBB", "111"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("BBB", "222"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("BBB", "333"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("BBB", "444"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("BBB", "555"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("BBB", "666"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("BBB", "777"));
		
		assertThat(newMap, IsBoostMapContaining.hasEntry("CCC", "111"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("CCC", "222"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("CCC", "333"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("CCC", "444"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("CCC", "555"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("CCC", "666"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("CCC", "777"));
		
		assertThat(newMap, IsBoostMapContaining.hasEntry("EEE", "111"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("EEE", "222"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("EEE", "333"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("EEE", "444"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("EEE", "555"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("EEE", "666"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("EEE", "777"));
		
		assertThat(newMap, IsBoostMapContaining.hasEntry("FFF", "111"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("FFF", "222"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("FFF", "333"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("FFF", "444"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("FFF", "555"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("FFF", "666"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("FFF", "777"));
		
		assertThat(newMap, IsBoostMapContaining.hasEntry("ZZZ", "111"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("ZZZ", "222"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("ZZZ", "333"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("ZZZ", "444"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("ZZZ", "555"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("ZZZ", "666"));
		assertThat(newMap, IsBoostMapContaining.hasEntry("ZZZ", "777"));
	}
	
	@Test
	public void foreachTest() {
		List<String> stringList = new ArrayList<>();
		
		BoostList<String> list = buildBoostList1(); 
		list.foreach(new VoidFunction<String>() {
			public void call(String str) {
				stringList.add(str + "#");
			}
		});
		
		assertThat(stringList, contains("AAA#", "BBB#", "ZZZ#", "CCC#", "CCC#", "CCC#", "EEE#", "FFF#", "FFF#"));
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
		
		assertThat(result, IsMapContaining.hasEntry("AAA", 1L));
		assertThat(result, IsMapContaining.hasEntry("CCC", 3L));
		assertThat(result, IsMapContaining.hasEntry("BBB", 1L));
		assertThat(result, IsMapContaining.hasEntry("EEE", 1L));
		assertThat(result, IsMapContaining.hasEntry("FFF", 2L));
		assertThat(result, IsMapContaining.hasEntry("ZZZ", 1L));
	}
	
	@Test
	public void takeTest() {
		BoostList<String> list = buildBoostList3();
		
		Assert.assertEquals(0, list.take(-10).size());
		Assert.assertEquals(0, list.take(0).size());
		assertThat(list.take(3), contains("111", "222", "333"));
		assertThat(list.take(20), contains("111", "222", "333", "444", "555", "666", "777"));
	}
}
