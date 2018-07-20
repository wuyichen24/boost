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

import java.util.Map.Entry;

import org.junit.Test;

import personal.wuyi.boost.api.Function2;
import personal.wuyi.boost.api.PairFunction;
import personal.wuyi.boost.core.BoostMap;
import personal.wuyi.boost.entity.BoostPair;

/**
 * Test class for {@code BoostMap}.
 * 
 * @author  Wuyi Chen
 * @date    06/22/2018
 * @version 1.1
 * @since   1.1
 */
public class BoostMapJunitTest {
	public BoostMap<String, Integer> buildBoostMap1() {
		BoostMap<String, Integer> map = new BoostMap<String, Integer>();
		map.put("DDD", 12);
		map.put("CCC", 16);
		map.put("AAA", 10);
		map.put("KKK", 23);
		map.put("BBB", 16);
		return map;
	}
	
	@Test
	public void reduceByKeyTest() {
		BoostMap<String, Integer> map = new BoostMap<String, Integer>();
		
		map.put("AAA", 1);
		map.put("AAA", 1);
		map.put("AAA", 1);
		map.put("BBB", 1);
		map.put("BBB", 1);
		map.put("CCC", 1);
		map.put("DDD", 1);
		map.put("DDD", 1);
		map.put("DDD", 1);
		map.put("DDD", 1);
		map.put("DDD", 1);

		BoostMap<String, Integer> newMap = map.reduceByKey(new Function2<Integer, Integer, Integer>() {
			public Integer call(Integer i1, Integer i2) {
				return i1 + i2; 
			}
		});
		
		// No proper assertion tools for BoostMap, just use print for now.
		for (Entry<String, Integer> entry : newMap.entries()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	@Test
	public void mapToPairPairTest() {
		BoostMap<String, Integer> map = buildBoostMap1();
		
		BoostMap<Integer, String> newMap = map.mapToPair(new PairFunction<BoostPair<String, Integer>, Integer, String>() {
			public BoostPair<Integer, String> call(BoostPair<String, Integer> pair) {
				return pair.swap();
			}
		});
		
		// No proper assertion tools for BoostMap, just use print for now.
		for (Entry<Integer, String> entry : newMap.entries()) {
			System.out.println(entry.getKey().toString() + " - " + entry.getValue());
		}
	}
	
	@Test
	public void sortByKeyTest() {
		BoostMap<String, Integer> map = buildBoostMap1();
		
		for (Entry<String, Integer> entry : map.entries()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
		System.out.println();
		
		map.sortByKey();
		
		// No proper assertion tools for BoostMap, just use print for now.
		for (Entry<String, Integer> entry : map.entries()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
}
