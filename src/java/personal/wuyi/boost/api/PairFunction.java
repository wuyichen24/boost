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
