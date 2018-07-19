package personal.wuyi.boost.entity;

/**
 * JavaRDD style implementation of key-value pair.
 * 
 * @author  Wuyi Chen
 * @date    06/22/2018
 * @version 1.1
 * @since   1.1
 *
 * @param <K>
 * @param <V>
 */
public class BoostPair<K, V> {
	private K key;
	private V value;
	
	/**
	 * Construct a {@code BoostPair}.
	 * 
	 * @param  k
	 *         The key.
	 *         
	 * @param  v
	 *         The value.
	 *         
	 * @since   1.1
	 */
	public BoostPair(K k, V v) {
		this.key = k;
		this.value = v;
	}
	
	public K getKey()   { return this.key;   }
	public V getValue() { return this.value; }
	
	/**
	 * Swap the order of key and value.
	 * 
	 * @return  The new {@code BoostPair} with reversed key and value.
	 * 
	 * @since   1.1
	 */
	public BoostPair<V, K> swap() {
		return new BoostPair<V, K>(this.value, this.key);
	}
}
