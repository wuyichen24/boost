package personal.wuyi.boost.entity;

import java.util.Map.Entry;

import com.google.common.base.Objects;

/**
 * The wrapper class for the entry of BoostMultimap.
 * 
 * <p>This class is the combination of {@code AbstractMapEntry} and 
 * {@code ImmutableEntry}.
 * 
 * @author  Wuyi Chen
 * @date    06/20/2018
 * @version 1.1
 * @since   1.1
 * 
 * @see com.google.common.collect.AbstractMapEntry
 * @see com.google.common.collect.AbstractMapEntry
 *
 * @param <K>
 * @param <V>
 */
public class BoostEntry<K, V> implements Entry<K, V>  {
	final K key;
	final V value;

	/**
	 * Construct a {@code BoostEntry}.
	 * 
	 * @param  key
	 *         The key
	 * 
	 * @param  value
	 *         The value
	 *         
	 * @since   1.1
	 */
	public BoostEntry(K key, V value) {
	    this.key = key;
	    this.value = value;
	}

	@Override
	public final K getKey() {
	    return key;
	}

	@Override
	public final V getValue() {
	    return value;
	}

	@Override
	public final V setValue(V value) {
	    throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object object) {
	    if (object instanceof Entry) {
	    	Entry<?, ?> that = (Entry<?, ?>) object;
	    	return Objects.equal(this.getKey(), that.getKey()) && Objects.equal(this.getValue(), that.getValue());
	    }
	    return false;
	}

	@Override 
	public int hashCode() {
	    K k = getKey();
	    V v = getValue();
	    return ((k == null) ? 0 : k.hashCode()) ^ ((v == null) ? 0 : v.hashCode());
	}

	@Override 
	public String toString() {
		return getKey() + "=" + getValue();
	}
}
