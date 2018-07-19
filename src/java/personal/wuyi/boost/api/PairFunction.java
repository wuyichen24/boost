package personal.wuyi.boost.api;

import personal.wuyi.boost.entity.BoostPair;

/**
 * A one-argument function that takes one record and return a {@code BoostPair} record.
 * 
 * @author  Wuyi Chen
 * @date    06/20/2016
 * @version 1.1
 * @since   1.1
 *
 * @param <E>
 * @param <K>
 * @param <V>
 */
public interface PairFunction<E, K, V> {
	public BoostPair<K,V> call(E f);
}
