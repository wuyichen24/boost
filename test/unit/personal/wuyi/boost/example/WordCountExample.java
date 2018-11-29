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

package personal.wuyi.boost.example;

import java.util.Arrays;
import java.util.Map.Entry;

import personal.wuyi.boost.api.FlatMapFunction;
import personal.wuyi.boost.api.Function2;
import personal.wuyi.boost.api.PairFunction;
import personal.wuyi.boost.core.BoostList;
import personal.wuyi.boost.core.BoostMap;
import personal.wuyi.boost.entity.BoostPair;

public class WordCountExample {
	public static void main(String[] args) {
		BoostList<String> list = new BoostList<String>();
		list.add("apple tree car king");
		list.add("open apple window car");
		list.add("high apple tall king team");
		
		BoostList<String> newList = list.flatMap(new FlatMapFunction<String, String>() {
			  public Iterable<String> call(String s) { 
			  		return Arrays.asList(s.split(" ")); 
			  }
		});
		
		BoostMap<String, Integer> newMap = newList.mapToPair(new PairFunction<String, String, Integer>() {
			public BoostPair<String, Integer> call(String s) {
				return new BoostPair<String, Integer>(s, 1);
			}
		});
		
		BoostMap<String, Integer> resultMap = newMap.reduceByKey(new Function2<Integer, Integer, Integer>() {
			public Integer call(Integer i1, Integer i2) {
				return i1 + i2; 
			}
		});
		
		for (Entry<String, Integer> entry : resultMap.entries()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
}
