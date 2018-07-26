package personal.wuyi.boost.core;

import java.util.Arrays;
import java.util.Map.Entry;

import org.junit.Test;

import personal.wuyi.boost.api.FlatMapFunction;
import personal.wuyi.boost.api.Function2;
import personal.wuyi.boost.api.PairFunction;
import personal.wuyi.boost.entity.BoostPair;

public class WordCountExample {
	@Test
	public void wordCount() {
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
