package personal.wuyi.boost.core;

import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import personal.wuyi.boost.api.Function2;
import personal.wuyi.boost.api.PairFunction;
import personal.wuyi.boost.entity.BoostPair;

/**
 * JavaRDD style implementation of {@code Multimap}.
 * 
 * <p>This implementation takes advantage of {@code Multimap}, it allows to 
 * return multiple values of same key. It is simplify the logic thinking of 
 * the solution of reduceByKey.
 * 
 * @author  Wuyi Chen
 * @date    06/21/2018
 * @version 1.1
 * @since   1.1
 *
 * @param <K>
 * @param <V>
 */
public class BoostMap<K, V> extends BoostMultimap<K,V> {
	/**
	 * Constructs an empty {@code BoostMap}.
	 * 
	 * @since   1.1
	 */
	public BoostMap () {
		super();
	}
	
	/**
	 * Merge the values for each key using an associative reduce function.
	 * 
	 * @param  obj
	 *         The anonymous inner class for generating the reduced value.
	 *         
	 * @return  The new {@code BoostMap}.
	 * 
	 * @since   1.1
	 */
	public BoostMap<K, V> reduceByKey(Function2<V, V, V> obj) {
		BoostMap<K,V> newMap = new BoostMap<>();
		Set<K> keys = this.keySet();
		for (K key : keys) {
			List<V> values = this.get(key);
			if (values.size() == 1) {
				newMap.put(key, values.get(0));
			} else {
				V reducedValue = values.get(0);
				for (int i=1 ; i < values.size() ; i++) {
					reducedValue = obj.call(reducedValue, values.get(i));
				}
				newMap.put(key, reducedValue);
			}
		}
		return newMap;
	}
	
	/**
	 * Return a new BoostMap by applying a function to all elements of this 
	 * {@code BoostMap}.
	 * 
	 * @param  obj
	 *         The anonymous inner class for processing each pair in the 
	 *         {@code BoostMap}.
	 * 
	 * @return  The new {@code BoostMap}.
	 * 
	 * @since   1.1
	 */
	public <T, R> BoostMap<T, R> mapToPair(PairFunction<BoostPair<K, V>, T, R> obj) {
		BoostMap<T,R> newMap = new BoostMap<>();
		for (Entry<K, V> entry : this.entries()) {
			BoostPair<T, R> pair = obj.call(new BoostPair<K, V> (entry.getKey(), entry.getValue()));
			newMap.put(pair.getKey(), pair.getValue());
		}
		return newMap;
	}
}
