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
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import personal.wuyi.boost.api.FlatMapFunction;
import personal.wuyi.boost.api.Function;
import personal.wuyi.boost.api.PairFunction;
import personal.wuyi.boost.api.VoidFunction;
import personal.wuyi.boost.entity.BoostPair;

/**
 * JavaRDD style implementation of {@code ArrayList}.
 * 
 * @author  Wuyi Chen
 * @date    06/21/2018
 * @version 1.1
 * @since   1.1
 * 
 * @see     Collection
 * @see     List
 * @see     LinkedList
 * @param <E>
 */
public class BoostList<E> extends ArrayList<E> {
	private static final long serialVersionUID = 4923284901849746049L;

	/**
	 * Constructs an empty {@code BoostList}.
	 * 
	 * @since   1.1
	 */
	public BoostList () {
		super();
	}
	
	/**
	 * Return a new {@code BoostList} containing only the elements that 
	 * satisfy a predicate.
	 * 
	 * @param  f
	 *         The anonymous inner class for checking each element in the 
	 *         {@code BoostList}.
	 *         
	 * @return  The new {@code BoostList}.
	 * 
	 * @since   1.1
	 */
	public BoostList<E> filter(Function<E, Boolean> f) {
		BoostList<E> newList = new BoostList<>();
		for (E element : this) {
			if (f.call(element)) {
				newList.add(element);
			}
		}
		return newList;
	}
	
	/**
	 * Return a new {@code BoostList} by applying a function to all elements 
	 * of this {@code BoostList}.
	 * 
	 * <p>This function has potential to replace for-loop.
	 * 
	 * @param  f
	 *         The anonymous inner class for operating each element in the 
	 *         {@code BoostList}.
	 *         
	 * @return  The new {@code BoostList}.
	 * 
	 * @since   1.1
	 */
	public <R> BoostList<R> map(Function<E, R> f) {
		BoostList<R> newList = new BoostList<>();
		for(E ele : this) {
			newList.add(f.call(ele));
		}
		return newList;
	}
	
	/**
	 * Return a new {@code BoostList} by first applying a function to all 
	 * elements of this {@code BoostList}, and then flattening the results.
	 * 
	 * @param  f
	 *         The anonymous inner class for operating each element in the 
	 *         {@code BoostList}.
	 *         
	 * @return  The new {@code BoostList}.
	 * 
	 * @since   1.1
	 */
	public <R> BoostList<R> flatMap(FlatMapFunction<E, R> f) {
		BoostList<R> newList = new BoostList<>();
		for (E ele1 : this) {
			Iterable<R> iterList = f.call(ele1);
			for (R ele2 : iterList) {
				newList.add(ele2);
			}
		}
		return newList;
	}
	
	/**
	 * Return a new {@code BoostMap} by applying a transforming function to 
	 * all elements of this {@code BoostList}.
	 * 
	 * @param  f
	 *         The anonymous inner class for operating each element in the 
	 *         {@code BoostList}.
	 * 
	 * @return  The new {@code BoostMap}.
	 * 
	 * @since   1.1
	 */
	public <K, V> BoostMap<K, V> mapToPair(PairFunction<E, K, V> f) {
		BoostMap<K,V> newMap = new BoostMap<>();
		for(E ele : this) {
			BoostPair<K, V> pair = f.call(ele);
			newMap.put(pair.getKey(), pair.getValue());
		}
		return newMap;
	}
	
	/**
	 * Remove duplicates.
	 * 
	 * @return  The new {@code BoostList}.
	 * 
	 * @since   1.1
	 */
	public BoostList<E> distinct() {
		BoostList<E> newList = new BoostList<>();
		for(E ele: this) {
			if (!newList.contains(ele)) {
				newList.add(ele);
			}
		}
		return newList;
	}
	
	/**
	 * Produce an {@code BoostList} containing elements from both. 
	 * {1,2,3}.union({3,4,5}) = {1,2,3,3,4,5}
	 * 
	 * @param  other
	 *         Another {@code BoostList} needs to be joined.
	 *         
	 * @return  The merged {@code BoostList}.
	 * 
	 * @since   1.1
	 */
	public BoostList<E> union(BoostList<E> other) {
		for (E ele : other) {
			this.add(ele);
		}
		return this;
	}
	
	/**
	 * {@code BoostList} containing only elements found in both 2 
	 * {@code BoostList}.
	 * {1,2,3}.intersection({3,4,5}) = {3} 
	 * 
	 * @param  other
	 *         Another {@code BoostList} needs to be intersected.
	 * 
	 * @return  The merged {@code BoostList}.
	 * 
	 * @since   1.1
	 */
	public BoostList<E> intersection(BoostList<E> other) {
		BoostList<E> newList = new BoostList<>();
		for(E ele : this) {
			if (other.contains(ele)) {
				newList.add(ele);
			}
		}
		return newList;
	}
	
	/**
	 * Remove the contents of one RDD (e.g.,remove training data).
	 * {1,2,3}.subtract({3,4,5}) = {1,2}
	 * 
	 * @param  other
	 *         Another {@code BoostList} needs to be referred.
	 * 
	 * @return  The new {@code BoostList}.
	 * 
	 * @since   1.1
	 */
	public BoostList<E> subtract(BoostList<E> other) {
		BoostList<E> newList = new BoostList<>();
		for(E ele : this) {
			if (!other.contains(ele)) {
				newList.add(ele);
			}
		}
		return newList;
	}
	
	/**
	 * Return the Cartesian product (all ordered pairs) of this 
	 * {@code BoostList} and another {@code BoostList}.
	 * 
	 * @param  other
	 *         Another {@code BoostList} needs to be operated.
	 * 
	 * @return  The new {@code BoostMap} which contains all the ordered pairs.
	 * 
	 * @since   1.1
	 */
	public <R> BoostMap<E,R> cartesian(BoostList<R> other) {
		BoostMap<E,R> newMap = new BoostMap<>();
		for(E ele1 : this) {
			for (R ele2 : other) {
				newMap.put(ele1, ele2);
			}
		}
		return newMap;
	}
	
	/**
	 * Applies a function f to all elements of this BoostList.
	 * 
	 * @param  f
	 *         The anonymous inner class for operating each element in the 
	 *         {@code BoostList}.
	 *         
	 * @since   1.1
	 */
	public void foreach(VoidFunction<E> f) {
		for(E ele : this) {
			f.call(ele);
		}
	}
	
	/**
	 * Return an array that contains all of the elements in this 
	 * {@code BoostList}.
	 * 
	 * @return  The new {@code ArrayList} which contains all of the elements.
	 * 
	 * @since   1.1
	 */
	public List<E> collect() {
		return new ArrayList<>(this);
	}
	
	/**
	 * Return the number of elements in the {@code BoostList}.
	 * 
	 * @return  The number of elements in the {@code BoostList}.
	 * 
	 * @since   1.1
	 */
	public Long count() {
		return (long) this.size();
	}
	
    /**
     * Return the count of each unique value in this {@code BoostList} as a 
     * map of (value, count) pairs.
     * 
     * @return  The map of the number of occurrences for each value.
     * 
	 * @since   1.1
     */
    public Map<E, Long> countByValue() {
    	Map<E,Long> newMap = new HashMap<>();
    	for (E ele : this) {
    		if (newMap.containsKey(ele)) {
    			newMap.put(ele, newMap.get(ele) + 1);
    		} else {
    			newMap.put(ele, 1L);
    		}
    	}
    	return newMap;
    }
    
    /**
     * Take the first N elements of this {@code BoostList}.
     * 
     * @param  num
     *         The number of elements will be taken.
     * 
     * @return  The new list contains N elements.
     * 
	 * @since   1.1
     */
    public List<E> take(int num) {
    	List<E> newList = new ArrayList<>();
    	if (num <= 0) {
    		return newList;
    	} else {
    		if (num < this.count()) {
    			for(int i = 0 ; i < num ; i++) {
    				newList.add(this.get(i));
    			}
    		} else if (num > this.count()) {
    			newList = new ArrayList<>(this);
    		}
    		return newList;
    	}
    }
}
