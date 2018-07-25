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
