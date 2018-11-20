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

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.hamcrest.collection.IsIterableContainingInOrder;
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
		
		assertThat(newMap, IsBoostMapContaining.hasEntry("AAA", 3));
		assertThat(newMap, IsBoostMapContaining.hasEntry("BBB", 2));
		assertThat(newMap, IsBoostMapContaining.hasEntry("CCC", 1));
		assertThat(newMap, IsBoostMapContaining.hasEntry("DDD", 5));
	}
	
	@Test
	public void mapToPairPairTest() {
		BoostMap<String, Integer> map = buildBoostMap1();
		
		BoostMap<Integer, String> newMap = map.mapToPair(new PairFunction<BoostPair<String, Integer>, Integer, String>() {
			public BoostPair<Integer, String> call(BoostPair<String, Integer> pair) {
				return pair.swap();
			}
		});
		
		assertThat(newMap, IsBoostMapContaining.hasEntry(12, "DDD"));
		assertThat(newMap, IsBoostMapContaining.hasEntry(16, "CCC"));
		assertThat(newMap, IsBoostMapContaining.hasEntry(10, "AAA"));
		assertThat(newMap, IsBoostMapContaining.hasEntry(23, "KKK"));
		assertThat(newMap, IsBoostMapContaining.hasEntry(16, "BBB"));
	}
	
	@Test
	public void sortByKeyTest() {
		BoostMap<String, Integer> map = buildBoostMap1();
		
		map.sortByKey();
		
		Set<String> keySet = map.keySet();
		List<String> keyList = new ArrayList<>(keySet);
		assertThat(keyList, IsIterableContainingInOrder.contains("AAA", "BBB", "CCC", "DDD", "KKK"));
	}
}
